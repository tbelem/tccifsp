package proj.ifsp.tcc1.Activity;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import proj.ifsp.tcc1.Model.Alternativa;
import proj.ifsp.tcc1.Model.Questao;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.InstanceFactory;

public class AlternativaActivity extends AppCompatActivity {

    private TextView txtTitulo;
    private TextView txtQuestao;
    private RadioGroup rdgAlternativas;
    private Button btnProxima;
    private Button btnConcluir;
    private Button btnAnterior;

    private String questionarioID;
    private int questaoSequence;
    private String usuarioUID;
    private int qtdQuestoes;

    private Questao questao;

    private int respostaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternativa);

        txtTitulo = (TextView) findViewById(R.id.txTitulo);
        txtQuestao = (TextView) findViewById(R.id.txQuestao);
        rdgAlternativas = (RadioGroup) findViewById(R.id.rgAlternativas);
        btnProxima = (Button) findViewById(R.id.btProxima);
        btnConcluir = (Button) findViewById(R.id.btConcluir);
        btnAnterior = (Button) findViewById(R.id.btAnterior);

        questionarioID = this.getIntent().getStringExtra("questionario");
        questaoSequence = this.getIntent().getIntExtra("questaoSequence",0);
        usuarioUID = this.getIntent().getStringExtra("usuario");
        qtdQuestoes = this.getIntent().getIntExtra("qtdQuestoes",0);

        respostaAtual = -1;

        buscaQuestao();
    }

    private void buscaQuestao(){
        DatabaseReference query = InstanceFactory.getDBInstance().getReference("questoes").child(questionarioID).child(String.valueOf(questaoSequence));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questao = dataSnapshot.getValue(Questao.class);

                if (questao == null){
                    Log.e("DATABASE ERROR","Questao nao encontrada !");
                }
                else {
                    questao.setSequence(questaoSequence);

                    for (DataSnapshot row : dataSnapshot.child("alternativas").getChildren()) {
                        Alternativa alt = new Alternativa(Integer.parseInt(row.getKey()), row.getValue(String.class));

                        questao.addAlternativa(alt);
                    }

                    if (questao.getAlternativas().get(0) == null){
                        Log.e("DATABASE ERROR","Nenhuma alternativa encontrada !");
                    }
                    else{
                        montaTela();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        });
    }

    private void montaTela(){
        txtTitulo.setText("Questao " + String.valueOf(questao.getSequence()));

        txtQuestao.setText(questao.getTexto());

        rdgAlternativas.removeAllViews();

        for (Alternativa alt : questao.getAlternativas()){
            RadioButton rb = new RadioButton(this);
            rb.setId(alt.getID());
            rb.setText(alt.getTexto());
            rdgAlternativas.addView(rb);
        }

        if (questaoSequence == 1){
            btnAnterior.setEnabled(false);
        }

        if (questaoSequence == qtdQuestoes){
            btnProxima.setEnabled(false);
            btnProxima.setVisibility(View.INVISIBLE);
            btnConcluir.setEnabled(true);
            btnConcluir.setVisibility(View.VISIBLE);
        }
        else{
            btnProxima.setEnabled(true);
            btnProxima.setVisibility(View.VISIBLE);
            btnConcluir.setEnabled(false);
            btnConcluir.setVisibility(View.INVISIBLE);
        }

        buscaRespostaAtual();
    }

    private void buscaRespostaAtual(){
        DatabaseReference query = InstanceFactory.getDBInstance().getReference("respostas").child(questionarioID).child(String.valueOf(questaoSequence));

        query.orderByChild(usuarioUID).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null){
                    long count = dataSnapshot.getChildrenCount();

                    for (DataSnapshot row : dataSnapshot.getChildren()){
                        if(count > 1){
                            /*DELETA*/
                            Log.d("respostaLog","Delete "+row.toString());
                        }
                        else {
                            respostaAtual = Integer.parseInt(row.getKey());
                            rdgAlternativas.check(respostaAtual);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        });
    }

    private void chamaAlternativa(int pSequence){
        Log.d("respostaLog","Chama alternativa " + pSequence);
    }

    private void salvaResposta(final String pDestino){
        DatabaseReference node = InstanceFactory.getDBInstance().getReference("respostas").child(questionarioID).child(String.valueOf(questaoSequence));

        Log.d("respostaLog","Resposta: " + rdgAlternativas.getCheckedRadioButtonId());

        int resposta = rdgAlternativas.getCheckedRadioButtonId();

        if (resposta != -1) {
            node.child(String.valueOf(resposta)).child(usuarioUID).setValue(true, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast erro = Toast.makeText(AlternativaActivity.this, "Erro ao salvar resposta !", Toast.LENGTH_LONG);
                        erro.show();
                    }
                    else {
                        if(pDestino.equals("PROXIMA")){
                            chamaAlternativa(questaoSequence+1);
                        }
                        else if(pDestino.equals("ANTERIOR")){
                            chamaAlternativa(questaoSequence-1);
                        }
                    }
                }
            });
        }
        else{
            if(pDestino.equals("ANTERIOR")){
                chamaAlternativa(questaoSequence-1);
            }
        }
    }

    public void proximaAlternativa (View v){

        if (rdgAlternativas.getCheckedRadioButtonId() == -1){
            Toast erro = Toast.makeText(AlternativaActivity.this, "Escolha uma alternativa !", Toast.LENGTH_LONG);
            erro.show();
        }
        else{
            salvaResposta("PROXIMA");
        }

    }

    public void alternativaAnterior (View v){
        salvaResposta("ANTERIOR");
    }
}
