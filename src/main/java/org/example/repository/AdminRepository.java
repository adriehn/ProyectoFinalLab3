package org.example.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.entity.Admin;
import org.example.entity.Persona;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.CRUD;
import org.example.repository.implementations.Logueo;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class AdminRepository implements CRUD, Logueo {
    private final String ADM_PATH = "src/main/resources/administrativos.json";
    private final Gson gson = new Gson();

    public Map<String, Persona> mapAdm;

    public AdminRepository() {
        this.mapAdm = new TreeMap<>();
    }

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

    @Override
    public Object create() {
        return null;
    }

    @Override
    public Object read(Object o) {
        return null;
    }

    @Override
    public Object update(Object o) {
        return null;
    }

    @Override
    public void delete(Object o) {

    }


}
