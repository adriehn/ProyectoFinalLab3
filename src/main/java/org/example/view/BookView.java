package org.example.view;

import org.example.entity.Book;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BookView {

    Scanner scanner = new Scanner(System.in);

    public Book creatBook()
    {



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
        System.out.println("Estado: " + libro.isStatus());
    }

    public Integer pedirEntero(String mensaje) {
        while (true) {
            try {

                System.out.println(mensaje);
                String input = scanner.nextLine();
                scanner.nextLine();

                // Verificar si la entrada está vacía o contiene solo espacios en blanco
                if (input.trim().isEmpty()) {
                    System.err.println("Entrada inválida. Por favor, ingrese un número entero.");
                    continue;
                }

                // Intentar convertir la entrada a un número entero
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Por favor, ingrese un número entero.");
            }
        }
    }

    public void editarLibro(Book libro){

        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        System.out.println("¿Qué campo desea editar?");
        System.out.println("1. Nombre del Libro");
        System.out.println("2. Autor");
        System.out.println("3. Editorial");
        System.out.println("4. Género");
        System.out.println("5. Idioma");
        System.out.println("6. Sinopsis");
        System.out.println("7. Calificación");
        System.out.println("8. Stock");
        System.out.println("9. Vendidos");
        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch(opcion) {
            case 1:
                System.out.println("Nuevo nombre del libro:");
                libro.setNameBook(scanner.nextLine());
                break;
            case 2:
                System.out.println("Nuevo autor:");
                libro.setAuthor(scanner.nextLine());
                break;
            case 3:
                System.out.println("Nueva editorial:");
                libro.setPublisher(scanner.nextLine());
                break;
            case 4:
                System.out.println("Nuevo género:");
                libro.setGenero(scanner.nextLine());
                break;
            case 5:
                System.out.println("Nuevo idioma:");
                libro.setLanguage(scanner.nextLine());
                break;
            case 6:
                System.out.println("Nueva sinopsis:");
                libro.setSynopsis(scanner.nextLine());
                break;
            case 7:
                System.out.println("Nueva calificación:");
                libro.setRate(scanner.nextDouble());
                break;
            case 8:
                System.out.println("Nuevo stock:");
                libro.setStock(scanner.nextInt());
                break;
            case 9:
                System.out.println("Cantidad vendida:");
                libro.setSold(scanner.nextInt());
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

}