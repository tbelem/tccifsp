package proj.ifsp.tcc1.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import proj.ifsp.tcc1.Model.Usuario;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.InstanceFactory;

public class CadastroActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtConfirmacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtEmail = (EditText) findViewById(R.id.txEmail);
        txtSenha = (EditText) findViewById(R.id.txSenha);
        txtConfirmacao = (EditText) findViewById(R.id.txConfirmacao);

    }

    public void enviarCadastro(View v){

        if(txtEmail.getText().toString().equals("")){
            Toast.makeText(this, "Preencha o campo Email !", Toast.LENGTH_LONG).show();
        }
        else if(txtSenha.getText().toString().equals("")){
            Toast.makeText(this, "Preencha o campo Senha !", Toast.LENGTH_LONG).show();
        }
        else if(txtConfirmacao.getText().toString().equals("")){
            Toast.makeText(this, "Preencha o campo de Confirmação !", Toast.LENGTH_LONG).show();
        }
        else if(!txtSenha.getText().toString().equals(txtConfirmacao.getText().toString())){
            Toast.makeText(this, "Senha não confere com a Confirmação !", Toast.LENGTH_LONG).show();
        }
        else{
            criarLogin(txtEmail.getText().toString(),txtSenha.getText().toString());
        }
    }

    public void criarLogin (String pEmail, String pSenha){

        final FirebaseAuth autenticador = InstanceFactory.getAuthInstance();

        autenticador.createUserWithEmailAndPassword(pEmail,pSenha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    cadastrarUsuario();
                }
                else{
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e) {
                        Toast.makeText(CadastroActivity.this, "Senha muito fraca !", Toast.LENGTH_LONG).show();
                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(CadastroActivity.this, "Email inválido !", Toast.LENGTH_LONG).show();
                    } catch(FirebaseAuthUserCollisionException e) {
                        Toast.makeText(CadastroActivity.this, "Email já cadastrado !", Toast.LENGTH_LONG).show();
                    } catch(Exception e) {
                        Toast.makeText(CadastroActivity.this, "Senha muito fraca !", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public void cadastrarUsuario(){

        Usuario usuario = new Usuario(InstanceFactory.getAuthInstance().getCurrentUser().getUid()
                                     ,InstanceFactory.getAuthInstance().getCurrentUser().getEmail());

        DatabaseReference node = InstanceFactory.getDBInstance().getReference("usuarios");

        node.child(usuario.getUID()).setValue(usuario, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError == null){
                    chamaLogin();
                }
                else{
                    Log.e("DATABASE ERROR",databaseError.toString());
                }
            }
        });

    }

    public void chamaLogin(){
        Toast.makeText(CadastroActivity.this, "Conta criada com sucesso !", Toast.LENGTH_LONG).show();
        Toast.makeText(CadastroActivity.this, "Faça login para continuar.", Toast.LENGTH_LONG).show();

        InstanceFactory.getAuthInstance().signOut();

        Intent login = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}
