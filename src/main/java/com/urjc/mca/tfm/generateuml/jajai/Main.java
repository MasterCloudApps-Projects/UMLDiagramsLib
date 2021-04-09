package com.urjc.mca.tfm.generateuml.jajai;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        boolean generateImports = false;
        boolean rebuild = false;
        String classToFind = null;
        String classpath = System.getProperty("java.class.path");
        String javahome = System.getProperty("java.home");
        String homedir = System.getProperty("user.home");
        String packageName = null;
        String cacheName = homedir + File.separator + ".classfind";
        boolean duplicates = true;

        //java -jar classfind.jar -v Stream
        for(int a = 0; a < args.length; ++a) {
            if (args[a].equals("-v")) {
                generateImports = true;
                classToFind = args[a + 1];
            } else if (args[a].equals("-r")) {
                rebuild = true;
            } else if (args[a].equals("-c")) {
                classToFind = args[a + 1];
            } else if (args[a].equals("-f")) {
                cacheName = args[a + 1];
            } else if (args[a].equals("-p")) {
                packageName = args[a + 1];
            } else if (args[a].equals("-d")) {
                duplicates = false;
            } else if (args[a].equals("-h")) {
                System.err.println("classfind - www.oopsconsultancy.com");
                System.err.println("  -h         : this message");
                System.err.println("  -r         : rebuilds the cache");
                System.err.println("  -d         : don't display classes duplicated in multiple packages");
                System.err.println("  -c [class] : finds the given class");
                System.err.println("  -v [class] : displays the complete package for the given class");
                System.err.println("  -f [file]  : the classfind cache file to use");
                System.err.println("  -p [pkg]   : the package to expand from");
                System.exit(0);
            }
        }

        if (!javahome.endsWith(File.separator)) {
            javahome = javahome + File.separator;
        }

        String runtime = javahome + "lib" + File.separator + "rt.jar";
        classpath = classpath + File.pathSeparator + runtime;
        ClassCache cache = null;

        try {
            cache = new ClassCache(cacheName, classpath);
            if (rebuild || cache.isExpired()) {
                cache.build(classpath);
                cache.save(cacheName);
            }
        } catch (IOException var16) {
            var16.printStackTrace();
        }

        List results;
        Iterator iresults;
        if (classToFind != null) {
            results = cache.findClass(classToFind);
            iresults = results.iterator();

            ClassCache.Pair result;
            for(HashMap output = new HashMap(); iresults.hasNext(); output.put(result.fullname, result)) {
                result = (ClassCache.Pair)iresults.next();
                if (duplicates || output.get(result.fullname) == null) {
                    if (generateImports) {
                        System.out.println(result.fullname);
                    } else {
                        System.out.println(result);
                    }
                }
            }
        } else if (packageName != null) {
            results = cache.findPackages(packageName);
            iresults = results.iterator();

            while(iresults.hasNext()) {
                String pkg = (String)iresults.next();
                System.out.print(pkg + " ");
            }

            System.out.println("");
        }

    }
}

