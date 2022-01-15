package Domain;

public enum Gender {
    MALE("Man"),
    FEMALE("Vrouw"),
    UNKNOWN("Onbekend");

    private String value;

    private Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Gender convertToEnum(String value) {
        if (value.equals("Man")) {
            return Gender.MALE;
        } else if (value.equals("Vrouw")) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }
}