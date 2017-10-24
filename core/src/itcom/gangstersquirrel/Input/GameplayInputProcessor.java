package itcom.gangstersquirrel.Input;

import com.badlogic.gdx.InputProcessor;

import static itcom.gangstersquirrel.Screens.PlayScreen.keyBindings;
import static itcom.gangstersquirrel.Screens.PlayScreen.isJumping;
import static itcom.gangstersquirrel.Screens.PlayScreen.isMovingLeft;
import static itcom.gangstersquirrel.Screens.PlayScreen.isMovingRight;

public class GameplayInputProcessor implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {

        if (keyBindings.MOVE_LEFT.contains(keycode)) {
            isMovingLeft = true;
        }

        if (keyBindings.MOVE_RIGHT.contains(keycode)) {
            isMovingRight = true;
        }

        if (keyBindings.JUMP.contains(keycode)) {
            isJumping = true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keyBindings.MOVE_LEFT.contains(keycode)) {
            isMovingLeft = false;
        }

        if (keyBindings.MOVE_RIGHT.contains(keycode)) {
            isMovingRight = false;
        }

        if (keyBindings.JUMP.contains(keycode)) {
            isJumping = false;
        }

        return false;
    }

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
