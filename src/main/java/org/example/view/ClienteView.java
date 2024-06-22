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
    public  final String requestPasswordMessage = "\nIngrese su contraseña: ";
    public  final String requestPasswordMessage2 = "\nIngrese su nueva contraseña: ";
    public  final String admMessage = "\nSe asigno correctamente su rol como administrativo.\n";
    public  final String requestDepartmentMessage = "\nIngrese su departamento: ";
    public  final String requestSpecialityMessage = "\nIngrese su especialidad: ";

    public  final String registerOK = "\nRegistrado exitosamente!\n";
    public  final String dniExistente = "\nEl DNI ingresado ya posee una cuenta.\n";
    public  final String addFav = "\nSe ha agregado a sus favoritos.\n";
    public  final String addBookError = "\nActualmente ya tiene una copia en su poder.\n";
    public  final String changePassOK = "\nSu contraseña se ha actualizado correctamente.\n";
    public  final String searchBook = "\nNo se encontraron coincidencias.\n";

    Scanner scanner = new Scanner(System.in);


    public Cliente registerCliente() {
        String dni = pedirDato(requestDniMessage);
        String name = pedirDato(requestNameMessage);
        String lastName = pedirDato(requestlastNameMessage);
        Integer age = pedirEntero(requestAge);
        String email = pedirDato(requestEmailMessage);
        String phone = pedirDato(requestPhoneMessage);
        String address = pedirDato(requestAddressMessage);
        String passwordInput = pedirDato(requestPasswordMessage);
        return new Cliente(dni, name, lastName, age, email, phone, address, passwordInput, false);
    }

    @Override
    public String pedirDato(Object o) throws MisExcepciones {
        if (o instanceof String) {
            System.out.println(o);
            String dato = scanner.nextLine();
            while (checkInput(dato)) {
                System.out.println("Reingrese el dato.");
                dato = scanner.nextLine();
            }
            return dato;
        }
        return null;
    }

    public static boolean checkInput(String atributo) throws MisExcepciones {
        try {
            return validarScanner(atributo);
        } catch (MisExcepciones miExcepcion) {
            System.err.println(miExcepcion.getMessage());
            return true;
        }
    }

    public static boolean validarScanner(String atributo) throws MisExcepciones {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new MisExcepciones("El dato no puede estar vacío.");
        }
        return false;
    }

    @Override
    public Integer pedirEntero(Object o) {
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
    public Double pedirDouble(Object o) {
        if (o instanceof String) {
            while (true) {
                try {
                    System.out.println(o);
                    String input = scanner.nextLine().trim();

                    // Intentar convertir la entrada a un numero double
                    return Double.parseDouble(input);

                } catch (NumberFormatException e) {
                    System.err.println("Entrada inválida. Por favor, ingrese un número válido.");
                }
            }
        }
        return null;
    }


    public Integer opcionesCliente() {
        System.out.println("01. Solicitar.");
        System.out.println("02. Agregar a Favoritos");
        System.out.println("03. Anterior.");
        System.out.println("04. Siguiente.");
        System.out.println("05. Buscar libro");
        System.out.println("06. Ver libros sugeridos");
        System.out.println("07. Perfil.");
        System.out.println("08. Ver mensajes no leidos.");
        System.out.println("09. Ver todos los mensajes.");
        System.out.println("10. Cerrar Session..");
        System.out.print("Ingresar opcion: ");
        return scanner.nextInt();
    }
    public Integer opcionesCliente2() {
        System.out.println("01. Solicitar.");
        System.out.println("02. Agregar a Favoritos");
        System.out.println("03. Siguiente.");
        System.out.println("04. Volver.");
        System.out.print("Ingresar opcion: ");
        return scanner.nextInt();
    }
    public Integer opcionesSearchBook() {
        System.out.println("01. Solicitar.");
        System.out.println("02. Agregar a Favoritos");
        System.out.println("03. Volver.");
        System.out.print("Ingresar opcion: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;

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
        Double estrellas = pedirDouble("De 1 a 5 estrellas, que tal te parecio este libro?");
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
        System.out.println("03. Edad.");
        System.out.println("04. Email.");
        System.out.println("05. Direccion.");
        System.out.println("06. Telefono.");
        System.out.println("07. Contraseña");
        System.out.println("08. Cerrar temporalmente la cuenta.");
        System.out.println("09. Volver.");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }

    public boolean checkDownAccount() {
        System.out.println("Tenga presente que podra volver cuando lo desee, con solo iniciar seccion de nuevo.");
        System.out.println("Esta seguro que desea desactivar su cuenta? s/n");
        String choice = scanner.nextLine();
        return !choice.equalsIgnoreCase("s");
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

