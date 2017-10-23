package itcom.gangstersquirrel.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import itcom.gangstersquirrel.MainGameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Gangster Squirrel";
		config.foregroundFPS = 60;
		config.width = MainGameClass.WIDTH;
		config.height = MainGameClass.HEIGHT;
		config.fullscreen = true;
		config.resizable = false;

		new LwjglApplication(new MainGameClass(), config);
	}
}
