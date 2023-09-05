package com.example.heyho;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class OnlineBookstore extends Application {

    private BookstoreController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        controller = new BookstoreController();
        BookstoreView bookstoreView = controller.getBookstoreView ();

        Image backgroundImage = new Image( "photoForBack.jpg" );
        BorderPane root = new BorderPane();
        ImageView ade=new ImageView (backgroundImage);

        root.getChildren ().add ( ade );
        root.setCenter (bookstoreView);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Online Bookstore");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
