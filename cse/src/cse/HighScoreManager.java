package cse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HighScoreManager {
    private static final String FILE_PATH = "src/images/high.txt";
    private static final int MAX_HIGH_SCORES = 10;

    public static ArrayList<String> getHighScores() {
        ArrayList<String> highScores = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                highScores.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return highScores;
    }

    public static void saveHighScore(String playerName, int score) {
        ArrayList<String> highScores = getHighScores();
        highScores.add(0, playerName + ": " + score);
        if (highScores.size() > MAX_HIGH_SCORES) {
            highScores.subList(MAX_HIGH_SCORES, highScores.size()).clear();
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String highScore : highScores) {
                writer.println(highScore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
