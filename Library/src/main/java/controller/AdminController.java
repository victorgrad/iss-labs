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
import model.User;
import service.IService;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AdminController {
    private Stage stage;
    private Stage manageLibrariansStage;
    private Stage manageSubscribersStage;
    private IService service;
    private User loggedUser;

    ObservableList<BookModel> books = FXCollections.observableArrayList();

    @FXML
    TextField searchField;

    @FXML
    TextField nameField;

    @FXML
    TextField authorField;

    @FXML
    TextField categoryField;

    @FXML
    DatePicker releaseField;

    @FXML
    Spinner<Integer> quantityField;

    @FXML
    TableView<BookModel> booksTableView;

    @FXML
    TableColumn<BookModel,Integer> idCollumn;

    @FXML
    TableColumn<BookModel,String> nameCollumn;

    @FXML
    TableColumn<BookModel,String> authorCollumn;

    @FXML
    TableColumn<BookModel,String> categoryCollumn;

    @FXML
    TableColumn<BookModel,String> releaseCollumn;

    @FXML
    TableColumn<BookModel,Integer> quantityCollumn;

    public void init() throws Exception {
        idCollumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameCollumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        authorCollumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
        categoryCollumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
        releaseCollumn.setCellValueFactory(new PropertyValueFactory<>("Release"));
        quantityCollumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        quantityField.setValueFactory(valueFactory);

        refresh();
    }

    public void setService(IService service) {
        this.service = service;
    }

    public void setAdminStage(Stage admin) {
        this.stage = admin;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }


    public void refresh() throws Exception {
        books.clear();
        service.getBooks().forEach(book -> {
            BookModel aux = new BookModel(book);
            books.add(aux);
        });
        booksTableView.setItems(books);
    }

    @FXML
    public void filter(){
        ObservableList<BookModel> filteredBooks = FXCollections.observableArrayList(books.stream().filter(new Predicate<BookModel>() {
            @Override
            public boolean test(BookModel bookModel) {
                return bookModel.getName().contains(searchField.getText()) || bookModel.getAuthor().contains(searchField.getText()) || bookModel.getCategory().contains(searchField.getText());
            }
        }).collect(Collectors.toList()));
        booksTableView.setItems(filteredBooks);

    }

    @FXML
    public void save(){
        Integer id = null;
        String name = nameField.getText();
        String author = authorField.getText();
        String category = categoryField.getText();
        LocalDate release = releaseField.getValue();
        Integer quantity = quantityField.getValue();

        try {
            id = service.addBook(name, author, category, release, quantity);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        BookModel book = new BookModel(id,name,author,category,release.toString(),quantity);
        books.add(book);
        filter();
    }

    @FXML
    public void delete(){
        BookModel selectedBook = booksTableView.getSelectionModel().getSelectedItem();
        if(selectedBook!=null){
            try {
                service.deleteBook(selectedBook.getId());
                books.remove(selectedBook);
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
    public void manageLibrarians(){
        try{
            Stage stage = loadManageLibrariansStage(service);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private Stage loadManageLibrariansStage(IService service) throws Exception {
        Stage ml = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ManageLibrariansView.fxml"));

        AnchorPane pane = loader.load();
        ml.setScene(new Scene(pane));
        ml.setTitle("Manage Librarians");

        ManageLibrariansController manageLibrariansController = loader.getController();

        manageLibrariansController.setService(service);
        manageLibrariansController.init();
        return ml;
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
