package itcom.gangstersquirrel.Objects.MapObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import itcom.gangstersquirrel.Items.WeaponList;
import itcom.gangstersquirrel.Items.WeaponObject;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Objects.Enemy;
import itcom.gangstersquirrel.Objects.InteractiveMapTileObject;
import itcom.gangstersquirrel.Objects.Player;
import itcom.gangstersquirrel.Screens.PlayScreen;

import java.util.ArrayList;

public class WeaponPickup extends InteractiveMapTileObject {

    private PlayScreen playScreen;

    public WeaponPickup(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        playScreen = screen;

        fixture.setUserData(this);
        createFilterMask();
    }

    @Override
    public void onPlayerBeginContact(Player player) {

        // Remove texture
        getCell(map.getLayers().getIndex("graphics")).setTile(null);

        // Add the picked up weapon
        MapObject pickedUpWeapon = getCollidingMapObject(map.getLayers().getIndex("weapon"));
        if (pickedUpWeapon != null && pickedUpWeapon.getProperties().containsKey("weapon_name")) {
            String weaponName = pickedUpWeapon.getProperties().get("weapon_name", String.class);
            playScreen.log("Picked up weapon: " +  weaponName);

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
                    playScreen.log("Weapon not defined");
                    break;
            }

            if (changedWeaponList) {
                playScreen.getPlayer().setWeapons(playerWeaponList);
                playScreen.getPlayer().changeWeapon(weaponName);

                // Save picked up item to statistics
                playScreen.getStatistics().setItemsCollected(playScreen.getStatistics().getItemsCollected() + 1);
            }
        }
    }

    @Override
    public void onPlayerEndContact(Player player) {

    }

    @Override
    public void onEnemyBeginContact(Enemy enemy) {

    }

    @Override
    public void onEnemyEndContact(Enemy enemy) {

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
                playScreen.log("Picked up weapon already in player's weapon list");
                return true;
            }
        }

        return false;
    }

    @Override
    public void createFilterMask() {
        Filter filter = new Filter();
        filter.categoryBits = MainGameClass.CATEGORY_WEAPON;
        filter.maskBits =  MainGameClass.MASK_WEAPON;
        fixture.setFilterData(filter);
    }
}
