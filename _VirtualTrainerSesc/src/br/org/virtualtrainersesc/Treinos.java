package br.org.virtualtrainersesc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import roboguice.activity.RoboActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.org.virtualtrainersesc.model.Treino;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

public class Treinos extends RoboActivity{
	
	ListView lista;
	
	AQuery a;
	
	@SuppressLint("NewApi") 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.treinos);
		
		a = new AQuery(this);
		
		lista = (ListView) findViewById(R.id.lsTreinos);
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View linha, int position, long idLinha) {
				
                Bundle param = new Bundle();
                param.putInt("idTreino", ((Treino)linha.getTag()).getId());
                param.putString("nomeTreino", ((Treino)linha.getTag()).getNome());
                
                Intent intent = new Intent(Treinos.this, Exercicios.class);
                intent.putExtras(param);
                
                startActivity(intent);
			}
		});
		
		
		popularListView();
		
	}
	
	private void popularListView() {
		/*List<Treino> TreinosFake = new ArrayList<Treino>();*/
		
        if(getIntent().getExtras() != null){
        	Bundle param = getIntent().getExtras();
        	
        	if(param != null){
        		String url = "http://172.20.10.7:8080/gym/pages/mobile/treinos/"+param.getInt("matricula");
        		
        		/*String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";*/
        		
        		System.out.println(" A URL é [" + url + "]");
        		
        		a.ajax(url, JSONObject.class, this, "retorno");
        	}
        }
		
		
/*		for (int i = 0; i <= 3; i++) {
			Treino treinoFake = new Treino();
			
			treinoFake.setId(200+i);
			treinoFake.setNome("Treino"+i);
			
			TreinosFake.add(treinoFake);
		}*/
		
/*		ListaTreinoAdapter adapter = new ListaTreinoAdapter(this, R.layout.linha_treino, TreinosFake);
		
		lista.setAdapter(adapter);*/
	}
	
	public void retorno(String url, JSONObject json, AjaxStatus status) throws JSONException {
		if(json != null && json.getJSONArray("listData") != null){ 
			JSONArray jsonArray = json.getJSONArray("listData");

			if(jsonArray.length() == 0){
				TextView linhaTreino = (TextView) findViewById(R.id.txtInfo);
				linhaTreino.setText("Não existem treinos, procure o seu professor!");

				return;
			}
			
			List<Treino> treinos = new ArrayList<Treino>();
			for (int i = 0; i < jsonArray.length() ; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				Treino treino = new Treino();

				treino.setId(jsonObject.getInt("id"));
				treino.setNome(jsonObject.getString("nome"));

				treinos.add(treino);
			}
			

			ListaTreinoAdapter adapter = new ListaTreinoAdapter(this, R.layout.linha_treino, treinos);

			lista.setAdapter(adapter);
		} 
	}
	
	private class ListaTreinoAdapter extends ArrayAdapter<Treino>{

		List<Treino> treinos = null;
		
		public ListaTreinoAdapter(Context context, int textViewResourceId, List<Treino> treinos) {
			super(context, textViewResourceId);
			
			this.treinos = treinos;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View linha = convertView;
			
			if(linha == null){
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				linha = vi.inflate(R.layout.linha_treino, null);
			}
			
			TextView linhaTreino = (TextView) linha.findViewById(R.id.txTreino);
			
			Treino treino = treinos.get(position);
			
			linhaTreino.setText(treino.getNome());
			linha.setTag(treino);
			
			return linha;
		}
		
		@Override
		public int getCount() {
			return treinos.size(); 
		}
	}
}
