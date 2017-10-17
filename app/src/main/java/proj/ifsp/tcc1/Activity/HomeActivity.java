package proj.ifsp.tcc1.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import proj.ifsp.tcc1.R;

public class HomeActivity extends AppCompatActivity {

    private String userUID;

    private TextView lblEmail;
    private TextView lblSaldoValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lblEmail = (TextView) findViewById(R.id.lbEmail);
        lblSaldoValor = (TextView) findViewById(R.id.lbSaldoValor);

        lblEmail.setText(this.getIntent().getStringExtra("userEmail"));
        userUID = this.getIntent().getStringExtra("userUID");

        lblSaldoValor.setText("0");

        String  katlista [] = {
                "Início: 20/07/2017 | Expira em: 20 minutos",
                "Início: 05/08/2017 | Expira em: 5 dias",
                "Início: 17/08/2017 | Expira em: 5 dias",
                "Início: 20/08/2017 | Expira em: 10 dias",
                "Início: 14/09/2017 | Expira em: indefinido",
                "Início: 24/09/2017 | Expira em: 120 dias",
                "Início: 20/07/2017 | Expira em: 20 minutos",
                "Início: 05/08/2017 | Expira em: 5 dias",
                "Início: 17/08/2017 | Expira em: 5 dias",
                "Início: 20/08/2017 | Expira em: 10 dias",
                "Início: 14/09/2017 | Expira em: indefinido",
                "Início: 24/09/2017 | Expira em: 120 dias"
        };

        ListView contact = (ListView) findViewById(R.id.listPendentes);
        contact.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,katlista));

        /*ListView contact = (ListView) findViewById(R.id.ListView1);
        View header = getLayoutInflater().inflate(R.layout.pendentes_header, null);
        contact.addHeaderView(header);*/
    }

    public void Logout(View v){
        FirebaseAuth.getInstance().signOut();

        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

    public void Utilizar(View v){
        Intent intentUtilizar = new Intent(this, UseActivity.class);
        intentUtilizar.putExtra("saldo",lblSaldoValor.getText().toString());
        startActivity(intentUtilizar);
    }
}
