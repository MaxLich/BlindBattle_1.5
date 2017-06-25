package maxlich.game.activeobjects.enemy;

import maxlich.game.Arena;
import maxlich.game.activeobjects.ActiveObject;

/**
 * Created by Максим on 25.06.2017.
 */
public class EnemyWithIqLevel3 extends Enemy {
    public EnemyWithIqLevel3(String name, int healthpoints, int minDamage, int maxDamage, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, arena);
    }

    public EnemyWithIqLevel3(String name, int healthpoints, int minDamage, int maxDamage, int location, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, location, arena);
    }

    public EnemyWithIqLevel3(Arena arena) {
        super(arena);
    }

    public EnemyWithIqLevel3(String name, Arena arena) {
        super(name, arena);
    }

    @Override
    public int getIqLevel() {
        return 3;
    }

    @Override
    public Action attackOrMove() {
        return null;
    }

    @Override
    public int move() {
        return 0;
    }


    //TODO: проверить всё дерево ходов игрока
    //сделать массив всего поля и отмечать на нём уже выбранные позиции
    @Override
    public int attack(ActiveObject object) {
        return 0;
    }

    @Override
    int guessLocationOfPlayer() {
        return 0;
    }
}
