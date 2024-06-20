package org.example.view;

import org.example.entity.Cliente;
import org.example.exception.MisExcepciones;

import java.util.Scanner;

public class ClienteView {

    public final String requestNameMessage = "\nIngrese su nombre: ";
    public final String requestDniMessage = "\nIngrese su numero de DNI (sin puntos, ni coma): ";
    public final String requestlastNameMessage = "\nIngrese su apellido: ";
    public final String requestEmailMessage = "\nIngrese su email: ";
    public final String requestPhoneMessage = "\nIngrese su numero de teléfono: ";
    public final String requestAddressMessage = "\nIngrese su direccion: ";
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


        String dni = pedirDato(requestDniMessage);

        String name = pedirDato(requestNameMessage);

        String lastName = pedirDato(requestlastNameMessage);

        Integer age = pedirEntero();

        String email = pedirDato(requestEmailMessage);

        String phone = pedirDato(requestPhoneMessage);

        String address = pedirDato(requestAddressMessage);

        String passwordInput = pedirDato(requestPasswordMessage);

        return new Cliente(dni, name, lastName, age, email, phone, address, passwordInput, false);

    }

    public String pedirDato(String mensaje) throws MisExcepciones {
        System.out.println(mensaje);
        String dato = scanner.nextLine();

        while (!checkInput(dato)) {
            System.out.println("Reingrese el dato.");
            dato = scanner.nextLine();
        }

        return dato;
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

    private Integer pedirEntero() {
        while (true) {
            try {
                System.out.println("Ingrese su edad: ");
                String input = scanner.nextLine();

                // Verificar si la entrada está vacía o contiene solo espacios en blanco
                if (input.trim().isEmpty()) {
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

    public Integer opcionesCliente() {
        System.out.println("01. Solicitar.");
        System.out.println("02. Agregar a Favoritos");
        System.out.println("03. Anterior.");
        System.out.println("04. Siguiente.");
        System.out.println("05. Perfil.");
        System.out.println("06. Salir..");
        System.out.print("Ingresar opcion: ");
        return scanner.nextInt();
    }

    public Integer opcionesLibrosClientes() {
        System.out.println("01. Ver libros solicitados.");
        System.out.println("02. Ver libros Favoritos.");
        System.out.println("03. Ver Historial libros solicitados.");
        System.out.println("04. Modificar datos.");
        System.out.println("05. Salir.");
        return scanner.nextInt();
    }

    public Double setStars() {
        System.out.println("De 1 a 5 estrellas, que tal te parecio este libro?");
        Double estrellas = scanner.nextDouble();
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

