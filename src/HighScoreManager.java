import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HighScoreManager {
    private static final String SCORE_FILE = "highscores.dat";
    private List<Score> highScores;
    
    public HighScoreManager() {
        highScores = loadHighScores();
    }
    
    public void addScore(String name, int score) {
        highScores.add(new Score(name, score));
        Collections.sort(highScores);
        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);
        }
        saveHighScores();
    }
    
    public List<Score> getHighScores() {
        return new ArrayList<>(highScores);
    }
    
    @SuppressWarnings("unchecked")
    private List<Score> loadHighScores() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(SCORE_FILE))) {
            return (List<Score>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    private void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SCORE_FILE))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 