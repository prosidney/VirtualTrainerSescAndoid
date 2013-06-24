package br.org.virtualtrainersesc;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

public class LoginActivity extends Activity {

	AQuery a;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		a = new AQuery(this);
		
		// "chamando" os objetos
		final EditText matricula = (EditText) findViewById(R.id.txMatricula);
		final EditText senha = (EditText) findViewById(R.id.txSenha);
		Button entrar = (Button) findViewById(R.id.btnEntrar);
		Button limpar = (Button) findViewById(R.id.btnLimpar);
		
		
		entrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				String strMatricula = matricula.getText().toString();
				String strSenha = senha.getText().toString();
				
				if(strMatricula.trim().length() == 0){
					matricula.setError("Favor informar Matricula!");
					
					return;
				}
				if(strSenha.trim().length() == 0){
					matricula.setError("Favor informar Senha!");
					
					return;
				} 
					
        		String url = "http://172.20.10.7:8080/gym/pages/mobile/login/" + strMatricula + "/" + strSenha;
        		System.out.println(" A URL é [" + url + "]");
        		
        		a.ajax(url, JSONObject.class, this, "retorno");
			}
			
			public void retorno(String url, JSONObject json, AjaxStatus status) throws JSONException {
				if(json != null){
					String retorno = json.getString("data");
					if(retorno != null){
						System.out.println(retorno);
						
						if(retorno.toString().equalsIgnoreCase("OK")){
							EditText matricula = (EditText) findViewById(R.id.txMatricula);
							logar(Integer.parseInt(matricula.getText().toString()));
						} else {
							Toast.makeText(LoginActivity.this, retorno.toString() , Toast.LENGTH_SHORT).show();
						}
					}
				} 
			}

			private void logar(int matricula) {
                Bundle param = new Bundle();
                param.putInt("matricula", matricula);
                
                Intent intent = new Intent(LoginActivity.this, Treinos.class);
                intent.putExtras(param);
                
                startActivity(intent);
				
			}
		});
		
		limpar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				matricula.setText("");
				senha.setText("");
			}
		});
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
