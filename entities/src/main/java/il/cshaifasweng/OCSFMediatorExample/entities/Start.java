package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;


public class Start implements Serializable {

    private int id;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Start(int id, String role) {
        this.id = id;
        this.role = role;
    }

}