package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import itcom.gangstersquirrel.GameProgress.GameProgress;
import itcom.gangstersquirrel.Items.WeaponList;
import itcom.gangstersquirrel.Items.WeaponObject;
import itcom.gangstersquirrel.MainGameClass;
import itcom.gangstersquirrel.Screens.PlayScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The class of the player, extending the Sprite class and handling the textures, animations, positions and more of the player
 */
public class Player extends Sprite {

    private PlayScreen screen;
    private World world;
    public Body body;

    // Player parameters
    private int health;
    private ArrayList<WeaponObject> weapons = new ArrayList<>();
    private float jumpImpulseVelocity;
    private float walkImpulseVelocity;
    private float climbImpulseVelocity;
    private float maxWalkVelocity;
    private float maxClimbVelocity;

    // Spawn parameters
    private int spawnTileX = 0;
    private int spawnTileY = 0;

    // Gameplay relevant variables
    private boolean isOnJumpableGround;

    // Texture variables
    private final int PLAYER_PIXEL_WIDTH = 48;
    private final int PLAYER_PIXEL_HEIGHT = 24;

    private HashMap<String, String> textureRegionNames = new HashMap<String, String>(); // K = Display Name, V = Texture region name
    private HashMap<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>(); // K = Texture region name, V = Texture region

    public Player(PlayScreen screen, int spawnPosition_X, int spawnPosition_Y) {
        // Get texture region out of the texture atlas for the squirrel
        super(screen.getPlayerTextureAtlas().findRegion("squirrel_default"));

        this.screen = screen;
        this.world = screen.getWorld();
        this.spawnTileX = spawnPosition_X;
        this.spawnTileY = spawnPosition_Y;
        this.weapons = new GameProgress().getPlayerWeaponList();

        definePlayer();

        setUpTextureRegions();

        setBounds(0, 0, PLAYER_PIXEL_WIDTH / MainGameClass.PPM, PLAYER_PIXEL_HEIGHT / MainGameClass.PPM);

        // Set current player texture to the first weapon texture in the list of the player's weapons
        if (weapons.size() > screen.getGameProgress().getCurrentlyEquippedWeapon()) {
            setRegion(textureRegions.get(weapons.get(screen.getGameProgress().getCurrentlyEquippedWeapon()).getName()));
        } else {
            setRegion(textureRegions.get("Fists"));
        }
    }

    /**
     * Change the texture of the player depending on the current weapon he is holding
     * @param indexInWeaponList the index of the new weapon in the weapon list
     */
    public void changeWeapon(int indexInWeaponList) {
        if (weapons.size() - 1 >= indexInWeaponList) {
            setRegion(textureRegions.get(weapons.get(indexInWeaponList).getName()));
            screen.getGameProgress().setCurrentlyEquippedWeapon(indexInWeaponList);
        }
    }

    /**
     * Change the texture of the player depending on the current weapon he is holding
     * @param weaponName the name of the new weapon in the weapon list
     */
    public void changeWeapon(String weaponName) {
        if (weapons.size() > 0) {
            for (int i = 0; i < weapons.size(); i++) {
                if (weapons.get(i).getName().equals(weaponName)) {
                    setRegion(textureRegions.get(weaponName));
                    screen.getGameProgress().setCurrentlyEquippedWeapon(i);
                }
            }
        }
    }

    /**
     * Update the position of the sprite
     * @param deltaTime the time between the last and current frame in seconds
     */
    public void update(float deltaTime) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    /**
     * Defines the Box2D physics properties of the player, including collision box, body and fixture definition
     */
    private void definePlayer() {

        // Body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnTileX * MainGameClass.TILE_PIXEL_SIZE / MainGameClass.PPM, spawnTileY * MainGameClass.TILE_PIXEL_SIZE / MainGameClass.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        // Shape definition
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(PLAYER_PIXEL_WIDTH / 4 / MainGameClass.PPM, PLAYER_PIXEL_HEIGHT / 2 / MainGameClass.PPM); // divided by 4 to make width of collision box half as wide as the texture

        // Fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        // Create body
        body.createFixture(fixtureDef);

        // Collision sensor fixture definition
        FixtureDef collisionFixtureDef = new FixtureDef();
        collisionFixtureDef.shape = shape;
        collisionFixtureDef.isSensor = true;
        body.createFixture(collisionFixtureDef).setUserData("player");
    }

    private void setUpTextureRegions() {
        // Add weapon display names as key and their texture region names as values in the HashMap for texture region names
        List<WeaponObject> weaponList = new WeaponList().getAllWeapons();
        for (WeaponObject weapon : weaponList) {
            switch (weapon.getName()) {
                case "Fists":
                    textureRegionNames.put(weapon.getName(), "squirrel_default");
                    break;
                case "Branch":
                    textureRegionNames.put(weapon.getName(), "squirrel_branch_frame0");
                    break;
                case "Swiss Army Knife":
                    textureRegionNames.put(weapon.getName(), "squirrel_swiss_army_knife_frame0");
                    break;
                case "Switchblade":
                    textureRegionNames.put(weapon.getName(), "squirrel_switchblade_frame0");
                    break;
                case "Katana":
                    textureRegionNames.put(weapon.getName(), "squirrel_katana_frame0");
                    break;
                case "Tommy Gun":
                    textureRegionNames.put(weapon.getName(), "squirrel_tommy_gun_frame0");
                    break;
                case "Bazooka":
                    textureRegionNames.put(weapon.getName(), "squirrel_bazooka_frame0");
                    break;
                default:
                    break;
            }
        }

        // Add texture regions to the HashMap for texture regions, with the key being the display name of the weapon
        for (String textureRegionName : textureRegionNames.keySet()) {
            textureRegions.put(
                    textureRegionName,
                    new TextureRegion(
                            getTexture(),
                            screen.getPlayerTextureAtlas().findRegion(textureRegionNames.get(textureRegionName)).getRegionX(),
                            screen.getPlayerTextureAtlas().findRegion(textureRegionNames.get(textureRegionName)).getRegionY(),
                            PLAYER_PIXEL_WIDTH,
                            PLAYER_PIXEL_HEIGHT
                    )
            );
        }
    }

    /* ----- GETTER AND SETTER -------------------------------------------------------------------------------------- */

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public ArrayList<WeaponObject> getWeapons() {
        return weapons;
    }

    public void setWeapons(ArrayList<WeaponObject> weapons) {
        this.weapons = weapons;
        screen.getGameProgress().setPlayerWeaponList(weapons);
    }

    public float getJumpImpulseVelocity() {
        return jumpImpulseVelocity;
    }

    public void setJumpImpulseVelocity(float jumpImpulseVelocity) {
        this.jumpImpulseVelocity = jumpImpulseVelocity;
    }

    public float getWalkImpulseVelocity() {
        return walkImpulseVelocity;
    }

    public void setWalkImpulseVelocity(float walkImpulseVelocity) {
        this.walkImpulseVelocity = walkImpulseVelocity;
    }

    public float getClimbImpulseVelocity() {
        return climbImpulseVelocity;
    }

    public void setClimbImpulseVelocity(float climbImpulseVelocity) {
        this.climbImpulseVelocity = climbImpulseVelocity;
    }

    public float getMaxWalkVelocity() {
        return maxWalkVelocity;
    }

    public void setMaxWalkVelocity(float maxWalkVelocity) {
        this.maxWalkVelocity = maxWalkVelocity;
    }

    public float getMaxClimbVelocity() {
        return maxClimbVelocity;
    }

    public void setMaxClimbVelocity(float maxClimbVelocity) {
        this.maxClimbVelocity = maxClimbVelocity;
    }

    public boolean getIsOnJumpableGround() {
        return isOnJumpableGround;
    }

    public void setIsOnJumpableGround(boolean onJumpableGround) {
        isOnJumpableGround = onJumpableGround;
    }

    /* ---------------------------------------------------------------------------------------------------------------*/
}
