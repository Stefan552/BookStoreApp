# BookStoreApp
The given Java app is a JavaFX application for managing a virtual bookstore, including features like adding, updating, and removing books from a database, managing a shopping cart, and displaying order summaries using Java's JavaFX framework.

# Bookstore Application

The Bookstore Application is a JavaFX application that allows users to manage a collection of books, add them to a shopping cart, and perform various operations such as updating book information, adding new books, and checking out the cart. The application connects to a MySQL database to store and retrieve book information.

## Features

- View a list of available books.
- Add books to the shopping cart.
- Remove books from the shopping cart.
- Calculate and display the total price of items in the cart.
- Update book information, including title, author, and price.
- Add new books to the database.
- Remove books from the database.
- Refresh the view to display the latest book information.

## Getting Started

To use the Bookstore Application, follow these steps:

1. Make sure you have Java and JavaFX installed on your system.
2. Set up a MySQL database with the name "library154" on localhost (port 3306), or update the database connection URL in the code to match your setup.
3. Import the provided Java source files into your project.
4. Compile and run the `BookstoreController` class to launch the application.

## Application Architecture

The application follows the Model-View-Controller (MVC) architecture:

- **Model**: The `Book` class represents a book with properties such as title, author, price, and ID.
- **View**: The `BookstoreView` class defines the graphical user interface using JavaFX components.
- **Controller**: The `BookstoreController` class manages the application logic, handles user interactions, and connects the model and view components.

## Usage

1. Launch the application.
2. Browse the list of available books in the main window.
3. Click the "Add to Cart" button to add a selected book to the shopping cart.
4. Click the "Clear Cart" button to remove all items from the shopping cart.
5. Click the "Cart" button to display a summary of items in the cart along with the total price.
6. Click the "Add new Book" button to add a new book to the database.
7. Click the "Clear Book" button to remove a book from the database.
8. Click the "Update Book" button to update the information of a selected book.
9. Click the "Refresh" button to update the view with the latest book information.
