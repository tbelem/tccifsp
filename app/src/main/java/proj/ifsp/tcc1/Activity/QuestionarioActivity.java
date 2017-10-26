package proj.ifsp.tcc1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import proj.ifsp.tcc1.Model.Questionario;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.InstanceFactory;

public class QuestionarioActivity extends AppCompatActivity {

    private TextView txtDescricao;

    private Questionario questionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        txtDescricao = (TextView) findViewById(R.id.txDescricao);

        buscaQuestionario(this.getIntent().getStringExtra("selecionadoID"));
    }

    private void buscaQuestionario(String qID){
        DatabaseReference query = InstanceFactory.getDBInstance().getReference("questionarios").child(qID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionario = dataSnapshot.getValue(Questionario.class);
                questionario.setId(dataSnapshot.getKey());

                if(questionario == null){
                    Log.e("DATABASE ERROR","Questionario nao encontrado !");
                }
                else{
                    if(! questionario.getDescricao().equals("")) {
                        txtDescricao.setText(questionario.getDescricao());
                    }
                    else{
                        txtDescricao.setText(getResources().getString(R.string.txDescricaoDefault));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        });
    }

    public void Voltar (View v){
        finish();
    }

}
