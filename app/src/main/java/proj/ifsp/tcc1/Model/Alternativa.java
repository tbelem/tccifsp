package proj.ifsp.tcc1.Model;

/**
 * Created by Tiago on 30/10/2017.
 */

public class Alternativa {

    private int ID;
    private String texto;

    public Alternativa(int pID, String pTexto){
        this.ID = pID;
        this.texto = pTexto;
    }

    public int getID() { return ID;  }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
