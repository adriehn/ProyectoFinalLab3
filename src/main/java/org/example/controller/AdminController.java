package org.example.controller;

import org.example.entity.*;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.AdminRepository;
import org.example.repository.implementations.ClienteRepository;
import org.example.repository.Controller;
import org.example.view.AdminView;
import org.example.view.ClienteView;
import org.example.view.PersonaView;

import java.util.*;

public class AdminController implements Controller<Admin> {
    private final AdminRepository adminRepository;
    private final AdminView adminView = new AdminView();
    private BookController bookController;
    private final ClienteRepository clienteRepository = new ClienteRepository();
    private final ClienteController clienteController = new ClienteController();
    private final ClienteView clienteView = new ClienteView();

    public AdminController(AdminRepository adminRepository, BookController bookController) {
        this.bookController = bookController;
        this.adminRepository = adminRepository;
    }

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Metodos que la clase Persona utiliza para crear un ADM y listarlo.
    public void createAdm(Persona nuevo) {
        try {
            adminRepository.create(nuevo);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Map<String, Admin> getListPersonas() {
        return adminRepository.read(null);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Optional<Admin> login(String dni, String password) {

        ///Si la llamada a findUser devuelve null, "ofNullable" lo encapsula como un "optional" y lo verificamos en el primer if.
        ///Optional es una clase que utiliza genéricos y verifica si tiene o no un valor dentro. Evitando el "NullPointerException".
        Optional<Admin> requestedUser = Optional.ofNullable(adminRepository.findUser(dni));

        if (requestedUser.isEmpty()) {
            return Optional.empty();
        }
        if (requestedUser.get().getPassword().equals(password)) {
            return requestedUser;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void inicio(Admin admin) {
        boolean exit = false;

        while (!exit) {
            Integer option = adminView.opcionesAdmin();

            switch (option) {
                case 1 -> bookController.creatBook(); // Agregar un libro nuevo
                case 2 -> bookController.editBook(); // Editar un libro
                case 3 -> toListBook();//Listar los libros
                case 4 -> searchLibro(null);
                case 5 -> bookController.terminateBook(); // Dar de baja/alta un libro
                case 6 -> SendMessagesEveryone();
                case 7 -> searchClient();
                case 8 -> exit = setUpdate(admin);
                case 9 -> exit = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    public boolean setUpdate(Admin user) {
        boolean exit = true;
        while (exit) {
            adminView.displayInfo(user);
            Integer opcion = adminView.modifyObject(null);
            switch (opcion) {
                case 1 -> user.setName(adminView.pedirDato(PersonaView.requestNameMessage));
                case 2 -> user.setLastName(adminView.pedirDato(PersonaView.requestlastNameMessage));
                case 3 -> user.setAge(adminView.pedirEntero(PersonaView.requestAge));
                case 4 -> user.setEmail(adminView.pedirDato(PersonaView.requestEmailMessage));
                case 5 -> user.setAdress(adminView.pedirDato(PersonaView.requestAddressMessage));
                case 6 -> user.setPhone(adminView.pedirDato(PersonaView.requestPhoneMessage));
                case 7 -> {
                    try {
                        user.setPassword(changePassword(user));
                        System.out.println(PersonaView.changePassOK);
                    } catch (MisExcepciones e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 8 -> {
                    adminRepository.delete(user);
                    if (!user.isUserActive()) {
                        return false;
                    }
                }
                case 9 -> {
                    return false;
                }
            }
            clienteRepository.update(user);
        }
        return false;

    }

    public String changePassword(Admin user) {
        ///metodo que para modificar la contraseña debe ingresar su contraseña actual
        if (adminView.pedirDato(PersonaView.requestPasswordMessage).equals(user.getPassword())) {
            return adminView.pedirDato(PersonaView.requestPasswordMessage2);
        } else {
            throw MisExcepciones.wrongPassword();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 3
    public void toListBook() {
        try {
            bookController.toListBooks(); // Listar
        } catch (MisExcepciones e) {
            System.out.println(e.getMessage());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 4
    @Override
    public void searchLibro(Admin admin) {
        //Por parametro recive un null porque no necesita recibir nada
        //en cambio en otras llamadas de otra clase se implementa distinto y si necesita recibir un parametro,
        Book libro = bookController.searchBookId();

        if (libro != null) {
            bookController.viewBook(libro);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 6
    public void SendMessagesEveryone() {
        String contenido = adminView.pedirDato(adminView.sendMensagges);
        sendMessageToAll(contenido);
    }

    public void sendMessageToAll(String contenido) {
        Messages mensaje = new Messages(contenido);
        Map<String, Cliente> mapaClientes = clienteRepository.read(null); ///convertimos un Map en una coleccion
        Collection<Cliente> coleccionClientes = mapaClientes.values();
        List<Cliente> listaClientes = new ArrayList<>(coleccionClientes);
        for (Cliente cliente : listaClientes) {
            clienteController.receiveMessage(cliente, mensaje);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Case 7
    public void searchClient() {
        String dni = adminView.pedirDato(adminView.searchUser);
        try {
            Optional<Cliente> requestedUser = Optional.ofNullable(clienteRepository.findUser(dni));
            requestedUser.ifPresentOrElse(this::sendMesagge, MisExcepciones::usuarioNoEncontrado);
        } catch (MisExcepciones e) {
            System.out.printf(e.getLocalizedMessage());
        }
    }

    private void sendMesagge(Cliente cliente) {
        String content = adminView.pedirDato(adminView.sendMensagges);
        Messages messages = new Messages(content);
        cliente.getListMessages().add(messages);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Metodo que llama al repositorio para cargar el json.
    public void loadAdm() {
        adminRepository.loadAdm();
    }

    public void saveAdm() {
        adminRepository.saveAdm();
    }

}