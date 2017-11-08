package proj.ifsp.tcc1.Model;

import com.google.firebase.database.Exclude;

/**
 * Created by Tiago on 16/10/2017.
 */

public class Usuario {

    private String UID;
    private String email;
    private long nascimento;
    private Questionario[] pendentes;
    private String estado;
    private String cidade;
    private String bairro;
    private int saldo;

    public Usuario() {
    }

    public Usuario(String UID, String email) {
        this.UID = UID;
        this.email = email;
        this.nascimento = 0;
        this.estado = "";
        this.cidade = "";
        this.bairro = "";
        this.saldo = 0;
    }

    public int getSaldo() { return saldo;  }

    public void setSaldo(int saldo) { this.saldo = saldo;  }

    public long getNascimento() { return nascimento;  }

    public void setNascimento(long nascimento) { this.nascimento = nascimento; }

    @Exclude
    public Questionario[] getPendentes() { return pendentes;  }

    public void setPendentes(Questionario[] pendentes) { this.pendentes = pendentes;  }

    public String getEstado() { return estado;  }

    public void setEstado(String estado) { this.estado = estado; }

    public String getCidade() { return cidade;  }

    public void setCidade(String cidade) { this.cidade = cidade;  }

    public String getBairro() { return bairro; }

    public void setBairro(String bairro) { this.bairro = bairro; }

    @Exclude
    public String getUID() { return UID; }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

}
