package me.sasuke.reflection;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Hokage {


    private void registerListeners(File file){
        Set<Class<?>> classSet = getClasses(file , "me.sasuke");

    }

    public static Set<Class<?>> getClasses(File file, String name) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            JarFile jar = new JarFile(file);
            for (Enumeration<JarEntry> entry = jar.entries(); entry.hasMoreElements(); ) {
                JarEntry jarEntry = entry.nextElement();
                String named = jarEntry.getName().replace("/", ".");
                if (named.contains(name) && named.endsWith(".class") && (!named.contains("$"))) {
                    Class<?> clazz = Class.forName(named.replace(".class", ""));
                    classes.add(clazz);
                }
            }
            jar.close();
        } catch (Exception localException) {
        }
        return classes;
    }


}
