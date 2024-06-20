package org.example.controller;

import java.io.IOException;
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

    public ClienteController() {
    }


    public void inicio(Cliente user) {
        boolean exit = false;
        List<Book> libros = bookController.getListaLibros();

        for (int i = 0; !exit; ) {
            i = restoreIndex(i, libros.size());
            System.out.println(libros.get(i));

            Integer option = clienteView.opcionesCliente();

            switch (option) {
                case 1 -> tryAddBook(user, libros.get(i));
                case 2 -> addBookFav(user, libros.get(i));
                case 3 -> i = anterior(i);
                case 4 -> {
                }
                case 5 -> perfil(user);
                case 6 -> exit = true;
                default -> System.out.println("Opción invalida");
            }
            i++;

        }
    }

    public int restoreIndex(int i, int size) {
        if (i >= size) {
            i = 0;///reiniciamos el contador cuando se alcanza el final de la lista para seguir la ejecucion
        }
        return i;
    }

    public void tryAddBook(Cliente user, Book libro) {
        //intenta agregar un libro al ArrayList, sino captura el error
        try {
            addBook(user, libro);
        } catch (MisExcepciones e) {
            System.out.println(e.getMessage());
            System.out.println(clienteView.addFav);
        }
    }

    public void addBook(Cliente user, Book libro) {
///checkea que haya stock para asignarle el libro al cliente, luego verifica que no este el libro en su ARraylist
        ///si pasa estos dos if..
        if (bookController.checkStock(libro)) {
            if (!user.getCurrentlyBorrowedBook().contains(libro)) {
                user.getCurrentlyBorrowedBook().add(libro);//agrega el libro
                libro.setStock(libro.getStock() - 1);//se descuenta el stock en 1 unidad
                deleteFav(user, libro); //Si el libro solicitado esta en sus Favoritos , es eliminado
                bookRepository.saveLibros(); //se guardan los libros para persistir el nuevo stock
            } else {
                System.out.println(clienteView.addBookError);
            }
        } else {
            addBookFav(user, libro);// si no hay Stock, se le guarda el libro en favoritos para el futuro
            throw MisExcepciones.libroSinStock();
        }
    }

    public void deleteFav(Cliente user, Book libro) {
        if (user.getListFavBook().contains(libro)) {
            user.getListFavBook().remove(libro);
        }
    }

    public void addBookFav(Cliente user, Book libro) {
        user.getListFavBook().add(libro);
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
            switch (option) {
                case 1 -> verLibrosSolicitados(user);
                case 2 -> verLibrosFavoritos(user);
                case 3 -> verHistorialSolicitados(user);
                case 4 -> modificarCliente(user);
                case 5 -> exit = true;
            }
        }
    }

    public void modificarCliente(Cliente user) {
        boolean exit = true;
        while (exit) {
            exit = setUpdate(user, clienteView.modificarCliente(user));
        }
    }

    public boolean setUpdate(Cliente user, int opcion) {
        switch (opcion) {
            case 1 -> user.setName(clienteView.pedirDato(clienteView.requestNameMessage));
            case 2 -> user.setLastName(clienteView.pedirDato(clienteView.requestlastNameMessage));
            case 3 -> user.setEmail(clienteView.pedirDato(clienteView.requestEmailMessage));
            case 4 -> user.setAdress(clienteView.pedirDato(clienteView.requestAddressMessage));
            case 5 -> {
                try {
                    user.setPassword(changePassword(user));
                    System.out.println(clienteView.changePassOK);
                } catch (MisExcepciones e) {
                    System.out.println(e.getMessage());
                }
            }
            case 6 -> {
                return false;
            }
        }
        clienteRepository.update(user);
        return true;
    }

    public String changePassword(Cliente user) {
        if (clienteView.pedirDato(clienteView.requestPasswordMessage).equals(user.getPassword())) {
            return clienteView.pedirDato(clienteView.requestPasswordMessage2);
        } else {
            throw MisExcepciones.wrongPassword();
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
                solicitados.get(i).setStock(solicitados.get(i).getStock() + 1);//aumentamos stock
                setStars(solicitados.get(i));
                solicitados.remove(i); // Eliminar el elemento actual de manera segura
                i--; // Ajustar el índice después de la eliminación
            }
        }

    }

    public void devolution(Cliente user, Book b) {
        user.getReturnHistory().put(b.getIdBook(), b);
    }

    public void setStars(Book book) {
        Double estrellas = clienteView.setStars();
        bookController.addRating(book, estrellas);
    }

    public void verLibrosFavoritos(Cliente user) {
        List<Book> list = new ArrayList<>(user.getListFavBook());
        bookController.viewBooks(list);
    }

    public void verHistorialSolicitados(Cliente user) {
        // Obtener el historial de devoluciones como un mapa
        Map<Integer, Book> historial = user.getReturnHistory();

        // Convertir los valores del mapa en una lista
        List<Book> bookList = new ArrayList<>(historial.values());

        // Llamar al método viewBooks con la lista de libros
        bookController.viewBooks(bookList);
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
            System.out.println(clienteView.registerOK);
        } else {
            System.out.println(clienteView.dniExistente);
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


