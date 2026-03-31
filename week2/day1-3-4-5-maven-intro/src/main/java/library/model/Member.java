package library.model;

public class Member {
    private int id;
    private String name;
    private String email;

    public Member( int id , String name , String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return id+" | "+name+" | "+email;
    }
}
