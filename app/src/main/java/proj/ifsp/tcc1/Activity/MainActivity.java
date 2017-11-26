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
import java.util.Calendar;
import java.util.Date;

import proj.ifsp.tcc1.Model.Questionario;
import proj.ifsp.tcc1.Model.Usuario;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.DateConverter;
import proj.ifsp.tcc1.Util.InstanceFactory;

public class MainActivity extends AppCompatActivity {

    private class Regiao{
        String estado;
        String cidade;
        String bairro;

        Regiao(String est, String cid, String bai){
            estado = est;
            cidade = cid;
            bairro = bai;
        }

        public String toString(){
            return estado + " " + cidade + " " + bairro;
        }
    }

    private TextView txtCount;
    private TextView txtTotal;

    int teste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCount = (TextView) findViewById(R.id.txCount);
        txtTotal = (TextView) findViewById(R.id.txTotal);

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

    }

    public void begin (View v){
        Toast.makeText(MainActivity.this, "BEGIN...", Toast.LENGTH_SHORT).show();

        final Regiao[] regioes = new Regiao[27];

        regioes[0] = new Regiao("MG","","");
        regioes[1] = new Regiao("SP","","");
        regioes[2] = new Regiao("AC","","");
        regioes[3] = new Regiao("AL","","");
        regioes[4] = new Regiao("AP","","");
        regioes[5] = new Regiao("AM","","");
        regioes[6] = new Regiao("BA","","");
        regioes[7] = new Regiao("CE","","");
        regioes[8] = new Regiao("DF","","");
        regioes[9] = new Regiao("ES","","");
        regioes[10] = new Regiao("GO","","");
        regioes[11] = new Regiao("MA","","");
        regioes[12] = new Regiao("MT","","");
        regioes[13] = new Regiao("MS","","");
        regioes[14] = new Regiao("PA","","");
        regioes[15] = new Regiao("PB","","");
        regioes[16] = new Regiao("PR","","");
        regioes[17] = new Regiao("PE","","");
        regioes[18] = new Regiao("PI","","");
        regioes[19] = new Regiao("RJ","","");
        regioes[20] = new Regiao("RN","","");
        regioes[21] = new Regiao("RS","","");
        regioes[22] = new Regiao("RO","","");
        regioes[23] = new Regiao("RR","","");
        regioes[24] = new Regiao("SC","","");
        regioes[25] = new Regiao("SE","","");
        regioes[26] = new Regiao("TO","","");

        // DATA INICIO
        // 0 anos = 26/11/2017
        // 40 anos = 26/11/1977
        // 60 anos = 26/11/1957

        // DATA FIM (+1)
        // 40 anos = 26/11/1976
        // 60 anos = 26/11/1956

        long maior = DateConverter.stringDateToTimestamp("26/11/2017"); // 01/11/1987
        long menor = DateConverter.stringDateToTimestamp("26/11/1956"); // 01/11/1967

        DatabaseReference node = InstanceFactory.getDBInstance().getReference("usuarios");

        node.orderByChild("nascimento").startAt(menor).endAt(maior).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot row : dataSnapshot.getChildren()){
                    Usuario temp = row.getValue(Usuario.class);
                    temp.setUID(row.getKey());

                    for (Regiao r : regioes){

                        if(! r.bairro.equals("")){
                            if(temp.getBairro().equals(r.bairro)){
                                gravaPendente(temp.getUID(),"567");
                                teste++;
                            }
                        }
                        else{
                            if(! r.cidade.equals("")){
                                if(temp.getCidade().equals(r.cidade)){
                                    gravaPendente(temp.getUID(),"567");
                                    teste++;
                                }
                            }
                            else{
                                if(! r.estado.equals("")){
                                    if(temp.getEstado().equals(r.estado)){
                                        gravaPendente(temp.getUID(),"567");
                                        teste++;
                                    }
                                }
                            }
                        }

                    }

                }

                txtCount.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                txtTotal.setText(String.valueOf(teste));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "DE "+databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void gravaPendente (String pUID, String pQuest){

        DatabaseReference user = InstanceFactory.getDBInstance().getReference("usuarios");

        user.child(pUID).child("pendentes").child(pQuest).setValue(DateConverter.sysdateToTimestamp());
    }

    /*public void bh (){

        DatabaseReference node = InstanceFactory.getDBInstance().getReference("usuarios");

        for(int i = 100001;i<=100010;i++){
            Usuario aux = new Usuario(String.valueOf(i),"teste"+i+"@example.com");

            aux.setEstado("MG");
            aux.setCidade("Belo Horizonte");
            aux.setBairro("Centro");

            if(i >= 100006){
                aux.setNascimento(DateConverter.stringDateToTimestamp("01/11/1987"));
            }
            else{
                aux.setNascimento(DateConverter.stringDateToTimestamp("01/11/1967"));
            }

            node.child(aux.getUID()).setValue(aux);
        }

        Toast.makeText(MainActivity.this, "FIM.", Toast.LENGTH_SHORT).show();

    }*/
}
