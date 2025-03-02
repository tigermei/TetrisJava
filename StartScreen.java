import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

public class StartScreen extends JFrame {
    private ResourceBundle messages;
    
    public StartScreen() {
        messages = ResourceBundle.getBundle("resources.messages");
        setTitle(messages.getString("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(40, 44, 52));
        
        // 添加标题
        JLabel titleLabel = new JLabel(messages.getString("title"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // 添加按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setOpaque(false);
        
        JButton startButton = createStyledButton(messages.getString("start_game"));
        JButton helpButton = createStyledButton(messages.getString("game_help"));
        JButton highScoreButton = createStyledButton(messages.getString("high_score"));
        JButton exitButton = createStyledButton(messages.getString("exit"));
        
        startButton.addActionListener(e -> {
            DifficultySelector selector = new DifficultySelector(this);
            selector.setVisible(true);
            this.setVisible(false);
        });
        helpButton.addActionListener(e -> showHelp());
        highScoreButton.addActionListener(e -> showHighScores());
        exitButton.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(startButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(highScoreButton);
        buttonPanel.add(exitButton);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // 设置窗口大小和位置
        setContentPane(mainPanel);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(61, 90, 254));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void showHelp() {
        JOptionPane.showMessageDialog(this, messages.getString("help_text"), 
            messages.getString("game_help"), JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showHighScores() {
        HighScoreManager manager = new HighScoreManager();
        List<Score> scores = manager.getHighScores();
        
        StringBuilder message = new StringBuilder(messages.getString("high_score_title") + ":\n\n");
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            message.append(String.format("%d. %s: %d\n", i + 1, score.getName(), score.getScore()));
        }
        
        JOptionPane.showMessageDialog(this, message.toString(), 
            messages.getString("high_score"), JOptionPane.INFORMATION_MESSAGE);
    }
} 