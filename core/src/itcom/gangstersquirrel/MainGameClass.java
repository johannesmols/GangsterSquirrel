package itcom.gangstersquirrel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import itcom.gangstersquirrel.Screens.MainMenuScreen;

public class MainGameClass extends Game {

	public static final boolean FULLSCREEN = true;
	public static final boolean RESIZABLE = false;
	public static final int FPS = 60;
	public static final String NAME = "Gangster Squirrel";
	public static int WIDTH; //Game width
	public static int HEIGHT; //Game height
	public static final float PPM = 1;  //Pixels per meter

	public SpriteBatch batch; //Contains every sprite in the game
	public BitmapFont default_font;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		// No parameters = use libGDX's default Arial font
		default_font = new BitmapFont();

		// Load main menu screen
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		// Very important!
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		default_font.dispose();
	}
}
