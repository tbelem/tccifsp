package proj.ifsp.tcc1.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import proj.ifsp.tcc1.R;

public class UseActivity extends AppCompatActivity {

    private TextView lblSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use);

        lblSaldo = (TextView) findViewById(R.id.lbSaldoValor);

        lblSaldo.setText(this.getIntent().getStringExtra("saldo"));

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
