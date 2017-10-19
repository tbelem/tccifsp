package proj.ifsp.tcc1.Model;

import java.util.Date;

/**
 * Created by Tiago on 19/10/2017.
 */

public class Questionario {

    private String id;
    private long inicio;
    private long fim;
    private int recompensa;

    public Questionario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getInicio() {
        return inicio;
    }

    public void setInicio(long inicio) {
        this.inicio = inicio;
    }

    public long getFim() {
        return fim;
    }

    public void setFim(long fim) {
        this.fim = fim;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }

    public String toString (){
        return "id: " + id + " | inicio: " + String.valueOf(inicio) + " | fim: " + String.valueOf(fim) + " | rec: " + String.valueOf(recompensa);
    }
}
