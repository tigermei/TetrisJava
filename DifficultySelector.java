import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class DifficultySelector extends JFrame {
    private final StartScreen startScreen;
    
    public DifficultySelector(StartScreen startScreen) {
        this.startScreen = startScreen;
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1));
        setResizable(false);  // 禁止调整窗口大小
        
        add(createButton("difficulty.easy", 1));
        add(createButton("difficulty.medium", 2));
        add(createButton("difficulty.hard", 3));
    }
    
    private JButton createButton(String key, int difficulty) {
        ResourceBundle messages = ResourceBundle.getBundle("resources.messages");
        JButton button = new JButton(messages.getString(key));
        button.addActionListener(e -> {
            Tetris game = new Tetris();
            game.setDifficulty(difficulty);
            game.setVisible(true);
            startScreen.setVisible(false);  // 关闭开始界面
            this.dispose();  // 关闭难度选择界面
        });
        return button;
    }
}