package proj.ifsp.tcc1.Activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import proj.ifsp.tcc1.Model.Movimentacao;
import proj.ifsp.tcc1.Model.Questionario;
import proj.ifsp.tcc1.Model.Usuario;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.DateConverter;
import proj.ifsp.tcc1.Util.InstanceFactory;

public class RecompensaActivity extends AppCompatActivity {

    private TextView txtRecompensa;

    private Questionario questionario;
    private Usuario usuario;
    private boolean ready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recompensa);

        txtRecompensa = (TextView) findViewById(R.id.txRecompensa);

        txtRecompensa.setText("");

        ready = false;

        buscaQuestionario(this.getIntent().getStringExtra("questionarioID")
                         ,this.getIntent().getStringExtra("usuarioID"));

        ConstraintLayout tela = (ConstraintLayout) findViewById(R.id.recompensaLayout);
        tela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ready){
                    finish();
                }
            }
        });
    }

    private void buscaQuestionario(String pQuestionarioID, final String pUsuarioID){
        DatabaseReference query = InstanceFactory.getDBInstance().getReference("questionarios").child(pQuestionarioID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionario = dataSnapshot.getValue(Questionario.class);
                questionario.setId(dataSnapshot.getKey());

                if (questionario == null){
                    Log.e("DATABASE ERROR","Questionario nao encontrado !");
                }
                else{
                    buscaUsuario(pUsuarioID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        });
    }

    private void buscaUsuario(String pUsuarioID){
        DatabaseReference query = InstanceFactory.getDBInstance().getReference("usuarios").child(pUsuarioID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
                usuario.setUID(dataSnapshot.getKey());

                if (usuario == null){
                    Log.e("DATABASE ERROR","Usuario nao encontrado !");
                }
                else{
                    calculaSaldo();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        });
    }

    private void calculaSaldo(){
        int novoSaldo = usuario.getSaldo() + questionario.getRecompensa();

        DatabaseReference node = InstanceFactory.getDBInstance().getReference("usuarios").child(usuario.getUID());

        node.child("saldo").setValue(novoSaldo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    txtRecompensa.setText(String.valueOf(questionario.getRecompensa()));
                    salvaMovimentacao();
                }
                else {
                    Log.e("DATABASE ERROR",databaseError.toString());
                }
            }
        });
    }

    private void salvaMovimentacao(){
        DatabaseReference node = InstanceFactory.getDBInstance().getReference("movimentacao").child(usuario.getUID());

        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setData(DateConverter.sysdateToTimestamp());
        movimentacao.setDescricao("Resposta Questionario");
        movimentacao.setTipo("credito");
        movimentacao.setValor(questionario.getRecompensa());

        node.push().setValue(movimentacao);

        retiraPendencia();
    }

    private void retiraPendencia(){
        DatabaseReference node = InstanceFactory.getDBInstance().getReference("usuarios").child(usuario.getUID()).child("pendentes");

        node.child(questionario.getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    ready = true;
                }
                else{
                    Log.e("DATABASE ERROR",databaseError.toString());
                }
            }
        });
    }
}
