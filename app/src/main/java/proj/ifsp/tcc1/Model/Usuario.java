package proj.ifsp.tcc1.Model;

/**
 * Created by Tiago on 16/10/2017.
 */

public class Usuario {

    private String UID;
    private String email;

    public Usuario(String UID, String email) {
        this.UID = UID;
        this.email = email;
    }

    public String getUID() { return UID; }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
