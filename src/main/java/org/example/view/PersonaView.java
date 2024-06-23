package org.example.view;

import org.example.entity.Persona;

public class PersonaView {
    ClienteView clienteView = new ClienteView();
    public Persona registerUser() {
        String dni = clienteView.pedirDato(clienteView.requestDniMessage);
        String name = clienteView.pedirDato(clienteView.requestNameMessage);
        String lastName = clienteView.pedirDato(clienteView.requestlastNameMessage);
        Integer age = clienteView.pedirEntero(clienteView.requestAge);
        String email = clienteView.pedirDato(clienteView.requestEmailMessage);
        String phone = clienteView.pedirDato(clienteView.requestPhoneMessage);
        String address = clienteView.pedirDato(clienteView.requestAddressMessage);
        String passwordInput = clienteView.pedirDato(clienteView.requestPasswordMessage);
        return new Persona(dni, name, lastName, age, email, phone, address, passwordInput, false);
    }
}
