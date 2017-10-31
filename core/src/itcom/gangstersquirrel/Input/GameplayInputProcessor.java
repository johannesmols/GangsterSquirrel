package itcom.gangstersquirrel.Input;

import com.badlogic.gdx.InputProcessor;

import static itcom.gangstersquirrel.Screens.PlayScreen.*;

/**
 * Handles most gameplay related input for the PlayScreen class
 */
public class GameplayInputProcessor implements InputProcessor {

    /**
     * Gets called every time a key is pressed down
     * @param keycode an integer, which contains the keycode of the pressed key
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {

        if (keyBindings.EXIT.contains(keycode)) {
            isPressingExit = true;
        }

        if (keyBindings.MOVE_LEFT.contains(keycode)) {
            isPressingMoveLeft = true;
        }

        if (keyBindings.MOVE_RIGHT.contains(keycode)) {
            isPressingMoveRight = true;
        }

        if (keyBindings.JUMP.contains(keycode)) {
            isPressingJump = true;
        }

        return false;
    }

    /**
     * Gets called every time a key is released
     * @param keycode an integer, which contains the keycode of the pressed key
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {

        if (keyBindings.EXIT.contains(keycode)) {
            isPressingExit = false;
        }

        if (keyBindings.MOVE_LEFT.contains(keycode)) {
            isPressingMoveLeft = false;
        }

        if (keyBindings.MOVE_RIGHT.contains(keycode)) {
            isPressingMoveRight = false;
        }

        if (keyBindings.JUMP.contains(keycode)) {
            isPressingJump = false;
        }

        return false;
    }

    /**
     * Gets called every time a key is pressed down and released again
     * @param character the character of the typed key
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
