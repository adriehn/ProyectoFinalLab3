package org.example.view;

import org.example.entity.Cliente;
import org.example.exception.MisExcepciones;

import java.util.Scanner;

public class ClienteView {

    public final String requestNameMessage = "Ingrese su nombre: ";
    public final String requestDniMessage = "Ingrese su numero de DNI (sin puntos, ni coma): ";
    public final String requestlastNameMessage = "Ingrese su apellido: ";
    public final String requestEmailMessage = "Ingrese su email: ";
    public final String requestPhoneMessage = "Ingrese su numero de teléfono: ";
    public final String requestAddressMessage = "Ingrese su direccion: ";
    public static final String requestPasswordMessage = "Ingrese su contraseña: ";
    public static final String admMessage = "Se asigno correctamente su rol como administrativo.";
    public static final String requestDepartmentMessage = "Ingrese su departamento: ";
    public static final String requestSpecialityMessage = "Ingrese su especialidad: ";


    Scanner scanner = new Scanner(System.in);


    public Cliente registerCliente() {

        scanner.nextLine();

        String dni = pedirDato(requestDniMessage);

        String name = pedirDato(requestNameMessage);

        String lastName = pedirDato(requestlastNameMessage);

        Integer age = pedirEntero();

        String email = pedirDato(requestEmailMessage);

        String phone = pedirDato(requestPhoneMessage);

        String address = pedirDato(requestAddressMessage);

        String passwordInput = pedirDato(requestPasswordMessage);

        return new Cliente(dni,name,lastName,age,email,phone,address,passwordInput,false);

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

    public Integer opcionesCliente()
    {
        System.out.println("01. Solicitar.");
        System.out.println("02. Agregar a Favoritos");
        System.out.println("03. Anterior.");
        System.out.println("04. Siguiente.");
        System.out.println("05. Perfil.");
        System.out.println("06. Salir..");
        System.out.print("Ingresar opcion: ");
        return scanner.nextInt();
    }

    public Integer opcionesLibrosClientes()
    {
        System.out.println("01. Ver libros solicitados.");
        System.out.println("02. Ver libros Favoritos.");
        System.out.println("03. Ver Historial libros solicitados.");
        System.out.println("04. Salir.");
        return scanner.nextInt();
    }
}
