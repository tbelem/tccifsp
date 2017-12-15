package proj.ifsp.tcc1.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.PreferencesHelper;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar pbProgresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pbProgresso = (ProgressBar) findViewById(R.id.pbProgresso);

        new Thread(new Runnable() {
            @Override
            public void run() {
               carregamento();
                chamarLogin();
            }
        }).start();

    }

    private void carregamento() {

        for (int i=0;i<=100;i+=5){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pbProgresso.setProgress(i);
        }
    }

    private void chamarLogin(){
        Intent login = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}
