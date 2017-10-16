package proj.ifsp.tcc1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import proj.ifsp.tcc1.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        ListView contact = (ListView) findViewById(R.id.ListView1);
        contact.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,katlista));

        /*ListView contact = (ListView) findViewById(R.id.ListView1);
        View header = getLayoutInflater().inflate(R.layout.pendentes_header, null);
        contact.addHeaderView(header);*/
    }
}
