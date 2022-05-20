package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Status;
import service.IService;

import java.io.IOException;


public class LogInController {
    private IService service;
    private Stage logInStage;
    private Stage nextStage;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    public void setService(IService service){
        this.service = service;
    }

    public void setLogInStage(Stage stage){
        this.logInStage = stage;
    }

    @FXML
    public void authenticate(){
        try {
            Status status = service.authenticate(usernameField.getText(),passwordField.getText());
            switch (status){
                case ERROR ->{
                    throw new Exception("Credentiale gresite");
                }
                case USER -> {
                    loadBooksStage(service);
                }
                case LIBRARIAN -> {
                }
                case ADMIN -> {
                }
            }
            nextStage.show();
            logInStage.hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void loadBooksStage(IService service) throws Exception {
        Stage books = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BooksView.fxml"));

        AnchorPane pane = loader.load();
        books.setScene(new Scene(pane));
        books.setTitle("Library books");

        BooksController booksController = loader.getController();
        booksController.setService(service);
        booksController.setBooksStage(books);
        booksController.setLoggedUser(service.getUser(usernameField.getText()));
        booksController.init();
        nextStage = books;
    }

}
