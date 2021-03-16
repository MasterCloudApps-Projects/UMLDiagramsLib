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
import java.util.function.Predicate;
import java.util.stream.Stream;

public class JavaAnalyzerEclipseAST {
    private static final Logger LOG = LoggerFactory.getLogger(JavaAnalyzerEclipseAST.class);
    static Set<String> objects = new HashSet<>(Arrays.asList("RuntimeException", "StateValue,AcceptorController", "List<Color", "String", "StateValue,Controller", "AssertionError", "Logger", "Map", "Set", "BufferedReader", "Random", "Collections", "Arrays", "Files", "LOG", "Objects", "System", "SpringApplication", "CommandLineRunner", "JavaClasses", "SecureRandom", "JavaClass", "ObjectInputStream", "ByteArrayInputStream", "ObjectOutputStream", "ByteArrayOutputStream", "Object", "Serializable", "String", "StringBuilder", "ProcessBuilder", "Path", "Process", "InputStream", "Thread", "Invocable", "ScriptEngine"));
    static Set<String> primitives = new HashSet<>(Arrays.asList("int", "byte", "short", "long", "float", "double", "boolean", "char"));
    static Set<String> aggregation = new HashSet<>();

    private JavaAnalyzerEclipseAST() {
        throw new IllegalStateException("Utility class");
    }

    public static Domain run(String path) {
        Domain domain = new Domain("domain");
        Optional<Path> hit;

//        try (Stream<Path> entries = Files.walk(Path.of(System.getProperty("user.dir")))) {
//            hit = entries.filter(file -> file.toString().contains(path))
//                    .findAny();
//
//        } catch (IOException e) {
//            LOG.debug("context", e);
//        }

        hit = Optional.ofNullable(Path.of(path));
        try (Stream<Path> entries
                     = Files.walk(Paths.get(hit.stream().findFirst().orElse(null).toString()).toAbsolutePath())) {
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

    public static char[] readFileToString(String filePath) throws IOException {
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

    public static void analyzed(Domain domain, String pathFile) throws IOException {

        Set<String> aggregation = new HashSet<>();
        Set<String> primitives = new HashSet<>(Arrays.asList("int", "byte", "short", "long", "float", "double", "boolean", "char"));
        Set<String> objects = new HashSet<>(Arrays.asList("RuntimeException", "StateValue,AcceptorController", "List<Color","String", "StateValue,Controller","AssertionError", "Logger", "Map", "Set", "BufferedReader", "Random", "Collections", "Arrays", "Files", "LOG", "Objects", "System", "SpringApplication", "CommandLineRunner", "JavaClasses", "SecureRandom", "JavaClass", "ObjectInputStream", "ByteArrayInputStream", "ObjectOutputStream", "ByteArrayOutputStream", "Object", "Serializable", "String", "StringBuilder", "ProcessBuilder", "Path", "Process", "InputStream", "Thread", "Invocable", "ScriptEngine"));
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
        objects.addAll(primitives);
        Set<String> fields = new HashSet<>();
        Map<String, String> fields2 = new HashMap<>();
        aggregation = new HashSet<>();
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

            public boolean visit(VariableDeclarationFragment node) {

                if (node.getParent() instanceof FieldDeclaration) {
                    fields.add(((FieldDeclaration) node.getParent()).getType().toString());
                }
                return false; // do not continue to avoid usage info
            }

            public boolean visit(MethodDeclaration unit) {
                if (!unit.isConstructor()) {
                    unit.parameters().stream()
                            .filter(unitNotInPrimitiveBlackList(true))
                            .filter(unitNotInObjectsBlackList(true))
                            .filter(unitNotInAggregationList())
                            .forEach(p -> {
                                if (((SingleVariableDeclaration) p).getType().isSimpleType())
                                    domain.addUsed(obtainClass(p.toString()));
                                    units.add(obtainClass(p.toString()));
                                }
                            });
                } else {
                    unit.parameters().stream()
                            .filter(unitNotInPrimitiveBlackList(true))
                            .filter(unitNotInObjectsBlackList(true))
                            .forEach(p -> {
                                domain.addAssociate(obtainClassFromList(obtainClass(p.toString())));
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

            private boolean isConstructor(ClassInstanceCreation node) {
                Object object = node.getParent().getParent().getParent().getParent();
                return object instanceof MethodDeclaration ? ((MethodDeclaration) object).isConstructor() : false;
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
                        domain.addPart(aux);
                        units.add(aux);
                    }
                } else {
                    if (notExitsInObjectBlackList(aux)) {
                        domain.addUsed(aux);
                        units.add(aux);
                    }
                }
                return true;
            }

            public boolean visit(ExpressionStatement node) {
                String aux = node.toString();
                if (Character.isUpperCase(aux.charAt(0))) {

                    aux = aux.substring(0, aux.indexOf("."));
                    if (notExitsInObjectBlackList(aux)) {
                        domain.addUsed(aux);
                        units.add(aux);
                    }
                }
                return true;
            }

            public boolean visit(PackageDeclaration node) {
                mypackage = node.getName().toString();
                return true;
            }

            public boolean visit(ParameterizedType node) {
                node.typeArguments().stream()
                        .filter(unitNotInObjectsBlackList(false))
                        .forEach(t -> aggregation.add(t.toString()));

                return true;
            }

            public boolean visit(TypeDeclaration node) {
                if (node.getName().toString().equals("JavaAnalyzer"))
                    return false;
                Unit unit = domain.getUnit(node.getName().toString());
                if (unit != null) {
                    unit.setMyPackage(mypackage);
                    domain.addUnit(unit.name);
                    units.add(unit.name);
                } else {
                    domain.addUnit(node.getName().toString(), mypackage);
                    units.add(node.getName().toString());
                }
                //for base
                if (node.getSuperclassType() != null) {
                    domain.addBase(node.getSuperclassType().toString());
                    units.add(node.getSuperclassType().toString());
                }
                node.superInterfaceTypes().forEach(i -> {
                    domain.addBase(i.toString());
                    units.add(i.toString());
                });
                return true;
            }

            //dependency
            public boolean visit(VariableDeclarationStatement node) {
                String aux = node.getType().toString();
                if (notExitsInPrimitiveList(aux) && !node.getType().isSimpleType()) {
                    aux = obtainClassFromList(aux);

                }
                //for dependency
                if (notExitsInPrimitiveList(aux) && notExitsInObjectBlackList(aux) && notExitInAggregationList(aux)) {
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
                    units.add(node.getName().toString());
                }
                return true;
            }

            public boolean visit(Assignment node) {
                if (node.getRightHandSide().getParent().getParent().getParent().getParent() instanceof MethodDeclaration &&
                        ((MethodDeclaration) node.getRightHandSide().getParent().getParent().getParent().getParent()).isConstructor()
                        && Character.isUpperCase(node.getRightHandSide().toString().split("\\.")[0].charAt(0))) {
                    //Aqui consigo entrar con los repos del master (with Composite)
                    if (notExitsInObjectBlackList(node.getRightHandSide().toString().split("\\.")[0])) {
                        domain.addPart(node.getRightHandSide().toString().split("\\.")[0]);
                        units.add(node.getRightHandSide().toString().split("\\.")[0]);
                    }
                }
                return true;
            }
        });

        aggregation.stream().filter(unitNotInPrimitiveBlackList(false))
                .filter(unitNotInObjectsBlackList(false))
                .forEach(a -> {
                    if (!units.contains(a)) {
                        String unit = (String)a;
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
                        } else {
                            domain.addAssociate(unit);
                        }
                    }
                });
    }

    private static boolean notExitInAggregationList(String aux) {
        return !aggregation.contains(aux);
    }

    private static boolean notExitsInPrimitiveList(String aux) {
        return !primitives.contains(aux);
    }

    private static boolean notExitsInObjectBlackList(String aux) {
        return !objects.contains(aux);
    }

    private static Predicate unitNotInAggregationList() {
        return p -> notExitInAggregationList(obtainClass(p.toString()));
    }

    private static boolean isArrayOrList(String aux) {
        return aux.contains("<") || aux.contains("[");
    }

    private static String obtainClassFromList(String aux) {
        if (aux.contains("[") && aux.contains("]")) {
            aux = aux.substring(0, aux.indexOf("["));
        } else if (aux.contains("<") && aux.contains(">")) {
            aux = aux.substring(aux.indexOf("<") + 1);
            aux = aux.substring(0, aux.indexOf(">"));
        }
        return aux;
    }

    static Predicate unitNotInObjectsBlackList(boolean obtainClass) {
        if (obtainClass)
            return p -> notExitsInObjectBlackList(obtainClass(p.toString()));
        else
            return p -> notExitsInObjectBlackList(p.toString());
    }
    static Predicate unitNotInPrimitiveBlackList(boolean obtainClass) {
        if (obtainClass)
            return p -> notExitsInPrimitiveList(obtainClass(p.toString()));
        else
            return p -> notExitsInPrimitiveList(p.toString());
    }

    private static String obtainClass(String aux) {
        String[] auxArray = aux.split(" ");
        if (auxArray.length == 2)
            return auxArray[0].split("\\.")[0];
        else //aqui tampoco entra
            return auxArray[1].split("\\.")[0];
    }
}


