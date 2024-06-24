package org.example.controller;

import java.util.*;

import org.example.entity.*;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.BookRepository;
import org.example.repository.implementations.ClienteRepository;
import org.example.repository.Controller;
import org.example.view.ClienteView;
import org.example.view.PersonaView;


public class ClienteController implements Controller {

    private final BookRepository bookRepository = new BookRepository();
    private final BookController bookController = new BookController(bookRepository);

    private final ClienteView clienteView = new ClienteView();
    private final ClienteRepository clienteRepository = new ClienteRepository();

    private final Scanner scanner = new Scanner(System.in);

    public ClienteController() {
    }

    public void createClient(Persona nuevo) {
        clienteRepository.create(nuevo);


    }

    public Map<String, Cliente> getListPersonas() {
        return clienteRepository.read(null);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int restoreIndex(int i, int size) {
        if (i >= size) {
            i = 0;///reiniciamos el contador cuando se alcanza el final de la lista para seguir la ejecucion
        }
        return i;
    }

    public void ClearConsole() {
        ///Como no existe un System("cls) como en C, es la forma mas sencilla de "limpiar" la consola.
        for (int i = 0; i < 6; i++) {
            System.out.println();
        }
    }

    public int checkMessages(Cliente user) {//Contador de mensajes del usuario
        int cont = 0;
        for (Messages mensajes : user.getListMessages()) {
            if (!mensajes.isRead()) {
                cont++;
            }
        }
        return cont;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///METODOS QUE SE LLAMAN EN EL PRIMER MENU DE LOGUEO/REGISTRARSE
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


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void inicio(Object obj) {
        if (obj instanceof Cliente user) {
            user.setUserActive(true);//Ahorramos tiempo al no comprobar, si inicio seccion es porque quiere estar activo.
            boolean exit = false;
            List<Book> libros = bookController.getListaLibros();
            int index = 0;
            while (!exit) {
                ClearConsole();

                int contMensajesNoleidos = checkMessages(user);//metodo que usa un contador para ver cuantos mensajes sin leer tiene
                System.out.println("Tienes (" + contMensajesNoleidos + ") mensajes sin leer");
                System.out.println();
                //controla que el index no sobrepase los limites del array. Se hizo asi para poder reiniciar el contador y que la ejecucion no finalice
                index = restoreIndex(index, libros.size()); ///porque el index igualarse al .size() de la lista, se finalizaba la ejecucion.
                bookController.viewBook(libros.get(index));//tomamos un libro para imprimirlo.
                if (libros.get(index).isStatus())//filtro para mostrar solo libros que el admin dispone
                {
                    Integer option = clienteView.opcionesCliente();
                    switch (option) {
                        case 1 -> tryAddBook(user, libros.get(index)); //Solicita un libro y se agrega a la lista
                        case 2 -> addBookFav(user, libros.get(index));//Lo agrega a favoritos
                        case 3 -> index = previousBook(index);//Se desincrementa el Index para ver la posicion anterior.
                        case 4 -> {//no hace nada, incrementa index
                        }
                        case 5 -> searchLibro(user);//Busca libros por coincidencia de una palabra clave
                        case 6 ->
                                suggestedBooks(user, libros);//Es como este menu, pero recorre la lista de forma aleatoria
                        case 7 -> {
                            profile(user);///menu del usuario, evalua si sigue activo sino finaliza la session
                            if (!user.isUserActive()) {
                                exit = true;
                            }
                        }
                        case 8 -> showUnreadMessages(user);//Muestra los mensajes sin leer
                        case 9 -> viewMesagges(user);//Muestra todos los mensajes recibidos
                        case 10 -> exit = true;
                        default -> System.out.println("Opción invalida");
                    }
                }

                index++;
            }
        }
        ClearConsole();
        ClearConsole();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///CASE 1
    public void tryAddBook(Cliente user, Book libro) {
        //intenta agregar un libro al ArrayList, sino captura el error
        try {
            addBook(user, libro);
        } catch (MisExcepciones e) {
            System.out.println(e.getMessage());
            System.out.println(PersonaView.addFav);
        }
        scanner.nextLine();

    }

    public void addBook(Cliente user, Book libro) {
        //checkea que haya stock para asignarle el libro al cliente, luego verifica que no este el libro en su ARraylist
        //si pasa estos dos if..
        if (bookController.checkStock(libro)) {
            if (!user.getCurrentlyBorrowedBook().contains(libro)) {
                user.getCurrentlyBorrowedBook().add(libro);//agrega el libro
                libro.setStock(libro.getStock() - 1);//se descuenta el stock en 1 unidad
                deleteFav(user, libro); //Si el libro solicitado esta en sus Favoritos , es eliminado
                bookRepository.saveBooks(); //se guardan los libros para persistir el nuevo stock
                System.out.println(PersonaView.addedBorrowedBook);
            } else {
                System.out.println(PersonaView.addBookError);
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///CASE 2 - SE USA EN CASE 1 TAMBIEN
    public void addBookFav(Cliente user, Book libro) {
        if (!user.getCurrentlyBorrowedBook().contains(libro)) {
            user.getListFavBook().add(libro);
        } else {
            System.out.println(PersonaView.redundantFav);
            scanner.nextLine();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///CASE 3
    public int previousBook(int i) {
        ///controlo que el index no sea mayor o igual a -2,
        int index = i - 2;
        if (index < -1) {
            index = -1;//retorno -1, porque al entrar al for, lo incrementa en 1
        }
        return index;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///CASE 5
    @Override
    public void searchLibro(Object object) {
        if (object instanceof Cliente) {
            ClearConsole();
            String busqueda = clienteView.pedirDato("Ingrese el nombre del libro: ");
            List<Book> librosEncontrados = new ArrayList<>(); ///creamos una lista nueva donde se almacenan las coincidencias
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
        //Metodo para verificar que la lista no este vacia, sino lo esta, se muestra de a uno y despliegan opciones.
        if (librosEncontrados.isEmpty()) {
            System.out.println(PersonaView.searchBook);
            scanner.nextLine();
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

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///CASE 6
    public void suggestedBooks(Cliente user, List<Book> book) {//Este menu recorre la lista aleatoriamente
        boolean exit = false;

        // Instanciamos RandomArrayList con la lista de libros.
        RandomArrayList listaRandom = new RandomArrayList((ArrayList<Book>) book);

        while (!exit) {
            ClearConsole();

            // Obtenemos el proximo libro aleatorio
            Book libroActual = listaRandom.getNextRandomBook();
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

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///CASE 7
    public void profile(Cliente user) {
        boolean exit = true;

        while (exit) {
            ClearConsole();

            Integer option = clienteView.opcionesLibrosClientes();
            switch (option) {
                case 1 -> seeRequestedBooks(user);//Ver libros en poder el usuario
                case 2 -> seeBooksFavorites(user);//Ver libros favoritos
                case 3 -> seeHistoryRequested(user);//Ver hisotorial de prestamos
                case 4 -> exit = modifyClient(user);//modificar datos del usuario
                case 5 -> exit = false;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 7.1
    public void seeRequestedBooks(Cliente user) {
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

    public void devolution(Cliente user, Book b) {//Agrega el libro devuelto al historial
        user.getReturnHistory().put(b.getIdBook(), b);
    }

    public void setStars(Book book) {//Metodo para dar puntaje a los libros
        Double estrellas = clienteView.setStars();
        bookController.addRating(book, estrellas);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 7.2
    public void seeBooksFavorites(Cliente user) {
        List<Book> list = new ArrayList<>(user.getListFavBook());
        if (list.isEmpty()) {
            System.out.println(PersonaView.searchBook);
            scanner.nextLine();
        } else {
            bookController.viewBooks(list);
            scanner.nextLine();
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 7.3
    public void seeHistoryRequested(Cliente user) {
        List<Book> bookList = new ArrayList<>(user.getReturnHistory().values());// Obtener el historial de devoluciones y Convertir los valores del mapa en una lista
        bookController.viewBooks(bookList);// Llamar al método viewBooks con la lista de libros
        scanner.nextLine();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 7.4
    public boolean modifyClient(Cliente user) {
        boolean exit = true;
        while (exit) {//un bucle para modificar cuantas cosas quiera sin salir del menu de modificar
            exit = setUpdate(user, clienteView.modifyObject(user));
        }
        return exit;
    }

    public boolean setUpdate(Cliente user, int opcion) {
        switch (opcion) {
            ///Metodo practico para actualizar algun dato o desactivar la cuenta temporalmente
            ///Nos basamos en la logica que un objeto no debe eliminarse, sino desactivarse mediante algun atributo que haga de switch
            ///y poder reactivarse cuando se solicite, siendo que el dni (dato unico) es la key del Map donde se almacenan las Personas
            case 1 -> user.setName(clienteView.pedirDato(PersonaView.requestNameMessage));
            case 2 -> user.setLastName(clienteView.pedirDato(PersonaView.requestlastNameMessage));
            case 3 -> user.setAge(clienteView.pedirEntero(PersonaView.requestAge));
            case 4 -> user.setEmail(clienteView.pedirDato(PersonaView.requestEmailMessage));
            case 5 -> user.setAdress(clienteView.pedirDato(PersonaView.requestAddressMessage));
            case 6 -> user.setPhone(clienteView.pedirDato(PersonaView.requestPhoneMessage));
            case 7 -> {
                try {
                    user.setPassword(changePassword(user));
                    System.out.println(PersonaView.changePassOK);
                } catch (MisExcepciones e) {
                    System.out.println(e.getMessage());
                }
            }
            case 8 -> {
                clienteRepository.delete(user);
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
        ///metodo que para modificar la contraseña debe ingresar su contraseña actual
        if (clienteView.pedirDato(PersonaView.requestPasswordMessage).equals(user.getPassword())) {
            return clienteView.pedirDato(PersonaView.requestPasswordMessage2);
        } else {
            throw MisExcepciones.wrongPassword();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    // Metodos que trabajan con mensajeria
    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 8
    public void showUnreadMessages(Cliente cliente) {
        int cont = 0;
        for (Messages mensaje : cliente.getListMessages()) {
            if (!mensaje.isRead()) {
                System.out.println(mensaje.getContent());
                scanner.nextLine();
                mensaje.Mark_AsRead();
                cont++;
            }
        }
        if (cont == 0) {
            System.out.println(clienteView.newMessagesEmpty);
            scanner.nextLine();
        }
        clienteRepository.saveClientes();

    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 9
    public void viewMesagges(Cliente cliente) {
        if (cliente.getListMessages().isEmpty()) {
            System.out.println(clienteView.newMessagesEmpty);
        } else {
            for (Messages mensaje : cliente.getListMessages()) {
                System.out.println(mensaje.toString());
                mensaje.Mark_AsRead();
                scanner.nextLine();
            }
            clienteRepository.saveClientes();
        }
        scanner.nextLine();

    }

    ///Metodo que llama el administrador para dejarle un mensaje al usuario
    public void receiveMessage(Cliente cliente, Messages mensaje) {
        cliente.getListMessages().add(mensaje);
    }

    public void loadClient() {
        clienteRepository.loadClientes();

    }

    public void saveClientes() {
        clienteRepository.saveClientes();
    }
}