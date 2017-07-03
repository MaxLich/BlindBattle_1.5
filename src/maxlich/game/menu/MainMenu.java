package maxlich.game.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static maxlich.game.utils.Helper.printMsg;

/**
 * Created by Максим on 23.03.2017.
 */
public class MainMenu { //меню игры
    private List<MainMenuItem> menuList;

    public MainMenu() {
        this.menuList = new ArrayList<>();
    }

    public MainMenu(MainMenuItem... menuItem) {
        this.menuList = new ArrayList<>(Arrays.asList(menuItem));


    }

    public void addMenuItems(MainMenuItem... menuItem) {
        menuList.addAll(Arrays.asList(menuItem));
    }

    public void printMenu() {
        for (int i = 0, menuSize = menuList.size(); i < menuSize; i++) {
            printMsg((i + 1) + ") " + this.menuList.get(i).getName());
        }

    }

    public MainMenuItem getUserInput() {
        printMsg("\nВыберете пункт меню: ");
        int menuItemInput;
        int menuSize = menuList.size();

        BufferedReader reader  = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                menuItemInput = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException | IOException e) {
                continue;
            }
            if (menuItemInput >= 1 && menuItemInput <= menuSize)
                break;
        }

        return menuList.get(menuItemInput - 1);
    }


    public enum MainMenuItem {
        START_GAME("Начать игру"),
        CHOOSE_DIFFICULTY_OF_GAME("Выбрать уровень сложности игры"),
        PRINT_RULES("Показать правила игры"),
        PRINT_TOPLIST("Топ-лист игроков"),
        EXIT("Выход");

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        MainMenuItem(String name) {
            this.name = name;
        }
    }
}