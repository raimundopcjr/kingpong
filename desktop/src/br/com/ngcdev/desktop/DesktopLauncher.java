package br.com.ngcdev.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.com.ngcdev.KingPongClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "King Pong";
		config.useGL30 = false;
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new KingPongClass(), config);
	}
}
