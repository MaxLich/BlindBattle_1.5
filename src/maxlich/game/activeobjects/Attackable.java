package maxlich.game.activeobjects;

/**
 * Created by Максим on 22.06.2017.
 */
public interface Attackable {
    int attack(ActiveObject object); // производит атаку объектом другого объекта, возвращает нанесённый урон
}
