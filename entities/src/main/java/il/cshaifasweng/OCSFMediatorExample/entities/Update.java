package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class Update implements Serializable {

    private int index;
    private String role;

    public int getIndex() {
        return index;
    }

    public void setId(int index) {
        this.index = index;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Update(int index, String role) {
        this.index = index;
        this.role = role;
    }


}
