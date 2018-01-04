package loader;

import java.security.SecureClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ClassLoader extends SecureClassLoader {
    private ArrayList<File> pathPlugins;

    public ClassLoader(ArrayList<File> p) {
        this.pathPlugins = p;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassData(name);
        if(b == null) {
            throw new ClassNotFoundException();
        }
        return defineClass(name, b, 0, b.length);
    }

    /**
     * Load a file in path only .jar or .zip
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    // TODO: 28/12/2017 regarder le mécanisme de chargement des classes avec anotation + stocker les plugins chargé
    private byte[] loadClassData(String name) throws ClassNotFoundException {
        for (File plugin : pathPlugins) {
            if(name.endsWith(".jar"))
                try {
                    JarFile jarFile = new JarFile(plugin.getPath() + File.separator + name);
                    Enumeration<? extends JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = entries.nextElement();
                        if (entry.toString().endsWith(".class")) {
                            String nomPackage = plugin.getPath();
                            nomPackage = nomPackage.replace(plugin.getName(), "");
                            //System.out.println(plugin.getPath());

                            String nomFichier = entry.toString();
                            String nomClasse = nomFichier.replace("/", ".");
                            nomClasse = nomClasse.substring(0, nomClasse.lastIndexOf('.'));
                            //System.out.println(nomFichier.substring(nomFichier.lastIndexOf('f'), ));

                            if (nomClasse.contains(name.substring(0, name.lastIndexOf('.')))) {
                                File f = new File(nomFichier);
                                System.out.println(f.getAbsolutePath());
                                Path cheminFichier = Paths.get(f.getAbsolutePath());
                                byte[] fileData = Files.readAllBytes(cheminFichier);
                                return fileData;
                            }
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            else {
                System.out.println("Format du plugin incorrect");
            }
        }
        return null;
    }
}