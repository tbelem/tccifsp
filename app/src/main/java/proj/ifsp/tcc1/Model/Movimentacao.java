package proj.ifsp.tcc1.Model;

import com.google.firebase.database.Exclude;

/**
 * Created by Tiago on 02/11/2017.
 */

public class Movimentacao {

    private String id;
    private long data;
    private String descricao;
    private String tipo;
    private int valor;

    public Movimentacao() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
