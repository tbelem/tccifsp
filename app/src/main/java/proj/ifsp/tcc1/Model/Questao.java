package proj.ifsp.tcc1.Model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by Tiago on 30/10/2017.
 */

public class Questao {

    private int sequence;
    private String texto;
    private ArrayList<Alternativa> alternativas;

    public Questao() {
        alternativas = new ArrayList<>();
    }

    @Exclude
    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Exclude
    public ArrayList<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(ArrayList<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    public void addAlternativa (Alternativa alternativa){
        this.alternativas.add(alternativa);
    }
}
