package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.BookModel;
import model.BorrowModel;
import model.User;
import service.IService;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LibrarianController {
    private Stage stage;
    private IService service;
    private User loggedUser;

    ObservableList<BorrowModel> borrows = FXCollections.observableArrayList();

    @FXML
    TextField searchField;

    @FXML
    TableView<BorrowModel> borrowTableView;

    @FXML
    TableColumn<BorrowModel,String> fnameCollumn;

    @FXML
    TableColumn<BorrowModel,String> lnameCollumn;

    @FXML
    TableColumn<BorrowModel,String> bnameCollumn;

    @FXML
    TableColumn<BorrowModel,String> authorCollumn;

    @FXML
    TableColumn<BorrowModel,String> releaseCollumn;


    public void init() throws Exception {
        fnameCollumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lnameCollumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        bnameCollumn.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        authorCollumn.setCellValueFactory(new PropertyValueFactory<>("AuthorName"));
        releaseCollumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        refresh();
    }

    public void setService(IService service) {
        this.service = service;
    }

    public void setLibrarianStage(Stage librarian) {
        this.stage = librarian;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }


    public void refresh() throws Exception {
        borrows.clear();
        service.getBorrows().forEach(borrow -> {
            BorrowModel aux = new BorrowModel(borrow);
            borrows.add(aux);
        });
        borrowTableView.setItems(borrows);
    }

    @FXML
    public void filter(){
        ObservableList<BorrowModel> filteredBorrows = FXCollections.observableArrayList(borrows.stream().filter(new Predicate<BorrowModel>() {
            @Override
            public boolean test(BorrowModel borrowModel) {
                return borrowModel.getFirstName().contains(searchField.getText()) || borrowModel.getAuthorName().contains(searchField.getText()) || borrowModel.getLastName().contains(searchField.getText()) || borrowModel.getBookName().contains(searchField.getText());
            }
        }).collect(Collectors.toList()));
        borrowTableView.setItems(filteredBorrows);

    }

    @FXML
    public void receive(){
        BorrowModel selectedBorrow = borrowTableView.getSelectionModel().getSelectedItem();
        if(selectedBorrow!=null){
            try {
                service.receive(selectedBorrow.getBookId(),selectedBorrow.getUsername());
                borrows.remove(selectedBorrow);
                filter();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "You must select a book first!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void manageSubscribers(){
        try{
            Stage stage = loadManageSubscribersStage(service);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private Stage loadManageSubscribersStage(IService service) throws Exception {
        Stage ms = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ManageSubscribersView.fxml"));

        AnchorPane pane = loader.load();
        ms.setScene(new Scene(pane));
        ms.setTitle("Manage Subscribers");

        ManageSubscribersController manageSubscribersController = loader.getController();

        manageSubscribersController.setService(service);
        manageSubscribersController.init();
        return ms;
    }

    @FXML
    public void exitButtonPressed(){
        stage.close();
    }
}
