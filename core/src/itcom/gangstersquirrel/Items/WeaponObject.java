package itcom.gangstersquirrel.Items;

/**
 * An object which stores a weapon's information such as ID, type and strength
 */
public class WeaponObject {

    // Test commit in the collectibles branch

    private int id;
    private String name;
    private boolean meleeOrRanged;
    private int strength;

    public WeaponObject(int id, String name, boolean meleeOrRanged, int strength) {
        this.id = id;
        this.name = name;
        this.meleeOrRanged = meleeOrRanged;
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
