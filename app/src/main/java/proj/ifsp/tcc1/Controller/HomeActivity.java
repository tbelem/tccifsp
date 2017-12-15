package proj.ifsp.tcc1.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import proj.ifsp.tcc1.Adapter.PendentesAdapter;
import proj.ifsp.tcc1.Model.Questionario;
import proj.ifsp.tcc1.Model.Usuario;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Service.NotificationService;
import proj.ifsp.tcc1.Util.InstanceFactory;
import proj.ifsp.tcc1.Util.PreferencesHelper;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private TextView lblEmail;
    private TextView lblSaldoValor;
    private ListView pendentesList;

    private DatabaseReference saldoReference;
    private DatabaseReference pendentesReference;

    private ValueEventListener listenerSaldo;
    private ValueEventListener listenerPendentes;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lblEmail = (TextView) findViewById(R.id.lbEmail);
        lblSaldoValor = (TextView) findViewById(R.id.lbSaldoValor);
        pendentesList = (ListView) findViewById(R.id.listPendentes);

        firebaseAuth = InstanceFactory.getAuthInstance();

        validaPrimeiroAcesso(firebaseAuth.getCurrentUser().getUid());

        lblEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        lblSaldoValor.setText(String.valueOf(PreferencesHelper.getIntPreference(getApplicationContext(),"userSaldo")));
        saldoReference = InstanceFactory.getDBInstance().getReference("usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("saldo");
        montaListenerSaldo();

        pendentesReference = InstanceFactory.getDBInstance().getReference("usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("pendentes");
        montaListenerPendentes();

        pendentesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Questionario selecionado = (Questionario) adapterView.getItemAtPosition(i);

                Intent questIntent = new Intent(HomeActivity.this,QuestionarioActivity.class);

                questIntent.putExtra("selecionadoID",selecionado.getId());

                startActivity(questIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        saldoReference.addValueEventListener(listenerSaldo);
        pendentesReference.addValueEventListener(listenerPendentes);

        stopService(new Intent(HomeActivity.this,NotificationService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();

        saldoReference.removeEventListener(listenerSaldo);
        pendentesReference.removeEventListener(listenerPendentes);

        if (! NotificationService.running && InstanceFactory.getAuthInstance().getCurrentUser() != null) {
            startService(new Intent(HomeActivity.this, NotificationService.class));
        }
    }

    private void validaPrimeiroAcesso(final String pUID){
        DatabaseReference query = InstanceFactory.getDBInstance().getReference("usuarios").child(pUID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot == null){
                    Log.e("DATABASE ERROR","[HOME] Usuario nao encontrado !");
                }
                else{
                    usuario = dataSnapshot.getValue(Usuario.class);
                    usuario.setUID(pUID);

                    if(usuario.getNascimento() == 0){
                        Intent dados = new Intent(HomeActivity.this, DadosActivity.class);
                        dados.putExtra("usuarioID",pUID);
                        startActivity(dados);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        });
    }

    private void montaListenerSaldo(){

        listenerSaldo = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    int saldo = Integer.parseInt(dataSnapshot.getValue().toString());
                    lblSaldoValor.setText(String.valueOf(saldo));
                    PreferencesHelper.saveIntPrefence(getApplicationContext(),"userSaldo",saldo);
                }
                else{
                    Log.e("DATABASE ERROR","Saldo nao encontrado");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        };

    }

    private void montaListenerPendentes(){

        final ArrayList<Questionario> pendentes = new ArrayList<>();

        final PendentesAdapter pa = new PendentesAdapter(this,pendentes);
        pendentesList.setAdapter(pa);

        listenerPendentes = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pendentes.clear();

                if(dataSnapshot.getChildrenCount() == 0){
                    pa.notifyDataSetChanged();
                }

                for (DataSnapshot row : dataSnapshot.getChildren()){

                    DatabaseReference buscaQuestionario = InstanceFactory.getDBInstance()
                            .getReference("questionarios").child(row.getKey());

                    buscaQuestionario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() != null) {
                                Questionario encontrado = dataSnapshot.getValue(Questionario.class);
                                encontrado.setId(dataSnapshot.getKey());
                                pendentes.add(encontrado);
                                pa.notifyDataSetChanged();
                            }
                            else{
                                Log.e("DATABASE ERROR","Pendente nao encontrado !");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("DATABASE ERROR",databaseError.toString());
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        };

    }

    public void Logout(View v){
        stopService(new Intent(HomeActivity.this,NotificationService.class));

        firebaseAuth.signOut();

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
