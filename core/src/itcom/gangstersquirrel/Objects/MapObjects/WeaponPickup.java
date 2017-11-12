package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Screens.PlayScreen;

public class WeaponPickup extends InteractiveMapTileObject {

    private PlayScreen playScreen;

    public WeaponPickup(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);

        fixture.setUserData(this);

        playScreen = screen;
    }

    @Override
    public void onPlayerBeginContact() {

        // TODO: Identify the collided object, at the moment it just looks if there is any object with a weapon property

        MapObjects objects = playScreen.getMap().getLayers().get("weapon").getObjects();
        for (MapObject object : objects) {
            if (object.getProperties().containsKey("weapon_name")) {
                String weaponName = object.getProperties().get("weapon_name", String.class);
                Gdx.app.log("Picked up weapon", weaponName);
            }
        }
    }

    @Override
    public void onPlayerEndContact() {

    }
}
