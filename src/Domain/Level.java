package Domain;

public enum Level {
    BEGINNER("Beginner"),
    ADVANCED("Gevorderd"),
    EXPERT("Expert"),
    UNKNOWN("Onbekend");

    private String value;

    private Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Level convertToEnum(String value) {
        if (value.equals("Beginner")) {
            return Level.BEGINNER;
        } else if (value.equals("Gevorderd")) {
            return Level.ADVANCED;
        } else if (value.equals("Expert")) {
            return Level.EXPERT;
        } else{
            return Level.UNKNOWN;
        }
    }
}