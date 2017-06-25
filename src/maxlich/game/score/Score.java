package maxlich.game.score;

import maxlich.game.Main;

import static maxlich.game.utils.Helper.printMsg;

/**
 * Created by Максим on 23.03.2017.
 */
public class Score { //очки, счёт
    private int numOfTurns = 0; //количество ходов (циклов) игры, за которое игрок прошёл уровень (или проиграл)
    private int numOfPlrAttcks = 0; //количество совершённых атак игроком
    private int totalDamagePlrForLvl = 0; //общий урон, который нанёс игрок врагу за уровень (иди до своего проигрыша)
    private int numOfHittingTheEnemy = 0; //количество попаданий по врагу за уровень (или до проигрыша)
    private int numOfPlrMovements = 0; //количество передвижений игрока за левел (или до проигрыша)
    private int numOfHittingThePlayer = 0; //количество попаданий по игроку
    private int lostHPPlrForLvl = 0; //число очков здоровья игрока, потерянных за уровень
    private int difficultyGame; //сложность игры
    private Main.GameResult resultOfLevel; //исход уровня: победа игрока или его поражение
    private int totalScore = 0; //общее количество очков за игру

    public Score() {
        this.difficultyGame = 1;
    }

    public Score(Main.DifficultyGame difficulty) {
        this.difficultyGame = difficulty.ordinal() + 1;
    }

    public int getNumOfTurns() {
        return numOfTurns;
    }

    public void setNumOfTurns(int numOfTurns) {
        this.numOfTurns = numOfTurns;
    }

    public int incrNumfTurns() {
        return ++this.numOfTurns;
    }

    public int getNumOfPlrAttcks() {
        return numOfPlrAttcks;
    }

    public void setNumOfPlrAttcks(int numOfSuccPlrAttcks) {
        this.numOfPlrAttcks = numOfSuccPlrAttcks;
    }

    public int incrNumOfPlrAttcks() {
        return ++this.numOfPlrAttcks;
    }

    public int getTotalDamagePlrForLvl() {
        return totalDamagePlrForLvl;
    }

    public void setTotalDamagePlrForLvl(int totalDamagePlrForLvl) {
        this.totalDamagePlrForLvl = totalDamagePlrForLvl;
    }

    public int increaseTotalDamagePlrForLvl(int damage) {
        this.totalDamagePlrForLvl += damage;
        return this.totalDamagePlrForLvl;
    }

    public int getNumOfHittingTheEnemy() {
        return numOfHittingTheEnemy;
    }

    public void setNumOfHittingTheEnemy(int numOfHittingTheEnemy) {
        this.numOfHittingTheEnemy = numOfHittingTheEnemy;
    }

    public int incrNumOfHittingTheEnemy() {
        return  ++this.numOfHittingTheEnemy;
    }

    public int getNumOfPlrMovements() {
        return numOfPlrMovements;
    }

    public void setNumOfPlrMovements(int numOfPlrMovements) {
        this.numOfPlrMovements = numOfPlrMovements;
    }

    public int incrNumOfPlrMovements() {
        return ++this.numOfPlrMovements;
    }

    public int getNumOfHittingThePlayer() {
        return numOfHittingThePlayer;
    }

    public void setNumOfHittingThePlayer(int numOfHittingThePlayer) {
        this.numOfHittingThePlayer = numOfHittingThePlayer;
    }

    public int incrNumOfHittingThePlayer() {
        return ++this.numOfHittingThePlayer;
    }

    public int getLostHPPlrForLvl() {
        return lostHPPlrForLvl;
    }

    public void setLostHPPlrForLvl(int lostHPPlrForLvl) {
        this.lostHPPlrForLvl = lostHPPlrForLvl;
    }

    public int increaseLostHPPlrForLvl(int lostHP) {
        this.lostHPPlrForLvl += lostHP;
        return this.lostHPPlrForLvl;
    }

    public void clearAllIndicators() {
        this.numOfTurns = 0;
        this.numOfPlrAttcks = 0;
        this.totalDamagePlrForLvl = 0;
        this.numOfHittingTheEnemy = 0;
        this.numOfPlrMovements = 0;
        this.numOfHittingThePlayer = 0;
        this.lostHPPlrForLvl = 0;
    }

    public int getDifficultyGame() {
        return difficultyGame;
    }

    public void setDifficultyGame(Main.DifficultyGame complexity) {
        this.difficultyGame = complexity.ordinal() + 1;
    }

    public Main.GameResult getResultOfLevel() {
        return resultOfLevel;
    }

    public void setResultOfLevel(Main.GameResult resultOfLevel) {
        this.resultOfLevel = resultOfLevel;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int calculateTotalScore(int lvl) {

        int T = (numOfTurns == 0)? 0 : 600/numOfTurns; //1-ая составляющая итогового счёта: количество ходов за уровень
        int LHP = 60/(lostHPPlrForLvl+1); //2-ая составляющая итогового счёта: хп игрока потерянные за уровень
        int HIT = 30/(numOfHittingThePlayer+1); //3-ья составляющая итогового счёта: кол-во попаданий по игроку за уровень
        int A = 2*numOfPlrAttcks; //4-ая составляющая итогового счёта: кол-во атак игрока
        int M = 20/(numOfPlrMovements+1); //5-ая составляющая итогового счёта: кол-передвижений игрока
        int D = totalDamagePlrForLvl; //6-ая составляющая итогового счёта: общий урон игрока за уровень
        int L = lvl*20; //7-ая составляющая итогового счёта: номер уровня, который прошёл игрок
        int C = (difficultyGame - 1) * 200; //8-ая составляющая итогового счёта: сложность игры
        int R = resultOfLevel.ordinal() * -1 * 2 * (10 - lvl); //9-ая составляющая итогового счёта:
                                                                //исход уровня: победа или поражение

        this.totalScore += (T + LHP + HIT + A + M + D + L + C + R);
        return this.totalScore;
    }

    public void printTotalScore() {
        printMsg("Общее количество очков за игру: " + this.totalScore);
    }

    public void printAllIndicators() {
        printMsg("Количество ходов игры, за которое игрок прошёл уровень: " + this.numOfTurns);
        printMsg("Количество совершённых игроком атак за уровень: " + this.numOfPlrAttcks);
        printMsg("Общий урон, который нанёс игрок врагу за уровень: " + this.totalDamagePlrForLvl);
        printMsg("Количество попаданий игрока по врагу за уровень: " + this.numOfHittingTheEnemy);
        printMsg("Количество передвижений игрока за уровень: " + this.numOfPlrMovements);
        printMsg("Число очков здоровья игрока, которые он потерял за уровень: " + this.lostHPPlrForLvl);
    }

}


