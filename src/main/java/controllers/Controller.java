package controllers;
import jarOperations.JarModifier;
import jarOperations.JarOpener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Controller {

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private TreeView<String> tree;

    @FXML
    private ComboBox<String> select;

    @FXML
    private ListView<String> attributes;

    @FXML
    private TextArea code;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    private static String path;

    @FXML
    void cellSelected(MouseEvent event){
                if(tree.getSelectionModel().getSelectedItem() != null && tree.getSelectionModel().getSelectedItem().getValue().contains(".class")){
                    fillList();
                }
        }

    @FXML
    void addClass(ActionEvent event) {
        StringBuilder str = resolvePackage();
        String codeText = code.getText();
        str.append(".");
        str.append(codeText);
        String packagePath = str.toString();
        packagePath = packagePath.substring(1);
        try{
            JarModifier.addClass(path,packagePath);
        }catch (CannotCompileException e){
            showError("Cannot compile");
        } catch (IOException ex) {
            showError("IO problem");
        }
        setTree();

    }

    @FXML
    void addConstructor(ActionEvent event) {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            String codeText = code.getText();
            try{
                JarModifier.addConstructor(path, classpath, codeText);
                fillList();
            } catch (CannotCompileException e){
                showError("Cannot compile");
            }
        } else showError("Not allowed");

    }

    @FXML
    void addField(ActionEvent event) {
        String classpath = resolvePath();
        String field;
        if(classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
                field = code.getText();
                try{
                    JarModifier.addField(path, classpath, field);
                } catch (CannotCompileException e){
                    showError("Cannot compile");
                }
                fillList();
        } else showError("Not allowed");

    }

    @FXML
    void addMethod(ActionEvent event) {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            String codeText = code.getText();
            try {
                    JarModifier.addMethod(path, classpath, codeText);
                    fillList();
            } catch (CannotCompileException e) {
                showError("Cannot compile");
            }
        } else showError("You can't add method here");
    }

    @FXML
    void addPackage(ActionEvent event) {

    }

    @FXML
    void deleteClass(ActionEvent event) {
    }

    @FXML
    void deleteConstructor(ActionEvent event) {
        String classpath = resolvePath();
        if (classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            if (select.getValue().equals("Constructors") && !attributes.getSelectionModel().isEmpty()) {
                String name = attributes.getSelectionModel().getSelectedItem();
                JarModifier.deleteConstructor(path, classpath, name);
                fillList();
            } else showError("Not allowed");
        }
    }

    @FXML
    void deleteField(ActionEvent event) {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            if(select.getValue().equals("Fields") && !attributes.getSelectionModel().isEmpty()){
                String name =  attributes.getSelectionModel().getSelectedItem();
                JarModifier.deleteField(path, classpath, name);
                fillList();
            } else showError("It's not a field");
        } else showError("It's not a field");
    }

    @FXML
    void deleteMethod(ActionEvent event) {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            if(select.getValue().equals("Methods") && !attributes.getSelectionModel().isEmpty()){
                String name =  attributes.getSelectionModel().getSelectedItem();
                JarModifier.deleteMethod(path, classpath, name);
                fillList();
            } else showError("It's not a method");
        } else showError("It's not a method");

    }

    @FXML
    void deletePackage(ActionEvent event) {

    }

    @FXML
    void insertBeginning(ActionEvent event) {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            String codeText = code.getText();
            if (select.getValue().equals("Methods") && !attributes.getSelectionModel().isEmpty()) {
                String name = attributes.getSelectionModel().getSelectedItem();
                try {
                    JarModifier.insertInTheBeginnning(path, classpath, codeText, name);
                    fillList();
                } catch (CannotCompileException e) {
                    showError("Cannot compile");
                }
            } else showError("Not allowed");
        } else showError("Not allowed");

    }

    @FXML
    void insertEnd(ActionEvent event) {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            String codeText = code.getText();
            if (select.getValue().equals("Methods") && !attributes.getSelectionModel().isEmpty()) {
                String name = attributes.getSelectionModel().getSelectedItem();
                try {
                    JarModifier.insertAtTheEnd(path, classpath, codeText, name);
                    fillList();
                } catch (CannotCompileException e) {
                    showError("Cannot compile");
                }
            } else showError("Not allowed");
        } else showError("Not allowed");
    }

    @FXML
    void openJar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR files", "*.jar"));
        File jarFile = fileChooser.showOpenDialog(null);
        if(jarFile != null){
            path = jarFile.getAbsolutePath();
        } else {
            System.out.println("Error occured");
            return;
        }

        setTree();
        addOptions();

    }

    @FXML
    void overwriteConstructor(ActionEvent event) {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            String codeText = code.getText();
            if (select.getValue().equals("Constructors") && !attributes.getSelectionModel().isEmpty()) {
                String name = attributes.getSelectionModel().getSelectedItem();
                try {
                    JarModifier.overwriteConstructor(path, classpath, codeText, name);
                    fillList();
                } catch (CannotCompileException e) {
                    showError("Cannot compile");
                }
            } else showError("Not allowed");
        } else showError("Not allowed");
    }

    @FXML
    void overwriteMethod(ActionEvent event) {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            String codeText = code.getText();
            if (select.getValue().equals("Methods") && !attributes.getSelectionModel().isEmpty()) {
                String name = attributes.getSelectionModel().getSelectedItem();
            try {
                JarModifier.overwriteMethod(path, classpath, codeText, name);
                fillList();
            } catch (CannotCompileException e) {
                showError("Cannot compile");
            }
            } else showError("Not allowed");
        } else showError("Not allowed");

    }

    @FXML
    void save(ActionEvent event) {
    }

    private TreeItem<String> createTreeView(TreeItem<String> parent, String value) {

        for (TreeItem<String> child : parent.getChildren()) {
            if (value.equals(child.getValue())) {
                return child ;
            }
        }
        TreeItem<String> newChild = new TreeItem<>(value);
        parent.getChildren().add(newChild);
        return newChild ;
    }

    private void setTree() {
        List<String> paths;
        try {
            paths = JarOpener.getClasses(path);
            TreeItem<String> root = new TreeItem<>("JAR");
            tree.setRoot(root);
            for (String path : paths) {
                TreeItem<String> current = root;
                for (String component : path.split("\\/")) {
                    current = createTreeView(current, component);
                }
            }
        } catch (IOException e) {
            showError("Ooops, there was an error!");
        }
    }

    private String resolvePath(){
        StringBuilder pathBuilder = new StringBuilder();
        for (TreeItem<String> item = tree.getSelectionModel().getSelectedItem();
             item != null && !item.getValue().equals("JAR") ; item = item.getParent()) {
            pathBuilder.insert(0, item.getValue());
            pathBuilder.insert(0, ".");
        }
        String path = pathBuilder.toString();
        return path;
    }

    public void addOptions(){
        select.getItems().addAll("Methods","Constructors","Fields");
        select.getSelectionModel().selectFirst();
    }

    public void fillList() {
        String classPath;
        classPath = resolvePath();
        classPath = classPath.substring(1, classPath.length() - 6);

        if (classPath != null) try {
            ArrayList<String> methods = JarOpener.getMethods(path, classPath);
            ArrayList<String> fields = JarOpener.getFields(path, classPath);
            ArrayList<String> construcors = JarOpener.getConstructors(path,classPath);
            ObservableList<String> dolist = FXCollections.observableArrayList();
            if (select.getValue().equals("Methods")) {
                dolist.addAll(methods);
                attributes.getItems().clear();
                attributes.setItems(dolist);
            }
            if(select.getValue().equals("Fields")) {
                attributes.getItems().clear();
                dolist.addAll(fields);
                attributes.setItems(dolist);
            }
            if(select.getValue().equals("Constructors")){
                attributes.getItems().clear();
                dolist.addAll(construcors);
                attributes.setItems(dolist);
            }

        } catch (NotFoundException e) {
            System.out.println("Error");
        }

    }

    public void showError(String txt){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText(txt);
        alert.showAndWait();
    }

    public StringBuilder resolvePackage(){
        StringBuilder pathBuilder = new StringBuilder();
        for (TreeItem<String> item = tree.getSelectionModel().getSelectedItem();
             item != null && !item.getValue().equals("JAR"); item = item.getParent()) {
            if(!item.getValue().contains(".class")) {
                pathBuilder.insert(0, item.getValue());
                pathBuilder.insert(0, ".");
            }
        }
        return pathBuilder;
    }



}
