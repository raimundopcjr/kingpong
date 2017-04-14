package br.com.ngcdev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static java.lang.Math.abs;

/**
 * Created by raimundo on 07/04/17.
 */

public class KingPongGameSprite extends Texture {

    float incY = 200;
    float incX = 200;

    float maxY;
    float minY;

    float maxX;
    float minX;

    private Rectangle bbox;

    Vector3 touchPos;

    int hit = 0;

    public KingPongGameSprite(String internalPath) {
        super(internalPath);

        bbox = new Rectangle();
        bbox.width = this.getWidth();
        bbox.height = this.getHeight();
        touchPos = new Vector3();
    }

    public void calcpos(){

        setBboxX(getBboxX() + incX * Gdx.graphics.getDeltaTime());
        setBboxY(getBboxY() + incY * Gdx.graphics.getDeltaTime());
        if(getBboxY() > maxY) incY = - abs(incY);
        if(getBboxY() < minY) incY = abs(incY);

        if(hit > 4) {
            if(incX > 0) incX += 30;
            if(incX < 0) incX -= 30;
            hit = 0;
            Gdx.app.log("incX ", new StringBuffer().append(incX).toString());
        }
    }

    public boolean checkOverlap(KingPongGameSprite temp){
        if(bbox.overlaps(temp.getBbox())) {
            incX = -incX;
            hit += 1;
            // Se o centro da bola for mais alto que o da raquete  + offset incrementa incY
            // Se o centro da bola for mais alto que o da raquete  + offset incrementa incY
            return true;
        }
        return false;
    }

    public int checkPoint() {

        if(bbox.x + bbox.width > getMaxX()+8) {
            Gdx.app.log("Ponto ", "jogador esquerdo");
            setBboxX(400);
            setIncX(200);
            return 1;
        }
        if(bbox.x < getMinX()-8) {
            Gdx.app.log("Ponto ", "jogador direito");
            setBboxX(400);
            setIncX(-200);
            return 2;
        }
        return 0;
    }

    public void calcpos(OrthographicCamera camera) {
        for (int i = 0; i < 20; i++) { // 20 dedos !!
            if (Gdx.input.isTouched(i)) {
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                camera.unproject(touchPos);
                //Raquete Esquerda
//            if(touchPos.x < getBboxX() + 100 || Gdx.input.isKeyPressed(Input.Keys.Z) || Gdx.input.isKeyPressed(Input.Keys.X)) { // Jogador esquerdo
                if (getBboxX() < 100 && touchPos.x < getBboxX() + 100) { // Jogador esquerdo
                    if (touchPos.y > 240) {
                        setBboxY(getBboxY() + incY * Gdx.graphics.getDeltaTime());
                    }
                    if (touchPos.y <= 240) {
                        setBboxY(getBboxY() - incY * Gdx.graphics.getDeltaTime());
                    }
                }
                // Raquete Direita
                if (getBboxX() > 700 && touchPos.x > getBboxX() - 100) { // Jogador direito
//            if(touchPos.x > getBboxX() - 100 || Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){ // Jogador direito
                    if (touchPos.y > 240) {
                        setBboxY(getBboxY() + incY * Gdx.graphics.getDeltaTime());
                    }
                    if (touchPos.y <= 240) {
                        setBboxY(getBboxY() - incY * Gdx.graphics.getDeltaTime());
                    }
                }
            }
        }
        if (getBboxX() < 100) { // Jogador esquerdo
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                setBboxY(getBboxY() + incY * Gdx.graphics.getDeltaTime());
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                setBboxY(getBboxY() - incY * Gdx.graphics.getDeltaTime());
            }
        }
        // Raquete Direita
        if (getBboxX() > 700) { // Jogador direito
//            if(touchPos.x > getBboxX() - 100 || Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){ // Jogador direito
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                setBboxY(getBboxY() + incY * Gdx.graphics.getDeltaTime());
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                setBboxY(getBboxY() - incY * Gdx.graphics.getDeltaTime());
            }
        }
        // Limita entre Min e Max Y
        if(this.bbox.x > this.maxX) this.bbox.x = this.maxX;
        if(this.bbox.x < this.minX) this.bbox.x = this.minX;
        // Limita entre Min e Max Y
        if(this.bbox.y > this.maxY) this.bbox.y = this.maxY;
        if(this.bbox.y < this.minY) this.bbox.y = this.minY;
    }

    /*public void do_print(){
    boolean do_print = false;
    strTemp.setLength(0);
            strTemp.append(" rect_raquete.y ");
        strTemp.append(rect_raquete.y);
        if(do_print)
            Gdx.app.log("Touch ", strTemp.toString());
            strTemp.append(" X ");
            strTemp.append(touchPos.x);
            strTemp.append(" Y ");
            strTemp.append(touchPos.y);
            do_print = false;
    }*/

    public float getMedY(){
        return getMinY() + ((getMaxY()-getMinY())/2);
    }

    public Rectangle getBbox() {
        return bbox;
    }

    public float getBboxX() {
        return bbox.x;
    }

    public void setBboxX(float bboxX) {
        this.bbox.x = bboxX;
        //if(this.bbox.x > this.maxX) this.bbox.x = this.maxX;
        //if(this.bbox.x < this.minX) this.bbox.x = this.minX;
    }

    public float getBboxY() {
        return bbox.y;
    }

    public void setBboxY(float bboxY) {
        this.bbox.y = bboxY;
        // Limita entre Min e Max Y
        //if(this.bbox.y > this.maxY) this.bbox.y = this.maxY;
        //if(this.bbox.y < this.minY) this.bbox.y = this.minY;
    }

    public float getIncY() {
        return incY;
    }

    public void setIncY(float incY) {
        this.incY = incY;
    }

    public float getIncX() {
        return incX;
    }

    public void setIncX(float incX) {
        this.incX = incX;
    }


    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getMinX() {
        return minX;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }
}
