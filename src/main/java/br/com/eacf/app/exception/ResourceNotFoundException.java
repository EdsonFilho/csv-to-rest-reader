package br.com.eacf.app.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Long id) {
        super("Could not find movie " + id);
    }
}
