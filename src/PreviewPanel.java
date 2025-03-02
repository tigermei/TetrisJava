import javax.swing.*;
import java.awt.*;

public class PreviewPanel extends JPanel {
    private Shape nextShape;
    
    public PreviewPanel() {
        setPreferredSize(new Dimension(150, 150));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder(I18N.getString("next")));
    }
    
    public void setNextShape(Shape shape) {
        this.nextShape = shape;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (nextShape == null) {
            g.setColor(Color.GRAY);
            g.drawString(I18N.getString("next"), 
                getWidth()/2 - 30, getHeight()/2);
            return;
        }
        
        int[][] blocks = nextShape.getBlocks();
        int blockSize = 25;
        
        int startX = (getWidth() - blocks[0].length * blockSize) / 2;
        int startY = (getHeight() - blocks.length * blockSize) / 2;
        
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (blocks[i][j] == 1) {
                    g.setColor(nextShape.getColor());
                    g.fillRect(startX + j * blockSize, startY + i * blockSize, 
                             blockSize - 1, blockSize - 1);
                    g.setColor(Color.BLACK);
                    g.drawRect(startX + j * blockSize, startY + i * blockSize, 
                             blockSize - 1, blockSize - 1);
                }
            }
        }
    }

    public void updatePreview() {
        // 更新预览面板显示
        repaint();  // 触发重绘
    }
} 