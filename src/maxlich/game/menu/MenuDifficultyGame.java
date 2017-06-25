package maxlich.game.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static maxlich.game.utils.Helper.printMsg;
import static maxlich.game.Main.DifficultyGame;

/**
 * Created by Максим on 25.06.2017.
 */
public class MenuDifficultyGame {
    private DifficultyGame[] diffs = DifficultyGame.values();

    public void printMenu() {
        for (int i = 0; i < diffs.length; i++) {
            printMsg((i + 1) + ") " + diffs[i].getName());
        }
    }

    public DifficultyGame getUserInput() {
        printMsg("\nВыберете сложность игры: ");
        printMenu();
        int menuItemInput;

        BufferedReader reader  = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                menuItemInput = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException | IOException e) {
                continue;
            }
            if (menuItemInput >= 1 && menuItemInput <= diffs.length)
                break;
        }
        return diffs[menuItemInput - 1];
    }
}
