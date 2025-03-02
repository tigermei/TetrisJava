import javax.swing.*;

public class TetrisMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // 设置界面风格为系统默认外观
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // 显示开始界面
            new StartScreen();
        });
    }
} 