package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Status;
import model.User;
import service.IService;

public class ManageSubscribersController {
    private IService service;

    ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    TextField unameField;

    @FXML
    TextField fnameField;

    @FXML
    TextField lnameField;

    @FXML
    TextField passwordField;

    @FXML
    TableView<User> userTableView;

    @FXML
    TableColumn<User,String> unameColumn;

    @FXML
    TableColumn<User,String> fnameColumn;

    @FXML
    TableColumn<User,String> lnameColumn;


    public IService getService() {
        return service;
    }

    public void setService(IService service) {
        this.service = service;
    }

    public void init() throws Exception {
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        unameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        refresh();
    }

    public void refresh() throws Exception {
        users.clear();
        users.addAll(service.getSubscribers());
        userTableView.setItems(users);
    }

    public void save(){
        String username = unameField.getText();
        String fname = fnameField.getText();
        String lname = lnameField.getText();
        String pswd = passwordField.getText();

        try {
            service.addUser(username, pswd, Status.USER, fname, lname);
            User user = new User(username,Status.USER,fname,lname);
            users.add(user);
            userTableView.refresh();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void update(){
        String username = unameField.getText();
        String fname = fnameField.getText();
        String lname = lnameField.getText();
        String pswd = passwordField.getText();

        try {
            service.updateUser(username, pswd, Status.USER, fname, lname);
            refresh();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void delete(){
        String username = unameField.getText();

        try {
            service.deleteUser(username);
            refresh();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void selectionChanged(){
        User user = userTableView.getSelectionModel().getSelectedItem();
        unameField.setText(user.getUsername());
        fnameField.setText(user.getFirstName());
        lnameField.setText(user.getLastName());
    }

}
