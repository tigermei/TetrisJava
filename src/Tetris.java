import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.HierarchyEvent;

public class Tetris extends JFrame {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 700;
    private GameBoard board;
    private PreviewPanel previewPanel;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private int difficulty = 2;

    public Tetris() {
        setTitle(I18N.getString("title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);  // 移除窗口装饰
        setResizable(false);   // 禁止调整大小
        
        // 设置固定大小
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        // 使用绝对布局
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        
        // 添加标题栏
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(40, 44, 52));
        titleBar.setBounds(0, 0, WINDOW_WIDTH, 30);
        titleBar.setLayout(null);
        
        // 添加标题文字
        JLabel titleLabel = new JLabel("俄罗斯方块");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(10, 0, 100, 30);
        titleBar.add(titleLabel);
        
        // 添加关闭按钮
        JButton closeButton = new JButton("×");
        closeButton.setBounds(WINDOW_WIDTH - 50, 0, 50, 30);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setBackground(new Color(40, 44, 52));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> System.exit(0));
        titleBar.add(closeButton);
        
        contentPane.add(titleBar);
        
        // 游戏主面板
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 30, WINDOW_WIDTH, WINDOW_HEIGHT - 30);
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // 游戏板
        board = new GameBoard();
        board.setBounds(10, 10, 300, 600);
        mainPanel.add(board);
        
        // 右侧面板
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBounds(320, 10, 170, 600);
        rightPanel.setBackground(new Color(245, 245, 245));
        
        // 预览面板
        previewPanel = new PreviewPanel();
        previewPanel.setBounds(10, 10, 150, 150);
        rightPanel.add(previewPanel);
        
        // 分数和等级标签
        scoreLabel = new JLabel("分数: 0");
        levelLabel = new JLabel("难度: " + getDifficultyText());
        scoreLabel.setBounds(10, 170, 150, 30);
        levelLabel.setBounds(10, 200, 150, 30);
        rightPanel.add(scoreLabel);
        rightPanel.add(levelLabel);
        
        // 控制按钮面板
        JPanel controlPanel = createControlPanel();
        controlPanel.setBounds(10, 240, 150, 200);
        rightPanel.add(controlPanel);
        
        // 游戏按钮面板
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(10, 450, 150, 120);
        rightPanel.add(buttonPanel);
        
        mainPanel.add(rightPanel);
        contentPane.add(mainPanel);
        
        // 设置GameBoard的引用
        board.setPreviewPanel(previewPanel);
        board.setScoreLabel(scoreLabel);
        
        // 居中显示
        setLocationRelativeTo(null);
        
        // 使窗口可拖动
        enableWindowDrag(titleBar);
        
        // 在创建游戏主面板后添加焦点监听
        mainPanel.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (mainPanel.isShowing()) {
                    board.requestFocusInWindow();
                }
            }
        });
    }
    
    // 添加窗口拖动功能
    private void enableWindowDrag(JPanel titleBar) {
        Point[] dragStart = {null};
        
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragStart[0] = e.getPoint();
            }
            
            public void mouseReleased(MouseEvent e) {
                dragStart[0] = null;
            }
        });
        
        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dragStart[0] != null) {
                    Point current = e.getLocationOnScreen();
                    setLocation(current.x - dragStart[0].x, 
                              current.y - dragStart[0].y);
                }
            }
        });
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(I18N.getString("controls")));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // 创建按钮
        JButton upButton = new JButton(I18N.getString("up"));
        JButton leftButton = new JButton(I18N.getString("left"));
        JButton rightButton = new JButton(I18N.getString("right"));
        JButton downButton = new JButton(I18N.getString("down"));
        JButton dropButton = new JButton(I18N.getString("drop"));
        
        // 设置按钮大小
        Dimension arrowSize = new Dimension(50, 40);
        upButton.setPreferredSize(arrowSize);
        leftButton.setPreferredSize(arrowSize);
        rightButton.setPreferredSize(arrowSize);
        downButton.setPreferredSize(arrowSize);
        dropButton.setPreferredSize(new Dimension(150, 40));
        
        // 上按钮
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(upButton, gbc);
        
        // 左按钮
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(leftButton, gbc);
        
        // 下按钮
        gbc.gridx = 1;
        panel.add(downButton, gbc);
        
        // 右按钮
        gbc.gridx = 2;
        panel.add(rightButton, gbc);
        
        // 直接落下按钮
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;  // 跨越3列
        gbc.insets = new Insets(10, 5, 5, 5);  // 增加上边距
        panel.add(dropButton, gbc);
        
        // 添加事件监听
        upButton.addActionListener(e -> {
            board.rotateShape();
            board.requestFocus();
        });
        leftButton.addActionListener(e -> {
            board.moveShape(-1);
            board.requestFocus();
        });
        rightButton.addActionListener(e -> {
            board.moveShape(1);
            board.requestFocus();
        });
        downButton.addActionListener(e -> {
            board.dropShape();
            board.requestFocus();
        });
        dropButton.addActionListener(e -> {
            board.dropShapeFully();
            board.requestFocus();
        });
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        
        JButton pauseButton = new JButton(I18N.getString("pause"));
        JButton restartButton = new JButton(I18N.getString("restart"));
        JButton exitButton = new JButton(I18N.getString("exit"));
        
        pauseButton.addActionListener(e -> {
            board.togglePause();
            String message = board.isPaused() ? 
                I18N.getString("pause.message") : 
                I18N.getString("resume.message");
            JOptionPane.showMessageDialog(this, message);
        });
        
        restartButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                this,
                I18N.getString("restart.confirm"),
                I18N.getString("title"),
                JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                board.restart();
            }
        });
        
        exitButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(
                this,
                I18N.getString("exit.confirm"),
                I18N.getString("title"),
                JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        
        panel.add(pauseButton);
        panel.add(restartButton);
        panel.add(exitButton);
        
        return panel;
    }
    
    private String getDifficultyText() {
        return switch (difficulty) {
            case 1 -> I18N.getString("difficulty.easy");
            case 2 -> I18N.getString("difficulty.medium");
            case 3 -> I18N.getString("difficulty.hard");
            default -> I18N.getString("difficulty.unknown");
        };
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        if (board != null) {
            board.setDifficulty(difficulty);
        }
        if (levelLabel != null) {
            levelLabel.setText("难度: " + getDifficultyText());
        }
    }

    public void setVisible(boolean visible) {
        if (visible) {
            initGame();
        }
        super.setVisible(visible);
    }

    private void initGame() {
        board.startGame();
        previewPanel.updatePreview();
        scoreLabel.setText("分数: 0");
        levelLabel.setText("难度: " + getDifficultyText());
    }
} 