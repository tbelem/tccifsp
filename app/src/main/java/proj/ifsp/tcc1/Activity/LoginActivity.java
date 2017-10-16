package proj.ifsp.tcc1.Activity;

import android.content.Intent;
import android.nfc.Tag;
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
import com.google.firebase.auth.FirebaseUser;

import proj.ifsp.tcc1.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText txtEmail;
    private EditText txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        txtEmail = (EditText) findViewById(R.id.txEmail);
        txtSenha = (EditText) findViewById(R.id.txSenha);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intentHome);
            finish();
        }
    }

    public void Entrar(View v){
        mAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtSenha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginLog","Logado com sucesso !");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intentHome);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginLog", "Falha ao logar !", task.getException());
                            Toast.makeText(LoginActivity.this, "Falha ao logar.",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
