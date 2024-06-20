package org.example.exception;


public class MisExcepciones extends RuntimeException {

    public MisExcepciones(String message) {
        super(message);
    }

    public static MisExcepciones usuarioNoEncontrado() {
        return new MisExcepciones("El usuario no existe.");
    }

    public static MisExcepciones dniExistente() {
        return new MisExcepciones("El DNI ya tiene una cuenta asociada.");
    }

    public static MisExcepciones libroSinStock() {
        return new MisExcepciones("El libro solicitado no se encuentra disponible.");
    }

    public static MisExcepciones datoInvalido() {
        return new MisExcepciones("Introdujo un dato que no corresponde.");
    }
}
