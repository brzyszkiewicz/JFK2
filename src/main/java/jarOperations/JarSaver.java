package jarOperations;

import controllers.Controller;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarSaver {

   public static void saveFile(String jarPath) throws IOException, NotFoundException, CannotCompileException {
       ClassPool cp = ClassPool.getDefault();
       cp.insertClassPath(jarPath);
       List<String> names = JarOpener.getClasses(jarPath);
       String destination = jarPath.substring(0,jarPath.lastIndexOf('\\'));

       for(String name:names){
           CtClass toWrite = cp.getCtClass(name.replace('/','.').substring(0,name.length()-6));
           toWrite.debugWriteFile(destination+"/newJar");
       }

       JarFile oldJar = new JarFile(jarPath);
       Enumeration oldJarEntries = oldJar.entries();
       JarEntry jarEntry;

       while(oldJarEntries.hasMoreElements()) {
           jarEntry = (JarEntry) oldJarEntries.nextElement();

           if (jarEntry.getName().endsWith(".class") || jarEntry.isDirectory()) {
               continue;
           }
           File nonClassFile = new File(destination+"\\newJar\\" + jarEntry.getName());
           nonClassFile.getParentFile().mkdirs();
           InputStream  inputStream = oldJar.getInputStream(jarEntry);
           FileOutputStream fileOutputStream = new FileOutputStream(nonClassFile);

           while (inputStream.available() > 0){
               fileOutputStream.write(inputStream.read());
           }
           fileOutputStream.close();
           inputStream.close();


       }

       ProcessBuilder jarBuilder = new ProcessBuilder("cmd.exe","/c cd C:/Users/brzyszkiewicz/Desktop/JFK/newJar && jar cfm newJar.jar META-INF/MANIFEST.MF *");
       jarBuilder.start();
       Controller.showError("Saved!");


    }
}