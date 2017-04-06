package br.com.ngcdev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class KingPongClass extends ApplicationAdapter {

	Texture tt_raquete;
	Texture tt_bola;
	Texture tt_campo;
	//private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	final float VERT_RATE = 200;
	final float HORIZ_RATE = 200;
	float vertInc = VERT_RATE;
	float horizInc = HORIZ_RATE;
	float maxHeight;
	float minHeight;
	//BitmapFont font = new BitmapFont();
	//CharSequence str;
	StringBuffer strTemp;
	//BitmapFont bmpFont;
	private Rectangle rect_raquete;
	private Rectangle rect_bola;

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
		maxHeight = 285;
		minHeight = 58;
		//str = "Untouched";
		strTemp = new StringBuffer();
		rect_raquete = new Rectangle();
		rect_bola = new Rectangle();
		rect_raquete.x = 29;
		rect_raquete.y = minHeight;
		rect_raquete.width = tt_raquete.getWidth();
		rect_raquete.height = tt_raquete.getHeight();
		rect_bola.x = 400;
		rect_bola.y = 180;
		rect_bola.width = tt_bola.getWidth();
		rect_bola.height = tt_bola.getHeight();
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
		batch.draw(tt_raquete, rect_raquete.x, rect_raquete.y);
		batch.draw(tt_bola, rect_bola.x, rect_bola.y);
		batch.end();
		process_input();
		move_bola();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tt_bola.dispose();
		tt_raquete.dispose();
		tt_campo.dispose();
	}
	public void move_bola(){
		rect_bola.x -= horizInc * Gdx.graphics.getDeltaTime();
		if(rect_bola.x < 0) rect_bola.x = 400;
		if(rect_bola.x > 800) {
			rect_bola.x = 400;
			horizInc = HORIZ_RATE;
		}
		if(rect_bola.overlaps(rect_raquete)) {
			horizInc = -HORIZ_RATE;
		}

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
				rect_raquete.y += vertInc * Gdx.graphics.getDeltaTime(); // toque na parte superior da tela = move para cima
			} else {
				rect_raquete.y -= vertInc * Gdx.graphics.getDeltaTime(); // toque na parte inferior da tela = move para baixo
			}
			do_print = true;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			rect_raquete.y += vertInc * Gdx.graphics.getDeltaTime();
			do_print = true;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			rect_raquete.y -= vertInc * Gdx.graphics.getDeltaTime();
			do_print = true;
		}
		if (rect_raquete.y > maxHeight) rect_raquete.y = maxHeight;
		if (rect_raquete.y < minHeight) rect_raquete.y = minHeight;

		strTemp.append(" rect_raquete.y ");
		strTemp.append(rect_raquete.y);
		if(do_print) {
			Gdx.app.log("Touch ", strTemp.toString());
		}

		do_print = false;
	}
}
