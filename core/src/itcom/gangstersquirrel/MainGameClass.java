package itcom.gangstersquirrel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class MainGameClass extends Game {

	// Enables certain debugging features like collision box rendering
	public static final boolean DEBUG = true;
	public static final boolean DEBUG_PLAY_SOUNDS = false;

	// Configuration
	public static final boolean FULLSCREEN = true;
	public static final boolean RESIZABLE = false;
	public static final int FPS = 60;
	public static final String NAME = "Gangster Squirrel";
	public static int WIDTH; // Game width
	public static int HEIGHT; // Game height
	public static float ASPECT_RATIO; // Aspect ratio
	public static final float PPM = 100; // Pixels per meter
    public static int ZOOM = 32;

	// Internal units
	public static float GAME_WORLD_WIDTH = 16 * ZOOM; // Game world size (map dimension in pixels)
	public static float GAME_WORLD_HEIGHT = 9 * ZOOM;
	public static float GRAVITY = 9.81f;

	// Internal objects
	public SpriteBatch batch; //Contains every sprite in the game
	public BitmapFont default_font;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		ASPECT_RATIO = (float) WIDTH / (float) HEIGHT;

		batch = new SpriteBatch();
		// No parameters = use libGDX's default Arial font
		default_font = new BitmapFont();

		// Load first screen
		this.setScreen(new PlayScreen(this));
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
