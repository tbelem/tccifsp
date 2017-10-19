package proj.ifsp.tcc1.Model;

/**
 * Created by Tiago on 16/10/2017.
 */

public class Usuario {

    private String UID;
    private String email;
    private String senha;
    private long nascimento;
    private Questionario[] pendentes;
    private Regiao[] regioes;
    private int saldo;

    public Usuario() {
    }

    public Usuario(String UID, String email) {
        this.UID = UID;
        this.email = email;
    }

    public String getSenha() { return senha; }

    public void setSenha(String senha) { this.senha = senha;  }

    public int getSaldo() { return saldo;  }

    public void setSaldo(int saldo) { this.saldo = saldo;  }

    public long getNascimento() { return nascimento;  }

    public void setNascimento(long nascimento) { this.nascimento = nascimento; }

    public Questionario[] getPendentes() { return pendentes;  }

    public void setPendentes(Questionario[] pendentes) { this.pendentes = pendentes;  }

    public Regiao[] getRegioes() { return regioes;  }

    public void setRegioes(Regiao[] regioes) { this.regioes = regioes;  }

    public String getUID() { return UID; }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

}
