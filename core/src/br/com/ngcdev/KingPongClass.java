package br.com.ngcdev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class KingPongClass extends ApplicationAdapter {

	Texture tt_raquete;
	Texture tt_bola;
	Texture tt_campo;
	//private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	float vertPos;
	final float vertInc = 200;
	float maxHeight;
	float minHeight;
	//BitmapFont font = new BitmapFont();
	//CharSequence str;
	StringBuffer strTemp;
	//BitmapFont bmpFont;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tt_raquete = new Texture("raquete.png");
		tt_bola = new Texture("bola.png");
		tt_campo = new Texture("campo.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("gameplay.mp3"));
		//rainMusic.setLooping(true);
		//rainMusic.play();
		vertPos = 180;
		maxHeight = 326;
		minHeight = 18;
		//str = "Untouched";
		strTemp = new StringBuffer();
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
		batch.draw(tt_raquete, 22, vertPos);
		batch.draw(tt_bola, 100, 100);
		batch.end();
		process_input();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tt_bola.dispose();
		tt_raquete.dispose();
		tt_campo.dispose();
	}

	public void process_input(){
		// process user input
		boolean do_print = false;

		strTemp.setLength(0);
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			strTemp.append(" X ");
			strTemp.append(touchPos.x);
			strTemp.append(" Y ");
			strTemp.append(touchPos.y);
			if(touchPos.y > 240) {
				vertPos += vertInc * Gdx.graphics.getDeltaTime(); // toque na parte superior da tela = move para cima
			} else {
				vertPos -= vertInc * Gdx.graphics.getDeltaTime(); // toque na parte inferior da tela = move para baixo
			}
			do_print = true;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			vertPos = vertPos + vertInc * Gdx.graphics.getDeltaTime();
			do_print = true;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			vertPos = vertPos - vertInc * Gdx.graphics.getDeltaTime();
			do_print = true;
		}
		if (vertPos > maxHeight) vertPos = maxHeight;
		if (vertPos < minHeight) vertPos = minHeight;

		strTemp.append(" vertPos ");
		strTemp.append(vertPos);
		if(do_print) {
			Gdx.app.log("Touch ", strTemp.toString());
		}

		do_print = false;
	}
}
