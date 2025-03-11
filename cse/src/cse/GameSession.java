package cse;


public class GameSession {
    private static GameSession instance;
    private int totalScore;

    private GameSession() {
        totalScore = 0;
    }

    public static GameSession getInstance() {
        if (instance == null) {
            instance = new GameSession();
        }
        return instance;
    }

    public void addScore(int score) {
        totalScore += score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void reset() {
        totalScore = 0;
    }
}
