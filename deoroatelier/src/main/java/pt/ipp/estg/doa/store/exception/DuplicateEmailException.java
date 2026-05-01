package pt.ipp.estg.doa.store.exception;

public class DuplicateEmailException extends RuntimeException {
    private final String email;
    private final String entityType;

    public DuplicateEmailException(String entityType, String email) {
        super(String.format("%s with email '%s' already exists", entityType, email));
        this.entityType = entityType;
        this.email = email;
    }

    public String getEmail() { return email; }
    public String getEntityType() { return entityType; }
}

