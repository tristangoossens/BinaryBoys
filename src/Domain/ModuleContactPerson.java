package Domain;

public class ModuleContactPerson {
    private String name;
    private String email;

    public ModuleContactPerson(String name, String email){
        this.setName(name);
        this.setEmail(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}
