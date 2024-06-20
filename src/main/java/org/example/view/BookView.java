package org.example.view;

import org.example.entity.Book;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BookView {

    public Book creatBook()
    {

        Scanner scanner = new Scanner(System.in);

        String nombreLibro = null;
        String autor = null;
        String editorial = null;
        String genero = null;
        String idioma = null;
        String sinopsis = null;
        Double calificacion = null;
        Integer stock = null;
        Integer vendidos = null;

        try {
            System.out.println("Introduce el nombre del libro:");
            nombreLibro = scanner.nextLine();

            System.out.println("Introduce el autor del libro:");
            autor = scanner.nextLine();

            System.out.println("Introduce la editorial del libro:");
            editorial = scanner.nextLine();

            System.out.println("Introduce el género del libro:");
            genero = scanner.nextLine();

            System.out.println("Introduce el idioma del libro:");
            idioma = scanner.nextLine();

            System.out.println("Introduce la sinopsis del libro:");
            sinopsis = scanner.nextLine();

            System.out.println("Introduce la calificación del libro:");
            calificacion = scanner.nextDouble();

            System.out.println("Introduce el stock del libro:");
            stock = scanner.nextInt();

            System.out.println("Introduce el número de libros vendidos:");
            vendidos = scanner.nextInt();

        } catch (InputMismatchException e) {
            System.err.println("Error: Entrada inválida. Por favor, ingresa el tipo de dato correcto.");
            scanner.nextLine(); // Limpiar el buffer
            return null;
        }

        return new Book(nombreLibro,autor,editorial,genero,idioma,sinopsis,stock,vendidos);
    }

    public static void viewBooks(List<Book> books)
    {
        for (Book libro : books)
        {
            System.out.println("-------------------------------------");
            viewBook(libro);
            System.out.println("-------------------------------------");
            System.out.println();
        }
    }

    public static void viewBook(Book libro) {
        System.out.println("Datos del Libro:");
        System.out.println("Nombre: " + libro.getNameBook());
        System.out.println("Id: " + libro.getIdBook());
        System.out.println("Autor: " + libro.getAuthor());
        System.out.println("Editorial: " + libro.getPublisher());
        System.out.println("Género: " + libro.getGenero());
        System.out.println("Idioma: " + libro.getLanguage());
        System.out.println("Sinopsis: " + libro.getSynopsis());
        System.out.println("Calificación: " + libro.getRate());
        System.out.println("Stock: " + libro.getStock());
        System.out.println("Vendidos: " + libro.getSold());
    }

}

