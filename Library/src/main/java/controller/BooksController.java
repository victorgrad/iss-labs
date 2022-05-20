package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.BookModel;
import model.User;
import service.IService;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BooksController {
    private Stage stage;
    private IService service;
    private User loggedUser;

    ObservableList<BookModel> books = FXCollections.observableArrayList();
    Set<BookModel> basketBooks = new HashSet<>();

    @FXML
    ListView<BookModel> booksListView;

    @FXML
    Label welcomeLabel;

    @FXML
    TextField searchField;

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

    public void init() throws Exception {
        idCollumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameCollumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        authorCollumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
        categoryCollumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
        releaseCollumn.setCellValueFactory(new PropertyValueFactory<>("Release"));
        welcomeLabel.setText("Welcome "+loggedUser.getFirstName()+" "+loggedUser.getLastName());
        refresh();
    }

    public void setService(IService service) {
        this.service = service;
    }

    public void setBooksStage(Stage books) {
        this.stage = books;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void refreshBasket(){
        booksListView.getItems().clear();
        booksListView.getItems().addAll(basketBooks);
    }

    public void refresh() throws Exception {
        books.clear();
        service.getBooks().forEach(book -> {
            BookModel aux = new BookModel(book);
            books.add(aux);
        });
        booksTableView.setItems(books);
        refreshBasket();
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
    public void addToBasketButtonPressed() throws Exception {
        BookModel bookModel = booksTableView.getSelectionModel().getSelectedItem();
        basketBooks.add(bookModel);
        refreshBasket();
    }

    @FXML
    public void rent(){
        try {
            service.rent(loggedUser, basketBooks);
            basketBooks.clear();
            refreshBasket();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    public void exitButtonPressed(){
        stage.close();
    }
}
