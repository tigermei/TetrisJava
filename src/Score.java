import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {
    private String name;
    private int score;
    
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }
    
    @Override
    public int compareTo(Score other) {
        // Sort in descending order (highest score first)
        return Integer.compare(other.getScore(), this.score);
    }
    
    public String getName() {
        return name;
    }
    
    public int getScore() {
        return score;
    }
    // ... existing code ...
} 