package utils;

public interface Persistable {
    String toCSV();
    void fromCSV(String csvLine);
}
