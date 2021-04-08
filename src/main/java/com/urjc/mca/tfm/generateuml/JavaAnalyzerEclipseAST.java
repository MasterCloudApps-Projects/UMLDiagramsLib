package com.urjc.mca.tfm.generateuml;

import com.urjc.mca.tfm.generateuml.model.Domain;
import com.urjc.mca.tfm.generateuml.model.Unit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
public class JavaAnalyzerEclipseAST {
    private final Logger LOG = LoggerFactory.getLogger(JavaAnalyzerEclipseAST.class);

    Set<String> aggregation = new HashSet<>();

    @Value("${primitives}")
    public Set<String> primitives;

    @Value("${objectBlackList}")
    public Set<String> objectsBlackList;

    @Value("${packageBlacklist}")
    public Set<String> packageBlacklist;

    @Value("${objectWhiteList}")
    public Set<String> objectWhiteList;

    @Value("#{${annotation.spring.background.color}}")
    Map<String, String> annotationSpringBackgroundColor;

    public Domain run(String path) {
        Domain domain = new Domain("domain");
        Optional<Path> hit;

        hit = Optional.ofNullable(Path.of(path));
        try (Stream<Path> entries
                     = Files.walk(Paths.get(hit.stream().findFirst().toString()).toAbsolutePath())) {
            entries.forEach(ruta -> {
                if (Files.isRegularFile(ruta)) {
                    try {
                        analyzed(domain, ruta.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            LOG.debug("context", e);
        }
        return domain;
    }

    public char[] readFileToString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            char[] buf = new char[10];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
        }

        return fileData.toString().toCharArray();
    }

    public void analyzed(Domain domain, String pathFile) throws IOException {

        Set<String> objectJavaAnalyzer = new HashSet<>(Arrays.asList("CompilationUnit", "TypeDeclaration",
                "SimpleName",
                "MethodDeclaration",
                "ClassInstanceCreation",
                "EnumDeclaration",
                "ParameterizedType",
                "Assignment",
                "VariableDeclarationFragment",
                "ExpressionStatement",
                "VariableDeclarationStatement",
                "PackageDeclaration"));
        objectsBlackList.addAll(objectJavaAnalyzer);
        objectsBlackList.addAll(primitives);
        Set<String> fields = new HashSet<>();
        Map<String, String> fields2 = new HashMap<>();
        aggregation = new HashSet<>();
        Set<String> units = new HashSet<>();

        //JAJAI
        Set<String> base = new HashSet<>();
        Set<String> associate = new HashSet<>();
        //composition
        Set<String> parts = new HashSet<>();

        //JAJAI
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setSource(readFileToString(pathFile));
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setBindingsRecovery(true);
        parser.setResolveBindings(true);
        parser.setStatementsRecovery(true);
        Map options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5); //or newer version
        parser.setCompilerOptions(options);

        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        cu.accept(new ASTVisitor() {

            String mypackage = "";

            //fields declaration
            public boolean visit(VariableDeclarationFragment node) {

                if (node.getParent() instanceof FieldDeclaration) {
                    fields.add(((FieldDeclaration) node.getParent()).getType().toString());
                    fields2.put(node.toString(), ((FieldDeclaration) node.getParent()).getType().toString());
                }
                return false; // do not continue to avoid usage info
            }

            public boolean visit(AnnotationTypeDeclaration node) {

                return true;
            }

            public boolean visit(AnnotationTypeMemberDeclaration node) {

                return true;
            }

            //declaración del método
            public boolean visit(MethodDeclaration unit) {
                if (!unit.isConstructor()) {
                    unit.parameters().stream()
                            .filter(unitNotInPrimitiveBlackList(true))
                            .filter(unitNotInObjectsBlackList(true))
                            .filter(unitNotInAggregationList())
                            .forEach(p -> {
                                //for dependency
                                if (isSimpleType(p)) {
                                    addUsed(obtainClass(p.toString()));
                                } else {
                                    addUsed(obtainClassFromList(obtainClass(p.toString())));
                                }
                            });
                } else {
                    //for associate
                    unit.parameters().stream()
                            .filter(unitNotInPrimitiveBlackList(true))
                            .filter(unitNotInObjectsBlackList(true))
                            .forEach(p -> {
                                //                                ((MethodDeclaration) ((SingleVariableDeclaration) p).getParent()).getBody().statements()
                                addAssociate(obtainClassFromList(obtainClass(p.toString())));
                            });
                }
                return true;
            }

            private boolean isSimpleType(Object p) {
                return ((SingleVariableDeclaration) p).getType().isSimpleType();
            }


            private boolean isConstructor(ClassInstanceCreation node) {
                Object object = node.getParent().getParent().getParent().getParent();
                return object instanceof MethodDeclaration && ((MethodDeclaration) object).isConstructor();
            }

            //Constructor
            public boolean visit(ClassInstanceCreation node) {
                String aux = node.getType().toString();
                if (isConstructor(node)) {
                    if (notExitsInPrimitiveList(aux) &&
                            //comprueba si es collection
                            !node.getType().isSimpleType()) {
                        aux = obtainClassFromList(aux);
                    }
                    //for composition
                    if (notExitsInObjectBlackList(aux)) {
                        addPart(aux);
                    }
                } else {
                    if (notExitsInObjectBlackList(aux)) {
                        if(!node.getType().isSimpleType())
                            aux = obtainClassFromList(aux);
                        addUsed(aux);
                    }
                }
                return true;
            }


            //Asignaciones
            public boolean visit(ExpressionStatement node) {
                String aux = node.toString();
                if (startWithUpperCase(aux)) {
                    //revistar porque esta esto para darle semantica
                    aux = extractClassWithMethods(aux);
                    if (notExitsInObjectBlackList(aux)) {
                        addUsed(aux);
                    }
                }
                return true;
            }

            private String extractClassWithMethods(String aux) {
                return aux.substring(0, aux.indexOf("."));
            }

            private boolean startWithUpperCase(String aux) {
                return Character.isUpperCase(aux.charAt(0));
            }

            public boolean visit(PackageDeclaration node) {
                mypackage = node.getName().toString();
                return true;
            }

            //for aggregation
            public boolean visit(ParameterizedType node) {
                node.typeArguments().stream()
                        .filter(unitNotInObjectsBlackList(false))
                        .forEach(t -> aggregation.add(t.toString()));

                return true;
            }

            public boolean visit(TypeDeclaration node) {
//                if (node.getName().toString().equals("JavaAnalyzer"))
//                    return false;
                //Aqui podría ver si tiene anotaciones la clase para ver si es component, repository, etc
                Unit unit = domain.getUnit(node.getName().toString());
                if (unit != null) {
                    unit.setMyPackage(mypackage);
                    addUnit(unit.name, null);
                } else {
                    addUnit(node.getName().toString(), mypackage);
                }

                node.modifiers().stream()
                        .filter(m -> annotationSpringBackgroundColor.containsKey(m.toString().substring(1)))
                        .forEach( m -> domain.getUnit(node.getName().toString()).addAnnotation(m.toString().substring(1)));
            //for base
                if (node.getSuperclassType() != null) {
                    addBase(node.getSuperclassType().toString());
                }
                node.superInterfaceTypes().forEach(i -> addBase(i.toString()));
                return true;
            }

            private void addUnit(String unit, String mypackage) {
                domain.addUnit(unit, mypackage);
                units.add(unit);
            }

            private void addAssociate(String p) {
                domain.addAssociate(p);
                units.add(p);
            }

            private void addBase(String base) {
                domain.addBase(base);
                units.add(base);
            }

            private void addPart(String aux) {
                domain.addPart(aux);
                units.add(aux);
            }

            private void addUsed(String aux) {
                domain.addUsed(aux);
                units.add(aux);
            }

            //dependency
            public boolean visit(VariableDeclarationStatement node) {
                String aux = node.getType().toString();
                if (notExitsInPrimitiveList(aux) && !node.getType().isSimpleType()) {
                    aux = obtainClassFromList(aux);
                }
                //for dependency
                if (notExitsInPrimitiveList(aux) && notExitsInObjectBlackList(aux) && notExitInAggregationList(aux)) {
                    addUsed(aux);
                }
                return true;
            }

            //Enumerados
            public boolean visit(EnumDeclaration node) {
                Unit unit = domain.getUnit(node.getName().toString());
                if (unit != null)
                    unit.setMyPackage(mypackage);
                else {
                    addUnit(node.getName().toString(), mypackage);
//                    domain.addUnit(node.getName().toString(), mypackage);
//                    units.add(node.getName().toString());
                }
                return true;
            }

            //asignaciones, ejemplo : composition = new Composition();
            //y con el ejemplo de arriba no funciona
            public boolean visit(Assignment node) {
                if (isMethodDeclaration(node) &&
                        isConstructor(node)) {
                    String unit = node.getRightHandSide().toString().split("\\.")[0];
                    if (startWithUpperCase(unit)) {
                        if (notExitsInObjectBlackList(unit)) {
                            addPart(unit);
                        }
                    }
                }
                return true;
            }

            private boolean isConstructor(Assignment node) {
                return ((MethodDeclaration) node.getRightHandSide().getParent().getParent().getParent().getParent()).isConstructor();
            }

            private boolean isMethodDeclaration(Assignment node) {
                return node.getRightHandSide().getParent().getParent().getParent().getParent() instanceof MethodDeclaration;
            }


            //Añadido para comprobar si me puede ayudar alguna que nos falta
            public boolean visit(AnonymousClassDeclaration node) {
                return true;
            }


            //block de una clase
            public boolean visit(Block node) {
                return true;
            }

            //cast
            public boolean visit(CastExpression node) {
                return true;
            }


            //toda la clase incluido el paquete , importaciones clase etc
            public boolean visit(CompilationUnit node) {
                return true;
            }

            // conditional ? :
            public boolean visit(ConditionalExpression node) {
                return true;
            }


            //imports
            public boolean visit(ImportDeclaration node) {
                return true;
            }

            //Lambda expresion
            public boolean visit(LambdaExpression node) {
                return true;
            }

            //anotación de la clase
            public boolean visit(MarkerAnnotation node) {
                return true;
            }


            //nombre del paquet cualificado
            public boolean visit(QualifiedName node) {
                return true;
            }

            //return
            public boolean visit(ReturnStatement node) {
                return true;
            }

            //tipo de clase que asignas en una asignación
            public boolean visit(SimpleName node) {
                return true;
            }

            //variable declaration expresion
            public boolean visit(VariableDeclarationExpression node) {
                return true;
            }


        });

        aggregation.stream().filter(unitNotInPrimitiveBlackList(false))
                .filter(unitNotInObjectsBlackList(false))
                .forEach(a -> {
                    if (!units.contains(a)) {
                        String unit = (String) a;
                        domain.addElement(unit);
                        units.add(unit);
                    }
                });

        fields.stream()
                .filter(unitNotInPrimitiveBlackList(false))
                .filter(unitNotInObjectsBlackList(false))
                .forEach(f -> {
                    if (!units.contains(f)) {
                        String unit = (String) f;
                        if (isArrayOrList(unit)) {
                            String aux = obtainClassFromList(unit);
                            //aqui no consigo entrar
                            //tiene pinta que estaba puesto por si guardaba Tipos con Collections
                            if (domain.getUnit(aux) == null && notExitsInObjectBlackList(aux)) {
                                domain.addAssociate(aux);
                                units.add(aux);
                            }
                            //aqui entro en el test de domain model y en el del proyecto
                        } else {
                            domain.addAssociate(unit);
                        }
                    }
                });
    }


    private boolean notExitInAggregationList(String aux) {
        return !aggregation.contains(aux);
    }

    private boolean notExitsInPrimitiveList(String aux) {
        return !primitives.contains(aux);
    }

    private boolean notExitsInObjectBlackList(String aux) {
        return !objectsBlackList.contains(aux);
    }

    private Predicate unitNotInAggregationList() {
        return p -> notExitInAggregationList(obtainClass(p.toString()));
    }

    private boolean isArrayOrList(String aux) {
        return aux.contains("<") || aux.contains("[");
    }

    private String obtainClassFromList(String aux) {
        if (aux.contains("[") && aux.contains("]")) {
            aux = aux.substring(0, aux.indexOf("["));
        } else if (aux.contains("<") && aux.contains(">")) {
            aux = aux.substring(aux.indexOf("<") + 1);
            aux = aux.substring(0, aux.indexOf(">"));
        }
        return aux;
    }

    Predicate unitNotInObjectsBlackList(boolean obtainClass) {
        if (obtainClass)
            return p -> notExitsInObjectBlackList(obtainClass(p.toString()));
        else
            return p -> notExitsInObjectBlackList(p.toString());
    }

    Predicate unitNotInPrimitiveBlackList(boolean obtainClass) {
        if (obtainClass)
            return p -> notExitsInPrimitiveList(obtainClass(p.toString()));
        else
            return p -> notExitsInPrimitiveList(p.toString());
    }

    private String obtainClass(String aux) {
        String[] auxArray = aux.split(" ");
        if (auxArray.length == 2)
            return auxArray[0].split("\\.")[0];
        else //aqui tampoco entra
            return auxArray[1].split("\\.")[0];
    }
}


