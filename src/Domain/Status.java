package Domain;

public enum Status {
    CONCEPT("Concept"),
    ACTIVE("Actief"),
    ARCHIVED("Gearchiveerd"),
    UNKNOWN("Onbekend");

    private String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Status convertToEnum(String value) {
        if (value.equals("Concept")) {
            return Status.CONCEPT;
        } else if (value.equals("Actief")) {
            return Status.ACTIVE;
        } else if (value.equals("Gearchiveerd")) {
            return Status.ARCHIVED;
        } else{
            return Status.UNKNOWN;
        }
    }
}