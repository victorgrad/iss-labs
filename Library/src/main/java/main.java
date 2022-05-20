import controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Status;
import repository.*;
import service.*;

import java.io.IOException;
import java.time.LocalDate;

public class main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/Library";
        String username = "postgres";
        String password = "postgres";

        UserRepo userRepo = new UserRepository(url,username,password);
        BorrowRepo borrowRepo = new BorrowRepository(url,username,password);
        BookRepo bookRepo = new BookRepository(url,username,password);

        BorrowService borrowService = new BorrowService(borrowRepo,bookRepo);
        UserService userService = new UserService(userRepo);
        BookService bookService = new BookService(bookRepo);

        IService service = new Service(borrowService, userService, bookService);

        Stage login = getLoginStage(service);
        login.show();
    }

    private Stage getLoginStage(IService service) throws IOException {
        Stage login = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/LogInView.fxml"));

        AnchorPane pane = loader.load();
        login.setScene(new Scene(pane));
        login.setTitle("LogIn");

        LogInController loginController = loader.getController();
        loginController.setService(service);
        loginController.setLogInStage(login);
        return login;
    }
}
