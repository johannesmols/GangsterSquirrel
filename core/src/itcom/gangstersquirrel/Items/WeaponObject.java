package itcom.gangstersquirrel.Items;

/**
 * An object which stores a weapon's information such as ID, type and strength
 */
public class WeaponObject {

    private int id;
    private boolean meleeOrRanged;
    private int strength;

    public WeaponObject(int id, boolean meleeOrRanged, int strength) {
        this.id = id;
        this.meleeOrRanged = meleeOrRanged;
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
