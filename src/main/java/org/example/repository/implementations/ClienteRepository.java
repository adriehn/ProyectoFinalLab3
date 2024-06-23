package org.example.repository.implementations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.entity.Cliente;
import org.example.entity.Persona;
import org.example.exception.MisExcepciones;
import org.example.repository.CRUD;
import org.example.repository.Logueo;
import org.example.view.ClienteView;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class ClienteRepository implements CRUD, Logueo {
    private final String CLIENTE_PATH = "src/main/resources/clientes.json";
    private final Gson gson = new Gson();
    public static  Map<String, Cliente> mapClientes = new TreeMap<>();
    private final ClienteView clienteView = new ClienteView();

    public ClienteRepository() {
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    //Implementacion de <Interface> Logueo


    @Override
    public Cliente findUser(Object o) {
        if (o instanceof String) {
            Cliente p = mapClientes.get(o);
            if (p == null) {
                MisExcepciones.usuarioNoEncontrado();
            }
            return p;
        } else {
            MisExcepciones.datoInvalido();
        }
        return null;
    }
    @Override
    public void Register(Object c) {
        mapClientes.put(((Cliente)c).getDni() , (Cliente) c);
        saveClientes();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    //Implementacion de <Interface> CRUD
    @Override
    public void create(Object o) {
        if (o instanceof Persona) {
            Cliente client = new Cliente(((Persona) o).getDni(),((Persona) o).getName(), ((Persona) o).getLastName(), ((Persona) o).getAge(), ((Persona) o).getEmail(), ((Persona) o).getPhone(),((Persona) o).getAdress(),((Persona) o).getPassword(),false);
            client.setIdPersona(((Persona) o).getIdPersona());
            Register(client);
            Persona.setId(Persona.getId()-1);
        }
    }

    @Override
    public Map<String, Cliente> read(Object o) {
        return mapClientes;
    }

    @Override
    public void update(Object o) {

        if (o instanceof Cliente) {
            System.out.println("Actualizado Correctamente.");

            saveClientes();
        }
    }

    @Override
    public void delete(Object o) {
        if (o instanceof Cliente user) {
            user.setUserActive(clienteView.checkDownAccount());
        }
        saveClientes();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Manejo de Json
    public void saveClientes() {
        try (Writer writer = new FileWriter(CLIENTE_PATH)) {
            gson.toJson(mapClientes, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadClientes() {
        try (Reader reader = new FileReader(CLIENTE_PATH)) {
            Type userListType = new TypeToken<Map<String, Cliente>>() {
            }.getType();
            mapClientes = gson.fromJson(reader, userListType);
            if (mapClientes == null) {
                mapClientes = new TreeMap<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
