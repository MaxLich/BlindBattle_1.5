package maxlich.game.activeobjects;

import maxlich.game.Arena;

import static maxlich.game.utils.Helper.generateRandomInt;
import static maxlich.game.utils.Helper.printMsg;

/**
 * Created by Максим on 22.06.2017.
 */
public abstract class ActiveObject implements Movable, Attackable {
    protected String name;
    protected HealthPoints healthPoints;// хп объекта: текущее и максимальное
    protected Damage dmg; //урон, наносимый объектом: минимальный, максимальный, текущий
    protected int location; //текущая позиция объекта
    protected Arena arena;

    protected ActiveObject(String name, int healthpoints, int minDamage, int maxDamage, int location, Arena arena) {
        this.name = name;
        this.healthPoints = new HealthPoints();
        this.healthPoints.max = this.healthPoints.current = healthpoints;
        this.dmg = new Damage();
        dmg.min = minDamage;
        dmg.max = maxDamage;
        this.location = location;
        this.arena = arena;
    }

    protected ActiveObject(String name, int healthpoints, int minDamage, int maxDamage,  Arena arena) {
        this(name,healthpoints,minDamage,maxDamage,arena.getRandomLocation(),arena);
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getCurrentHeath() {
        return healthPoints.current;
    }

    public void setCurrentHealth(int newHP) {
        this.healthPoints.current = newHP;
    }

    public int getMaxHealth() {
        return healthPoints.max;
    }

    public void setMaxHealth(int newMaxHP, boolean alsoSetCurrentHP) {
        healthPoints.max = newMaxHP;
        if (alsoSetCurrentHP)
            healthPoints.current = newMaxHP;
    }

    public void changeCurrentHealthPoints(int value) {
        this.healthPoints.current += value;
    }

    public void setMinDamage(int minDamage) {
        dmg.min = minDamage;
    }

    public int getMinDamage() {
        return dmg.min;
    }

    public void setMaxDamage(int maxDamage) {
        dmg.max = maxDamage;
    }

    public int getMaxDamage() {
        return dmg.max;
    }

    public void printCharacteristics() {
        printMsg(this.toString());
    }

    public void printHealth() {
        printMsg(this.healthPoints.current + "/" + this.healthPoints.max + "\n");
    }

    @Override
    public String toString() {
        return name + " -> очки здоровья: " + this.healthPoints.max + ", урон: " + this.dmg.min + "-" + this.dmg.max;
    }

    public abstract Action attackOrMove();

    public boolean checkLocation(int loc) {
        return (this.location == loc);
    }

    protected int damage(ActiveObject otherObject) {
        int deltaDamage = this.dmg.max - this.dmg.min;
        this.dmg.current = generateRandomInt(++deltaDamage) + this.dmg.min;

        otherObject.healthPoints.current -= this.dmg.current;
        return this.dmg.current;
    }

    protected void checkBorders(int minLocation, int maxLocation) { //проверить, чтобы объект не выходил за границы арены
        if (location < minLocation) location = minLocation;
        if (location > maxLocation) location = maxLocation;
    }

    protected int makeAMove(int delta) { //совершить движение (передвинуться на delta)
        this.location += delta;
        checkBorders(1,arena.getSize());
        return this.location;
    }


    //вспомогательные внутренние классы
    protected static class Damage {
        int min;
        int max;
        int current;
    }

    protected static class HealthPoints {
        public int current;
        public int max;
    }

    public enum Action {
        ATTACK,
        MOVE
    }
}
