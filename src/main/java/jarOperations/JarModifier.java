package jarOperations;

import javassist.*;

import java.io.IOException;
import java.io.NotActiveException;
import java.security.acl.NotOwnerException;
import java.util.ArrayList;

public class JarModifier {

    public static ArrayList<String> addedClasses = new ArrayList<>();
    public static ArrayList<String> addedPackages = new ArrayList<>();


    public static void addMethod(String jar, String classpath, String code) throws CannotCompileException {
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(jar);
            CtClass randomClass = cp.getCtClass(classpath);
            CtMethod ctMethodTest = CtMethod.make(code, randomClass);
            randomClass.addMethod(ctMethodTest);
        } catch (NotFoundException e){
            e.printStackTrace();
        }

    }
    public static void deleteMethod(String jar, String classpath, String name){
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(jar);
            CtClass randomClass = cp.getCtClass(classpath);
            CtMethod method = randomClass.getDeclaredMethod(name);
            System.out.println(method);
            randomClass.removeMethod(method);

        } catch (NotFoundException e){
            e.printStackTrace();
        }

    }

    public static void addConstructor(String jar, String classpath, String code) throws CannotCompileException{
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(jar);
            CtClass randomClass = cp.getCtClass(classpath);
            CtConstructor constructor = CtNewConstructor.make(code,randomClass);
            randomClass.addConstructor(constructor);
        } catch (NotFoundException e){
            e.printStackTrace();
        }


    }
    public static void deleteConstructor(String jar, String classpath, String signature){
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(jar);
            CtClass randomClass = cp.getCtClass(classpath);
            CtConstructor toDelete = randomClass.getConstructor(signature);
            randomClass.removeConstructor(toDelete);

        } catch (NotFoundException e){
            e.printStackTrace();
        }

    }
    public static void deleteField(String jar, String classpath, String name){
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(jar);
            CtClass randomClass = cp.getCtClass(classpath);
            CtField field = randomClass.getField(name);
            randomClass.removeField(field);

        } catch (NotFoundException e){
            e.printStackTrace();
        }
    }
    public static void overwriteMethod(String jar, String classpath, String code,String name) throws CannotCompileException{

       try {
           ClassPool cp = ClassPool.getDefault();
           cp.insertClassPath(jar);
           CtClass randomClass = cp.getCtClass(classpath);
           CtMethod method = randomClass.getDeclaredMethod(name);
           method.setBody(code);
       } catch (NotFoundException e){
           e.printStackTrace();
       }

    }
    public static void overwriteConstructor(String jar, String classpath, String code,String name) throws CannotCompileException{
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(jar);
            CtClass randomClass = cp.getCtClass(classpath);
            CtConstructor constructor = randomClass.getConstructor(name);
            constructor.setBody(code);
        } catch (NotFoundException e){
            e.printStackTrace();
        }

    }
    public static void insertAtTheEnd(String jar, String classpath, String code,String name) throws CannotCompileException{
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(jar);
            CtClass randomClass = cp.getCtClass(classpath);
            CtMethod ctMethodTest = randomClass.getDeclaredMethod(name);
            ctMethodTest.insertBefore(code);
        } catch (NotFoundException e){
            e.printStackTrace();
        }

    }
    public static void insertInTheBeginnning(String jar, String classpath, String code,String name) throws CannotCompileException{
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(jar);
            CtClass randomClass = cp.getCtClass(classpath);
            CtMethod ctMethodTest = randomClass.getDeclaredMethod(name);
            ctMethodTest.insertBefore(code);
        } catch (NotFoundException e){
            e.printStackTrace();
        }
    }

    public static void addClass(String path, String newClass)throws IOException, CannotCompileException {

        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(path);
            CtClass classNew = cp.makeClass(newClass);
            addedClasses.add(classNew.getName());

        } catch (NotFoundException e){
            e.printStackTrace();
        }

    }

    public static void addField(String path, String classPath, String field) throws CannotCompileException{
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(path);
            CtClass randomClass = cp.getCtClass(classPath);
            randomClass.addField(CtField.make(field, randomClass));
        } catch (NotFoundException e){
            e.printStackTrace();
        }
    }

    public static void deleteClass(String path, String packagePath) throws NotOwnerException{
        try {
            ClassPool cp = ClassPool.getDefault();
            cp.insertClassPath(path);
            if(addedClasses.contains(packagePath)){
                CtClass klasa = cp.getCtClass(packagePath);
                klasa.detach();
                addedClasses.remove(packagePath);
            } else throw new NotOwnerException();
        } catch (NotFoundException e){
            e.printStackTrace();
        }
    }

}
