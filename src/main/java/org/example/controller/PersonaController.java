package org.example.controller;

import org.example.entity.*;
import org.example.repository.implementations.AdminRepository;
import org.example.repository.implementations.ConfigRepository;
import org.example.view.AdminView;
import org.example.view.ClienteView;
import org.example.view.PersonaView;


public class PersonaController {
    private static final Config config = ConfigRepository.loadConfig();
    private final PersonaView personaView = new PersonaView();
    private final AdminRepository adminRepository;
    private final ClienteController clienteController;
    private final ClienteView clienteView;
    private final AdminController adminController;

    public PersonaController(   ) {
        this.adminRepository = new AdminRepository();
        this.clienteController = new ClienteController();
        this.clienteView = new ClienteView();
        this.adminController = new AdminController(new AdminRepository());
    }

    public void createPersona() {
        Persona newUser = personaView.registerUser();
        boolean check = dniCheck(newUser.getDni());//Verificamos si existe el dni
        if (!check) {
            if (config.getAdminPassword().equalsIgnoreCase(newUser.getPassword())) {//Verificamos si la contraseña es la predefinida para registrar un nuevo admin
                System.out.println(AdminView.admMessage);
                adminController.createAdm(newUser);
            } else {
                clienteController.createClient(newUser);
            }
            System.out.println(PersonaView.registerOK);
        } else {
            System.out.println(PersonaView.dniExistente);
            //ajustamos el id estatico para mantener un id real en base a cuantos usuarios se guardaron realmente
            //Porque al instanciar un objeto con un dni existente, el id aumentaba igual.
            Persona.setId(Persona.getId()-1);
        }

    }

    public boolean dniCheck(String dni) {
        if (clienteController.getListPersonas().containsKey(dni)) {
            return true;
        }
        return adminController.getListPersonas().containsKey(dni);
    }
}

