package proj.ifsp.tcc1.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import proj.ifsp.tcc1.Model.Questionario;
import proj.ifsp.tcc1.R;


/**
 * Created by Tiago on 23/10/2017.
 */

public class PendentesAdapter extends BaseAdapter {

    private final ArrayList<Questionario> pendentes;
    private final Activity activity;

    public PendentesAdapter(Activity activity, ArrayList<Questionario> pendentes) {
        this.pendentes = pendentes;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pendentes.size();
    }

    @Override
    public Object getItem(int i) {
        return pendentes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item_layout = activity.getLayoutInflater().inflate(R.layout.pendentes_item,viewGroup,false);

        TextView txData_Inicio = (TextView) item_layout.findViewById(R.id.pendentes_data_inicio);
        TextView txData_Fim = (TextView) item_layout.findViewById(R.id.pendentes_data_fim);

        //txData_Inicio.setText("Início: " + pendentes.get(i).getInicio());
        //txData_Fim.setText("Expira em: " + pendentes.get(i).getFim());

        txData_Inicio.setText("Início: " + "20/07/2017");
        txData_Fim.setText("Expira em: " + "31/10/2017");


        return item_layout;
    }
}
