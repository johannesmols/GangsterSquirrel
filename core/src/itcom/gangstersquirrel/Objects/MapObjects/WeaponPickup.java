package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
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

        // Remove texture
        getCell(map.getLayers().getIndex("graphics")).setTile(null);

        // Add the picked up weapon
        MapObject pickedUpWeapon = getCellProperties(map.getLayers().getIndex("weapon"));
        if (pickedUpWeapon.getProperties().containsKey("weapon_name")) {
            String weaponName = pickedUpWeapon.getProperties().get("weapon_name", String.class);
            Gdx.app.log("Picked up weapon", weaponName);

            boolean changedWeaponList = false;
            ArrayList<WeaponObject> playerWeaponList = playScreen.getPlayer().getWeapons();
            WeaponObject weapon;
            switch (weaponName) {
                case "Fists":
                    weapon = getWeaponObjectByName("Fists");
                    if (weapon != null && !weaponAlreadyInList(weapon)) {
                        playerWeaponList.add(weapon);
                        changedWeaponList = true;
                    }
                    break;
                case "Branch":
                    weapon = getWeaponObjectByName("Branch");
                    if (weapon != null && !weaponAlreadyInList(weapon)) {
                        playerWeaponList.add(weapon);
                        changedWeaponList = true;
                    }
                    break;
                case "Swiss Army Knife":
                    weapon = getWeaponObjectByName("Swiss Army Knife");
                    if (weapon != null && !weaponAlreadyInList(weapon)) {
                        playerWeaponList.add(weapon);
                        changedWeaponList = true;
                    }
                    break;
                case "Switchblade":
                    weapon = getWeaponObjectByName("Switchblade");
                    if (weapon != null && !weaponAlreadyInList(weapon)) {
                        playerWeaponList.add(weapon);
                        changedWeaponList = true;
                    }
                    break;
                case "Katana":
                    weapon = getWeaponObjectByName("Katana");
                    if (weapon != null && !weaponAlreadyInList(weapon)) {
                        playerWeaponList.add(weapon);
                        changedWeaponList = true;
                    }
                    break;
                case "Tommy Gun":
                    weapon = getWeaponObjectByName("Tommy Gun");
                    if (weapon != null && !weaponAlreadyInList(weapon)) {
                        playerWeaponList.add(weapon);
                        changedWeaponList = true;
                    }
                    break;
                case "Bazooka":
                    weapon = getWeaponObjectByName("Bazooka");
                    if (weapon != null && !weaponAlreadyInList(weapon)) {
                        playerWeaponList.add(weapon);
                        changedWeaponList = true;
                    }
                    break;
                default:
                    Gdx.app.log("Undefined", "Weapon not defined");
                    break;
            }

            if (changedWeaponList) {
                playScreen.getPlayer().setWeapons(playerWeaponList);
                playScreen.getPlayer().changeWeapon(weaponName);
            }
        } else {
            Gdx.app.log("Not a weapon", "Collided object not a weapon");
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
