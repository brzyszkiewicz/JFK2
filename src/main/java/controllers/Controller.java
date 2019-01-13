package controllers;
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

    }

    @FXML
    void addConstructor(ActionEvent event) {

    }

    @FXML
    void addField(ActionEvent event) {

    }

    @FXML
    void addMethod(ActionEvent event) {

    }

    @FXML
    void addPackage(ActionEvent event) {

    }

    @FXML
    void deleteClass(ActionEvent event) {

    }

    @FXML
    void deleteConstructor(ActionEvent event) {

    }

    @FXML
    void deleteField(ActionEvent event) {

    }

    @FXML
    void deleteMethod(ActionEvent event) {

    }

    @FXML
    void deletePackage(ActionEvent event) {

    }

    @FXML
    void insertBeginning(ActionEvent event) {

    }

    @FXML
    void insertEnd(ActionEvent event) {

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

    }

    @FXML
    void overwriteMethod(ActionEvent event) {

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
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



}
