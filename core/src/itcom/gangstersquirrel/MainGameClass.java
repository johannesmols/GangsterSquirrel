/**
 * A simple 2D game created with the libGDX framework
 * This game was created during the P1 project in IT, Communication and New Media (1. Semester, 2017) at Aalborg University Copenhagen
 *
 * @author Johannes Mols, Ludvig Alexander Brüchmann, Boris Yordanov, Agata Surmacz, Muheb Khan, Rehan Mir, Martin Sander
 * @version 1.0
 * @since 2017-10-23
 */

package itcom.gangstersquirrel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import itcom.gangstersquirrel.Screens.PlayScreen;
import itcom.gangstersquirrel.Screens.SplashScreen;
import itcom.gangstersquirrel.Settings.Settings;
import itcom.gangstersquirrel.Tools.ResolutionObject;
import itcom.gangstersquirrel.Twitch.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * The main class of the game and entrance point
 */
public class MainGameClass extends Game {

	// Enables certain debugging features like collision box rendering
	public static boolean DEBUG = false;
	public static final boolean USE_PLAYSCREEN_LOG = true;

	// Configuration
	public static boolean FULLSCREEN = true;
	public static boolean RESIZABLE = false;
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
	public static final short CATEGORY_ENEMY_HEAD           = 0x0512;

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
	public static final short MASK_ENEMY_HEAD = CATEGORY_PLAYER;

	// Twitch
	public static final boolean USE_TWITCH = true;
	public static TwitchThread twitchThread;
	public float effectStateTime = 0f;
	public String currentEffect = "";
	public float maximumEffectTimeInSeconds = 15f;
	public boolean twitchEffectActive = false;
    private PlayScreen playScreen;

    // Possible display resolutions
	private static ArrayList<ResolutionObject> resolutions;

	/**
	 * The first method that is called in the entire application, sets up basic variables and loads the first screen
	 */
	@Override
	public void create () {
	    twitchThread = new TwitchThread(this);

	    setUpResolutions();

	    Settings settings = new Settings();

		// if values haven't been set yet
	    if (settings.getGameWidth() == 0 && settings.getGameHeight() == 0) {
	    	settings.setGameWidth(Gdx.graphics.getWidth());
	    	settings.setGameHeight(Gdx.graphics.getHeight());
		}

	    FULLSCREEN = settings.getFullscreen();
		WIDTH = settings.getGameWidth();
		HEIGHT = settings.getGameHeight();
		ASPECT_RATIO = (float) WIDTH / (float) HEIGHT;

		for (ResolutionObject resolution : resolutions) {
			if (resolution.getWidth() == WIDTH && resolution.getHeight() == HEIGHT) {
				switch (resolution.getAspectRatio()) {
					case "4:3":
						GAME_WORLD_WIDTH = ZOOM * 16;
						GAME_WORLD_HEIGHT = ZOOM * 12;
						break;
					case "5:3":
						GAME_WORLD_WIDTH = ZOOM * 15;
						GAME_WORLD_HEIGHT = ZOOM * 9;
						break;
					case "5:4":
						GAME_WORLD_WIDTH = ZOOM * 15;
						GAME_WORLD_HEIGHT = ZOOM * 12;
						break;
					case "16:9":
						GAME_WORLD_WIDTH = ZOOM * 16;
						GAME_WORLD_HEIGHT = ZOOM * 9;
						break;
					case "16:10":
						GAME_WORLD_WIDTH = ZOOM * 16;
						GAME_WORLD_HEIGHT = ZOOM * 10;
						break;
					case "17:9":
						GAME_WORLD_WIDTH = ZOOM * 17;
						GAME_WORLD_HEIGHT = ZOOM * 9;
						break;
					default:
						break;
				}
			}
		}

		changeGameResolution();

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
     * Changes the window size of the game and switches between fullscreen and windowed mode depending on the settings
     */
	public void changeGameResolution() {
		Settings settings = new Settings();
		Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();

		if (settings.getFullscreen()) {
			Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Gdx.graphics.setFullscreenMode(mode);
		} else if (!settings.getFullscreen()) {
			Gdx.graphics.setWindowedMode(settings.getGameWidth(), settings.getGameHeight());
		}
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
	public void resetTimer() {
		// Reset the game timer to zero, or when the game starts the next time, the timer will continue from the last saved point
		if (this.getScreen() != null && this.getScreen() instanceof PlayScreen ) {
			((PlayScreen) this.getScreen()).getGameProgress().setCurrentTime(0);
		}
	}

	/**
	 * Resets the current player lifes in the save file to the maximum
	 */
	public void resetPlayerLifes() {
		// Reset current lifes to the maximum
		if (this.getScreen() != null && this.getScreen() instanceof PlayScreen ) {
			((PlayScreen) this.getScreen()).getGameProgress().setPlayerLifes(((PlayScreen) this.getScreen()).getGameProgress().getPlayerMaximumLifes());
		}
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

	/**
	 * Receive an action that the twitch chat has decided for and convert it into an actual effect in the game
	 * @param action the name of the action
	 */
	public void receiveActionFromTwitch(String action) {
		System.out.println("Twitch chat has chosen an action: " + action);

		if (this.getCurrentScreen().isPresent() && this.getCurrentScreen().get() instanceof PlayScreen) {
			playScreen = (PlayScreen) this.getCurrentScreen().get();

			if (action.equalsIgnoreCase("Kreygasm")) {
			    if (playScreen != null) {
			        effectStateTime = 0f;
			        currentEffect = "Kreygasm";
			        twitchEffectActive = true;
                    playScreen.getWorld().setGravity(new Vector2(0, - GRAVITY / 2));
                }
			}
		}
	}

	/**
	 * Update the active twitch effect and it's timer
	 */
	public void updateTwitchEffectTimer() {
        if (effectStateTime >= maximumEffectTimeInSeconds) {
            effectStateTime = 0f;

            switch (currentEffect) {
                case "Kreygasm":
                    playScreen.getWorld().setGravity(new Vector2(0, - GRAVITY));
                    twitchEffectActive = false;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Gets the current screen as an Optional. This is an unnecessary method, because there is the method getScreen(), but I wanted to test the Optional class
     * @return the optional screen
     */
	public Optional<? extends Screen> getCurrentScreen() {
        return Optional.of(this.getScreen());
	}

	/**
	 * Sets up a list with common resolutions
	 */
	private void setUpResolutions() {
		resolutions = new ArrayList<>();

		resolutions.add(new ResolutionObject("4:3", 800, 600));
		resolutions.add(new ResolutionObject("4:3", 1024, 768));
		resolutions.add(new ResolutionObject("4:3", 1280, 960));
		resolutions.add(new ResolutionObject("4:3", 1400, 1050));
		resolutions.add(new ResolutionObject("4:3", 1600, 1200));
		resolutions.add(new ResolutionObject("4:3", 2048, 1536));
		resolutions.add(new ResolutionObject("4:3", 3200, 2400));
		resolutions.add(new ResolutionObject("4:3", 4000, 3000));
		resolutions.add(new ResolutionObject("4:3", 6400, 4800));

		resolutions.add(new ResolutionObject("5:3", 800, 480));
		resolutions.add(new ResolutionObject("5:3", 1280, 768));

		resolutions.add(new ResolutionObject("5:4", 1280, 1024));
		resolutions.add(new ResolutionObject("5:4", 2560, 2048));
		resolutions.add(new ResolutionObject("5:4", 5120, 4096));

		resolutions.add(new ResolutionObject("16:9", 852, 480));
		resolutions.add(new ResolutionObject("16:9", 1280, 720));
		resolutions.add(new ResolutionObject("16:9", 1366, 768));
		resolutions.add(new ResolutionObject("16:9", 1600, 900));
		resolutions.add(new ResolutionObject("16:9", 1920, 1080));
		resolutions.add(new ResolutionObject("16:9", 3200, 1800));

		resolutions.add(new ResolutionObject("16:10", 640, 400));
		resolutions.add(new ResolutionObject("16:10", 1280, 800));
		resolutions.add(new ResolutionObject("16:10", 1440, 900));
		resolutions.add(new ResolutionObject("16:10", 1680, 1050));
		resolutions.add(new ResolutionObject("16:10", 1920, 1200));
		resolutions.add(new ResolutionObject("16:10", 2560, 1600));
		resolutions.add(new ResolutionObject("16:10", 3840, 2400));
		resolutions.add(new ResolutionObject("16:10", 7680, 4800));

		resolutions.add(new ResolutionObject("17:9", 2048, 1080));
	}

	/**
	 * Get the list with common resolutions
	 */
	public ArrayList<ResolutionObject> getResolutions() {
		return resolutions;
	}

	/**
	 * Get the twitch thread
	 */
	public TwitchThread getTwitchThread() {
		return twitchThread;
	}

	/**
	 * Reinitializes the twitch thread. Useful for when the credentials change
	 */
	public void newTwitchThread() {
		twitchThread = new TwitchThread(this);
	}
}