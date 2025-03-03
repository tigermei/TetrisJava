import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class GameBoard extends JPanel {
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private static final int BLOCK_SIZE = 30;
    private static final Dimension BOARD_DIMENSION = new Dimension(
        BOARD_WIDTH * BLOCK_SIZE,
        BOARD_HEIGHT * BLOCK_SIZE
    );
    
    private Timer timer;
    private boolean isPaused = false;
    private boolean isGameOver = false;
    private final int[][] board;
    private final Color[][] colors;
    private Shape currentShape;
    private Shape nextShape;
    private int score = 0;
    private PreviewPanel previewPanel;
    private JLabel scoreLabel;
    private int highScore = 0;  // 添加并初始化 highScore 字段
    
    public GameBoard() {
        Dimension size = new Dimension(BOARD_WIDTH * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE);
        setPreferredSize(size);
        setBackground(Color.BLACK);
        
        board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        colors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        
        // 初始化游戏
        nextShape = Shape.randomShape();
        createNewShape();
        
        timer = new Timer(1000, e -> gameStep());
        timer.start();
        
        setFocusable(true);
        
        // 在 GameBoard$1 (KeyListener) 的 keyPressed 方法中添加对游戏结束状态的处理
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isGameOver) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        restart();
                        return;
                    }
                }
                
                if (!isPaused) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            moveShape(-1);
                            break;
                        case KeyEvent.VK_RIGHT:
                            moveShape(1);
                            break;
                        case KeyEvent.VK_DOWN:
                            dropShape();
                            break;
                        case KeyEvent.VK_UP:
                            rotateShape();
                            break;
                        case KeyEvent.VK_SPACE:
                            dropShapeFully();
                            break;
                    }
                }
                
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    togglePause();
                }
            }
        });
    }
    
    private void initGame() {
        // 清空游戏板
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = 0;
                colors[i][j] = null;
            }
        }
        
        currentShape = Shape.randomShape();
        nextShape = Shape.randomShape();
        score = 0;
        isGameOver = false;
        
        // 设置游戏速度
        timer = new Timer(1000, e -> gameStep());
        timer.start();
        
        // 确保初始化时就有下一个方块
        createNewShape();  // 创建第一个当前方块
    }
    
    private void gameStep() {
        if (!isPaused && !isGameOver) {
            if (!moveShapeDown()) {
                placeShape();
                removeFullLines();
                if (!createNewShape()) {
                    gameOver();
                }
            }
            repaint();
        }
    }
    
    private boolean moveShapeDown() {
        currentShape.setY(currentShape.getY() + 1);
        if (!isValidPosition()) {
            currentShape.setY(currentShape.getY() - 1);
            return false;
        }
        return true;
    }
    
    public void moveShape(int dx) {
        currentShape.setX(currentShape.getX() + dx);
        if (!isValidPosition()) {
            currentShape.setX(currentShape.getX() - dx);
        }
        repaint();
    }
    
    public void rotateShape() {
        currentShape.rotate();
        if (!isValidPosition()) {
            for (int i = 0; i < 3; i++) currentShape.rotate();
        }
        repaint();
    }
    
    private boolean isValidPosition() {
        int[][] shape = currentShape.getBlocks();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 0) continue;
                
                int x = currentShape.getX() + j;
                int y = currentShape.getY() + i;
                
                if (x < 0 || x >= BOARD_WIDTH || 
                    y < 0 || y >= BOARD_HEIGHT || 
                    board[y][x] == 1) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void placeShape() {
        int[][] shape = currentShape.getBlocks();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    int x = currentShape.getX() + j;
                    int y = currentShape.getY() + i;
                    board[y][x] = 1;
                    colors[y][x] = currentShape.getColor();
                }
            }
        }
    }
    
    private boolean createNewShape() {
        currentShape = nextShape;
        nextShape = Shape.randomShape();
        
        // 更新预览面板
        if (previewPanel != null) {
            previewPanel.setNextShape(nextShape);
            previewPanel.repaint();
        }
        
        if (currentShape != null) {
            currentShape.setX(BOARD_WIDTH / 2 - currentShape.getBlocks()[0].length / 2);
            currentShape.setY(0);
        }
        
        return isValidPosition();
    }
    
    private void removeFullLines() {
        int linesRemoved = 0;
        
        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean isLineFull = true;
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == 0) {
                    isLineFull = false;
                    break;
                }
            }
            
            if (isLineFull) {
                linesRemoved++;
                // 移动上面的行下来
                for (int k = i; k > 0; k--) {
                    System.arraycopy(board[k-1], 0, board[k], 0, BOARD_WIDTH);
                    System.arraycopy(colors[k-1], 0, colors[k], 0, BOARD_WIDTH);
                }
                // 清空最上面的行
                Arrays.fill(board[0], 0);
                Arrays.fill(colors[0], null);
                i++; // 重新检查当前行
            }
        }
        
        if (linesRemoved > 0) {
            score += linesRemoved * 100;
            updateScore(linesRemoved * 100);
        }
    }
    
    public void dropShape() {
        moveShapeDown();
        repaint();
    }
    
    public void dropShapeFully() {
        while (moveShapeDown());
    }
    
    private void gameOver() {
        isGameOver = true;
        timer.stop();
        JOptionPane.showMessageDialog(this, 
            "游戏结束！\n得分: " + score, 
            "游戏结束", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 绘制已固定的方块
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == 1) {
                    g.setColor(colors[i][j]);
                    g.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, 
                             BLOCK_SIZE-1, BLOCK_SIZE-1);
                }
            }
        }
        
        // 绘制当前下落的方块
        if (currentShape != null) {
            g.setColor(currentShape.getColor());
            int[][] shape = currentShape.getBlocks();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[0].length; j++) {
                    if (shape[i][j] == 1) {
                        g.fillRect((currentShape.getX() + j) * BLOCK_SIZE,
                                 (currentShape.getY() + i) * BLOCK_SIZE,
                                 BLOCK_SIZE-1, BLOCK_SIZE-1);
                    }
                }
            }
        }
        
        // 绘制分数
        drawScore(g);
        
        // 如果游戏结束，绘制结束画面
        if (isGameOver) {
            drawGameOver(g);
        }
    }
    
    public void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
        } else {
            timer.start();
        }
    }
    
    public void restart() {
        score = 0;
        updateScore(0);
        if (scoreLabel != null) {
            scoreLabel.setText("分数: 0");
        }
        timer.stop();
        initGame();
        timer.start();
    }
    
    public void requestFocus() {
        super.requestFocus();
    }
    
    public void setPreviewPanel(PreviewPanel panel) {
        this.previewPanel = panel;
        if (nextShape != null) {
            panel.setNextShape(nextShape);
            panel.repaint();
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return BOARD_DIMENSION;
    }
    
    @Override
    public Dimension getMinimumSize() {
        return BOARD_DIMENSION;
    }
    
    @Override
    public Dimension getMaximumSize() {
        return BOARD_DIMENSION;
    }
    
    public void setScoreLabel(JLabel label) {
        this.scoreLabel = label;
        updateScore(0);
    }
    
    private void updateScore(int points) {
        score += points;
        if (scoreLabel != null) {
            scoreLabel.setText(I18N.getString("score", score));
        }
    }
    
    public void setDifficulty(int difficulty) {
        int speed = switch (difficulty) {
            case 1 -> 1000;  // Easy: 1 second
            case 2 -> 750;   // Medium: 0.75 seconds
            case 3 -> 500;   // Hard: 0.5 seconds
            default -> 750;
        };
        timer.setDelay(speed);
    }
    
    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 20));
        
        String highScoreText = I18N.getString("score.high") + ": " + highScore;
        String currentScore = I18N.getString("score.current") + ": " + score;
        
        int x = 10;
        int y = 30;
        g.drawString(highScoreText, x, y);
        g.drawString(currentScore, x, y + 30);
    }
    
    private void drawGameOver(Graphics g) {
        g.setColor(new Color(255, 255, 255, 128));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.RED);
        g.setFont(new Font("微软雅黑", Font.BOLD, 30));
        
        String gameOver = I18N.getString("game.over");
        String pressSpace = I18N.getString("press.space");
        
        int x = (getWidth() - g.getFontMetrics().stringWidth(gameOver)) / 2;
        int y = getHeight() / 2 - 20;
        g.drawString(gameOver, x, y);
        
        x = (getWidth() - g.getFontMetrics().stringWidth(pressSpace)) / 2;
        y = getHeight() / 2 + 20;
        g.drawString(pressSpace, x, y);
    }
    
    public boolean isPaused() {
        return isPaused;
    }
    
    public void startGame() {
        // 初始化游戏状态
        currentShape = Shape.randomShape();
        nextShape = Shape.randomShape();
        score = 0;
        isGameOver = false;
        timer.start();  // 启动游戏计时器
    }
}