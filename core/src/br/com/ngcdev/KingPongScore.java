package br.com.ngcdev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by raimundo on 09/04/17.
 */

public class KingPongScore {

    int total_pontos;
    int pontos_atual;
    float initX;
    float initY;
    float curX;
    String texture_on = "ponto_on.png";
    String texture_off = "ponto_off.png";
    Array <Texture> pontos_on;
    Array <Texture> pontos_off;
    boolean esquerda_direita;


    public KingPongScore(int total_pontos, float initX, float initY, boolean esquerda_direita) {
        this.total_pontos = total_pontos;
        this.initX = initX;
        this.initY = initY;
        this.esquerda_direita = esquerda_direita;
        pontos_on = new Array<Texture>();
        pontos_off = new Array<Texture>();

        for(int x = 0; x < total_pontos; x++) {
            pontos_on.add(new Texture(texture_on));
            pontos_off.add(new Texture(texture_off));
        }
        pontos_atual = total_pontos;

    }

    public void drawScore(SpriteBatch sprbatch){
        curX = initX;
        for(int x = 0; x < total_pontos; x++) {
            if(esquerda_direita){
                if(pontos_atual > x) {
                    sprbatch.draw(pontos_on.get(x), curX + pontos_on.get(x).getWidth(), initY);
                    curX += pontos_on.get(x).getWidth();
                } else {
                    sprbatch.draw(pontos_off.get(x), curX + pontos_off.get(x).getWidth(), initY);
                    curX += pontos_off.get(x).getWidth();
                }
            } else {
                if(pontos_atual > x) {
                    sprbatch.draw(pontos_on.get(x), curX - pontos_on.get(x).getWidth(), initY);
                    curX -= pontos_on.get(x).getWidth();
                }else {
                    sprbatch.draw(pontos_off.get(x), curX - pontos_off.get(x).getWidth(), initY);
                    curX -= pontos_off.get(x).getWidth();
                }
            }
        }
    }
}
