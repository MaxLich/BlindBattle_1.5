package maxlich.game.activeobjects.enemy;

import maxlich.game.Arena;
import maxlich.game.activeobjects.ActiveObject;

import static maxlich.game.utils.Helper.generateRandomInt;
import static maxlich.game.utils.Helper.printMsg;

/**
 * Created by Максим on 24.06.2017.
 */
public class EnemyWithIqLevel1 extends Enemy {
    public EnemyWithIqLevel1(Arena arena) {
        super(arena);
    }

    public EnemyWithIqLevel1(String name, Arena arena) {
        super(name, arena);
    }

    public EnemyWithIqLevel1(String name, int healthpoints, int minDamage, int maxDamage, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, arena);
    }

    public EnemyWithIqLevel1(String name, int healthpoints, int minDamage, int maxDamage, int location, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, location, arena);
    }

    @Override
    public int getIqLevel() {
        return 1;
    }


    @Override
    public Action attackOrMove() {
        return makeARandomChoiceOfAction();
    }

    @Override
    public int move() {
        return randomMove();
    }

    @Override
    protected void missOnPlayer(int locPlayer) {
        if (numOfMisses < 0)
            numOfMisses = 1;
        else
            numOfMisses++; //увеличиваем количество промахов на 1

        if (numOfMisses >= 2) lastPlayerLocation = -1;
    }

    @Override
    protected int InflictDamageOnPlayer(ActiveObject player) {
        int curDamage = this.damage(player);
        printMsg("Враг угадал Ваше местоположение, атаковал Вас и нанёс Вам урон " + curDamage + " ед.");
        printMsg("Ваши очки здоровья после атаки врага: ");
        player.printHealth();
        return curDamage;
    }

    @Override
    int guessLocationOfPlayer() {
        if (lastPlayerLocation >= 1 && numOfMisses == 0)
            return lastPlayerLocation;
        else
            return generateRandomInt(arena.getSize()) + 1;
    }
}
