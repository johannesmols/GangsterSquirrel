package itcom.gangstersquirrel.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    private boolean isFacingLeftOrRight;

    // Texture variables
    private final int PLAYER_PIXEL_WIDTH = 48;
    private final int PLAYER_PIXEL_HEIGHT = 24;
    private HashMap<String, String> textureRegionNames = new HashMap<String, String>(); // K = Display Name, V = Texture region name
    private HashMap<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>(); // K = Texture region name, V = Texture region

    // Attack texture variables
    private boolean attackAnimationPlaying;
    private float attackTime = 0f;
    private final float ATTACK_DURATION = 1f;

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

        flipPlayerDirection(isFacingLeftOrRight);
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

        flipPlayerDirection(isFacingLeftOrRight);
    }

    /**
     * Update the position of the sprite
     * @param deltaTime the time between the last and current frame in seconds
     */
    public void update(float deltaTime) {
        // Make the sprite stay in the same position when switching directions
        if (isFacingLeftOrRight) {
            setPosition(body.getPosition().x - getWidth(), body.getPosition().y - getHeight() / 2);
        } else {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }

        // Attack animation
        if (attackTime <= ATTACK_DURATION) {
            if (attackAnimationPlaying) {
                attackTime += deltaTime;

                switch (screen.getGameProgress().getPlayerWeaponList().get(screen.getGameProgress().getCurrentlyEquippedWeapon()).getName()) {
                    case "Fists":
                        // No attack texture yet
                        break;
                    case "Branch":
                        setRegion(textureRegions.get("branch_attack"));
                        flipPlayerDirection(isFacingLeftOrRight);
                        break;
                    case "Swiss Army Knife":
                        setRegion(textureRegions.get("swiss_army_knife_attack"));
                        flipPlayerDirection(isFacingLeftOrRight);
                        break;
                    case "Switchblade":
                        setRegion(textureRegions.get("switchblade_attack"));
                        flipPlayerDirection(isFacingLeftOrRight);
                        break;
                    case "Katana":
                        setRegion(textureRegions.get("katana_attack"));
                        flipPlayerDirection(isFacingLeftOrRight);
                        break;
                    case "Tommy Gun":
                        // No attack texture yet
                        break;
                    case "Bazooka":
                        // No attack texture yet
                        break;
                    default:
                        break;
                }
            }
        } else {
            attackTime = 0f;
            attackAnimationPlaying = false;
            changeWeapon(screen.getGameProgress().getPlayerWeaponList().get(screen.getGameProgress().getCurrentlyEquippedWeapon()).getName());
        }
    }

    /**
     * Flip the player's texture horizontally
     * @param leftOrRight if true, the player now faces the left side
     */
    public void flipPlayerDirection(boolean leftOrRight) {
        setFlip(leftOrRight, false);
        isFacingLeftOrRight = leftOrRight;
    }

    /**
     * Executes an attack by the player with the currently equipped weapon in the direction he is facing
     */
    public void attack() {
        if (!attackAnimationPlaying) {
            attackAnimationPlaying = true;

            // Deal damage to all enemies in range
            if (isFacingLeftOrRight) {
                for (Enemy enemy : screen.getEnemiesCurrentlyInLeftAttackRange()) {
                    enemy.setHealth(enemy.getHealth() - screen.getGameProgress().getPlayerWeaponList().get(screen.getGameProgress().getCurrentlyEquippedWeapon()).getStrength());
                }
            } else {
                for (Enemy enemy : screen.getEnemiesCurrentlyInRightAttackRange()) {
                    enemy.setHealth(enemy.getHealth() - screen.getGameProgress().getPlayerWeaponList().get(screen.getGameProgress().getCurrentlyEquippedWeapon()).getStrength());
                }
            }
        }
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

        // Create fixtures for the body
        createNewFixtures();
    }

    private void createNewFixtures() {

        FixtureDef playerFixtureDef = new FixtureDef();
        playerFixtureDef.shape = getPlayerShape();
        playerFixtureDef.filter.categoryBits = MainGameClass.CATEGORY_PLAYER;
        playerFixtureDef.filter.maskBits = MainGameClass.MASK_PLAYER;
        body.createFixture(playerFixtureDef).setUserData(this);

        FixtureDef attackRightFixtureDef = new FixtureDef();
        attackRightFixtureDef.shape = getAttackShape(false);
        attackRightFixtureDef.isSensor = true;
        attackRightFixtureDef.filter.categoryBits = MainGameClass.CATEGORY_PLAYER_ATTACK;
        attackRightFixtureDef.filter.maskBits = MainGameClass.MASK_PLAYER_ATTACK;
        body.createFixture(attackRightFixtureDef).setUserData("player_attack_right");

        FixtureDef attackLeftFixtureDef = new FixtureDef();
        attackLeftFixtureDef.shape = getAttackShape(true);
        attackLeftFixtureDef.isSensor = true;
        attackLeftFixtureDef.filter.categoryBits = MainGameClass.CATEGORY_PLAYER_ATTACK;
        attackLeftFixtureDef.filter.maskBits = MainGameClass.MASK_PLAYER_ATTACK;
        body.createFixture(attackLeftFixtureDef).setUserData("player_attack_left");
    }

    private PolygonShape getPlayerShape() {

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                PLAYER_PIXEL_WIDTH / 4 / MainGameClass.PPM, // half the texture width to not include white space of the texture in the collision box
                PLAYER_PIXEL_HEIGHT / 2 / MainGameClass.PPM, // the full texture height (sizes are half sizes, so divided by two)
                new Vector2( - PLAYER_PIXEL_WIDTH / 4 / MainGameClass.PPM, 0), // center of the box, needs to be half of the half size of the texture negative to make the flipping work
                0f // the angle of the box
        );

        return shape;
    }

    /**
     * Create a shape that attaches to the right or left side of the player to check collisions with enemies when attacking
     * @param leftOrRight if the shape should be created on the left or right side of the player (true = left; false = right)
     * @return the shape
     */
    private PolygonShape getAttackShape(boolean leftOrRight) {

        // Center of the shape needs to be shifted three fourths of the player center to the left, if it should be attached on the left side, because the origin of the shape is on the left side of the shape
        int factor = leftOrRight ? -3 : 1;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                PLAYER_PIXEL_WIDTH / 4 / MainGameClass.PPM,
                0,
                new Vector2( factor * PLAYER_PIXEL_WIDTH / 4 / MainGameClass.PPM, 0),
                0f
        );

        return shape;
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

        // Manually add the attack textures, naming convention: displayName_attack (for example: branch_attack)
        addAttackTextureRegions("branch_attack", "squirrel_branch_frame1");
        addAttackTextureRegions("swiss_army_knife_attack", "squirrel_swiss_army_knife_frame1");
        addAttackTextureRegions("switchblade_attack", "squirrel_switchblade_frame1");
        addAttackTextureRegions("katana_attack", "squirrel_katana_frame1");
    }

    /**
     * Add texture regions to the HashMap that are used to display attacks, it is not working with the display name like the default player textures
     * @param name the key of the HashMap, the reference name of the texture region
     * @param region the actual texture region for the texture
     */
    private void addAttackTextureRegions(String name, String region) {
        textureRegions.put(
                name,
                new TextureRegion(
                        getTexture(),
                        screen.getPlayerTextureAtlas().findRegion(region).getRegionX(),
                        screen.getPlayerTextureAtlas().findRegion(region).getRegionY(),
                        PLAYER_PIXEL_WIDTH, PLAYER_PIXEL_HEIGHT
                )
        );
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
