package maxlich.game.score;

import maxlich.game.Main;
import maxlich.game.Main.DifficultyGame;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

import static maxlich.game.utils.Helper.printLine;
import static maxlich.game.utils.Helper.printMsg;

/**
 * Created by Максим on 18.05.2017.
 */
// класс, работающий с топ-листом игроков
public class TopList {
    //путь к файлу с топ-листом игроков
    private static final String fileTopList = "." + File.separator + "src" + File.separator +
            TopList.class.getPackage().getName().replace(".", File.separator) +
            File.separator + "TopListOfPlayers.dat";

    private static final int SIZE_TOP_LIST = 10; //количество позиций в топ-листе (строк)
    private Set<TopListEntry> topList;
    private final static int LENGTH_PLR_NAME = 10; //максимальный размер имени игрока в топ-листе


    public TopList() {
        topList = new TreeSet<>();
        loadFromFile();
        //init();
    }

    public void init() {
        for (int i = 0; i < SIZE_TOP_LIST; i++) {
            TopListEntry defaultTopListEntry = new TopListEntry("NoName" + i, 100 + 10 * i, 1,
                    DifficultyGame.EASY,Main.GameResult.PLAYER_WINS.getName());
            topList.add(defaultTopListEntry);
        }
    }

    public void saveToFile() {
        try (ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(fileTopList))) {
            cutTopListToMaxSize();
            objectOutput.writeObject(topList);
            objectOutput.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(fileTopList))) {
            topList = (Set<TopListEntry>) objectInput.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //обрезать список игроков до максимального размера SIZE_TOP_LIST
    private void cutTopListToMaxSize() {
        Set<TopListEntry> newSet = new TreeSet<>();
        int count = 1;
        for (TopListEntry entry : topList) {
            newSet.add(entry);
            if (count >= SIZE_TOP_LIST)
                break;
            else
                count++;
        }

        topList = newSet;
    }

    public int add(String nameOfPlayer, int score, int level, DifficultyGame complexity, Main.GameResult levelResult) {
        nameOfPlayer = checkPlayerName(nameOfPlayer);
        TopListEntry newEntry = new TopListEntry(nameOfPlayer, score, level, complexity,levelResult.getName());
        topList.add(newEntry);

        int position = calculatePlayerPositionInTopList(newEntry);

        return (position > SIZE_TOP_LIST) ? -1 : position;
    }

    private String checkPlayerName(String playerName) {
        if (playerName.length() > LENGTH_PLR_NAME)
            return String.format("%." + LENGTH_PLR_NAME +"s",playerName);
        else
            return playerName;
    }

    private int calculatePlayerPositionInTopList(TopListEntry entry) {
        int pos = 1;
        for (TopListEntry e : topList) {
            if (e.equals(entry))
                return pos;
            else
                pos++;
        }

        return SIZE_TOP_LIST * 1000;
    }

    public void print() { //вывести топ-лист на экран

        printMsg("10 лучших игроков:");
        printLine(76);
        printMsg(String.format("%3s %-15s %-18s %-12s %-11s %-10s","","Имя игрока","Набранные очки","Уровень","Сложность","Исход уровня"));
        printLine(76);

        cutTopListToMaxSize();
        int i = 1;
        for (TopListEntry entry: topList) {
            printMsg(String.format("%-3d %-15s %-18d %-12d %-11s %-10s", i, entry.playerName, entry.score,
                    entry.level, entry.difficulty.getName(),entry.levelResult));
            i++;
        }
        printMsg("\n");
    }

/*    public static void main(String[] args) {
        TopList topList = new TopList();
        topList.saveToFile();
        topList.loadFromFile();
        topList.print();
    }*/


    //одна запить (строка) в списке лучших игроков (топ-листе)
    private static class TopListEntry implements Serializable, Comparable<TopListEntry> {
        static int lastId = 0;
        int id = 0;
        String playerName;
        int score;
        int level;
        DifficultyGame difficulty;
        String levelResult;

        public TopListEntry() {
        }

        TopListEntry(String playerName, int score, int level, DifficultyGame difficulty, String levelResult) {
            id = ++lastId % 1000;
            lastId = id;
            this.playerName = playerName;
            this.score = score;
            this.level = level;
            this.difficulty = difficulty;
            this.levelResult = levelResult;
        }

        @Override
        public int compareTo(TopListEntry anotherEntry) {
            int diff = this.score - anotherEntry.score;
            if (diff != 0)
                return -diff;
            diff = this.level - anotherEntry.level;
            if (diff != 0)
                return -diff;
            diff = this.difficulty.ordinal() - anotherEntry.difficulty.ordinal();
            if (diff != 0)
                return -diff;
            diff = this.levelResult.compareTo(anotherEntry.levelResult);
            if (diff != 0)
                return diff;

            return this.playerName.compareTo(anotherEntry.playerName);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TopListEntry entry = (TopListEntry) o;

            if (id != entry.id) return false;
            if (score != entry.score) return false;
            if (level != entry.level) return false;
            if (!levelResult.equals(entry.levelResult)) return false;
            if (playerName != null ? !playerName.equals(entry.playerName) : entry.playerName != null) return false;
            return difficulty == entry.difficulty;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + (playerName != null ? playerName.hashCode() : 0);
            result = 31 * result + score;
            result = 31 * result + level;
            result = 31 * result + difficulty.hashCode();
            result = 31 * result + levelResult.hashCode();
            return result;
        }
    }

}
