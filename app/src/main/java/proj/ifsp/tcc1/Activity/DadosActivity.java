package proj.ifsp.tcc1.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import proj.ifsp.tcc1.Model.Estado;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.DateConverter;
import proj.ifsp.tcc1.Util.InstanceFactory;

public class DadosActivity extends AppCompatActivity {

    private EditText txNascimento;
    private Spinner cbEstado;
    private Spinner cbCidade;
    private Spinner cbBairro;

    private ArrayList<Estado> estados;

    private String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);

       this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        txNascimento = (EditText) findViewById(R.id.txNascimento);
        cbEstado = (Spinner) findViewById(R.id.cbEstado);
        cbCidade = (Spinner) findViewById(R.id.cbCidade);
        cbBairro = (Spinner) findViewById(R.id.cbBairro);

        estados = new ArrayList<Estado>();

        usuarioID = this.getIntent().getStringExtra("usuarioID");

        montaComboEstado();
    }

    private void montaComboEstado(){
        DatabaseReference query = InstanceFactory.getDBInstance().getReference("estados");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> arrayCB = new ArrayList<String>();

                Estado vazio = new Estado();
                vazio.setNome("");
                vazio.setSigla("");
                estados.add(vazio);
                arrayCB.add(getResources().getString(R.string.hintSelecione));

                for (DataSnapshot row : dataSnapshot.getChildren()){
                    Estado aux = new Estado();
                    aux.setNome(row.getValue(String.class));
                    aux.setSigla(row.getKey());
                    estados.add(aux);
                    arrayCB.add(aux.getNome()+" ("+aux.getSigla()+")");
                }

                ArrayAdapter<String> estadoAdapter = new ArrayAdapter<String>(DadosActivity.this,R.layout.spinner_item,arrayCB);
                estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cbEstado.setAdapter(estadoAdapter);

                cbEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        montaComboCidade(estados.get(i).getSigla());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        });
    }

    private void montaComboCidade(final String pSigla){

        if (pSigla.equals("")){
            cbCidade.setAdapter(null);
            cbBairro.setAdapter(null);
            return;
        }

        DatabaseReference query = InstanceFactory.getDBInstance().getReference("cidades").child(pSigla);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> arrayCB = new ArrayList<String>();

                arrayCB.add(getResources().getString(R.string.hintSelecione));

                for (DataSnapshot row : dataSnapshot.getChildren()){
                    arrayCB.add(row.getKey());
                }

                ArrayAdapter<String> cidadeAdapter = new ArrayAdapter<String>(DadosActivity.this,R.layout.spinner_item,arrayCB);
                cidadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cbCidade.setAdapter(cidadeAdapter);

                cbCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        montaComboBairro(pSigla,cbCidade.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        });
    }

    private void montaComboBairro(String pSigla, String pCidade){

        if(pSigla.equals("") || pCidade.equals(getResources().getString(R.string.hintSelecione))){
            cbBairro.setAdapter(null);
            return;
        }

        DatabaseReference query = InstanceFactory.getDBInstance().getReference("bairros").child(pSigla).child(pCidade);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> arrayCB = new ArrayList<String>();

                arrayCB.add(getResources().getString(R.string.hintSelecione));

                for (DataSnapshot row : dataSnapshot.getChildren()){
                    arrayCB.add(row.getKey());
                }

                ArrayAdapter<String> bairroAdapter = new ArrayAdapter<String>(DadosActivity.this,R.layout.spinner_item,arrayCB);
                bairroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cbBairro.setAdapter(bairroAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        });
    };

    public void salvarCadastro(View v){

        if (txNascimento.getText().toString().equals("")){
            Toast teste = Toast.makeText(this,"Preencha a Data de Nascimento !",Toast.LENGTH_LONG);
            teste.show();
        }
        else if(DateConverter.stringDateToTimestamp(txNascimento.getText().toString()) == 0){
            Toast teste = Toast.makeText(this,"Data Invalida !",Toast.LENGTH_LONG);
            teste.show();
        }
        else if(cbEstado.getSelectedItemPosition() == 0){
            Toast teste = Toast.makeText(this,"Selecione um Estado !",Toast.LENGTH_LONG);
            teste.show();
        }
        else if(cbCidade.getSelectedItemPosition() == 0){
            Toast teste = Toast.makeText(this,"Selecione uma Cidade !",Toast.LENGTH_LONG);
            teste.show();
        }
        else if(cbBairro.getSelectedItemPosition() == 0){
            Toast teste = Toast.makeText(this,"Selecione um Bairro !",Toast.LENGTH_LONG);
            teste.show();
        }
        else{
            atualizarUsuario();
        }

    }

    private void atualizarUsuario(){
        long dataNascimento = DateConverter.stringDateToTimestamp(txNascimento.getText().toString());
        String siglaEstado = estados.get(cbEstado.getSelectedItemPosition()).getSigla();
        String cidade = cbCidade.getSelectedItem().toString();
        String bairro = cbBairro.getSelectedItem().toString();

        Map<String,Object> userUpdate = new HashMap<String,Object>();
        userUpdate.put("nascimento",dataNascimento);
        userUpdate.put("estado",siglaEstado);
        userUpdate.put("cidade",cidade);
        userUpdate.put("bairro",bairro);

        DatabaseReference node = InstanceFactory.getDBInstance().getReference("usuarios").child(usuarioID);

        node.updateChildren(userUpdate);

        chamarHome();
    }

    private void chamarHome(){
        Intent home = new Intent(DadosActivity.this,HomeActivity.class);

        startActivity(home);
        finish();
    }
}
