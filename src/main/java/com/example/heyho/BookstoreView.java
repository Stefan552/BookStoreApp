package com.example.heyho;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BookstoreView extends VBox {

    private ListView<Book> bookListView;
    private Button addToCartButton;
    private Button checkoutButton;
    private TableView<Book> cartTableView;
    private Button removeFromCartButton;
    private Button clearFromDBButton;
    private Button updateBookButton;
    private Button addNewBookButton;
    private Button refreshButton;


    public BookstoreView() {


        bookListView = new ListView<> ();
        addToCartButton = new Button ( "Add to Cart" );
        refreshButton=new Button ("Refresh");
        checkoutButton = new Button ( "Cart" );
        removeFromCartButton=new Button ("Clear Cart");
        clearFromDBButton = new Button("Clear Book ");
        updateBookButton = new Button("Update Book");
        addNewBookButton = new Button("Add new Book");
        cartTableView = new TableView<> ();

        HBox buttonsBox = new HBox ();
        refreshButton.setPrefSize ( 200,30);
        addToCartButton.setPrefSize(200, 30);
        checkoutButton.setPrefSize(200, 30);
        removeFromCartButton.setPrefSize(200, 30);
        clearFromDBButton.setPrefSize(200, 30);
        updateBookButton.setPrefSize(200, 30);
        addNewBookButton.setPrefSize(200, 30);
        buttonsBox.getChildren().addAll(
                addToCartButton, checkoutButton, removeFromCartButton,
                clearFromDBButton, updateBookButton, addNewBookButton,refreshButton

        );

        getChildren().addAll(bookListView, buttonsBox);




    }

    public Button getRemoveFromCartButton ( ) {
        return removeFromCartButton;
    }

    public ListView<Book> getBookListView() {
            return bookListView;
        }

        public Button getAddToCartButton() {
            return addToCartButton;
        }

        public Button getCheckoutButton() {
            return checkoutButton;
        }

        public TableView<Book> getCartTableView() {
            return cartTableView;
        }

    public Button getClearFromDBButton ( ) {
        return clearFromDBButton;
    }

    public Button getUpdateBookButton ( ) {
        return updateBookButton;
    }

    public Button getAddNewBookButton ( ) {
        return addNewBookButton;
    }

    public Button getRefreshButton ( ) {
        return refreshButton;
    }
}



