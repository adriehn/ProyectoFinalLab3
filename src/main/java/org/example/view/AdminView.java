package org.example.view;

import org.example.entity.Admin;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.View;

import java.util.Scanner;

public class AdminView implements View {

    Scanner scanner = new Scanner(System.in);
    public final String sendMensagges = "Ingrese el mensaje a enviar...";
    public final String searchUser = "Ingrese el Dni del usuario.";
    public final String admMessage = "\nSe asigno correctamente su rol como administrativo.\n";

    @Override
    public Integer modifyObject(Object object) {
        System.out.println("01. Nombre.");
        System.out.println("02. Apellido.");
        System.out.println("03. Edad.");
        System.out.println("04. Email.");
        System.out.println("05. Direccion.");
        System.out.println("06. Telefono.");
        System.out.println("07. Contraseña");
        System.out.println("08. Cerrar temporalmente la cuenta.");
        System.out.println("09. Volver.");
        Integer opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }



    public Integer opcionesAdmin() {
        System.out.println("01. Agregar libro");
        System.out.println("02. Editar libro");
        System.out.println("03. Listar libros");
        System.out.println("04. Buscar libro");
        System.out.println("05. Dar de baja libro");
        System.out.println("06. Enviar notificacion a todos los usuarios.");
        System.out.println("07. Buscar usuario y enviar notificacion");
        System.out.println("08. Perfil");
        System.out.println("09. Salir");

        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
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

    @Override
    public String pedirDato(Object o) throws MisExcepciones {
        String dato = null;
        if (o instanceof String) {
            System.out.println(o);
            dato = scanner.nextLine();

            while (ClienteView.checkInput(dato)) {
                System.out.println("Reingrese el dato.");
                dato = scanner.nextLine();
            }
        }
        return dato;
    }

    @Override
    public void displayInfo(Object object) {
        if (object instanceof Admin admin) {
            System.out.println("****************************");
            System.out.println("*       Admin Details      *");
            System.out.println("****************************");
            System.out.println("* DNI       : " + admin.getDni());
            System.out.println("* Name      : " + admin.getName());
            System.out.println("* Last Name : " + admin.getLastName());
            System.out.println("* Age       : " + admin.getAge());
            System.out.println("* Email     : " + admin.getEmail());
            System.out.println("* Phone     : " + admin.getPhone());
            System.out.println("* Address   : " + admin.getAdress());
            System.out.println("* Pass   : " + admin.getPassword());
            System.out.println("****************************");
        }

    }


}
