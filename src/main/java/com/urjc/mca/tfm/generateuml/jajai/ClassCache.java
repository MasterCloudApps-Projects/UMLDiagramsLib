package com.urjc.mca.tfm.generateuml.jajai;

import java.io.*;
import java.util.*;
import java.util.jar.JarFile;

public class ClassCache {
    private static final String CLASS_SUFFIX = ".class";
    private Map components = new LinkedHashMap();
    private String classpath = null;
    private boolean expired = false;

    public List findClass(String classname) {
        List matches = new ArrayList();
        Set elements = this.components.keySet();
        Iterator ielements = elements.iterator();

        label27:
        while(ielements.hasNext()) {
            String pkg = (String)ielements.next();
            List classes = (List)this.components.get(pkg);
            Iterator iclasses = classes.iterator();

            while(true) {
                String fullname;
                do {
                    if (!iclasses.hasNext()) {
                        continue label27;
                    }

                    fullname = (String)iclasses.next();
                } while(!fullname.equals(classname) && !fullname.endsWith("." + classname));

                matches.add(new ClassCache.Pair(pkg, fullname));
            }
        }

        return matches;
    }

    public List findPackages(String packageName) {
        List matches = new ArrayList();
        Set elements = this.components.keySet();
        Iterator ielements = elements.iterator();

        while(ielements.hasNext()) {
            String pkg = (String)ielements.next();
            List classes = (List)this.components.get(pkg);
            Iterator iclasses = classes.iterator();

            while(iclasses.hasNext()) {
                String fullname = (String)iclasses.next();
                if (fullname.startsWith(packageName)) {
                    String remainder = fullname.substring(packageName.length());
                    int index = remainder.indexOf(".");
                    String res = null;
                    if (index == 0) {
                        index = remainder.indexOf(".", 0);
                    }

                    if (index == -1) {
                        res = fullname;
                    } else {
                        res = fullname.substring(0, packageName.length() + index);
                    }

                    if (res != null && !matches.contains(res)) {
                        matches.add(res);
                    }
                }
            }
        }

        return matches;
    }

    public ClassCache(String cacheName, String classpath) throws IOException {
        this.load(cacheName);
        if (this.classpath == null || !this.classpath.equals(classpath)) {
            this.expired = true;
            this.classpath = classpath;
        }

    }

    public void build(String classpath) {
        this.components.clear();
        StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);

        while(st.hasMoreTokens()) {
            String element = st.nextToken();
            if (this.components.get(element) == null && element.length() > 0) {
                File felement = new File(element);
                if (felement.exists()) {
                    List classes = this.getClasses(felement);
                    Iterator iclasses = classes.iterator();
                    this.components.put(element, classes);
                }
            }
        }

    }

    private List getClasses(File felement) {
        return felement.isDirectory() ? this.getClassesFromDir(felement) : this.getClassesFromJar(felement);
    }

    private List getClassesFromJar(File jarfile) {
        ArrayList classes = new ArrayList();

        try {
            JarFile jar = new JarFile(jarfile);
            Enumeration entries = jar.entries();

            while(entries.hasMoreElements()) {
                String name = entries.nextElement().toString();
                if (name.endsWith(".class")) {
                    classes.add(this.normalise(name));
                }
            }
        } catch (IOException var6) {
        }

        return classes;
    }

    private List getClassesFromDir(File directory) {
        return this.getClassesFromDir(directory, directory);
    }

    private List getClassesFromDir(File directory, File base) {
        List classes = new ArrayList();
        File[] contents = directory.listFiles();

        for(int c = 0; c < contents.length; ++c) {
            if (contents[c].isDirectory()) {
                classes.addAll(this.getClassesFromDir(contents[c], base));
            } else if (contents[c].getName().endsWith(".class")) {
                String name = contents[c].getAbsolutePath();
                name = name.substring(base.getAbsolutePath().length());
                classes.add(this.normalise(name));
            }
        }

        return classes;
    }

    private String normalise(String name) {
        if (name.startsWith(File.separator)) {
            name = name.substring(1);
        }

        name = name.substring(0, name.length() - ".class".length());
        StringTokenizer st = new StringTokenizer(name, File.separator);
        StringBuffer pkg = new StringBuffer();

        while(st.hasMoreTokens()) {
            pkg.append(st.nextToken());
            if (st.hasMoreTokens()) {
                pkg.append(".");
            }
        }

        return pkg.toString();
    }

    public void save(String name) throws IOException {
        FileWriter fw = new FileWriter(name);
        fw.write(this.classpath + "\n");
        Set elements = this.components.keySet();
        Iterator ielements = elements.iterator();

        while(ielements.hasNext()) {
            String pkg = (String)ielements.next();
            List classes = (List)this.components.get(pkg);
            Iterator iclasses = classes.iterator();

            while(iclasses.hasNext()) {
                String fullname = (String)iclasses.next();
                fw.write(pkg + "\t" + fullname + "\n");
            }
        }

        fw.close();
    }

    public boolean load(String name) throws IOException {
        if (!(new File(name)).exists()) {
            this.expired = true;
            return false;
        } else {
            FileReader fr = new FileReader(name);
            BufferedReader bfr = new BufferedReader(fr);

            for(int num = 0; bfr.ready(); ++num) {
                String line = bfr.readLine();
                if (num == 0) {
                    this.classpath = line;
                } else if (!line.startsWith("#") && !line.trim().equals("")) {
                    int tabindex = line.indexOf("\t");
                    if (tabindex != -1) {
                        String element = line.substring(0, tabindex);
                        String classname = line.substring(tabindex + 1);
                        if (this.components.get(element) == null) {
                            this.components.put(element, new ArrayList());
                        }

                        List list = (List)this.components.get(element);
                        list.add(classname);
                    }
                }
            }

            bfr.close();
            return true;
        }
    }

    public boolean isExpired() {
        return this.expired;
    }

    public class Pair {
        public String element;
        public String fullname;

        public Pair(String p, String name) {
            this.element = p;
            this.fullname = name;
        }

        public String toString() {
            return this.element + " -> " + this.fullname;
        }
    }
}

