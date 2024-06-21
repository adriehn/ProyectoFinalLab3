package org.example.controller;

import java.util.*;

import org.example.entity.*;
import org.example.exception.MisExcepciones;
import org.example.repository.*;
import org.example.repository.AdminRepository;
import org.example.repository.BookRepository;
import org.example.repository.ClienteRepository;
import org.example.repository.implementations.Controller;
import org.example.view.ClienteView;


public class ClienteController implements Controller {

    private final BookRepository bookRepository = new BookRepository();
    private final BookController bookController = new BookController(bookRepository);

    private final ClienteView clienteView = new ClienteView();
    private final ClienteRepository clienteRepository = new ClienteRepository();
    private final AdminRepository adminRepository = new AdminRepository();

    private static final Config config = ConfigRepository.loadConfig();
    Scanner scanner = new Scanner(System.in);

    public ClienteController() {
    }

    @Override
    public Optional<Cliente> login(String dni, String password) {

        ///Si la llamada a findUser devuelve null, "ofNullable" lo encapsula como un "optional" y lo verificamos en el primer if.
        ///Optional es una clase que utiliza genéricos y verifica si tiene o no un valor dentro. Evitando el "NullPointerException".
        Optional<Cliente> requestedUser = Optional.ofNullable(clienteRepository.findUser(dni));

        if (requestedUser.isEmpty()) {
            return Optional.empty();
        }
        if (requestedUser.get().getPassword().equals(password)) {
            return requestedUser;
        } else {
            return Optional.empty();

        }
    }

    public void ClearConsole() {
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
    }

    @Override
    public void inicio(Object obj) {
        if (obj instanceof Cliente user) {
            user.setUserActive(true);//Ahorramos tiempo al no comprobar, si inicio seccion es porque estara activo.
            boolean exit = false;
            List<Book> libros = bookController.getListaLibros();

            for (int i = 0; !exit; ) {
                ClearConsole();

                int contMensajesNoleidos = checkMessages(user);
                System.out.println("Tienes (" + contMensajesNoleidos + ") mensajes sin leer");
                System.out.println();
                i = restoreIndex(i, libros.size());
                System.out.println(libros.get(i));

                Integer option = clienteView.opcionesCliente();
                switch (option) {
                    case 1 -> tryAddBook(user, libros.get(i));
                    case 2 -> addBookFav(user, libros.get(i));
                    case 3 -> i = anterior(i);
                    case 4 -> {
                    }
                    case 5 -> searchLibro(user);
                    case 6 -> inicio2(user,libros);
                    case 7 -> {
                        perfil(user);
                        if (!user.isUserActive()) {
                            exit = true;
                        }
                    }
                    case 8 -> mostrarMensajesNoLeidos(user);
                    case 9 -> viewMesagges(user);
                    case 10 -> exit = true;
                    default -> System.out.println("Opción invalida");
                }
                i++;
            }
        }
    }

    public void inicio2(Cliente user, List<Book> book) {//Este menu recorre la lista aleatoriamente
        boolean exit = false;

        // Instanciamos RandomArrayListTraversal con la lista de libros
        RandomArrayListTraversal randomTraversal = new RandomArrayListTraversal((ArrayList<Book>) book);

        for (; !exit; ) {
            ClearConsole();

            // Obtenemos el próximo libro aleatorio
            Book libroActual = randomTraversal.getNextRandomBook();
            System.out.println(libroActual);

            Integer option = clienteView.opcionesCliente2();
            switch (option) {
                case 1 -> tryAddBook(user, libroActual);
                case 2 -> addBookFav(user, libroActual);
                case 3 -> {
                }
                case 4 -> exit = true;
                default -> System.out.println("Opción invalida");
            }
        }

    }


    public int checkMessages(Cliente user) {
        int cont = 0;
        for (Messages mensajes : user.getListMessages()) {
            if (!mensajes.isRead()) {
                cont++;
            }
        }
        return cont;
    }


    @Override
    public void searchLibro(Object object) {
        if (object instanceof Cliente) {
            ClearConsole();
            System.out.print("Ingrese el nombre del libro: ");
            String busqueda = scanner.nextLine();
            List<Book> librosEncontrados = new ArrayList<>();
            List<Book> resultados = bookController.getListaLibros();

            for (Book libro : resultados) {
                // Verificar si el nombre o la descripción del producto contiene la palabra de búsqueda
                if (libro.getNameBook().toLowerCase().contains(busqueda.toLowerCase()) ||
                        libro.getSynopsis().toLowerCase().contains(busqueda.toLowerCase())) {
                    librosEncontrados.add(libro);
                }
            }
            checkEmptySearch(librosEncontrados, (Cliente) object);
            ClearConsole();
        }


    }

    public void checkEmptySearch(List<Book> librosEncontrados, Cliente user) {
        if (librosEncontrados.isEmpty()) {
            System.out.println(clienteView.searchBook);
        } else {
            for (Book librosEncontrado : librosEncontrados) {
                System.out.println(librosEncontrado);
                optionsSearchBook(user, librosEncontrado);
            }
        }

    }

    public void optionsSearchBook(Cliente user, Book libro) {

        int option = clienteView.opcionesSearchBook();
        switch (option) {
            case 1 -> tryAddBook(user, libro);
            case 2 -> addBookFav(user, libro);
            case 3 -> {
            }
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
                scanner.nextLine();
            }
        } else {
            addBookFav(user, libro);// si no hay Stock, se le guarda el libro en favoritos para el futuro
            throw MisExcepciones.libroSinStock();
        }
    }

    public void deleteFav(Cliente user, Book libro) {
        user.getListFavBook().remove(libro);
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
        boolean exit = true;

        while (exit) {
            ClearConsole();

            Integer option = clienteView.opcionesLibrosClientes();
            switch (option) {
                case 1 -> verLibrosSolicitados(user);
                case 2 -> verLibrosFavoritos(user);
                case 3 -> verHistorialSolicitados(user);
                case 4 -> exit = modificarCliente(user);
                case 5 -> exit = false;
            }
        }
    }

    public boolean modificarCliente(Cliente user) {
        boolean exit = true;
        while (exit) {
            exit = setUpdate(user, clienteView.modificarCliente(user));
        }
        return exit;
    }

    public boolean setUpdate(Cliente user, int opcion) {
        switch (opcion) {
            case 1 -> user.setName(clienteView.pedirDato(clienteView.requestNameMessage));
            case 2 -> user.setLastName(clienteView.pedirDato(clienteView.requestlastNameMessage));
            case 3 -> user.setAge(clienteView.pedirEntero(clienteView.requestAge));
            case 4 -> user.setEmail(clienteView.pedirDato(clienteView.requestEmailMessage));
            case 5 -> user.setAdress(clienteView.pedirDato(clienteView.requestAddressMessage));
            case 6 -> user.setPhone(clienteView.pedirDato(clienteView.requestPhoneMessage));
            case 7 -> {
                try {
                    user.setPassword(changePassword(user));
                    System.out.println(clienteView.changePassOK);
                } catch (MisExcepciones e) {
                    System.out.println(e.getMessage());
                }
            }
            case 8 -> {
                user.setUserActive(clienteView.checkDownAccount());
                if (!user.isUserActive()) {
                    return false;
                }
            }
            case 9 -> {
                return false;
            }
        }
        clienteRepository.update(user);
        scanner.nextLine();
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
            } else {
                ClearConsole();
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
        if (list.isEmpty()) {
            System.out.println(clienteView.searchBook);
            scanner.nextLine();
        } else {
            bookController.viewBooks(list);
            scanner.nextLine();
        }

    }

    public void verHistorialSolicitados(Cliente user) {
        List<Book> bookList = new ArrayList<>(user.getReturnHistory().values());// Obtener el historial de devoluciones y Convertir los valores del mapa en una lista
        bookController.viewBooks(bookList);// Llamar al método viewBooks con la lista de libros
        scanner.nextLine();
    }

    //////////////////////////////////////////////////////////////////////////////////
    // Metodos que trabajan con mensajeria
    public void mostrarMensajesNoLeidos(Cliente cliente) {
        for (Messages mensaje : cliente.getListMessages()) {
            if (!mensaje.isRead()) {
                System.out.println(mensaje.getContent());
                scanner.nextLine();
                mensaje.Mark_AsRead();
            }
        }
        clienteRepository.saveClientes();

    }

    public void recibirMensaje(Cliente cliente, Messages mensaje) {
        cliente.getListMessages().add(mensaje);
    }

    public void viewMesagges(Cliente cliente) {
        for (Messages mensaje : cliente.getListMessages()) {
            System.out.println(mensaje.getContent());
            scanner.nextLine();
        }
        clienteRepository.saveClientes();
    }

    public void createPersona() {

        Cliente newcliente = clienteView.registerCliente();

        boolean check = clienteRepository.dniCheck(newcliente.getDni());//Verificamos si existe el dni

        if (!check) {
            if (config.getAdminPassword().equalsIgnoreCase(newcliente.getPassword())) {//Verificamos si la contraseña es la predefinida para registrar un nuevo admin

                System.out.println(clienteView.admMessage);
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


}

