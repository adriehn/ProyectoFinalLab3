package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;
import org.example.entity.Admin;
import org.example.entity.Cliente;
import org.example.entity.Persona;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.CRUD;
import org.example.repository.implementations.Logueo;
import org.example.view.AdminView;
import org.example.view.ClienteView;
import org.example.view.PersonaView;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class AdminRepository implements CRUD, Logueo {
    private final String ADM_PATH = "src/main/resources/administrativos.json";
    private final Gson gson = new Gson();
    private final ClienteView clienteView = new ClienteView();
    public static Map<String, Admin> mapAdm;

    public AdminRepository() {
        this.mapAdm = new TreeMap<>();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    //Implementacion de <Interface> Logueo
    @Override
    public void Register(Object a) {
        if (a instanceof Admin) {
            Admin adm = (Admin) a;
            mapAdm.put(adm.getDni(), adm);
            saveAdm();
        }
    }

    @Override
    public Admin findUser(Object o) {
        if (o instanceof String) {
            Admin a = (Admin) mapAdm.get(o);
            if (a == null) {
                MisExcepciones.usuarioNoEncontrado();
            }
            return a;
        } else {
            MisExcepciones.datoInvalido();
        }
        return null;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    //Implementacion de <Interface> CRUD
    @Override
    public void create(Object persona) {
        if (persona instanceof Admin admin) {
            Register(admin);
        } else if (persona instanceof Persona) {
            Persona personaObj = (Persona) persona;
            Admin admin = new Admin(
                    personaObj.getDni(),
                    personaObj.getName(),
                    personaObj.getLastName(),
                    personaObj.getAge(),
                    personaObj.getEmail(),
                    personaObj.getPhone(),
                    personaObj.getAdress(),
                    clienteView.pedirDato(PersonaView.requestPasswordMessage),
                    true,
                    clienteView.pedirDato(PersonaView.requestDepartmentMessage),
                    clienteView.pedirDato(PersonaView.requestSpecialityMessage)

            );
            admin.setIdPersona(((Persona) persona).getIdPersona());
            Register(admin);
        } else {
            throw new IllegalArgumentException("El objeto no es una instancia de Admin o Persona");
        }
        Persona.setId(Persona.getId() - 1);

    }

    @Override
    public Map<String, Admin> read(Object o) {
        return mapAdm;
    }

    @Override
    public void update(Object o) {
        if (o instanceof Admin) {
            System.out.println("Actualizado Correctamente.");
            saveAdm();
        }
    }

    @Override
    public void delete(Object o) {
        if (o instanceof Admin admin) {
            admin.setUserActive(clienteView.checkDownAccount());
        }
        saveAdm();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Manejo de Json
    public void loadAdm() {
        try (Reader reader = new FileReader(ADM_PATH)) {
            Type userListType = new TypeToken<Map<String, Admin>>() {
            }.getType();
            mapAdm = gson.fromJson(reader, userListType);
            if (mapAdm == null) {
                mapAdm = new TreeMap<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAdm() {
        try (Writer writer = new FileWriter(ADM_PATH)) {
            gson.toJson(mapAdm, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}