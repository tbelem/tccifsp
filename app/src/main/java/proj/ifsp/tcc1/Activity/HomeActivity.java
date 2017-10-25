package proj.ifsp.tcc1.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import proj.ifsp.tcc1.Adapter.PendentesAdapter;
import proj.ifsp.tcc1.Model.Questionario;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Service.NotificationService;
import proj.ifsp.tcc1.Util.DateConverter;
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

        if (! NotificationService.running) {
            startService(new Intent(HomeActivity.this, NotificationService.class));
        }
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
