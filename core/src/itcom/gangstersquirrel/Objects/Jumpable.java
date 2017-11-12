package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class Jumpable extends InteractiveTileObject {

    private PlayScreen playScreen;

    public Jumpable(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        fixture.setUserData(this);

        playScreen = screen;
    }

    @Override
    public void onPlayerBeginContact() {
        // Player is on jumpable ground
        playScreen.getPlayer().setIsOnJumpableGround(true);
    }

    @Override
    public void onPlayerEndContact() {
        // Player leaves jumpable ground
        playScreen.getPlayer().setIsOnJumpableGround(false);
    }
}
