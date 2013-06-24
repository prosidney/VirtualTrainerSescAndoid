package br.org.virtualtrainersesc;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.androidquery.AQuery;

public class SplashActivity extends RoboActivity{
	
	AQuery a;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		a = new AQuery(this);

		//Caminho paralelo (Thread)
		final Runnable irParaOutraTelaParalelamente = new Runnable(){
			
			@Override
			public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
			}
		};
		
		AnimationListener escutarAnimacao = new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				System.out.println("Iniciando animacao");
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				System.out.println("Finalizar animacao");
				//Executar o handler chamando a Thread
				Handler threads = new Handler();
				threads.postDelayed(irParaOutraTelaParalelamente, 3000);
			}
		};
		
		a.id(R.id.logo).animate(R.anim.caindo, escutarAnimacao);
	}
}
	
