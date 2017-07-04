package maxlich.game.activeobjects.enemy;

import maxlich.game.Arena;
import maxlich.game.activeobjects.ActiveObject;
import maxlich.game.utils.Helper;

import static maxlich.game.utils.Helper.getRandomInt;
import static maxlich.game.utils.Helper.getRandomIntOf2;

/**
 * Created by Максим on 22.06.2017.
 */
public abstract class Enemy extends ActiveObject {
    private final static int beginHP = 10;
    private final static int beginMinDamage = 1;
    private final static int beginMaxDamage = 3;


    boolean playerHasMoved; //отметка о том, двигался ли игрок
    int numOfMisses = -1; //количество промахов
    int lastPlayerLocation = -1; //последняя угаданная позиция игрока

    Enemy(Arena arena) {
        super("Враг",beginHP,beginMinDamage,beginMaxDamage, arena);
    }

    Enemy(String name,Arena arena) {
        super(name,beginHP,beginMinDamage,beginMaxDamage, arena);
    }

    Enemy(String name, int healthpoints, int minDamage, int maxDamage, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, arena);
    }

    Enemy(String name, int healthpoints, int minDamage, int maxDamage, int location, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, location, arena);
    }

    public abstract int getIqLevel();

    public void setPlayerHasMoved(boolean playerHasMoved) {
        this.playerHasMoved = playerHasMoved;
    }

    int randomMove() {
        int random  = getRandomIntOf2();
        return makeAMove((random == 0)? -1: 1);
    }

    Action makeARandomChoiceOfAction() {
        int choice = getRandomIntOf2();
        return Action.values()[choice];
    }

    abstract int guessLocationOfPlayer(); //угадывание позиции игрока, возвращает предполагаемую позицию

}
