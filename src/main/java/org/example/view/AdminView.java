package org.example.view;

import java.util.Scanner;

public class AdminView {

    Scanner scanner = new Scanner(System.in);


    public Integer opcionesAdmin()
    {
        System.out.println("01. Agregar libro");
        System.out.println("02. Editar libro");
        System.out.println("03. Listar libros");
        System.out.println("04. Buscar libro");
        System.out.println("05. Dar de baja libro");
        System.out.println("06. Salir");

        return scanner.nextInt();
    }
}
