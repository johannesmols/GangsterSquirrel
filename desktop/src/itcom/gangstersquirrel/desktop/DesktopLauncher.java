package itcom.gangstersquirrel.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import itcom.gangstersquirrel.MainGameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = MainGameClass.NAME;
		config.foregroundFPS = MainGameClass.FPS;
		config.width = MainGameClass.WIDTH;
		config.height = MainGameClass.HEIGHT;
		config.fullscreen = MainGameClass.FULLSCREEN;
		config.resizable = MainGameClass.RESIZABLE;

		new LwjglApplication(new MainGameClass(), config);
	}
}
