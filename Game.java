public class Game {

	public static void main(String[] args) {
		//Create characters
		Warrior warrior = new Warrior("Conan", 100, 15, 60);
		Mage mage = new Mage("Merlin", 80, 10, 30);
		Archer archer = new Archer("Robin", 80, 10, 30);
		
		//Create and equip items
		warrior.addItemToInventory(new Item("Sword of Might", "ATTACK", 5));
		warrior.addItemToInventory(new Item("Shield of Valor", "HEALTH", 15));
		warrior.addItemToInventory(new Item("Rune of Power", "ACCURACY", 5));
		warrior.equipItemFromInventory(0); //Equips Sword of Might (Bonus ATTACK: 5)
		warrior.equipItemFromInventory(0); //Equips Shield of Valor (Bonus HEALTH: 15)
		
		mage.addItemToInventory(new Item("Mystic Staff", "MANA", 10));
		mage.addItemToInventory(new Item("Rune of Fury", "ATTACK", 5));
		mage.equipItemFromInventory(0); //Equips Mystic Staff (Bonus MANA: 10)
		
		archer.addItemToInventory(new Item("Longbow", "ATTACK",	4));
		archer.addItemToInventory(new Item("Quiver of Precision", "ACCURACY", 5));
		archer.equipItemFromInventory(0); //Equips Longbow (Bonus ATTACK: 4)
		archer.equipItemFromInventory(0); //Equips Quiver of Precision (Bonus ACCURACY: 5)
		
		//Short combat simulation
		System.out.println("\n--- Combat Simulation ---");
		//Warrior attacks Mage.
		warrior.warriorAttack(mage);
		//Mage attacks Archer.
		mage.mageAttack(archer);
		//Archer attack Warrior
		archer.archerAttack(warrior);
		
		//Removal test: Mage unequios its equipment.
		System.out.println("\n--- Removal Test ---");
		mage.unequipItem(0); //Should remove Mystic Staff and show bonus as 0.
		
		//Final status.
		System.out.println("\n-- Final Stats --");
		System.out.println(warrior);
		System.out.println(mage);
		System.out.println(archer);
		
	}
}
class Item{
    public String name;
    public String bonusType;
    public int bonusValue;

    public Item(String name, String bonusType, int bonusValue){
        this.name=name;
        this.bonusType=bonusType;
        this.bonusValue=bonusValue;
    }
    public String getName(){
        return this.name;
    }
    public String getBonusType(){
        return this.bonusType;
    }
    public int getBonusValue(){
        return this.bonusValue;
    }
    public String toString(){
        return name + " [" + bonusType + ": " + bonusValue + "]";
    }
}
class Character{
    public String name;
    public int health;
    public int attackPower;
    public Item[] inventory;
    public Item[] equipment;

    public Character(String name, int health, int attackPower){
        this.name=name;
        this.health=health;
        this.attackPower=attackPower;
    }
    public int calculateHealth(){
        int health=0;
        for (int i=0; i<equipment.length; i++){
            if (equipment[i] == null){
                break;
            }
            if (equipment[i].getBonusType() == "HEALTH"){
                health+=equipment[i].getBonusValue();
            }
        }
        return health;
    }
    public int calculateMana(){
        int mana=0;
        for (int i=0; i<equipment.length; i++){
            if (equipment[i] == null){
                break;
            }
            if (equipment[i].getBonusType() == "MANA"){
                mana+=equipment[i].getBonusValue();
            }
        }
        return mana;
    }
    public int calculateAccuracy(){
        int accuracy=0;
        for (int i=0; i<equipment.length; i++){
            if (equipment[i] == null){
                break;
            }
            if (equipment[i].getBonusType() == "ACCURACY"){
                accuracy+=equipment[i].getBonusValue();
            }
        }
        return accuracy;
    }
    public void takeDamage(int damage){
        if (health<=damage){
            health=0;
        }
        health-=damage;
        System.out.println(name+ " takes " + damage + " damage. (Current Health: " + health + ")");
    }
    public String toString(){
        String str= "" + getClass();
        String a = "";
        return str.substring(6) + " " + name + " [Current Health: " + health+ ", Total Attack: " + attackPower;
    }
    public void addItemToInventory(Item item){
        boolean a=true;
        int key=0;
        while (a){
            if (inventory[key] == null){
                inventory[key]= item;
                a=false;
            }
            key++;
        }
    }
    public void removeItemFromInventory(int index){
        boolean a=true;
        int key=index;
        if (inventory[key+1] == null){
            a=false;
        }
        while (a){
            inventory[key] = inventory[key++];
        }
    }
    public void equipItemFromInventory(int invIndex){
        boolean b=true;
        int key=0;
        while (b){
            if (equipment[key] == null){
                equipment[key]= inventory[invIndex];
                b=false;
            }
            key++;
        }

        for (int i=invIndex; i<inventory.length-1; i++){
            inventory[i] = inventory[i+1];
        }

        System.out.println(name+" equips " + equipment[key-1].getName() + " from inventory. (Bonus " + equipment[key-1].getBonusType() + ": "+ equipment[key-1].getBonusValue() + ")");
    }
    public void unequipItem(int equipIndex){
        int key=0;
        String bonusType = equipment[equipIndex].getBonusType();
        switch (bonusType){
            case("ATTACK"):
                attackPower-=equipment[equipIndex].getBonusValue();
                break;
            case("HEALTH"):
                health-=equipment[equipIndex].getBonusValue();
                break;

        }

        for (int i=0; i<inventory.length; i++){
            if (inventory[i] == null){
                inventory[i] = equipment[equipIndex];
                key = i;
                i= inventory.length-1;
            }
        }
        for (int i=0; i<equipment.length-1; i++){
            equipment[i] = equipment[i++];
        }

        System.out.println(name+" removes " + equipment[key].getName() + " from equipment. (Bonus " + inventory[key].getBonusType() + ": 0)");
    }
}
class Warrior extends Character{
    public double armor;


    public Warrior(String name, int health, int attackPower, double armor){
        super(name, health, attackPower);
        this.armor=armor;
        inventory = new Item[6];
        equipment = new Item[3];
    }
    public String toString(){
        String className = "";
        className = "" + getClass();
        String str = "";
        int attackBonus =0;
        int healthBonus =0;
        int armorBonus =0;

        for(int i=0; i<equipment.length-1; i++){
            String bonusType = equipment[i].getBonusType();
            switch (bonusType){
                case "ATTACK":
                    attackBonus+=equipment[i].getBonusValue();
                    str += ", Bonus ATTACK: " + attackBonus;
                    break;
                case "HEALTH":
                    healthBonus+=equipment[i].getBonusValue();
                    str += ", Bonus HEALTH: " + healthBonus;
                    break;
                case "ARMOR":
                    armorBonus+=equipment[i].getBonusValue();
                    str += ", Bonus ARMOR: " + armorBonus;
                    break;
            }
        }
        str += "]";
        return className.substring(6) + " " + name + " [Current Health: " + health+ ", Total Attack: " + attackPower + ", Current Armor: " + armor + str;
    }
    public void warriorTakeDamage(int damage, boolean ignoreArmor){
        if (ignoreArmor || armor==0){
            takeDamage(damage);
        }
        else if(armor >= 50){
            damage = (int)Math.round(damage*9.0/10.0);
            takeDamage(damage);
            armor-= 10;
        }
        else {
            damage = (int)Math.round(damage*19/20);
            takeDamage(damage);
            if (armor<=10){
                armor=0;
            }
            else {
                armor-=10;
            }
        }
        System.out.println(name + "'s Remaining Armor: " + armor);
    }
    public void warriorAttack(Character target){
        int attPower = attackPower;
        for (int i=0; i<equipment.length; i++){
            if (equipment[i] != null){
                if (equipment[i].getBonusType() == "ATTACK"){
                    attPower+=equipment[i].getBonusValue();
                }
            }
        }
        System.out.println(name + " attacks " + target.name + " with total power " + attPower);
        target.takeDamage(attPower);
        attackPower=attPower;
    }
}
class Mage extends Character{
    public int mana;


    public Mage(String name, int health, int attackPower, int mana){
        super(name, health, attackPower);
        this.mana=mana;
        inventory=new Item[2];
        equipment=new Item[1];
    }
    public String toString(){
        String className = "";
        className = "" + getClass();
        String str = "";
        int attackBonus =0;
        int healthBonus =0;
        int manaBonus =0;

        for(int i=0; i<equipment.length; i++){
            if (equipment[i].getBonusType() == "ATTACK"){
                str += ", Bonus ATTACK: " + attackBonus;
            }
            if (equipment[i].getBonusType() == "HEALTH"){
                str += ", Bonus HEALTH: " + healthBonus;
            }
            if (equipment[i].getBonusType() == "MANA"){
                str += ", Bonus MANA: " + manaBonus;
            }
        }
        str += "]";
        return className.substring(6) + " " + name + " [Current Health: " + health+ ", Total Attack: " + attackPower + ", Current Mana: " + mana + str;
    }
    public void mageAttack(Character target){
        int totalDamage;
        if (mana >=10){
            if (equipment[0].getBonusType() == "ATTACK"){
                totalDamage = attackPower + equipment[0].getBonusValue() + 5;
            }
            else {
                totalDamage = attackPower + 5;
            }
            mana-=10;
        }
        else {
            if (equipment[0].getBonusType() == "ATTACK"){
                totalDamage = attackPower + equipment[0].getBonusValue();
            }
            else {
                totalDamage = attackPower;
            }
        }
        System.out.println(name + " casts a spell on " + target.name + " dealing " + totalDamage + " damage!");
        target.takeDamage(totalDamage);
        System.out.println(name + "'s Remaining Mana: " + mana);
        attackPower=totalDamage;
    }
}
class Archer extends Character{
    public int accuracy;

    public Archer(String name, int health, int attackPower, int accuracy){
        super(name,health,attackPower);
        this.accuracy=accuracy;
        inventory= new Item[4];
        equipment= new Item[2];
    }
    public String toString(){
        String className = "";
        className = "" + getClass();
        String str = "";
        int attackBonus =0;
        int healthBonus =0;
        int accuracyBonus =0;

        for(int i=0; i<equipment.length; i++){
            if (equipment[i].getBonusType() == "ATTACK"){
                str += ", Bonus ATTACK: " + attackBonus;
            }
            if (equipment[i].getBonusType() == "HEALTH"){
                str += ", Bonus HEALTH: " + healthBonus;
            }
            if (equipment[i].getBonusType() == "ACCURACY"){
                str += ", Bonus ACCURACY: " + accuracyBonus;
            }
        }
        str += "]";
        return className.substring(6) + " " + name + " [Current Health: " + health+ ", Total Attack: " + attackPower + ", Accuracy: " + accuracy + str;
    }
    public void archerAttack(Character target){
        boolean a=true;
        int key=0;
        int totalAtt= attackPower;
        while (a){
            if (equipment[key].getBonusType() == "ATTACK"){
                totalAtt+= equipment[key].getBonusValue();
                a=false;
            }
            key++;
        }

        boolean b=true;
        int key2=0;
        int totalAcc= accuracy;
        while (b){
            if (equipment[key2].getBonusType() == "ACCURACY"){
                totalAcc+= equipment[key2].getBonusValue();
                b=false;
            }
            key2++;
        }
        if (totalAcc > 80){
            totalAtt+=5;
        }
        System.out.println(name + " attacks " + target.name + " with total power " + totalAtt);
        if (target instanceof Warrior){
            ((Warrior) target).warriorTakeDamage(totalAtt, false);
        }
        else {
            target.takeDamage(totalAtt);
        }
        attackPower=totalAtt;
    }
}
