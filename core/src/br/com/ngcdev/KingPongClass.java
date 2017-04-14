package br.com.ngcdev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;


public class KingPongClass extends ApplicationAdapter {

	KingPongGameSprite raquete_esquerda;
	KingPongGameSprite raquete_direita;
	KingPongGameSprite bola;
	Array<KingPongGameSprite> paredes;
	KingPongScore score_esquerdo;
	KingPongScore score_direito;

	Texture tt_campo;
	Sound vitoria;
	Sound king;
	Sound pong;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	//BitmapFont font = new BitmapFont();
	//CharSequence str;
	StringBuffer strTemp;
	//BitmapFont bmpFont;
	int checkResult;

	@Override
	public void create () {
		batch = new SpriteBatch();
		raquete_esquerda = new KingPongGameSprite("raquete.png");
		raquete_direita = new KingPongGameSprite("raquete.png");
		bola = new KingPongGameSprite("bola.png");
		tt_campo = new Texture("campo.png");
		paredes = new Array<KingPongGameSprite>();
		for(int p=0; p < 4; p++){
			paredes.add(new KingPongGameSprite("parede.png"));
			paredes.get(p).setMinY(0);
			paredes.get(p).setMaxY(480);
			paredes.get(p).setMinX(0);
			paredes.get(p).setMaxX(800);
		}
		paredes.get(0).setBboxX(29);
		paredes.get(0).setBboxY(14);
		paredes.get(1).setBboxX(29);
		paredes.get(1).setBboxY(368);
		paredes.get(2).setBboxX(756);
		paredes.get(2).setBboxY(14);
		paredes.get(3).setBboxX(756);
		paredes.get(3).setBboxY(368);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		raquete_esquerda.setMinY(paredes.get(2).getBboxY()+paredes.get(2).getHeight());
		raquete_esquerda.setMaxY(285);
		raquete_esquerda.setBboxY(raquete_esquerda.getMedY());

		raquete_esquerda.setMinX(0);
		raquete_esquerda.setMaxX(800);
		raquete_esquerda.setBboxX(32);

		raquete_direita.setMinY(paredes.get(2).getBboxY()+paredes.get(2).getHeight());
		raquete_direita.setMaxY(285);
		raquete_direita.setBboxY(raquete_direita.getMedY());

		raquete_direita.setMinX(0);
		raquete_direita.setMaxX(800);
		raquete_direita.setBboxX(755);

		bola.setMinY(20);
		bola.setMaxY(390);
		bola.setBboxY(180);

		bola.setMinX(raquete_esquerda.getBboxX());
		bola.setMaxX(raquete_direita.getBboxX()+raquete_direita.getWidth());
		bola.setBboxX(400);
		//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("gameplay.mp3"));
		//rainMusic.setLooping(true);
		//rainMusic.play();
		strTemp = new StringBuffer();

		score_esquerdo = new KingPongScore(5, 44, 415, true);
		score_direito = new KingPongScore(5, 738, 415, false);

		vitoria = Gdx.audio.newSound(Gdx.files.internal("successful.mp3"));
		king = Gdx.audio.newSound(Gdx.files.internal("king.mp3"));
		pong = Gdx.audio.newSound(Gdx.files.internal("pong.mp3"));
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		//font.draw(batch, str, 200, 200);
		batch.draw(tt_campo, 0, 0);
		score_esquerdo.drawScore(batch);
		score_direito.drawScore(batch);
		batch.draw(raquete_esquerda, raquete_esquerda.getBboxX(), raquete_esquerda.getBboxY());
		batch.draw(raquete_direita, raquete_direita.getBboxX(), raquete_direita.getBboxY());
		batch.draw(bola, bola.getBboxX(), bola.getBboxY());
		for (KingPongGameSprite parede:paredes) {
			batch.draw(parede, parede.getBboxX(), parede.getBboxY());
		}
		batch.end();

		raquete_esquerda.calcpos(camera);
		raquete_direita.calcpos(camera);
		bola.calcpos();
		if(bola.checkOverlap(raquete_esquerda)) king.play();
		if(bola.checkOverlap(raquete_direita)) pong.play();
		for(KingPongGameSprite parede:paredes){
			bola.checkOverlap(parede);
		}
		checkResult = bola.checkPoint();
		if(checkResult == 1) {
			score_direito.pontos_atual--;
			Gdx.app.log("direto pontos_atual ", new StringBuffer().append(score_direito.pontos_atual).toString());
			if(score_direito.pontos_atual == 0) {
				vitoria.play();
				score_direito.pontos_atual = score_direito.total_pontos;
				score_esquerdo.pontos_atual = score_esquerdo.total_pontos;
			}
		}
		if(checkResult == 2) {
			score_esquerdo.pontos_atual--;
			//Gdx.app.log("esquerdo pontos_atual ", new StringBuffer().append(score_esquerdo.pontos_atual).toString());
			if (score_esquerdo.pontos_atual == 0) {
				vitoria.play();
				score_direito.pontos_atual = score_direito.total_pontos;
				score_esquerdo.pontos_atual = score_esquerdo.total_pontos;
			}
		}
	}
	
	@Override
	public void dispose(){
		batch.dispose();
		raquete_esquerda.dispose();
		raquete_direita.dispose();
		for (KingPongGameSprite parede:paredes) {
			parede.dispose();
		}
		for(Texture t:score_esquerdo.pontos_off) t.dispose();
		for(Texture t:score_esquerdo.pontos_on) t.dispose();
		for(Texture t:score_direito.pontos_off) t.dispose();
		for(Texture t:score_direito.pontos_on) t.dispose();

		bola.dispose();
		tt_campo.dispose();
	}

}
