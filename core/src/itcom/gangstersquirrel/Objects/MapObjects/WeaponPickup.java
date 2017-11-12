package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Rectangle;
import itcom.gangstersquirrel.Items.WeaponList;
import itcom.gangstersquirrel.Items.WeaponObject;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Screens.PlayScreen;

import java.util.ArrayList;

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

                boolean changedWeaponList = false;
                ArrayList<WeaponObject> playerWeaponList = playScreen.getPlayer().getWeapons();
                switch (weaponName) {
                    case "Branch":
                        WeaponObject weapon = getWeaponObjectByName("Branch");
                        if (weapon != null && !weaponAlreadyInList(weapon)) {
                            playerWeaponList.add(weapon);
                            changedWeaponList = true;
                        }
                        break;
                    default:
                        break;
                }

                if (changedWeaponList) {
                    playScreen.getPlayer().setWeapons(playerWeaponList);
                    playScreen.getPlayer().changeWeapon(weaponName);
                }
            }
        }
    }

    @Override
    public void onPlayerEndContact() {

    }

    private WeaponObject getWeaponObjectByName(String name) {
        for (WeaponObject weapon : new WeaponList().getAllWeapons()) {
            if (weapon.getName().equals(name)) {
                return weapon;
            }
        }
        return null;
    }

    private boolean weaponAlreadyInList(WeaponObject weapon) {
        for (WeaponObject weaponName : playScreen.getPlayer().getWeapons()) {
            if (weaponName.getName().equals(weapon.getName())) {
                Gdx.app.log("Weapon List", "Picked up weapon already in player's weapon list");
                return true;
            }
        }

        return false;
    }
}
