package utb.fai.RESTAPIServer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MyUser {
    @Id
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    public MyUser() {}

    public MyUser(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public boolean isUserDataValid() {
        // Add your validation logic here (e.g., email and phone number format
        // validation)
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
