package maxlich.game.activeobjects.enemy;

import maxlich.game.Arena;
import maxlich.game.activeobjects.ActiveObject;

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
        int random = (int) (Math.random() * 2);
        return makeAMove((random == 0)? -1: 1);
    }

    Action makeARandomChoiceOfAction() {
        int choice = (int) (Math.random() * 2);
        return Action.values()[choice];
    }

    abstract int guessLocationOfPlayer(); //угадывание позиции игрока, возвращает предполагаемую позицию

    /*public int guessLocationOfPlayer() { //попытка угадать позицию игрока, возращает предполагаемую позицию
        //используется предыдущая угаданная позиция
        //int mult = MAXLOCATION; //множителя для случайных чисел
        int loc = 3; //предполагаемая позиция игрока
        switch (iqLevel) {
            case 1:
                if (lastPlayerLocation >= 1 && numOfMisses == 0) loc = lastPlayerLocation;
                else loc = (int) (Math.random() * arena.getSize()) + 1;

                return loc;
            case 2:
                //TODO:
                if (lastPlayerLocation > 0) {
                    if (numOfMisses == 0) {
                        if (lastPlayerLocation == 1) {
                            return (playerHasMoved) ? 2 : 1;
                        } else if (lastPlayerLocation == arena.getSize()) {
                            return (playerHasMoved) ? (arena.getSize() - 1) : arena.getSize();
                        } else {
                            int random = (int) (Math.random() * 2);
                            return (random == 0) ? lastPlayerLocation - 1 : lastPlayerLocation + 1;
                        }
                    } else if (numOfMisses == 1) {

                    }
                } else {
                    return (int) (Math.random() * arena.getSize()) + 1;
                }


 *//*               if (locationsOfPlayer[1] >= 0) { //если угадана позиция игрока
                    if (playerWasMoving) {  //и если игрок двигался после этого
                        if (locationsOfPlayer[1] == 0) { //если позиция была нулевая
                            loc = locationsOfPlayer[1] + 1; //то игрок мог передвинуться только на первую
                        } else if (locationsOfPlayer[1] == MAXLOCATION) { //если позиция конечная
                            loc = locationsOfPlayer[1] - 1; //то игрок мог передвинуться только влево
                        } else { //если была угадана позиция игрока, он передвинулся и находился ни у одного из краёв линии передвижения
                            loc = (((int) (Math.random() * 2)) == 1) ? (locationsOfPlayer[1] + 1) : (locationsOfPlayer[1] - 1); //две позиции: позиция слева и позиция справа от текущей
                        }
                    } else { // если игрок не двигался
                        if (locationsOfPlayer[1] >= 0 && numOfMisses == 1) {
                            //if ()

                        } else {
                            loc = locationsOfPlayer[1];
                        }
                    }
                } else { //если позиция не была угадана, то ищём на линии передвижения случайным образом
                    loc = (int) (Math.random() * (MAXLOCATION + 1));
                }
                System.out.println(loc);*//*
                return loc;
            case 3:
                return loc;
            default:
                return loc;
        }

    }*/
}
