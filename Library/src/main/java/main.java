import controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Status;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
        SessionFactory sessionFactory = initialize();
        //UserRepo userRepo = new UserRepository(url,username,password);
        UserRepo userRepo = new UserORMRepository(sessionFactory);
        //BorrowRepo borrowRepo = new BorrowRepository(url,username,password);
        BorrowRepo borrowRepo = new BorrowORMRepository(sessionFactory);
        //BookRepo bookRepo = new BookRepository(url,username,password);
        BookRepo bookRepo = new BookORMRepository(sessionFactory);

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


    public SessionFactory initialize() {
        SessionFactory sessionFactory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return sessionFactory;
    }
}
