package org.example.entity;

public class Admin extends Persona {

    private final String departament;

    private final  String speciality;

    public Admin(String dni, String name, String lastName, Integer age, String email, String phone, String adress, String password, boolean rol, String departament, String speciality) {
        super(dni, name, lastName, age, email, phone, adress, password, rol);
        this.departament = departament;
        this.speciality = speciality;
    }


    public String getSpeciality() {
        return speciality;
    }

    public String getDepartament() {
        return departament;
    }
}
