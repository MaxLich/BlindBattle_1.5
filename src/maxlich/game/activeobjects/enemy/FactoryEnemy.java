package maxlich.game.activeobjects.enemy;

import maxlich.game.Arena;

/**
 * Created by Максим on 24.06.2017.
 */
public class FactoryEnemy {
    public static Enemy getNewEnemy(int iqLevel, String name, int healthpoints, int minDamage, int maxDamage, Arena arena) {
        switch (iqLevel) {
            case 1:
                return new EnemyWithIqLevel1(name, healthpoints, minDamage, maxDamage, arena);
            case 2:
                return new EnemyWithIqLevel2(name, healthpoints, minDamage, maxDamage, arena);
            case 3:
                return new EnemyWithIqLevel3(name, healthpoints, minDamage, maxDamage, arena);
            default:
                return new EnemyWithIqLevel1(name, healthpoints, minDamage, maxDamage, arena);
        }
    }

}
