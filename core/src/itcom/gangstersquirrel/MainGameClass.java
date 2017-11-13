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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import itcom.gangstersquirrel.GameProgress.GameProgress;
import itcom.gangstersquirrel.Screens.SplashScreen;

import itcom.gangstersquirrel.Twitch.*;
import org.jibble.pircbot.IrcException;
import java.io.IOException;

import java.util.HashMap;

/**
 * The main class of the game and entrance point
 */
public class MainGameClass extends Game implements ChatListener {

	// Enables certain debugging features like collision box rendering
	public static boolean DEBUG = true;
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

    // Number of levels
	public static final int NUMBER_OF_LEVELS = 2;

	public static final boolean USE_TWITCH = false;
	TwitchChat twitch = new TwitchChat();

	// Internal units
	public static int TILE_PIXEL_SIZE = 16;
	public static float GAME_WORLD_WIDTH = 16 * ZOOM; // Game world size (map dimension in pixels)
	public static float GAME_WORLD_HEIGHT = 9 * ZOOM;
	public static float GRAVITY = 9.81f;

	// Internal objects
	public SpriteBatch batch; //Contains every sprite in the game
	public HashMap<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();

	@Override
	public void messageReceived(String channel, String sender, String login, String hostname, String message) {
		System.out.println(sender + ": " + message);
	}

	/**
	 * The first method that is called in the entire application, sets up basic variables and loads the first screen
	 */
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		ASPECT_RATIO = (float) WIDTH / (float) HEIGHT;

		batch = new SpriteBatch();

		// Add fonts
		fonts.put("default", new BitmapFont());

		if (USE_TWITCH) {
			twitch.addListener(this);
			try {
				twitch.connect(
						twitch.getTwitchCredentials().getUrl(),
						twitch.getTwitchCredentials().getPort(),
						twitch.getTwitchCredentials().getOauth()
				);
			} catch (IOException | IrcException e) {
				e.printStackTrace();
			}
			twitch.joinChannel(twitch.getTwitchCredentials().getTag());
		}

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
	}

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

		resetTimer();
		resetPlayerLifes();

		Gdx.app.exit();
		System.exit(0);
	}

	private void resetTimer() {
		// Reset the game timer to zero, or when the game starts the next time, the timer will continue from the last saved point
		new GameProgress().setCurrentTime(0);
	}

	private void resetPlayerLifes() {
		// Reset current lifes to the maximum
		GameProgress gameProgress = new GameProgress();
		gameProgress.setPlayerLifes(gameProgress.getPlayerMaximumLifes());
	}
}
