package maxlich.game.activeobjects.enemy;

import maxlich.game.Arena;
import maxlich.game.activeobjects.ActiveObject;

import static maxlich.game.utils.Helper.generateRandomIntOf2;
import static maxlich.game.utils.Helper.printMsg;

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

    @Override
    public int move() { //выбор, куда двигаться
        int random  = generateRandomIntOf2();
        return makeAMove((random == 0)? -1: 1);
    }

    public Action attackOrMove() { //совершить случайный выбор действия: двигаться или атаковать
        int choice = generateRandomIntOf2();
        return Action.values()[choice];
    }

    @Override
    public int attack(ActiveObject player) { //попытка атаки игрока
        int locPlayer = guessLocationOfPlayer();
        printMsg("Противник думает, что Ваше местоположение: " + locPlayer + "\n");
        boolean hittingThePlayer = player.checkLocation(locPlayer);
        if (hittingThePlayer) { //если враг попал по игроку (угадал позицию игрока)
            lastPlayerLocation = locPlayer;
            numOfMisses = 0;

            return InflictDamageOnPlayer(player); //наносим урон по игроку, возвращаем нанесёный урон
        } else { //если враг проманулся
            printMsg("Враг промахнулся!");

            missOnPlayer(locPlayer);

            return 0;
        }
    }

    protected abstract void missOnPlayer(int locPlayer); //промах по игроку

    protected abstract int InflictDamageOnPlayer(ActiveObject player); //попадение, нанесение урона

    abstract int guessLocationOfPlayer(); //угадывание позиции игрока, возвращает предполагаемую позицию

}
