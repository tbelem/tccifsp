package proj.ifsp.tcc1.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import proj.ifsp.tcc1.Model.Questionario;
import proj.ifsp.tcc1.Model.Usuario;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.DateConverter;
import proj.ifsp.tcc1.Util.InstanceFactory;

public class MainActivity extends AppCompatActivity {

    //private FirebaseDatabase database;
    //private FirebaseAuth mAuth;
    //private ArrayList<Questionario> listaQuestionarios;

    private TextView txtCount;

    final long param = Long.parseLong("562723200000");
    int teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCount = (TextView) findViewById(R.id.txCount);

        InstanceFactory.getAuthInstance().signInWithEmailAndPassword("admin@admin.com","tiago1234").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Falha login", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, InstanceFactory.getAuthInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //

        long a = Long.parseLong("562723200000");
        long b = Long.parseLong("-68428800000");
        long c = Long.parseLong("562730400000");

        Log.d("MyConverting","87 "+String.valueOf(DateConverter.stringDateToTimestamp("01/11/1987")));
        Log.d("MyConverting","67 "+String.valueOf(DateConverter.stringDateToTimestamp("01/11/1967")));
        Log.d("MyConverting","87 "+DateConverter.timestampToStringDate(a));
        Log.d("MyConverting","67 "+DateConverter.timestampToStringDate(b));
        Log.d("MyConverting","aa "+DateConverter.timestampToStringDate(c));

        //mAuth = InstanceFactory.getAuthInstance();

        /*FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            mAuth.signInWithEmailAndPassword("tiago.blizz95@gmail.com" ,"tiago1234")
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("FBlearn", "Falha ao logar !", task.getException());
                            }
                        }
                    });
        }*/

        /*Log.d("FBlearn","Begin");

        database = InstanceFactory.getDBInstance();

        if (database == null) {Log.d("FBlearn","Ruim 1");}
        else {Log.d("FBlearn",database.toString());}

        DatabaseReference questionarios = database.getReference("questionarios");

        if (questionarios == null) {Log.d("FBlearn","Ruim 2");}
        else {Log.d("FBlearn",questionarios.toString());}

        questionarios.setValue("hahaha12", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null){
                    Log.d("FBlearn","Deu certo");
                }
                else{
                    Log.d("FBlearn","Deu errado: "+databaseError.toString());
                    Log.d("FBlearn","Deu errado: "+mAuth.getCurrentUser().toString());
                }
            }

        });*/

        /*questionarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FBlearn","Entrou");
                Log.d("FBlearn",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        /*questionarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FBlearn","Entrou");
                Questionario quest = dataSnapshot.getValue(Questionario.class);
                quest.setId(dataSnapshot.getKey());

                if (database == null) {Log.d("FBlearn","Nulou");}
                else {Log.d("FBlearn",quest.toString());}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FBlearn","Deu ruimX");
            }
        });*/

        /*listaQuestionarios = new ArrayList<Questionario>();

        questionarios.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("FBlearn","Added");
                Questionario aux = dataSnapshot.getValue(Questionario.class);
                aux.setId(dataSnapshot.getKey());
                listaQuestionarios.add(aux);
                printaLista(listaQuestionarios);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FBlearn","Error");
                Log.d("FBlearn",databaseError.toString());
            }
        });

        listaQuestionarios = new ArrayList<Questionario>();

        questionarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("FBlearn","Entrou");
                listaQuestionarios.clear();
                for(DataSnapshot row : dataSnapshot.getChildren()){
                    Questionario quest = row.getValue(Questionario.class);
                    quest.setId(row.getKey());
                    listaQuestionarios.add(quest);
                }

                printaLista(listaQuestionarios);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FBlearn","Deu ruimX");
            }
        });

        Log.d("FBlearn","End");*/

    }

    public void begin (View v){
        Toast.makeText(MainActivity.this, "BEGIN...", Toast.LENGTH_SHORT).show();

        DatabaseReference node = InstanceFactory.getDBInstance().getReference("usuarios");

        Toast.makeText(MainActivity.this, "SO FAR..", Toast.LENGTH_SHORT).show();

        /*node.orderByChild("cidade").equalTo("Campinas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(MainActivity.this, "SO GOOD.", Toast.LENGTH_SHORT).show();

                for (DataSnapshot row : dataSnapshot.getChildren()){
                    //Usuario temp = row.getValue(Usuario.class);
                    //temp.setUID(row.getKey());

                    //if(temp.getNascimento() == param){
                        teste++;
                    //}

                }

                txtCount.setText(String.valueOf(teste));
                Toast.makeText(MainActivity.this, "FIM.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "DE "+databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });*/



    }
}
