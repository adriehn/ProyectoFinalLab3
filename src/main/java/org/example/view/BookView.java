package org.example.view;

import org.example.entity.Book;
import org.example.exception.MisExcepciones;
import org.example.repository.View;

import java.text.DecimalFormat;
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
            String nombreLibro = pedirDato(requestNameBook);
            String autor = pedirDato(requestNameAuthor);
            String editorial = pedirDato(requestEditorial);
            String genero = pedirDato(requestGen);
            String idioma = pedirDato(requestLanguage);
            String sinopsis = pedirDato(requestSynopsis);
            Integer stock = pedirEntero(requestStock);
            Integer vendidos = pedirEntero("Introduce el número de libros vendidos:");
            return new Book(nombreLibro, autor, editorial, genero, idioma, sinopsis, stock, vendidos);
        } catch (InputMismatchException e) {
            System.err.println("Error: Entrada inválida. Por favor, ingresa el tipo de dato correcto.");
            scanner.nextLine(); // Limpiar el buffer
            return null;
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //Metodos para ver la lista de libro modularizada
    public void viewBooks(List<Book> books) {
        for (Book libro : books) {
            System.out.println("-------------------------------------");
            displayInfo(libro);
            System.out.println("-------------------------------------");
            System.out.println();
        }
    }

    @Override
    public void displayInfo(Object object) {
        if (object instanceof Book libro) {
            DecimalFormat formato = new DecimalFormat("#.##"); // Definir el formato de dos decimales

            System.out.println("****************************");
            System.out.println("*       Book Details       *");
            System.out.println("****************************");
            System.out.println("* Name      : " + libro.getNameBook());
            System.out.println("* ISBN      : " + libro.getIdBook());
            System.out.println("* Author    : " + libro.getAuthor());
            System.out.println("* Publisher : " + libro.getPublisher());
            System.out.println("* Genre     : " + libro.getGenero());
            System.out.println("* Language  : " + libro.getLanguage());
            System.out.println("* Synopsis  : " + libro.getSynopsis());

            if (libro.getRate() == 0) {
                System.out.println("* Rating    : Aun no posee puntuación");
            } else {
                System.out.println("* Rating    : " + formato.format(libro.getRate()));
            }

            System.out.println("* Stock     : " + libro.getStock());
            System.out.println("* Sold      : " + libro.getSold());
            System.out.println("* Status    : " + libro.isStatus());
            System.out.println("****************************");
        }
    }

    @Override
    public Integer modifyObject(Object object) {
            System.out.println("¿Qué campo desea editar?");
            System.out.println("1. Nombre del Libro");
            System.out.println("2. Autor");
            System.out.println("3. Editorial");
            System.out.println("4. Género");
            System.out.println("5. Idioma");
            System.out.println("6. Sinopsis");
            System.out.println("7. Stock");
            System.out.println("8. Vendidos");
            System.out.println("9. Volver");
            Integer opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer
            return opcion;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    public boolean editarLibro(Book libro) {
        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return false;
        }
        displayInfo(libro);

        Integer opcion = modifyObject(null);

        switch (opcion) {
            case 1 -> libro.setNameBook(pedirDato(requestNameBook));
            case 2 -> libro.setAuthor(pedirDato(requestNameAuthor));
            case 3 -> libro.setPublisher(pedirDato(requestEditorial));
            case 4 -> libro.setGenero(pedirDato(requestGen));
            case 5 -> libro.setLanguage(pedirDato(requestLanguage));
            case 6 -> libro.setSynopsis(pedirDato(requestSynopsis));
            case 7 -> libro.setStock(pedirEntero(requestStock));
            case 8 -> libro.setSold(pedirEntero(requestStock));
            case 9 -> {
                return false;
            }
            default -> System.out.println("Opción no válida.");
        }
        return true;
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

                    return Double.parseDouble(input);
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

