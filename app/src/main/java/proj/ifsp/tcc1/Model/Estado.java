package proj.ifsp.tcc1.Model;

import com.google.firebase.database.Exclude;

/**
 * Created by Tiago on 04/11/2017.
 */

public class Estado {

    private String sigla;
    private String nome;

    public Estado() {
    }

    @Exclude
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
