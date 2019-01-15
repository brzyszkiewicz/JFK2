package jarOperations;

import javassist.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarOpener {

    public static ArrayList<String> getMethods(String jarPath, String classPath) throws NotFoundException {
        ClassPool cp = ClassPool.getDefault();

        cp.insertClassPath(jarPath);
        ArrayList<String> lsta = new ArrayList<>();

        CtClass randomClass = cp.getCtClass(classPath);

        List<CtMethod> metoda = Arrays.asList(randomClass.getDeclaredMethods());

        for(CtMethod met:metoda){
            lsta.add(met.getName());
        }

        System.out.println(metoda);
        System.out.println("test");

        return lsta;

    }

    public static List<String> getClasses(String path) throws IOException{

        File jar = new File(path);
        JarInputStream jarStream = new JarInputStream(new FileInputStream(jar));
        JarEntry entry;
        List<String> names = new ArrayList<>();
        String name;

        while(true){
            entry = jarStream.getNextJarEntry();
            if(entry == null) break;

            if(entry.getName().endsWith(".class")){

                name = entry.getName();
                names.add(name);
            }
        }
        return names;

    }

    public static ArrayList<String> getFields(String jarPath, String  classPath) throws NotFoundException{
        ClassPool cp = ClassPool.getDefault();
        ArrayList<String> fieldsString = new ArrayList<>();

        cp.insertClassPath(jarPath);

        CtClass randomClass = cp.getCtClass(classPath);

        List<CtField> allFields = Arrays.asList(randomClass.getDeclaredFields());

        for (CtField field: allFields) {
            fieldsString.add(field.getName());
        }

        return fieldsString;
    }

    public static ArrayList<String> getConstructors(String jarPath, String classPath) throws NotFoundException{
        ClassPool cp = ClassPool.getDefault();
        ArrayList<String> constructorsString = new ArrayList<>();

        cp.insertClassPath(jarPath);

        CtClass randomClass = cp.getCtClass(classPath);

        List<CtConstructor> allConstructors = Arrays.asList(randomClass.getDeclaredConstructors());

        for (CtConstructor konstruktor: allConstructors) {
            constructorsString.add(konstruktor.getSignature());
        }
        return constructorsString;

    }
}
