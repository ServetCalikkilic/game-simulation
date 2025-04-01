public class Game {
    public static void main(String[] args) {

        Warrior warrior = new Warrior("Conan", 100, 15, 60);
        Mage mage = new Mage("Merlin", 90, 12, 40);
        Archer archer = new Archer("Robin", 85, 16, 65);

        // Pre-Battle: Equip items.
        // Warrior: "Steel Shield" (ARMOR), "Power Bracer" (ENERGY), "Sword of Might" (ATTACK)
        warrior.addItemToInventory(new Item("Steel Shield", "ARMOR", 20));
        warrior.addItemToInventory(new Item("Power Bracer", "ENERGY", 10));
        warrior.addItemToInventory(new Item("Sword of Might", "ATTACK", 5));
        warrior.equipItemFromInventory(0);
        warrior.equipItemFromInventory(0);
        warrior.equipItemFromInventory(0);

        // Mage: "Mana Pendant" (MANA)
        mage.addItemToInventory(new Item("Mana Pendant", "MANA", 8));
        mage.equipItemFromInventory(0);

        // Archer: "Eagle Eye" (ACCURACY), "Energy Booster" (ENERGY)
        archer.addItemToInventory(new Item("Eagle Eye", "ACCURACY", 10));
        archer.addItemToInventory(new Item("Energy Booster", "ENERGY", 8));
        archer.equipItemFromInventory(0);
        archer.equipItemFromInventory(0);

        System.out.println("\n === Combat Simulation ===");

        System.out.println("\n --- Round 1 ---");
        // Round 1:
        // Warrior special attacks Mage.
        warrior.specialAttack(mage);
        // Mage basic attacks Archer.
        mage.attack(archer);
        // Archer special attacks Warrior.
        archer.specialAttack(warrior);

        System.out.println("\n --- Round 2 ---");
        // Round 2:
        // Warrior basic attacks Archer.
        warrior.attack(archer);
        // Mage special attacks Warrior.
        mage.specialAttack(warrior);
        // Archer basic attacks Warrior.
        archer.attack(warrior);

        System.out.println("\n --- Final Status ---");
        System.out.println(warrior);
        System.out.println(mage);
        System.out.println(archer);

        System.out.println("\n=== Error Messages Test ===");
        // Create a small Mage for testing (Inventory: 2, Equipment: 1)
        Mage testMage = new Mage("TestMage", 50, 10, 30);

        // Try to add 3 items to inventory.
        testMage.addItemToInventory(new Item("Item1", "MANA", 5));
        testMage.addItemToInventory(new Item("Item2", "ATTACK", 3));
        // Third addition should trigger an error.
        testMage.addItemToInventory(new Item("Item3", "MANA", 4));

        // Try to equip more than capacity.
        // Since testMage's equipment capacity is 1, equipping a second item should trigger an error.
        testMage.equipItemFromInventory(0); // First item equipped successfully.
        testMage.equipItemFromInventory(0); // Second equip attempt should display an error.

        Archer testArcher = new Archer("Robin", 85, 16, 65);
        // Try to equip an item with an invalid bonus type.
        testArcher.addItemToInventory(new Item("Item4", "MANA", 4));
        testArcher.equipItemFromInventory(0); // Equip attempt should display an error.
    }
}
//An exception for using when an item broken.
class EquipmentBrokenException extends Exception{
    public EquipmentBrokenException(String message){
        System.out.println(message);
    }
}
//An exception for when the equipment is full.
class EquipmentFullException extends Exception{
    public EquipmentFullException(String message){
        System.out.println("Error: " + message);
    }
}
//An exception for when a bonus type is not allowed for the usage.
class BonusNotAllowedException extends Exception{
    public BonusNotAllowedException(String message){
        System.out.println("Error: " + message);
    }
}
//An exception for when the inventory is full.
class InventoryFullException extends Exception{
    public InventoryFullException(String message){
        System.out.println("Error: " + message);
    }
}

//Class for the item.
class Item{
    private String name;
    private String bonusType;
    private int bonusValue;
    //The constructor of the class.
    public Item(String name, String bonusType, int bonusValue){
        this.name=name;
        this.bonusType=bonusType;
        this.bonusValue=bonusValue;
    }
    //Getter method of name.
    public String getName(){
        return name;
    }

    //Setter method of name.
    public void setName(String name){
        this.name=name;
    }

    //Getter method of bonus type.
    public String getBonusType(){
        return bonusType;
    }

    //Setter method of bonus type.
    public void setBonusType(String bonusType){
        this.bonusType=bonusType;
    }

    //Getter method of bonus value.
    public int getBonusValue(){
        return bonusValue;
    }

    //Setter method of bonus value.
    public void setBonusValue(int bonusValue){
        this.bonusValue=bonusValue;
    }

    //this method is used for reducing the bonus of item by the amount.
    public void reduceBonus(int amount) throws EquipmentBrokenException{
        if (amount < getBonusValue()){
            setBonusValue(getBonusValue()-amount);
        }
        else {
            setBonusValue(0);
            throw new EquipmentBrokenException(getName() + " has broken!");
        }
    }

    //To String method.
    public String toString(){
        return name + "[" + bonusType + " Bonus: " + bonusValue + "]";
    }
}
//Abstract Character Class
abstract class Character{
    //Private Variables:
    private String name;
    private int health;
    private int attackPower;
    private int maxHealth;
    public Item[] inventory;
    private int inventoryCapacity;
    public Item[] equipment;
    private int equipmentCapacity;
    private String notifications;

    //Constructor of the class.
    public Character(String name, int health, int attackPower, int inventoryCapacity, int equipmentCapacity){
        this.name=name;
        this.health=health;
        this.attackPower=attackPower;
        this.maxHealth=health;
        this.inventory = new Item[inventoryCapacity];
        this.equipment = new Item[equipmentCapacity];
    }
    //Getter method of name.
    public String getName(){
        return name;
    }

    //Setter method of name.
    public void setName(String name){
        this.name=name;
    }

    //Getter method of health.
    public int getHealth(){
        return health;
    }

    //Setter method of health.
    public void setHealth(int health){
        this.health=health;
    }

    //Getter method of Attack Power.
    public int getAttackPower(){
        return attackPower;
    }

    //Setter method of Attack Power.
    public void setAttackPower(int attackPower){
        this.attackPower=attackPower;
    }

    //Getter method of Max Health.
    public int getMaxHealth(){
        return maxHealth;
    }

    //Setter method of Max Health.
    public void setMaxHealth(){
        this.maxHealth=maxHealth;
    }

    //Getter method of Inventory.
    public Item getInventory(int index){
        return inventory[index];
    }

    //Setter method of Inventory.
    public void setInventory(Item item, int index){
        inventory[index]=item;
    }

    //Getter method of Inventory Capacity.
    public int getInventoryCapacity(){
        return inventoryCapacity;
    }

    //Setter method of Inventory Capacity.
    public void setInventoryCapacity(int inventoryCapacity){
        this.inventoryCapacity=inventoryCapacity;
    }

    //Getter method of Equipment.
    public Item getEquipment(int index){
        return equipment[index];
    }

    //Setter method of Equipment.
    public void setEquipment(Item item, int index){
        equipment[index]=item;
    }

    //Getter method of Equipment Capacity.
    public int getEquipmentCapacity(){
        return equipmentCapacity;
    }

    //Setter method of Equipment Capacity.
    public void setEquipmentCapacity(int equipmentCapacity){
        this.equipmentCapacity=equipmentCapacity;
    }

    //A method for adding notification.
    public void addNotification(String msg){
        notifications = notifications + "\n" + msg;
    }

    //To print the notification.
    public void printNotifications(){
        System.out.println(notifications);
        notifications="";
    }

    //Abstract method for printing the stats.
    public abstract String getStatString();

    //Abstract method to get Total Armor.
    public abstract int getTotalArmor();

    //Abstract method to get Total Base Armor.
    public abstract int getTotalBaseArmor();

    //Abstract method for checking the bonus.
    protected abstract boolean isBonusAllowed(String bonusType);

    //Method for printing current stats.
    public void printCurrentStats(){
        System.out.println(name + getStatString() + health + notifications);
    }

    //For reducing the health.
    protected void takeHealthDamage(int damage){
        setHealth(Math.max(0, health-damage));
        System.out.println(name + " takes " + damage + " damage. (Health: " + health + ")");
    }

    //Checks ignoreArmor then applies the damage.
    public void receiveDamage(int damage, boolean ignoreArmor){
        if (ignoreArmor){
            System.out.println("This attack ignores armor! Full damage applied.");
            takeHealthDamage(damage);
        }
        else if (getTotalArmor() == 0) {
            takeHealthDamage(damage);
        }
        else if (getTotalArmor() >= 50) {
            System.out.println("Damage reduced by 10% (Armor reduced by: 20)");
            //Math round for rounding the value and casting it to integer.
            takeHealthDamage((int)(Math.round(damage*0.9)));
            baseArmorReduction();
        }
        else {
            System.out.println("Damage reduced by 5% (Armor reduced by: 20)");
            //Math round for rounding the value and casting it to integer.
            takeHealthDamage((int)(Math.round(damage*0.95)));
            baseArmorReduction();
        }
    }

    //Method for overriding.
    protected int baseArmorReduction(){
        return 0;
    }

    //Inventory/Equipment Methods:
    public boolean addItemToInventory(Item item){
        try {
            boolean full = true;
            for (int i=0; i<inventoryCapacity; i++){
                if (inventory[i] == null){
                    inventory[i]=item;
                    i=inventoryCapacity;
                    System.out.println(name + " added " + item.getName() + " to inventory. (" + item.getBonusType() + " bonus: " + item.getBonusValue() + ")");
                    full = false;
                    i=inventory.length;
                }
            }
            if (full){
                throw new InventoryFullException(getName() + "'s inventory is full!");
            }
        }
        catch (InventoryFullException e){

        }
        return false;
    }

    public Item removeItemFromInventory(int index){
        if (index < 0 || index >= inventoryCapacity){
            return inventory[index];
        }
        else {
            inventory[index] = null;
            for (int i=index; i+1<inventoryCapacity; i++){
                inventory[i]=inventory[i+1];
            }
            inventory[inventoryCapacity-1] = null;
            return inventory[index];
        }
    }

    public boolean equipItemFromInventory(int invIndex){
        Item itemKey = inventory[invIndex];
        try {
            removeItemFromInventory(invIndex);
            equipItem(itemKey);

        }
        catch (EquipmentFullException e){
            addItemToInventory(itemKey);
        }
        catch (BonusNotAllowedException e){
            addItemToInventory(itemKey);
        }
        return true;
    }

    public boolean unequipItem(int equipIndex){
        boolean full = true;
        for (int i=0; i<inventoryCapacity; i++){
            if (inventory[i] == null){
                inventory[i]=inventory[equipIndex];
                System.out.println("Item unequipped.");
                full = false;
                removeEquipmentAtIndex(equipIndex);
                break;
            }
        }
        if (full){
            return false;
        }
        return true;
    }

    public int getEquipmentBonus(String bonusType){
        int totalBonus=0;
        for (int i=0; i<equipmentCapacity; i++){
            if (equipment[i] != null && equipment[i].getBonusType() == bonusType){
                totalBonus += equipment[i].getBonusValue();
            }
        }
        return totalBonus;
    }

    public void equipItem(Item item) throws EquipmentFullException, BonusNotAllowedException {
        if (isBonusAllowed(item.getBonusType()) == false){
            throw new BonusNotAllowedException(item.getBonusType() + " bonus type is not allowed for " + getName() + ".");
        }
        boolean equipmentFull=true;
        int slot = 0;
        for (slot=0; slot<equipmentCapacity; slot++){
            if (equipment[slot] == null){
                equipmentFull = false;
                break;
            }
        }
        if (equipmentFull){
            throw new EquipmentFullException(getName() + "' equipment slots are full!");
        }
        else {
            System.out.println(getName() + " equipped " + item.getName() +
                    " (" + item.getBonusType() + " bonus: " + item.getBonusValue() + ").");
            setEquipment(item, slot);

        }
    }

    //Consumes equipmentBonus first if it is not sufficient consumes base resource.
    protected int consumeResource(String resourceType, int cost, int baseResource){
        try {
            if (getEquipmentBonus(resourceType) == 0){
                baseResource -= cost;
                return baseResource;
            }
            else {
                consumeResourceBonus(resourceType, cost);
                return baseResource;
            }
        }
        catch (EquipmentBrokenException e){
            for (int i=0; i<equipmentCapacity; i++){
                if (equipment[i] != null && equipment[i].getBonusType() == resourceType){
                    removeEquipmentAtIndex(i);
                }
            }
            int remains = cost - getEquipmentBonus(resourceType);
            baseResource -= remains;
            return baseResource;
        }
    }

    //Consumes items bonus.
    protected void consumeResourceBonus(String resourceType, int cost) throws EquipmentBrokenException{
        for (int i=0; i<equipment.length; i++){
            if (equipment[i] != null && equipment[i].getBonusType() == resourceType){
                equipment[i].reduceBonus(cost);
            }
        }
    }

    //Removes the item in given index.
    public void removeEquipmentAtIndex(int index){
        for (int i=index; i+1<equipmentCapacity; i++){
            equipment[i]=equipment[i+1];
        }
        inventory[equipmentCapacity-1] = null;
    }
}

//Warrior Class
class Warrior extends Character{
    private int baseArmor;
    private int energy;

    //Constructor of the class.
    public Warrior(String name, int health, int attackPower, int baseArmor) {
        super(name, health, attackPower, 5, 4);
        setInventoryCapacity(5);
        setEquipmentCapacity(4);
        this.baseArmor=baseArmor;
        energy = 50;
    }

    @Override
    public int getTotalArmor() {
        int totalArmor=baseArmor;
        for(int i=0; i<equipment.length; i++){
            if (equipment[i] != null && equipment[i].getBonusType() == "ARMOR"){
                totalArmor += equipment[i].getBonusValue();
            }
        }
        return totalArmor;
    }

    @Override
    public int getTotalBaseArmor() {
        return baseArmor;
    }

    @Override
    protected boolean isBonusAllowed(String bonusType){
        if (bonusType == "ATTACK" || bonusType == "ARMOR" || bonusType == "ENERGY"){
            return true;
        }
        return false;
    }

    @Override
    protected int baseArmorReduction(){
        consumeResource("ARMOR", 20, getTotalBaseArmor());
        return baseArmor;
    }

    //Simple attack method.
    public void attack(Character target){
        energy += getEquipmentBonus("ENERGY");
        int damage = getAttackPower() + getEquipmentBonus("ATTACK");
        System.out.println(getName() + " performs a basic attack on " + target.getName() + " (Energy reduced by: 10)");
        target.receiveDamage(damage, false);
        energy = consumeResource("ENERGY", 10, energy);
        System.out.println(getStatString());
    }

    //Special Attack method.
    public void specialAttack(Character target){
        int damage;

        if (energy +  getEquipmentBonus("ENERGY") >= 15){
            energy += getEquipmentBonus("ENERGY");
            damage = getAttackPower() + getEquipmentBonus("ATTACK") + 5;
            System.out.println(getName() + " uses " + equipment[0].getName() + " on " + target.getName() + " (Energy reduced by: 15)");
            target.receiveDamage(damage, false);
            energy = consumeResource("ENERGY", 15, energy);
            System.out.println(getStatString());
        }
        else {
            attack(target);
        }
    }

    @Override
    public String getStatString() {
        return "Conan's current stats: Total Attack: " + (getAttackPower() + getEquipmentBonus("ATTACK")) +
                ", Total Armor: " + (baseArmor + getEquipmentBonus("ARMOR")) +
                ", Total Energy: " + (energy + getEquipmentBonus("ENERGY")) +
                ", Health: " + getHealth();
    }

    //To string method.
    public String toString(){
        int totalEnergy = (energy + getEquipmentBonus("ENERGY"));
        String equipmentString = " ";
        for (int i=0; i<equipment.length; i++){
            if (equipment[i] != null){
                equipmentString += "Equipment: " + equipment[i].getName() + " [" + equipment[i].getBonusType() + " bonus: " + equipment[i].getBonusValue() + "])";
            }
        }
        return getName() + "(Health: " + getHealth() +
                ", Total Attack: " + (getAttackPower() + getEquipmentBonus("ATTACK"))+
                ", Total Armor: " + (getTotalArmor() + getEquipmentBonus("ARMOR")) +
                ", Total Energy: " + (totalEnergy + getEquipmentBonus("ENERGY")) +
                " | " + equipmentString;
    }
}

class Mage extends Character{
    private int mana;

    //Constructor of the class.
    public Mage(String name, int health, int attackPower, int mana){
        super(name, health, attackPower, 2, 1);
        setInventoryCapacity(2);
        setEquipmentCapacity(1);
        this.mana=mana;
    }

    @Override
    protected boolean isBonusAllowed(String bonusType){
        if (bonusType == "ATTACK" || bonusType == "MANA"){
            return true;
        }
        return false;

    }

    @Override
    public int getTotalArmor(){
        return 0;
    }

    @Override
    public int getTotalBaseArmor(){
        return 0;
    }

    //Simple attack method.
    public void attack(Character target){
        int damage = getAttackPower();
        System.out.println("Merlin casts a basic spell on " + target.getName() + " (Mana reduced by 10)");
        if (mana + getEquipmentBonus("MANA") >= 10){
            mana += getEquipmentBonus("MANA");
            damage = getAttackPower() + getEquipmentBonus("ATTACK");
            target.receiveDamage(damage, false);


        }
        else {
            target.receiveDamage(damage, false);

        }
        mana = consumeResource("MANA", 10, mana);
        System.out.println(getStatString());

    }

    //Special attack method.
    public void specialAttack(Character target){
        int damage;
        if (mana + getEquipmentBonus("MANA") >= 15){
            mana += getEquipmentBonus("MANA");
            System.out.println("Merlin uses a penetrating spell on " + target.getName() + " (Mana reduced by 15)");
            damage = getAttackPower() + getEquipmentBonus("ATTACK") + 10;
            if (target instanceof Warrior){
                target.receiveDamage(damage, true);
            }
            else {
                target.receiveDamage(damage, false);

            }
            mana = consumeResource("MANA", 15, mana);
            System.out.println(getStatString());
        }
        else {
            attack(target);
        }
    }

    //Method for getting the stats.
    public String getStatString(){
        return "Merlin's current stats: Total Attack: " + (getAttackPower() + getEquipmentBonus("ATTACK")) +
                ", Total Mana: " + (mana + getEquipmentBonus("MANA")) +
                ", Health: " + getHealth();

    }

    //To string method.
    public String toString(){
        String equipmentString = "";
        for (int i=0; i<getEquipmentCapacity(); i++){
            equipmentString += "Equipment: ";
            if (equipment[i] != null && equipment[i].getBonusValue() != 0){
                equipmentString += equipment[i].getName() + " [" + equipment[i].getBonusType() + " bonus: " + equipment[i].getBonusValue() + "]";
            }
        }
        return getName() + "(Health: " + getHealth() +
                ", Total Attack: " + getAttackPower()+
                ", Total Mana: " + mana +
                " | " + equipmentString + ")";
    }
}

//Archer class.
class Archer extends Character{
    private int accuracy;
    private int energy;

    //The constructor of the class.
    public Archer(String name, int health, int attackPower, int accuracy){
        super(name, health, attackPower, 4, 3);
        setInventoryCapacity(4);
        setEquipmentCapacity(3);
        this.accuracy=accuracy;
        energy = 50;
    }

    @Override
    protected boolean isBonusAllowed(String bonusType){
        if (bonusType == "ATTACK" || bonusType == "ACCURACY" || bonusType == "ENERGY"){
            return true;

        }
        return false;
    }

    @Override
    public int getTotalArmor(){
        return 0;
    }

    @Override
    public int getTotalBaseArmor(){
        return 0;
    }

    //Simple attack method.
    public void attack(Character target){
        int damage;
        System.out.println("Robin fires a basic arrow  at " + target.getName() + " (Energy reduced by: 10)");
        if (energy +  getEquipmentBonus("ENERGY") < 10){
            energy += getEquipmentBonus("ENERGY");
            damage = getAttackPower();
            target.receiveDamage(damage, false);
            energy = consumeResource("ENERGY", 10, energy);
        }
        else {
            damage = getAttackPower() + getEquipmentBonus("ATTACK");
            target.receiveDamage(damage, false);
            if (accuracy >= 80){
                damage+=5;
            }
            energy = consumeResource("ENERGY", 10, energy);
        }
        System.out.println(getStatString());
    }

    //Special attack method.
    public void specialAttack(Character target){
        int damage;
        if (energy +  getEquipmentBonus("ENERGY") >= 15){
            energy += getEquipmentBonus("ENERGY");
            System.out.println("Robin performs a critical shot on " + target.getName() + " (Energy reduced by: 15)");
            damage = getAttackPower() + getEquipmentBonus("ATTACK") + 5;
            if (accuracy >= 80){
                damage+=5;
            }
            if (target instanceof Warrior){

                target.receiveDamage(damage, true);
            }
            else {
                target.receiveDamage(damage, false);
            }
        }
        else {
            attack(target);
        }
        energy = consumeResource("ENERGY", 15, energy);
        System.out.println(getStatString());
    }

    //Method for getting the stats.
    public String getStatString(){
        return "Robin's current stats: Total Attack: " + (getAttackPower() + getEquipmentBonus("ATTACK")) +
                ", Total Aaccuracy: " + (accuracy + getEquipmentBonus("ACCURACY")) +
                ", Total Energy: " + (energy + getEquipmentBonus("ENERGY")) +
                ", Health: " + getHealth();
    }

    //To string method.
    public String toString(){
        String equipmentString = " ";
        for (int i=0; i<equipment.length; i++){
            if (equipment[i] != null){
                equipmentString += "Equipment: " + equipment[i].getName() + " [" + equipment[i].getBonusType() + " bonus: " + equipment[i].getBonusValue() + "])";
            }
        }
        return getName() + "(Health: " + getHealth() +
                ", Total Attack: " + (getAttackPower() + getEquipmentBonus("ATTACK"))+
                ", Total Accuracy: " + (accuracy  + getEquipmentBonus("ACCURACY"))+
                ", Total Energy: " + (energy + getEquipmentBonus("ENERGY")) +
                " | " + equipmentString;
    }
}
