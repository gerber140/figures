package pl.kurs.figures.security.entity;

public enum Role {
    CREATOR("user"),
    ADMIN("admin");

    public final String label;
    Role(String label) {
        this.label = label;
    }
}
