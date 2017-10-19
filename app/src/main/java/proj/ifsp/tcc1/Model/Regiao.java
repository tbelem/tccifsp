package proj.ifsp.tcc1.Model;

/**
 * Created by Tiago on 19/10/2017.
 */

public class Regiao {

    private String estado;
    private String cidade;
    private String bairro;

    public Regiao() {
    }

    public String getEstado() { return estado; }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
