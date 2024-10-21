package br.ufpb.dcx.dsc.paldex.exception;


public class UnauthorizedDeletionException extends RuntimeException {
    public UnauthorizedDeletionException(String message) {
        super(message);
    }
}
