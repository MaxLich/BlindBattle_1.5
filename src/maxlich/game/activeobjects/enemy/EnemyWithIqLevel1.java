package maxlich.game.activeobjects.enemy;

import maxlich.game.Arena;
import maxlich.game.activeobjects.ActiveObject;

import static maxlich.game.utils.Helper.getRandomInt;
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

    public int attack(ActiveObject player) {
        int locPlayer = guessLocationOfPlayer();
        printMsg("Противник думает, что Ваше местоположение: " + locPlayer + "\n");
        boolean hittingThePlayer = player.checkLocation(locPlayer);
        if (hittingThePlayer) { //если враг попал по игроку (угадал позицию игрока)
            lastPlayerLocation = locPlayer;
            numOfMisses = 0;

            int curDamage = this.damage(player);
            printMsg("Враг угадал Ваше местоположение, атаковал Вас и нанёс Вам урон " + curDamage  + " ед.");
            printMsg("Ваши очки здоровья после атаки врага: ");
            player.printHealth();
            return curDamage;
        } else { //если враг проманулся
            printMsg("Враг промахнулся!");
            if (numOfMisses < 0)
                numOfMisses = 1;
            else
                numOfMisses++; //увеличиваем количество промахов на 1

            if (numOfMisses >= 2) lastPlayerLocation = -1;
            return 0;
        }
    }

    @Override
    int guessLocationOfPlayer() {
        if (lastPlayerLocation >= 1 && numOfMisses == 0)
            return lastPlayerLocation;
        else
            return getRandomInt(arena.getSize()) + 1;
    }
}
