package proj.ifsp.tcc1.Activity;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.InstanceFactory;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText txtEmail;
    private EditText txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = InstanceFactory.getAuthInstance();

        txtEmail = (EditText) findViewById(R.id.txEmail);
        txtSenha = (EditText) findViewById(R.id.txSenha);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
            intentHome.putExtra("userEmail",currentUser.getEmail());
            intentHome.putExtra("userUID",currentUser.getUid());
            startActivity(intentHome);
            finish();
        }
    }

    public void Entrar(View v){
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        if (email.equals("")){
            Toast.makeText(this, R.string.emailBranco,Toast.LENGTH_SHORT).show();
            return;
        }

        if (senha.equals("")){
            Toast.makeText(this, R.string.senhaBranco,Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LoginLog","Logado com sucesso !");

                            Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intentHome);
                            finish();

                        } else {
                            Log.w("LoginLog", "Falha ao logar !", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.falhaLogin,Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
