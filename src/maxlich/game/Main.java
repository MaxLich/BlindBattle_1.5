package maxlich.game;

import maxlich.game.activeobjects.enemy.Enemy;
import maxlich.game.activeobjects.Player;
import maxlich.game.activeobjects.enemy.FactoryEnemy;
import maxlich.game.menu.MainMenu;
import maxlich.game.menu.MenuDifficultyGame;
import maxlich.game.score.Score;
import maxlich.game.score.TopList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static maxlich.game.utils.Helper.printMsg;
import static maxlich.game.utils.Helper.printRules;
import static maxlich.game.activeobjects.ActiveObject.Action;
import static maxlich.game.menu.MainMenu.MainMenuItem;

public class Main {
    public static final int SIZE_ARENA = 6;
    private Arena arena;
    private MainMenu menu;
    private Player player;
    private GameResult gameResult;
    private DifficultyGame difficultyGame;
    private Score score;
    private TopList topList;
    public static Main game;

    public Main(MainMenu menu, Player player, Arena arena, DifficultyGame difficultyGame, Score score, TopList topList) {
        this.menu = menu;
        this.player = player;
        this.arena = arena;
        this.difficultyGame = difficultyGame;
        this.score = score;
        this.topList = topList;
    }

    public DifficultyGame getDifficultyGame() {
        return difficultyGame;
    }

    public void setDifficultyGame(DifficultyGame difficultyGame) {
        this.difficultyGame = difficultyGame;
    }

    public static void main(String[] args) {
        MainMenu newMenu = new MainMenu("Начать игру", "Выбрать уровень сложности игры", "Показать правила игры", "Топ-лист игроков", "Выход");
        Arena arena = new Arena(SIZE_ARENA);
        TopList topList = new TopList();
        game = new Main(newMenu, new Player(arena), arena, DifficultyGame.EASY, new Score(),topList);
        game.run();
    }

    private void run() {
        while (true) { // Основной цикл программы
            printMsg("Игра \"Слепой бой\", v.1.5");
            menu.printMenu();

            MainMenuItem mainMenuItem = menu.getUserInput();
            if (mainMenuItem == MainMenuItem.EXIT)
                System.exit(0);

            if (mainMenuItem == MainMenuItem.CHOOSE_DIFFICULTY_LEVEL_OF_GAME) {
                difficultyGame = new MenuDifficultyGame().getUserInput();
                printMsg("Установлен новый уровень сложности: " + difficultyGame.getName() + "\n");
            }
            else if (mainMenuItem == MainMenuItem.PRINT_RULES) {
                printRules();
            }
            else if (mainMenuItem == MainMenuItem.PRINT_TOPLIST) {
                topList.print();
            }
            else if (mainMenuItem == MainMenuItem.START_GAME) {

                getInputPlayerName(); //запросить у игрока его имя

                boolean goToMainMenu = false;
                //цикл изменения сложности игры (изменяется после прохождения всех уровней игры на данной сложности)
                for (int diffIndex = difficultyGame.ordinal(); diffIndex < DifficultyGame.values().length; diffIndex++) {
                    if (goToMainMenu) break;
                    difficultyGame = DifficultyGame.values()[diffIndex];
                    for (int iLevel = 1; iLevel <= 10; iLevel++) { //цикл уровня игры
                        printMsg("Сложность игры: " + difficultyGame.getName() + ", Уровень: " + iLevel);

                        score.clearAllIndicators();

                        if (iLevel < 6) {
                            player.setCurrentHealth(30);
                        } else if (iLevel == 6) {
                            player.setMaxHealth(40, true);
                            player.setMinDamage(3);
                            player.setMaxDamage(8);
                        } else if (iLevel > 6) {
                            player.setCurrentHealth(40);
                        }

                        Enemy enemy = createEnemy("Враг №" + iLevel, 10 * iLevel, 1 * iLevel, 3 + 2 * (iLevel - 1));

                        player.printCharacteristics();
                        enemy.printCharacteristics();

                        printMsg("\nВсё, в бой!\n");

                        printMsg("Начальное местоположение игрока: " + player.getLocation());

                        while (true) { //цикл боя с противником
                            printMsg("\nВаш ход.");

                            Action actionOfPlayer = player.attackOrMove(); //выбор игрока: атаковать или передвигаться

                            if (actionOfPlayer == Action.ATTACK) { //если игрок выбирает атаковать

                                enemy.setPlayerHasMoved(false); //враг отмечает у себя, что игрок не передвигался
                                score.incrNumOfPlrAttcks(); //счётчик атак игрока для подсчёта очков
                                int curPlrDmg = player.attack(enemy); //атака игрока по врагу
                                if (curPlrDmg > 0) {
                                    score.incrNumOfHittingTheEnemy(); // увеличивается счётчик количеств попаданий по врагу
                                    score.increaseTotalDamagePlrForLvl(curPlrDmg); //увеличивается счётчик общиего урон, нанесённый игроком
                                }
                                if (enemy.getCurrentHeath() <= 0) {
                                    printMsg("Вы убили врага!");
                                    printMsg("Вы победили!");
                                    gameResult = GameResult.PLAYER_WINS;
                                    score.incrNumfTurns(); // увеличивается счётчик сделаных ходов в игре
                                    break;
                                }

                            } else if (actionOfPlayer == Action.MOVE) { //игрок выбрал передвижение
                                score.incrNumOfPlrMovements(); //счётчик передвижений игрока
                                int nextLocPlayer = player.move();
                                enemy.setPlayerHasMoved(true); //враг отмечает у себя, что игрок двигался
                                printMsg("Теперь Ваше текущее местоположение: " + nextLocPlayer);
                            }


                            printMsg("\nХод противника");

                            Action actionOfEnemy = enemy.attackOrMove();

                            if (actionOfEnemy == Action.ATTACK) {
                                printMsg("Противник решил атаковать Вас.");

                                int curEnemyDmg = enemy.attack(player);
                                if (curEnemyDmg > 0) {
                                    score.increaseLostHPPlrForLvl(curEnemyDmg); //счётчик потерянных ХП игрока
                                    score.incrNumOfHittingThePlayer(); //счётчик попаданий по игроку
                                }
                                if (player.getCurrentHeath() <= 0) {
                                    printMsg("Вы убиты своим врагом!");
                                    printMsg("Вы проиграли!");
                                    gameResult = GameResult.PLAYER_LOSES;
                                    score.incrNumfTurns(); //счётчик ходов игры
                                    break;
                                }
                            } else {
                                //printMsg("позиция врага до передвижения:" + enemy.getLocation());
                                printMsg("Противник решил передвинуться...");
                                enemy.move();
                                //printMsg("позиция врага: " + enemy.move());
                                printMsg("Враг передвинулся на энную позицию.");
                            }
                            score.incrNumfTurns(); //счётчик ходов игры
                        } //окончание цикла боя с противником

                        if (gameResult == GameResult.PLAYER_WINS) { //если игрок выиграл уровень
                            printMsg("Уровень " + iLevel + " пройден.");
                            calculateAndPrintScore(iLevel); //подсчитать и вывести счёт игрока за уровень
                            addPlrToTopList(iLevel); //добавление игрока в топ-лист

                            printMsg("\nПерейти на следующий уровень? (д/н): ");
                            try {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                String answerOfUser = "";
                                while (true) { //запрашивать у пользователя ввод, пока он не введёт нужный ответ
                                    answerOfUser = reader.readLine();
                                    if ("д".equalsIgnoreCase(answerOfUser) || "н".equalsIgnoreCase(answerOfUser)) //если корректный ввод, то...
                                        break; //то выходим из цикла
                                }
                                if ("н".equals(answerOfUser)) break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (gameResult == GameResult.PLAYER_LOSES) { //иначе, если игрок проиграл
                            printMsg("Игра окончена.");
                            calculateAndPrintScore(iLevel); //подсчитать и вывести счёт игрока за уровень
                            addPlrToTopList(iLevel); //добавление игрока в топ-лист

                            printMsg("\nХотите попробовать сыграть ещё? (д/н): ");
                            try {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                String answerOfUser = "";
                                while (true) { //цикл ввода пользователем ответа, ожидается нужный ответ (тогда выход из цикла)
                                    answerOfUser = reader.readLine();
                                    if ("д".equalsIgnoreCase(answerOfUser) || "н".equalsIgnoreCase(answerOfUser)) //если пользователь ввёл то, что нужно
                                        break; //выходим из цикла
                                }
                                if ("д".equals(answerOfUser))
                                    break; //выход из цикла боя с противником, начало нового уровня
                                else if ("н".equals(answerOfUser)) System.exit(0); //выход из программы
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } // конец if на проверку, проиграл игрок или победил
                        printMsg("\n");
                    } //конец цикла уровня игры

                    printMsg("\nПоздравляем!!! Вы прошли игру на текущем уровне сложности!");

                    printMsg("Хотите перейти на следующий уровень сложности? (д/н): ");
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        String answerOfUser = "";
                        while (true) { //цикл ввода пользователем ответа, ожидается нужный ответ (тогда выход из цикла)
                            answerOfUser = reader.readLine();
                            if ("д".equalsIgnoreCase(answerOfUser) || "н".equalsIgnoreCase(answerOfUser)) //если пользователь ввёл то, что нужно
                                break; //выходим из цикла
                        }
                        if ("д".equals(answerOfUser))
                            break; //выход из цикла уровня
                        else if ("н".equals(answerOfUser))
                            goToMainMenu = true; //выход цикла перебора сложности игры
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } //конец цикла перебора сложности игры
            } // конец условия, выбран ли пункт меню "начать игру"
        } // конец главного цикла
    } //конец метода


    //вспомогательные методы
    private void getInputPlayerName() {
        printMsg("Введите своё имя (оставьте строку пустой и нажмите Enter, если хотите оставить имя по умолчанию):");
        BufferedReader readerPlayerName = new BufferedReader(new InputStreamReader(System.in));
        try {
            String playerName = readerPlayerName.readLine();
            if (playerName != null && !playerName.isEmpty())
                player.setName(playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Enemy createEnemy(String name, int healthpoints, int minDamage, int maxDamage) {
        int enemyIqLevel = 1;
        switch (difficultyGame) {
            case EASY:
                enemyIqLevel = 1;
                break;
            case NORMAL:
                enemyIqLevel = 2;
                break;
            case HARD:
                enemyIqLevel = 3;
        }
        return FactoryEnemy.getNewEnemy(enemyIqLevel,name,healthpoints,minDamage,maxDamage,arena);
    }

    private void calculateAndPrintScore(int iLevel) {
        score.setResultOfLevel(gameResult);
        score.calculateTotalScore(iLevel); //подсчитать общий счёт
        score.printAllIndicators(); //вывести все показатели очков
        score.printTotalScore(); //вывести общий счёт
    }

    private void addPlrToTopList(int iLevel) {
        int plrPosInTopList = topList.add(player.getName(),score.getTotalScore(),iLevel, difficultyGame, gameResult);
        if (plrPosInTopList > 0) {
            printMsg("Вы вошли в десятку лучших игроков и заняли следующую позицию в этом списке: "
                    + plrPosInTopList);
        }
        topList.saveToFile();
    }


    //вспомогательные внутренние классы
    public enum GameResult {
        PLAYER_WINS("Победа"),
        PLAYER_LOSES("Поражение");

        private String name;

        GameResult(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum DifficultyGame {
        EASY("Легко"),
        NORMAL("Нормально"),
        HARD("Сложно");

        private String name;

        DifficultyGame(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
