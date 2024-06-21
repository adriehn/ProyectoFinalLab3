package org.example.view;

import org.example.entity.Cliente;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.View;

import java.util.Scanner;

public class ClienteView implements View {

    public final String requestNameMessage = "\nIngrese su nombre: ";
    public final String requestDniMessage = "\nIngrese su numero de DNI (sin puntos, ni coma): ";
    public final String requestlastNameMessage = "\nIngrese su apellido: ";
    public final String requestEmailMessage = "\nIngrese su email: ";
    public final String requestPhoneMessage = "\nIngrese su numero de teléfono: ";
    public final String requestAddressMessage = "\nIngrese su direccion: ";
    public final String requestAge = "\nIngrese su edad: ";
    public static final String requestPasswordMessage = "\nIngrese su contraseña: ";
    public static final String requestPasswordMessage2 = "\nIngrese su nueva contraseña: ";
    public static final String admMessage = "\nSe asigno correctamente su rol como administrativo.\n";
    public static final String requestDepartmentMessage = "\nIngrese su departamento: ";
    public static final String requestSpecialityMessage = "\nIngrese su especialidad: ";

    public static final String registerOK = "\nRegistrado exitosamente!\n";
    public static final String dniExistente = "\nEl DNI ingresado ya posee una cuenta.\n";
    public static final String addFav = "\nSe ha agregado a sus favoritos.\n";
    public static final String addBookError = "\nActualmente ya tiene una copia en su poder.\n";
    public static final String changePassOK = "\nSu contraseña se ha actualizado correctamente.\n";

    Scanner scanner = new Scanner(System.in);



    public Cliente registerCliente() {


        String dni = (String) pedirDato(requestDniMessage);

        String name = (String) pedirDato(requestNameMessage);

        String lastName = (String) pedirDato(requestlastNameMessage);

        Integer age = (Integer) pedirEntero(requestAge);

        String email = (String) pedirDato(requestEmailMessage);

        String phone = (String) pedirDato(requestPhoneMessage);

        String address = (String) pedirDato(requestAddressMessage);

        String passwordInput = (String) pedirDato(requestPasswordMessage);

        return new Cliente(dni, name, lastName, age, email, phone, address, passwordInput, false);

    }


    public static boolean checkInput(String atributo) throws MisExcepciones {
        try {
            return validarScanner(atributo);
        } catch (MisExcepciones miExcepcion) {
            System.err.println(miExcepcion.getMessage());
            return false;
        }
    }

    public static boolean validarScanner(String atributo) throws MisExcepciones {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new MisExcepciones("El dato no puede estar vacío.");
        }
        return true;
    }

    @Override
    public Object pedirEntero(Object o) {
        if (o instanceof String) {
            while (true) {
                try {
                    System.out.println(o);
                    String input = scanner.nextLine().trim();

                    // Verificar si la entrada está vacía o contiene solo espacios en blanco
                    if (input.isEmpty()) {
                        System.err.println("Entrada inválida. Por favor, ingrese un número entero.");
                        continue;
                    }
                    // Intentar convertir la entrada a un número entero
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.err.println("Entrada inválida. Por favor, ingrese un número entero.");
                }
            }
        }
        return null;
    }


    @Override
    public Object pedirDouble(Object o) {
        if (o instanceof String) {
            while (true) {
                try {
                    System.out.println(o);
                    String input = scanner.nextLine().trim();

                    // Intentar convertir la entrada a un número double
                    double numero = Double.parseDouble(input);

                    // Si la conversión fue exitosa, retornar el número
                    return numero;
                } catch (NumberFormatException e) {
                    System.err.println("Entrada inválida. Por favor, ingrese un número válido.");
                }
            }
        }
        return null;
    }

    @Override
    public Object pedirDato(Object o) throws MisExcepciones {
        String dato = null;
        if (o instanceof String) {
            System.out.println(o);
            dato = scanner.nextLine();

            while (!checkInput(dato)) {
                System.out.println("Reingrese el dato.");
                dato = scanner.nextLine();
            }
        }
        return ((String) dato);
    }

    public Integer opcionesCliente() {
        System.out.println("01. Solicitar.");
        System.out.println("02. Agregar a Favoritos");
        System.out.println("03. Anterior.");
        System.out.println("04. Siguiente.");
        System.out.println("05. Buscar libro");
        System.out.println("06. Perfil.");
        System.out.println("07. Cerrar Session..");
        System.out.print("Ingresar opcion: ");
        return scanner.nextInt();
    }

    public Integer opcionesLibrosClientes() {
        System.out.println("01. Ver libros solicitados.");
        System.out.println("02. Ver libros Favoritos.");
        System.out.println("03. Ver Historial libros solicitados.");
        System.out.println("04. Modificar datos.");
        System.out.println("05. Volver.");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    public Double setStars() {
        Double estrellas = (Double) pedirDouble("De 1 a 5 estrellas, que tal te parecio este libro?");
        scanner.nextLine();
        if (estrellas < 0 || estrellas > 5) {
            System.out.println("Ingrese de 1 a 5 por favor.");
            estrellas = setStars();
        }
        return estrellas;
    }

    public int modificarCliente(Cliente user) {

        displayClienteInfo(user);
        System.out.println("01. Nombre.");
        System.out.println("02. Apellido.");
        System.out.println("03. Email.");
        System.out.println("04. Direccion.");
        System.out.println("05. Contraseña");
        System.out.println("06. Volver.");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }


    public void displayClienteInfo(Cliente cliente) {
        System.out.println("****************************");
        System.out.println("*      Cliente Details     *");
        System.out.println("****************************");
        System.out.println("* DNI       : " + cliente.getDni());
        System.out.println("* Name      : " + cliente.getName());
        System.out.println("* Last Name : " + cliente.getLastName());
        System.out.println("* Age       : " + cliente.getAge());
        System.out.println("* Email     : " + cliente.getEmail());
        System.out.println("* Phone     : " + cliente.getPhone());
        System.out.println("* Address   : " + cliente.getAdress());
        System.out.println("* Pass   : " + cliente.getPassword());
        System.out.println("****************************");
    }


}

