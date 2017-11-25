package proj.ifsp.tcc1.Model;

/**
 * Created by Tiago on 23/11/2017.
 */

public class Cidade {

    private String nome;

    public Cidade(String pNome){
        this.nome = pNome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
