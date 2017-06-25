package maxlich.game.activeobjects.enemy;

import maxlich.game.Arena;
import maxlich.game.activeobjects.ActiveObject;

import static maxlich.game.utils.Helper.printMsg;

/**
 * Created by Максим on 25.06.2017.
 */
public class EnemyWithIqLevel2 extends Enemy {
    private int lastHP; // очки здоровья во время последнего хода противника
    private int lastUnguessedLocationOfPLayer = -1; //последняя неугаданная позиция игрока

    public EnemyWithIqLevel2(String name, int healthpoints, int minDamage, int maxDamage, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, arena);
    }

    public EnemyWithIqLevel2(String name, int healthpoints, int minDamage, int maxDamage, int location, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, location, arena);
    }

    public EnemyWithIqLevel2(Arena arena) {
        super(arena);
    }

    public EnemyWithIqLevel2(String name, Arena arena) {
        super(name, arena);
    }

    public int getLastHP() {
        return lastHP;
    }

    public void setLastHP(int lastHP) {
        this.lastHP = lastHP;
    }

    private boolean wasChangingHP() {
        return healthPoints.current != lastHP;
    }

    @Override
    public int getIqLevel() {
        return 2;
    }

    @Override
    public Action attackOrMove() {
        if (wasChangingHP())
            return Action.MOVE;
        if (numOfMisses == 0 || numOfMisses == 1)
            return Action.ATTACK;

        return makeARandomChoiceOfAction();
    }

    @Override
    public int move() {
        if (location == 1)
            return ++location;
        if (location == arena.getSize())
            return --location;
        int random = (int) (Math.random() * 2);
        lastHP = healthPoints.current;
        return makeAMove((random == 0) ? -1 : 1);
    }

    @Override
    public int attack(ActiveObject player) {
        int locPlayer = guessLocationOfPlayer();
        printMsg("Противник думает, что Ваше местоположение: " + locPlayer + "\n");
        boolean hittingThePlayer = player.checkLocation(locPlayer);
        if (hittingThePlayer) { //если враг попал по игроку (угадал позицию игрока)
            lastPlayerLocation = locPlayer;
            numOfMisses = 0;

            int curDamage = this.damage(player);
            printMsg("Враг угадал Ваше местоположение, атаковал Вас и нанёс Вам урон " + curDamage + " ед.");
            printMsg("Ваши очки здоровья после атаки врага: ");
            player.printHealth();

            lastHP = healthPoints.current;
            return curDamage;
        } else { //если враг проманулся

            printMsg("Враг промахнулся!");
            if (numOfMisses < 0)
                numOfMisses = 1;
            else
                numOfMisses++; //увеличиваем количество промахов на 1

            if (numOfMisses >= 2)
                lastPlayerLocation = -1;
            else
                lastUnguessedLocationOfPLayer = locPlayer;

            lastHP = healthPoints.current;
            return 0;
        }
    }

    @Override
    int guessLocationOfPlayer() {
        if (lastPlayerLocation > 0) {
            if (numOfMisses == 0) {
                if (lastPlayerLocation == 1) {
                    return (playerHasMoved) ? 2 : 1;
                } else if (lastPlayerLocation == arena.getSize()) {
                    return (playerHasMoved) ? (arena.getSize() - 1) : arena.getSize();
                } else {
                    if (!playerHasMoved) return lastPlayerLocation;
                    int random = (int) (Math.random() * 2);
                    return (random == 0) ? lastPlayerLocation - 1 : lastPlayerLocation + 1;
                }
            } else if (numOfMisses == 1) {
                if (!playerHasMoved) {
                    if (lastUnguessedLocationOfPLayer > lastPlayerLocation)
                        return lastPlayerLocation - 1;
                    else
                        return lastPlayerLocation + 1;
                } else {
                    int random = (int) (Math.random() * 2);
                    if (lastUnguessedLocationOfPLayer > lastPlayerLocation)
                        return (random == 0) ? lastPlayerLocation - 2 : lastPlayerLocation;
                    else
                        return (random == 0) ? lastPlayerLocation : lastPlayerLocation + 2;
                }
            }
        }
        return (int) (Math.random() * arena.getSize()) + 1;
    }
}
