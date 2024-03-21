package pl.kurs.figures.security.entity;

public enum Role {
    USER("user");
    public final String label;
    Role(String label) {
        this.label = label;
    }
}
