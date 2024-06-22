package org.example.controller;

import org.example.entity.Admin;
import org.example.entity.Book;
import org.example.entity.Cliente;
import org.example.entity.Messages;
import org.example.exception.MisExcepciones;
import org.example.repository.AdminRepository;
import org.example.repository.ClienteRepository;
import org.example.repository.implementations.Controller;
import org.example.view.AdminView;

import java.util.*;

public class AdminController implements Controller<Admin> {

    Scanner scanner = new Scanner(System.in);
    AdminRepository adminRepository;
    AdminView adminView = new AdminView();

    BookController bookController;

    ClienteRepository clienteRepository = new ClienteRepository();
    ClienteController clienteController = new ClienteController();

    public AdminController(AdminRepository adminRepository, BookController bookController) {
        this.bookController = bookController;
        this.adminRepository = adminRepository;
    }

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

    @Override
    public void inicio(Admin admin) {
        boolean exit = false;

        while (!exit) {
            Integer option = adminView.opcionesAdmin();

            switch (option) {
                case 1 -> bookController.creatBook(); // Agregar
                case 2 -> bookController.editBook(); // Editar
                case 3 -> toListBook();
                case 4 -> searchLibro(null);
                case 5 -> bookController.terminateBook(); // Dar de baja
                case 6 -> SendMessagesEveryone();
                case 7 -> searchClient();
                case 8 -> exit = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    @Override
    public void searchLibro(Admin admin) {
        Book libro = bookController.searchBookId();

        if (libro != null) {
            bookController.viewBook(libro);
        }
    }

    public void toListBook() {
        try {
            bookController.toListBooks(); // Listar
        } catch (MisExcepciones e) {
            System.out.println(e.getMessage());
        }
    }


    public void loadAdm() {
        adminRepository.loadAdm();
    }

    public void SendMessagesEveryone() {
        String contenido = adminView.pedirDato(adminView.sendMensagges);
        enviarMensajeATodos(contenido);
    }

    public void enviarMensajeATodos(String contenido) {
        Messages mensaje = new Messages(contenido);
        Map<String, Cliente> mapaClientes = clienteRepository.read(null); ///convertimos un Map en una coleccion
        Collection<Cliente> coleccionClientes = mapaClientes.values();
        List<Cliente> listaClientes = new ArrayList<>(coleccionClientes);
        for (Cliente cliente : listaClientes) {
            clienteController.recibirMensaje(cliente, mensaje);
        }
    }
}