package org.example.exception;


import java.util.Scanner;

public class MisExcepciones extends RuntimeException {

    private static Scanner scanner = new Scanner(System.in);

    public MisExcepciones(String message) {
        super(message);
    }

    public MisExcepciones() {
    }



    public static MisExcepciones usuarioNoEncontrado() {
        throw new MisExcepciones("\n\nEl usuario no existe.\n\n");
    }

    public static MisExcepciones dniExistente() {
        return new MisExcepciones("\n\nEl DNI ya tiene una cuenta asociada.\n\n");
    }

    public static MisExcepciones libroSinStock() {
        throw new MisExcepciones("\n\nEl libro solicitado no se encuentra disponible.");
    }

    public static MisExcepciones wrongPassword() {
        throw new MisExcepciones("\n\nLa contraseña ingresada en incorrecta.");
    }

    public static MisExcepciones datoInvalido() {
        return new MisExcepciones("\nIntrodujo un dato que no corresponde.\n");
    }
}
