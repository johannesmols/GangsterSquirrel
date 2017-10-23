package itcom.gangstersquirrel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import itcom.gangstersquirrel.Screens.MainMenuScreen;

public class MainGameClass extends Game {

	public static final int WIDTH = 800; //Game width
	public static final int HEIGHT = 480; //Game height
	public static final float PPM = 1;  //Pixels per meter

	public SpriteBatch batch; //Contains every sprite in the game
	public BitmapFont default_font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		default_font = new BitmapFont();

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		default_font.dispose();
	}
}
