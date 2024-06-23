package org.example.view;

import org.example.entity.Persona;

public class PersonaView {
    public static final String requestNameMessage = "\nIngrese su nombre: ";
    public static final String requestDniMessage = "\nIngrese su numero de DNI (sin puntos, ni coma): ";
    public static final String requestlastNameMessage = "\nIngrese su apellido: ";
    public static final String requestEmailMessage = "\nIngrese su email: ";
    public static final String requestPhoneMessage = "\nIngrese su numero de teléfono: ";
    public static final String requestAddressMessage = "\nIngrese su direccion: ";
    public static final String requestAge = "\nIngrese su edad: ";
    public static final String requestPasswordMessage = "\nIngrese su contraseña: ";
    public static final String requestPasswordMessage2 = "\nIngrese su nueva contraseña: ";
    public static final String requestDepartmentMessage = "\nIngrese su departamento: ";
    public static final String requestSpecialityMessage = "\nIngrese su especialidad: ";

    public static final String registerOK = "\nRegistrado exitosamente!\n";
    public static final String dniExistente = "\nEl DNI ingresado ya posee una cuenta.\n";
    public static final String addFav = "\nSe ha agregado a sus favoritos.\n";
    public static final String addBookError = "\nActualmente ya tiene una copia en su poder.\n";
    public static final String changePassOK = "\nSu contraseña se ha actualizado correctamente.\n";
    public static final String searchBook = "\nNo se encontraron coincidencias.\n";
    public static final String addedBorrowedBook = "\nSe ha reservado el libro correctamente.\n";
    public static final String redundantFav = "\n\"El libro esta en su posesion, es redundante agregar a sus favoritos\"\n";
    ClienteView clienteView = new ClienteView();
    public Persona registerUser() {
        String dni = clienteView.pedirDato(requestDniMessage);
        String name = clienteView.pedirDato(requestNameMessage);
        String lastName = clienteView.pedirDato(requestlastNameMessage);
        Integer age = clienteView.pedirEntero(requestAge);
        String email = clienteView.pedirDato(requestEmailMessage);
        String phone = clienteView.pedirDato(requestPhoneMessage);
        String address = clienteView.pedirDato(requestAddressMessage);
        String passwordInput = clienteView.pedirDato(requestPasswordMessage);
        return new Persona(dni, name, lastName, age, email, phone, address, passwordInput, false);
    }
}
