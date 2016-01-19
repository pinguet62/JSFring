package fr.pinguet62.jsfring.model.nosql;

public class Casting {

    private Person person;

    private String role;

    public Person getPerson() {
        return person;
    }

    public String getRole() {
        return role;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return getClass().getName() + ": [person.name=\"" + person.getName() + "\", role=\"" + role + "\"]";
    }

}