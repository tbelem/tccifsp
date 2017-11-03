package proj.ifsp.tcc1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import proj.ifsp.tcc1.R;

public class DadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);

        String[] estados = {"Sao Paulo (SP)","Rio de Janeiro (RJ)"};

        Spinner cbEstado = (Spinner) findViewById(R.id.cbEstado);
        ArrayAdapter<String> estadoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,estados);
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbEstado.setAdapter(estadoAdapter);

        String[] cidades = {"Sao Paulo","Campinas","Sao Jose dos Campos"};

        Spinner cbCidade = (Spinner) findViewById(R.id.cbCidade);
        ArrayAdapter<String> cidadesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cidades);
        cidadesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbCidade.setAdapter(cidadesAdapter);

        String[] bairros = {"Alphaville","Barao Geraldo","Centro","Jd Aurelia","Sao Fernando"};

        Spinner cbBairro = (Spinner) findViewById(R.id.cbBairro);
        ArrayAdapter<String> bairrosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bairros);
        bairrosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbBairro.setAdapter(bairrosAdapter);
    }
}
