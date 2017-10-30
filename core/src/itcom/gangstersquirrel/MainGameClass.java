/**
 * A simple 2D game created with the libGDX framework
 * This game was created during the P1 project in IT, Communication and New Media (1. Semester, 2017) at Aalborg University Copenhagen
 *
 * @author Johannes Mols, Alexander Ludvig Brüchmann, Boris Yordanov, Agata Surmacz, Muheb Khan, Rehan Mir, Martin Sander
 * @version 1.0
 * @since 2017-10-23
 */

package itcom.gangstersquirrel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import itcom.gangstersquirrel.Screens.SplashScreen;

/**
 * The main class of the game and entrance point
 */
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

	// Internal level counter
	public static int CURRENT_LEVEL = 1;

	// Internal objects
	public SpriteBatch batch; //Contains every sprite in the game
	public BitmapFont default_font;

	/**
	 * The first method that is called in the entire application, sets up basic variables and loads the first screen
	 */
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		ASPECT_RATIO = (float) WIDTH / (float) HEIGHT;

		batch = new SpriteBatch();
		// No parameters = use libGDX's default Arial font
		default_font = new BitmapFont();

		// Load first screen
		this.setScreen(new SplashScreen(this));
	}

	/**
	 * Gets called once every frame and updates all visual components of the application
	 */
	@Override
	public void render () {
		// Very important!
		super.render();
	}

	/**
	 * Disposes of unused resources correctly when closing the application
	 */
	@Override
	public void dispose () {
		batch.dispose();
		default_font.dispose();
	}
}
