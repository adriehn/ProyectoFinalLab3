package org.example.view;

import org.example.entity.Cliente;
import org.example.exception.MisExcepciones;
import org.example.repository.implementations.View;

import java.util.Scanner;

public class ClienteView implements View {
    private final Scanner scanner = new Scanner(System.in);
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

    public Double setStars() {///Metodo para puntuar un libro
        Double estrellas = pedirDouble("De 1 a 5 estrellas, que tal te parecio este libro?");
        if (estrellas < 0 || estrellas > 5) {
            System.out.println("Ingrese de 1 a 5 por favor.");
            estrellas = setStars();
        }
        return estrellas;
    }


    public boolean checkDownAccount() {///Metodo que reconfirma si el usuario quiere desactivar su cuenta
        System.out.println("Tenga presente que podra volver cuando lo desee, con solo iniciar seccion de nuevo.");
        System.out.println("Esta seguro que desea desactivar su cuenta? s/n");
        String choice = scanner.nextLine();
        return !choice.equalsIgnoreCase("s");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///Implementacion <Interface> View.
    @Override
    public void displayInfo(Object object) {
        if (object instanceof Cliente cliente) {
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

    @Override
    public Integer modifyObject(Object object) {
        if (object instanceof Cliente user) {
            displayInfo(user);
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
        return 0;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Comprueba que un String no se ingrese vacio
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

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Comprueba que el dato ingresado sea un numero entero
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

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///Comprueba que el dato ingresado sea un numero double
    @Override
    public Double pedirDouble(Object o) {
        if (o instanceof String) {
            while (true) {
                try {
                    System.out.println(o);
                    String input = scanner.nextLine().trim();
                    // Reemplazar punto por coma si es necesario
                    input = input.replace(',', '.');
                    // Intentar convertir la entrada a un numero double
                    return Double.parseDouble(input);

                } catch (NumberFormatException e) {
                    System.err.println("Entrada inválida. Por favor, ingrese un número válido.");
                }
            }
        }
        return null;
    }


}

