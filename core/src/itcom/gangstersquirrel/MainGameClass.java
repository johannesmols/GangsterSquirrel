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
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import itcom.gangstersquirrel.GameProgress.GameProgress;
import itcom.gangstersquirrel.Screens.SplashScreen;
import itcom.gangstersquirrel.Twitch.*;

import java.util.HashMap;

/**
 * The main class of the game and entrance point
 */
public class MainGameClass extends Game {

	// Enables certain debugging features like collision box rendering
	public static boolean DEBUG = false;
	public static final boolean PLAY_SOUNDS = false;

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
    public static boolean USE_CAMERA_INTERPOLATION = true;

    // Number of levels
	public static final int NUMBER_OF_LEVELS = 3;

	// Internal units
	public static int TILE_PIXEL_SIZE = 16;
	public static float GAME_WORLD_WIDTH = 16 * ZOOM; // Game world size (map dimension in pixels)
	public static float GAME_WORLD_HEIGHT = 9 * ZOOM;
	public static float GRAVITY = 9.81f;
	public static float INTERPOLATION_X = 0.3f;
	public static float INTERPOLATION_Y = 0.5f;

	// Internal objects
	public SpriteBatch batch; //Contains every sprite in the game
	public HashMap<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();
	public AssetManager assetManager;

	// Collision filter bits in binary notation
	public static final short CATEGORY_PLAYER				= 0x0001;
	public static final short CATEGORY_ENEMY 				= 0x0002;
	public static final short CATEGORY_GROUND 				= 0x0004;
	public static final short CATEGORY_DEATHTILE 			= 0x0008;
	public static final short CATEGORY_WEAPON 				= 0x0016;
	public static final short CATEGORY_FINISH 				= 0x0032;
	public static final short CATEGORY_JUMPABLE 			= 0x0064;
	public static final short CATEGORY_ENEMY_MOVE_BORDER 	= 0x0128;
	public static final short CATEGORY_PLAYER_ATTACK        = 0x0256;

	// Define masks for each object which define, with what objects this item can collide with
	// This is done with the BITWISE OR operator (see example here: https://www.programiz.com/java-programming/bitwise-operators)
	// With this method, using the geometric series from above, it is very easy to create one single number to store all mask types
	public static final short MASK_PLAYER = CATEGORY_ENEMY | CATEGORY_GROUND | CATEGORY_DEATHTILE | CATEGORY_WEAPON | CATEGORY_FINISH | CATEGORY_JUMPABLE;
	public static final short MASK_ENEMY = CATEGORY_PLAYER | CATEGORY_GROUND | CATEGORY_ENEMY_MOVE_BORDER | CATEGORY_ENEMY | CATEGORY_DEATHTILE | CATEGORY_PLAYER_ATTACK;
	public static final short MASK_GROUND = CATEGORY_PLAYER | CATEGORY_ENEMY;
	public static final short MASK_DEATHTILE = CATEGORY_PLAYER | CATEGORY_ENEMY;
	public static final short MASK_WEAPON = CATEGORY_PLAYER;
	public static final short MASK_FINISH = CATEGORY_PLAYER;
	public static final short MASK_JUMPABLE = CATEGORY_PLAYER | CATEGORY_ENEMY;
	public static final short MASK_ENEMY_MOVE_BORDER = CATEGORY_ENEMY;
	public static final short MASK_PLAYER_ATTACK = CATEGORY_ENEMY;

	// Twitch
	public static final boolean USE_TWITCH = true;
	public static TwitchThread twitchThread;

	/**
	 * The first method that is called in the entire application, sets up basic variables and loads the first screen
	 */
	@Override
	public void create () {
	    twitchThread = new TwitchThread(USE_TWITCH);

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		ASPECT_RATIO = (float) WIDTH / (float) HEIGHT;

		batch = new SpriteBatch();
		assetManager = new AssetManager();
		loadAssets();

		// Add fonts
		fonts.put("default", new BitmapFont());

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
		//assetManager.update(); // Doesn't need to be called when calling assetManager.finishLoading()
	}

	/**
	 * Disposes of unused resources correctly when closing the application
	 */
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

	/**
	 * Takes a screenshot of the current game state and saves it in the assets directory
	 */
	public void takeScreenshot() {
		byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
		Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
		BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
		PixmapIO.writePNG(Gdx.files.local("screenshots/screenshot.png"), pixmap);
		pixmap.dispose();
	}

	/**
	 * Exits the application properly
	 */
	public void exitApplication() {
		resetTimer();
		resetPlayerLifes();
		Gdx.app.exit();
		System.exit(0);
	}

	/**
	 * Exits the application properly
	 * @param errorMessage optional error message
	 */
	public void exitApplication(String errorMessage) {
		if (errorMessage != null && !errorMessage.isEmpty()) {
			System.err.println(errorMessage);
		}

		exitApplication();
	}

	/**
	 * Resets the current time in the save file to zero
	 */
	private void resetTimer() {
		// Reset the game timer to zero, or when the game starts the next time, the timer will continue from the last saved point
		new GameProgress().setCurrentTime(0);
	}

	/**
	 * Resets the current player lifes in the save file to the maximum
	 */
	private void resetPlayerLifes() {
		// Reset current lifes to the maximum
		GameProgress gameProgress = new GameProgress();
		gameProgress.setPlayerLifes(gameProgress.getPlayerMaximumLifes());
	}

	/**
	 * Load all assets into the assets folder
	 */
	private void loadAssets() {
		assetManager.load("audio/jump.mp3", Sound.class);
		assetManager.load("audio/level_1_music.mp3", Music.class);
		assetManager.load("audio/level_2_music.mp3", Music.class);

		assetManager.load("sprites/splashscreen/splashscreen.png", Texture.class);

		assetManager.finishLoading();
	}
}
