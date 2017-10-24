package proj.ifsp.tcc1.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import proj.ifsp.tcc1.Adapter.PendentesAdapter;
import proj.ifsp.tcc1.Model.Questionario;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.InstanceFactory;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private TextView lblEmail;
    private TextView lblSaldoValor;
    private ListView pendentesList;

    private DatabaseReference saldoReference;
    private DatabaseReference pendentesReference;

    private ValueEventListener listenerSaldo;
    private ValueEventListener listenerPendentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lblEmail = (TextView) findViewById(R.id.lbEmail);
        lblSaldoValor = (TextView) findViewById(R.id.lbSaldoValor);
        pendentesList = (ListView) findViewById(R.id.listPendentes);

        firebaseAuth = InstanceFactory.getAuthInstance();

        lblEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        saldoReference = InstanceFactory.getDBInstance().getReference("usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("saldo");
        montaListenerSaldo();

        pendentesReference = InstanceFactory.getDBInstance().getReference("usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("pendentes");
        montaListenerPendentes();

        /*ArrayList<Questionario> pendentes = new ArrayList<>();

        Questionario q1 = new Questionario("345",1507172400,1508295600,30);
        Questionario q2 = new Questionario("567",1503198000,1511146800,15);

        pendentes.add(q1);
        pendentes.add(q2);

        PendentesAdapter pa = new PendentesAdapter(this,pendentes);
        pendentesList.setAdapter(pa);

          katlista [] = {
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
        contact.setAdapter(new ArrayAdapter<String>(this, R.layout.pendentes_item,katlista));

        ListView contact = (ListView) findViewById(R.id.ListView1);
        View header = getLayoutInflater().inflate(R.layout.pendentes_header, null);
        contact.addHeaderView(header);*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        saldoReference.addValueEventListener(listenerSaldo);
        pendentesReference.addValueEventListener(listenerPendentes);
    }

    @Override
    protected void onStop() {
        super.onStop();

        saldoReference.removeEventListener(listenerSaldo);
        pendentesReference.removeEventListener(listenerPendentes);
    }

    private void montaListenerSaldo(){

        listenerSaldo = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    lblSaldoValor.setText(String.valueOf(dataSnapshot.getValue()));
                }
                else{
                    Log.e("DATABASE ERROR","Saldo nao encontrado");
                    lblSaldoValor.setText(String.valueOf(0));
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

                for (DataSnapshot row : dataSnapshot.getChildren()){

                    DatabaseReference buscaQuestionario = InstanceFactory.getDBInstance().getReference("questionarios").child(row.getKey());

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
