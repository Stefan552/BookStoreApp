package com.example.heyho;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BookstoreController {
    private BookstoreView view;
    private ObservableList<Book> bookList;
    private ObservableList<Book> cartItems;
    private static final String USER="root";
    private static final String PASSWORD="";

    public BookstoreController() {
        view = new BookstoreView();
        bookList = createSampleBookList();
        cartItems = FXCollections.observableArrayList();
        setupView();

    }

    public BookstoreView getBookstoreView() {
        return view;
    }

    private void setupView() {
        view.getBookListView().setItems(bookList);
        view.getRemoveFromCartButton ().setOnAction ( e->clearCart () );
        view.getAddToCartButton().setOnAction(e -> addToCart());
        view.getCheckoutButton().setOnAction(e -> checkout());
        view.getAddNewBookButton ().setOnAction ( e->showAddBookPopup ());
        view.getClearFromDBButton ().setOnAction ( e->showRemoveBookPopup () );
        view.getUpdateBookButton ().setOnAction ( e->showUpdateBookPopup () );
        view.getRefreshButton().setOnAction(e -> refreshView());
        view.setPrefSize ( 800,600 );

    }
    private void refreshView() {
        bookList.clear();
        bookList.addAll(createSampleBookList());

    }
    private void checkout() {
        double totalPrice = calculateTotalPrice();
        displayOrderSummary(totalPrice);

    }

    private void showUpdateBookPopup() {

        Book selectedBook = view.getBookListView().getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Update Book");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField priceField = new TextField();

        titleField.setText(selectedBook.getTitle());
        authorField.setText(selectedBook.getAuthor());
        priceField.setText(String.valueOf(selectedBook.getPrice()));

        gridPane.addRow(0, new Label("Title:"), titleField);
        gridPane.addRow(1, new Label("Author:"), authorField);
        gridPane.addRow(2, new Label("Price:"), priceField);

        Button updateButton = new Button("Update Book");
        updateButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            double price = Double.parseDouble(priceField.getText());
            Book updatedBook = new Book(selectedBook.getId(), title, author, price);
            updateBookInDatabase(updatedBook);
            popupStage.close();
        });

        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(gridPane, updateButton);
        Scene popupScene = new Scene(popupLayout);

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
            showInfoAlert("Success", selectedBook.getTitle() + " is updated .");
        } else {
            showWarningAlert("Warning", "Please select a book to update");
        }}

    private void updateBookInDatabase(Book book) {
        String updateQuery = "UPDATE books SET title = ?, author = ?, price = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library154", USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setDouble(3, book.getPrice());
            stmt.setInt(4, book.getId());
            stmt.executeUpdate();
            showInfoAlert("Book Updated", "The book information has been updated.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBookToDatabase(Book book) {
        String insertQuery = "INSERT INTO books (id,title, author, price) VALUES (?,?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library154", USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setInt ( 1,book.getId () );
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setDouble(4, book.getPrice());

            stmt.executeUpdate();
            showInfoAlert("Book Added", "The new book has been added to the database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double calculateTotalPrice() {
        return cartItems.stream().mapToDouble(Book::getPrice).sum();
    }

    private void displayOrderSummary(double totalPrice) {
        StringBuilder orderSummary = new StringBuilder();
        orderSummary.append("My Cart :\n\n");
        for (Book item : cartItems) {
            orderSummary.append(item.getTitle()).append(" - ").append(item.getPrice()).append(" USD\n");
        }
        orderSummary.append("\nTotal Price: ").append(totalPrice).append(" USD");

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cart Display");
        alert.setHeaderText(null);
        alert.setContentText(orderSummary.toString());
        alert.showAndWait();
    }

    private void clearCart() {
        cartItems.clear();
        showInfoAlert ( "Empty Cart","The Cart has been cleared completely!");
    }

    private void showAddBookPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Add New Book");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField idfield=new TextField ();
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField priceField = new TextField();


        gridPane.addRow(0, new Label("Title:"), titleField);
        gridPane.addRow(1, new Label("Author:"), authorField);
        gridPane.addRow(2, new Label("Price:"), priceField);
        gridPane.addRow(3, new Label("Id:"), idfield);



        Button addButton = new Button("Add Book");
        addButton.setOnAction(e -> {
            int id= Integer.parseInt ( idfield.getText () );
            String title = titleField.getText();
            String author = authorField.getText();
            double price = Double.parseDouble(priceField.getText());


            Book newBook = new Book( id, title, author, price);
            addBookToDatabase(newBook);
            popupStage.close();
        });

        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(gridPane, addButton);
        Scene popupScene = new Scene(popupLayout);

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void showRemoveBookPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Remove Book");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField bookIdField = new TextField();
        bookIdField.setPromptText("Enter Book ID");

        gridPane.addRow(0, new Label("Book ID:"), bookIdField);

        Button removeButton = new Button("Remove Book");
        removeButton.setOnAction(e -> {
            int bookId = Integer.parseInt(bookIdField.getText());
            removeBookFromDatabase(bookId);
            popupStage.close();
        });

        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(gridPane, removeButton);
        Scene popupScene = new Scene(popupLayout);

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void removeBookFromDatabase(int bookId) {
        String deleteQuery = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library154", USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
            showInfoAlert("Book Removed", "The book has been removed from the database.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Book> createSampleBookList() {
        List<Book> books = new ArrayList<> ();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library154",USER,PASSWORD);

             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                books.add(new Book(id, title, author, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(books);
    }

    private void showInfoAlert(String title, String message) {
        showAlert(AlertType.INFORMATION, title, message);
    }

    private void showWarningAlert(String title, String message) {
        showAlert(AlertType.WARNING, title, message);
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addToCart() {
        Book selectedBook = view.getBookListView().getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            cartItems.add(selectedBook);
            addToCartInDatabase(selectedBook);
            showInfoAlert("Success", selectedBook.getTitle() + " added to cart.");
        } else {
            showWarningAlert("Warning", "Please select a book to add to cart.");
        }
    }

    private void addToCartInDatabase(Book book) {
        String sql = "INSERT INTO cart (id, title, author, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library154", USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setDouble(4, book.getPrice());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







        }
