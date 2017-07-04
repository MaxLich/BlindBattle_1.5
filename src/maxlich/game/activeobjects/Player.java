package maxlich.game.activeobjects;

import maxlich.game.Arena;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static maxlich.game.utils.Helper.printMsg;

/**
 * Created by Максим on 22.06.2017.
 */
public class Player extends ActiveObject {
    private final static int beginHP = 30;
    private final static int beginMinDamage = 2;
    private final static int beginMaxDamage = 5;

    public Player(Arena arena) {
        super("Игрок", beginHP, beginMinDamage, beginMaxDamage, arena);
    }

    public Player(String name, Arena arena) {
        super(name, beginHP, beginMinDamage, beginMaxDamage, arena);
    }

    public Player(String name, int healthpoints, int minDamage, int maxDamage, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, arena);
    }

    public Player(String name, int healthpoints, int minDamage, int maxDamage, int location, Arena arena) {
        super(name, healthpoints, minDamage, maxDamage, location, arena);
    }

    @Override
    public Action attackOrMove() {
        printMsg("Что будете делать: атаковать (а) или передвигаться (п)? ");
        String answerOfUser = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) { //цикл нужен для фильтра ввода пользователя
                answerOfUser = reader.readLine();
                if ("а".equalsIgnoreCase(answerOfUser)) return Action.ATTACK;
                else if ("п".equalsIgnoreCase(answerOfUser)) return Action.MOVE;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int move() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            printMsg("Введите направление, куда будете двигаться (- или +): ");
            boolean isCorrectInput = false;
            String userInput = "";
            while (!isCorrectInput) {
                userInput = reader.readLine();
                if (userInput == null) continue;
                if (userInput.equals("+") || userInput.equals("-"))
                    isCorrectInput = true;
            }
            int delta = ("+".equals(userInput)) ? 1 : -1;
            return makeAMove(delta);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int attack(ActiveObject enemy) {
        printMsg("Введите предположительное местоположение противника (от 1 до " + arena.getSize() + "): ");
        boolean hittingTheEnemy = enemy.checkLocation(this.getInputLocationOfEnemy());
        printMsg("");

        if (hittingTheEnemy) {
            int curDamage = this.damage(enemy);
            printMsg("Вы попали по врагу и нанесли ему урон " + curDamage + " ед.");
            printMsg("Очки здоровья противника после Вашей атаки: ");
            enemy.printHealth();
            return curDamage;
        } else {
            printMsg("Промах!");
            return 0;
        }
    }

    public int getInputLocationOfEnemy() {
        int locInput;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                locInput = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                continue;
            } catch (Exception e) {
                return -1;
            }
            if (locInput >= 1 && locInput <= arena.getSize())
                break;
        }
        return locInput;
    }


}
