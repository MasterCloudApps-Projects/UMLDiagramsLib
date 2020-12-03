package com.urjc.mca.tfm.generateuml;

import com.urjc.mca.tfm.generateuml.model.Domain;
import com.urjc.mca.tfm.generateuml.model.Unit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class JavaAnalyzerEclipseAST {
    private static final Logger LOG = LoggerFactory.getLogger(JavaAnalyzerEclipseAST.class);

    public static Domain run(String path) {
        Domain domain = new Domain("domain");
        Optional<Path> hit = Optional.empty();

        try (Stream<Path> entries = Files.walk(Path.of(System.getProperty("user.dir")))) {
            hit = entries.filter(file -> file.toString().contains(path))
                    .findAny();

        } catch (IOException e) {
            LOG.debug("context", e);
        }

        try (Stream<Path> entries
                     = Files.walk(Paths.get(hit.get().toString()).toAbsolutePath())) {
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
        ClassDiagramGenerator classDiagramGenerator = new ClassDiagramGenerator();
        System.out.println(classDiagramGenerator.addDomain(domain).print());
        return domain;
    }

    public static char[] readFileToString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }

        reader.close();

        return fileData.toString().toCharArray();
    }

    public static void analyzed(Domain domain, String pathFile) throws IOException {

        Set<String> aggregation = new HashSet<>();
        Set<String> primitives = new HashSet<>(Arrays.asList("int", "byte", "short", "long", "float", "double", "boolean", "char"));
        Set<String> objects = new HashSet<>(Arrays.asList("Map", "Set", "BufferedReader", "Random", "Collections", "Arrays", "Files", "LOG", "Objects", "System", "SpringApplication", "CommandLineRunner", "JavaClasses", "JavaClass", "ObjectInputStream", "ByteArrayInputStream", "ObjectOutputStream", "ByteArrayOutputStream", "Object", "Serializable", "String", "StringBuilder", "ProcessBuilder", "Path", "Process", "InputStream", "Thread", "Invocable", "ScriptEngine"));
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
        objects.addAll(objectJavaAnalyzer);
        Set<String> fields = new HashSet<>();
        Set<String> units = new HashSet<>();
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
            Set names = new HashSet();


            public boolean visit(VariableDeclarationFragment node) {

                SimpleName name = node.getName();
                this.names.add(name.getIdentifier());
                //  System.out.println("Declaration of '" + name + "' type " );
                if (node.getParent() instanceof FieldDeclaration) {
                    // System.out.println(((FieldDeclaration) node.getParent()).getType().toString());
                    //domain.addAssociate(((FieldDeclaration) node.getParent()).getType().toString());
                    fields.add(((FieldDeclaration) node.getParent()).getType().toString());
                }
                //else if (node.getParent() instanceof VariableDeclarationStatement)
                //    System.out.println(((VariableDeclarationStatement) node.getParent()).getType().toString());

                return false; // do not continue to avoid usage info
            }

            //don't uses, it's for usages aqui podría poner 4 parents y añadirlo como composition
//            public boolean visit(SimpleName node) {
//                if (this.names.contains(node.getIdentifier())) {
//                    System.out.println("Usage of '" + node + "' at line " + cu.getLineNumber(node.getStartPosition()));
//                }
//                return true;
//            }

            //para dependecy and associate
            public boolean visit(MethodDeclaration unit) {
//                System.out.println("metodo: " + unit.getName());
//                System.out.println(unit.parameters());
                if (!unit.isConstructor()) {
                    unit.parameters().stream()
                            .filter(p -> !primitives.contains(obtainClass(p.toString())))
                            .filter(p -> !objects.contains(obtainClass(p.toString())))
                            .filter(p -> !aggregation.contains(obtainClass(p.toString())))
                            .forEach(p -> {
                                if (((SingleVariableDeclaration) p).getType().isSimpleType())
                                    domain.addUsed(obtainClass(p.toString()));
                                units.add(obtainClass(p.toString()));
                            });
                }
                //unit.getBody().statements()
                else {
                    unit.parameters().stream()
                            .filter(p -> !primitives.contains(obtainClass(p.toString())))
                            .filter(p -> !objects.contains(obtainClass(p.toString())))
                            .forEach(p -> {
                                domain.addAssociate(obtainClass(p.toString()));
                                units.add(obtainClass(p.toString()));
                            });
                }
                return true;
            }

            private String obtainClass(String aux) {
                String[] auxArray = aux.split(" ");
                if (auxArray.length == 2)
                    return auxArray[0].split("\\.")[0];
                else
                    return auxArray[1].split("\\.")[0];
            }

            //atributos
//            public boolean visit(FieldDeclaration unit) {
//                System.out.println("Atributo: " + unit.getType().toString());
//                return true;
//            }

            public boolean visit(ClassInstanceCreation node) {
                String aux = node.getType().toString();
                if (node.getParent().getParent().getParent().getParent() instanceof MethodDeclaration) {
                    if (((MethodDeclaration) node.getParent().getParent().getParent().getParent()).isConstructor()) {
                        if (!primitives.contains(aux) && !node.getType().isSimpleType()) {
                            if (node.getType().isArrayType()) {
                                aux = aux.substring(0, aux.indexOf("["));
                            } else {
                                aux = aux.substring(aux.indexOf("<") + 1);
                                aux = aux.substring(0, aux.indexOf(">"));
                            }
                        }
                        domain.addPart(aux);
                        units.add(aux);
                    }
                } else {
                    domain.addUsed(aux);
                    units.add(aux);
                }
                return true;
            }

            //toda la clase
//            public boolean visit(CompilationUnit node) {
//                return true;
//            }

            // podría valer para compositiones, comprobando que el padre sea constructor y que el objeto sea un atributo
            public boolean visit(ExpressionStatement node) {
                String aux = node.toString();
                if (Character.isUpperCase(aux.charAt(0))) {

                    aux = aux.substring(0, aux.indexOf("."));
                    if (!objects.contains(aux)) {
                        domain.addUsed(aux);
                        units.add(aux);
                    }
                }
                return true;
            }

            //for imports
//            public boolean visit(ImportDeclaration node) {
//                return true;
//            }

            public boolean visit(PackageDeclaration node) {
                mypackage = node.getName().toString();
//                System.out.println("Package: " + node.getName());
                return true;
            }

            //para listas
            public boolean visit(ParameterizedType node) {
                node.typeArguments().stream()
                        .filter(t -> !objects.contains(t.toString()))
                        .forEach(t -> {
                            //domain.addElement(t.toString());
                            aggregation.add(t.toString());
//                            units.add(t.toString());
                        });

                return true;
            }

            //return statement
//            public boolean visit(ReturnStatement node) {
//                return true;
//            }

            //Obtain all object to analyzed
//            public boolean visit(SimpleType node) {
//                return true;
//            }
//
//            parametros de metodos
//            public boolean visit(SingleVariableDeclaration node) {
//                return true;
//            }

            //La clase a analizar
            //La clase a analizar
            public boolean visit(TypeDeclaration node) {
                if (node.getName().toString().equals("JavaAnalyzer"))
                    return false;
                Unit unit = domain.getUnit(node.getName().toString());
                if (unit != null) {
                    unit.setMyPackage(mypackage);
                    domain.addUnit(unit.name);
                } else
                    domain.addUnit(node.getName().toString(), mypackage);
                if (node.getSuperclassType() != null) {
                    domain.addBase(node.getSuperclassType().toString());
                }
                node.superInterfaceTypes().forEach(i -> domain.addBase(i.toString()));
                System.out.println("Class to analyze: " + node.getName());
                return true;
            }

            //dependency
            public boolean visit(VariableDeclarationStatement node) {
                String aux = node.getType().toString();
                if (!primitives.contains(aux) && !node.getType().isSimpleType()) {
                    if (node.getType().isArrayType()) {
                        aux = aux.substring(0, aux.indexOf("["));
                    } else {
                        aux = aux.substring(aux.indexOf("<") + 1);
                        aux = aux.substring(0, aux.indexOf(">"));
                    }
                }
                if (!primitives.contains(aux) && !objects.contains(aux) && !aggregation.contains(aux)) {
                    domain.addUsed(aux);
                    units.add(aux);
                }
                return true;
            }

            public boolean visit(EnumDeclaration node) {
                Unit unit = domain.getUnit(node.getName().toString());
                if (unit != null)
                    unit.setMyPackage(mypackage);
                else
                    domain.addUnit(node.getName().toString(), mypackage);
                return true;
            }

            public boolean visit(Assignment node) {
                if (node.getRightHandSide().getParent().getParent().getParent().getParent() instanceof MethodDeclaration)
                    if (((MethodDeclaration) node.getRightHandSide().getParent().getParent().getParent().getParent()).isConstructor()
                            && Character.isUpperCase(node.getRightHandSide().toString().split("\\.")[0].charAt(0))) {
                        domain.addPart(node.getRightHandSide().toString().split("\\.")[0]);
                        units.add(node.getRightHandSide().toString().split("\\.")[0]);
                    }
                return true;
            }
        });

        aggregation.stream().filter(a -> !primitives.contains(a))
                .filter(a -> !objects.contains(a))
                .forEach(a -> {
                    if (!units.contains(a)) {
                        domain.addElement(a);
                        units.add(a);
                    }
                });
        fields.stream()
                .filter(f -> !primitives.contains(f))
                .filter(f -> !objects.contains(f))
                .forEach(f -> {
                    if (!units.contains(f))
                        if (f.contains("<") || f.contains("[")) {
                            String aux = f;
                            if (f.contains("[")) {
                                aux = aux.substring(0, aux.indexOf("["));
                            } else {
                                aux = aux.substring(aux.indexOf("<") + 1);
                                aux = aux.substring(0, aux.indexOf(">"));
                            }
                            if (domain.getUnit(aux) == null) {
                                domain.addAssociate(aux);
                                units.add(aux);
                            }
                        } else {
                            domain.addAssociate(f);
                        }
                });
    }
}

