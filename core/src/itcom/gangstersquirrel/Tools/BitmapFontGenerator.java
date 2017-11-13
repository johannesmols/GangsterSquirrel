package itcom.gangstersquirrel.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class BitmapFontGenerator {

    /**
     * Converts a true type font (ttf) into a BitmapFont taking the screen density into consideration and therefore adjusting the font size to the screen
     * See this stack exchange post: https://gamedev.stackexchange.com/questions/77658/how-to-match-font-size-with-screen-resolution
     * @param filePath the .ttf file path and name of the true type font
     * @param densityIndependentPixels an abstract unit based on the physical density of the screen, determines the font size
     * @return the generated BitmapFont
     */
    public static BitmapFont generateFont(String filePath, float densityIndependentPixels) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(filePath));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = (int) (densityIndependentPixels * Gdx.graphics.getDensity());

        return fontGenerator.generateFont(fontParameter);
    }
}
