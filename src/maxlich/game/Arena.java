package maxlich.game;

import maxlich.game.utils.Helper;

/**
 * Created by Максим on 22.06.2017.
 */
public class Arena {
    private int size;

    public Arena(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRandomLocation() {
        return Helper.generateRandomInt(size) + 1;
    }

}
