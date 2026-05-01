package pt.ipp.estg.doa.store.exception;

public class DuplicateNifException extends RuntimeException {
    private final String nif;
    private final String entityType;
    public DuplicateNifException(String nif, String entityType) {
        super(String.format("%s with NIF '%s' already exists", entityType, nif));
        this.entityType = entityType;
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }

    public String getEntityType() {
        return entityType;
    }
}
