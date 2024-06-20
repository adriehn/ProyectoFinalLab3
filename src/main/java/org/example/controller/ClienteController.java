package org.example.controller;

import java.util.*;

import org.example.entity.*;
import org.example.exception.MisExcepciones;
import org.example.repository.*;
import org.example.repository.AdminRepository;
import org.example.repository.BookRepository;
import org.example.repository.ClienteRepository;
import org.example.view.ClienteView;


public class ClienteController {

    private final BookController bookController = new BookController();
    private final BookRepository bookRepository = new BookRepository();

    private final ClienteView clienteView = new ClienteView();
    private final ClienteRepository clienteRepository = new ClienteRepository();

    private final AdminRepository adminRepository = new AdminRepository();

    private static final Config config = ConfigRepository.loadConfig();
    Scanner scanner = new Scanner(System.in);

    public ClienteController() {}


    public void inicio(Cliente user) {
        boolean exit = false;
        List<Book> libros = bookRepository.getListaLibros();

        for (int i = 0; i < libros.size() && !exit; i++) {
            System.out.println(libros.get(i));

            Integer option = clienteView.opcionesCliente();
            scanner.nextLine();

            switch (option) {
                case 1 -> addBook(user, i, libros.get(i));
                case 2 -> addBookFav(user, i);
                case 3 -> i = anterior(i);
                case 4 -> {}
                case 5 -> perfil(user);
                case 6 -> exit = true;
                default -> System.out.println("Opción invalida");
            }
        }
    }

    public int anterior(int i) {
        ///controlo que el index no sea mayor o igual a -2,
        int index = i - 2;
        if (index < -1) {
            index = -1;//retorno -1, porque al entrar al for, lo incrementa en 1
        }
        return index;
    }

    public void perfil(Cliente user) {
        boolean exit = false;

        while (!exit) {

            Integer option = clienteView.opcionesLibrosClientes();
            scanner.nextLine();

            switch (option) {
                case 1 -> verLibrosSolicitados(user);
                case 2 -> verLibrosFavoritos(user);
                case 3 -> verHistorialSolicitados(user);
                case 4 -> exit = true;
            }
        }
    }

    public void verLibrosSolicitados(Cliente user) {
        List<Book> solicitados = user.getCurrentlyBorrowedBook();

        for (int i = 0; i < solicitados.size(); i++) {

            bookController.viewBook(solicitados.get(i));

            System.out.println("01. Devolver? s/n.");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("s")) {
                devolution(user, solicitados.get(i));
                solicitados.remove(i); // Eliminar el elemento actual de manera segura
                i--; // Ajustar el índice después de la eliminación
            }
        }

    }

    public void devolution(Cliente user, Book b) {
        user.getReturnHistory().put(b.getIdBook(), b);
    }

    public void verLibrosFavoritos(Cliente user) {
        Set<Book> fav = user.getListFavBook();

        bookController.viewBooks((List<Book>) fav);

        for (Book b : fav) {
            System.out.println(b);
        }
    }

    public void verHistorialSolicitados(Cliente user) {
        // Obtener el historial de devoluciones como un mapa
        Map<Integer, Book> historial = user.getReturnHistory();

        // Convertir los valores del mapa en una lista
        List<Book> bookList = new ArrayList<>(historial.values());

        // Llamar al método viewBooks con la lista de libros
        bookController.viewBooks(bookList);

        /*for (Map.Entry<Integer, Book> entry : historial.entrySet()) {

            Integer key = entry.getKey();
            Book libro = entry.getValue();
            System.out.println("ID: " + key + "\n" + libro);

        }*///chequear si esta bien
    }

    public void addBookFav(Cliente cliente, int index) {
        cliente.getListFavBook().add(BookRepository.listaLibros.get(index));
    }

    public void addBook(Cliente user, int index, Book libro) {
        if (bookController.checkStock(libro)) {
            List<Book> aux = bookRepository.getListaLibros();
            if (index >= 0 && index < aux.size()) {
                user.getCurrentlyBorrowedBook().add(aux.get(index));
                Book b = aux.get(index);
                b.setStock(b.getStock() - 1);
                ///analizar si esta en fav para eliminarlo
            } else {
                throw new IndexOutOfBoundsException("Codigo de libro erroneo.");
            }
        }
    }

    public void createPersona() {

        Cliente newcliente = clienteView.registerCliente();

        boolean check = clienteRepository.dniCheck(newcliente.getDni());//Verificamos si existe el dni

        if (!check) {
            if (config.getAdminPassword().equalsIgnoreCase(newcliente.getPassword())) {//Verificamos si la contraseña es la predefinida para registrar un nuevo admin

                System.out.println(ClienteView.admMessage);
                String newPassword = clienteView.pedirDato(clienteView.requestPasswordMessage);
                String department = clienteView.pedirDato(clienteView.requestDepartmentMessage);
                String speciality = clienteView.pedirDato(clienteView.requestSpecialityMessage);

                adminRepository.Register(new Admin(newcliente.getDni(), newcliente.getName(), newcliente.getLastName(), newcliente.getAge(), newcliente.getEmail(), newcliente.getPhone(), newcliente.getAdress(), newPassword, true, department, speciality));

            } else {
                clienteRepository.Register(newcliente);
            }
        } else {
            MisExcepciones.dniExistente();
        }
    }

    public Optional<Cliente> login(String dni, String password) {

        ///Si la llamada a findUser devuelve null, "ofNullable" lo encapsula como un "optional" y lo verificamos en el primer if.
        ///Optional es una clase que utiliza genéricos y verifica si tiene o no un valor dentro. Evitando el "NullPointerException".
        Optional<Cliente> requestedUser = Optional.ofNullable(clienteRepository.findUser(dni));

        if (requestedUser.isEmpty()) {
            System.out.println("Usuario no existe");
            return Optional.empty();

        }
        if (requestedUser.get().getPassword().equals(password)) {
            System.out.println("Ingreso exitoso");

            return requestedUser;
        } else {
            System.out.println("Contraseña incorrecta");

            return Optional.empty();

        }

    }



}


