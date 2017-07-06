package maxlich.game.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by Максим on 22.06.2017.
 */
public class Helper {
    private static Random random = new Random();

    //путь до файла с правилами игры
    private static final String pathToRulesFile = "." + File.separator  + "src" +
            File.separator + "maxlich" + File.separator + "game" + File.separator + "Rules";

    public static void printRules() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(pathToRulesFile))) {
            String str;
            while ((str = reader.readLine()) != null) {
                printMsg(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        printMsg("\n");

    }

    public static void printMsg(String message) {
        System.out.println(message);
    }

    public static void printLine(int length) {
        if (length < 0)
            return;
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static int generateRandomInt(int n) {
        return random.nextInt(n);
    }

    public static int generatetRandomIntOf2() {
        return random.nextInt(2);
    }
}
