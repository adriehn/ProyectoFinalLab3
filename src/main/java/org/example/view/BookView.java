package org.example.view;

import org.example.entity.Book;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.View;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BookView implements View {

    Scanner scanner = new Scanner(System.in);
    private final String requestNameBook = "Ingrese el nombre del libro: ";
    private final String requestNameAuthor = "Introduce el autor del libro:";
    private final String requestEditorial = "Introduce la editorial del libro:";
    private final String requestGen = "Introduce el género del libro:";
    private final String requestLanguage = "Introduce el idioma del libro:";
    private final String requestSynopsis = "Introduce la sinopsis del libro:";
    private final String requestStock = "Introduce el stock del libro:";


    public Book creatBook() {
        try {
            String nombreLibro =  pedirDato(requestNameBook);
            String autor =  pedirDato(requestNameAuthor);
            String editorial =  pedirDato(requestEditorial);
            String genero =  pedirDato(requestGen);
            String idioma =  pedirDato(requestLanguage);
            String sinopsis =  pedirDato(requestSynopsis);
            Integer stock =  pedirEntero(requestStock);
            Integer vendidos =  pedirEntero("Introduce el número de libros vendidos:");
            return new Book(nombreLibro, autor, editorial, genero, idioma, sinopsis, stock, vendidos);
        } catch (InputMismatchException e) {
            System.err.println("Error: Entrada inválida. Por favor, ingresa el tipo de dato correcto.");
            scanner.nextLine(); // Limpiar el buffer
            return null;
        }

    }

    public static void viewBooks(List<Book> books) {
        for (Book libro : books) {
            System.out.println("-------------------------------------");
            viewBook(libro);
            System.out.println("-------------------------------------");
            System.out.println();
        }
    }

    public static void viewBook(Book libro) {
        System.out.println("Datos del Libro:");
        System.out.println("Nombre: " + libro.getNameBook());
        System.out.println("ISBN: " + libro.getIdBook());
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

    public void editarLibro(Book libro) {

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
        System.out.println("7. Stock");
        System.out.println("8. Vendidos");
        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch (opcion) {
            case 1:
                libro.setNameBook(pedirDato(requestNameBook));
                break;
            case 2:
                libro.setAuthor(pedirDato(requestNameAuthor));
                break;
            case 3:
                libro.setPublisher(pedirDato(requestEditorial));
                break;
            case 4:
                libro.setGenero(pedirDato(requestGen));
                break;
            case 5:
                libro.setLanguage(pedirDato(requestLanguage));
                break;
            case 6:
                libro.setSynopsis(pedirDato(requestSynopsis));
                break;
            case 7:
                libro.setStock(pedirEntero(requestStock));
                break;
            case 8:
                libro.setSold(pedirEntero(requestStock));
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    @Override
    public Integer pedirEntero(Object o) {
        if (o instanceof String) {
            while (true) {
                try {
                    System.out.println(o);
                    String input = scanner.nextLine();

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
        return null;

    }

    @Override
    public Double pedirDouble(Object o) {
        if (o instanceof String) {
            while (true) {
                try {
                    System.out.println(o);
                    String input = scanner.nextLine().trim();

                     return  Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    System.err.println("Entrada inválida. Por favor, ingrese un número válido.");
                }
            }
        }
        return null;
    }

    @Override
    public String pedirDato(Object o) throws MisExcepciones {
        String dato = null;
        if (o instanceof String) {
            System.out.println(o);
            dato = scanner.nextLine();

            while (ClienteView.checkInput(dato)) {
                System.out.println("Reingrese el dato.");
                dato = scanner.nextLine();
            }
        }
        return dato;
    }
}