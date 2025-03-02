import java.awt.Color;

public class Shape {
    private int[][] blocks;
    private Color color;
    private int x, y;  // 位置坐标
    
    // 定义所有可能的方块形状
    private static final int[][][] SHAPES = {
        // I
        {{1, 1, 1, 1}},
        // O
        {{1, 1},
         {1, 1}},
        // T
        {{0, 1, 0},
         {1, 1, 1}},
        // L
        {{1, 0},
         {1, 0},
         {1, 1}},
        // J
        {{0, 1},
         {0, 1},
         {1, 1}},
        // S
        {{0, 1, 1},
         {1, 1, 0}},
        // Z
        {{1, 1, 0},
         {0, 1, 1}}
    };
    
    private static final Color[] COLORS = {
        Color.CYAN, Color.YELLOW, Color.MAGENTA, 
        Color.ORANGE, Color.BLUE, Color.GREEN, Color.RED
    };
    
    public Shape(int type) {
        blocks = SHAPES[type];
        color = COLORS[type];
        x = 4;  // 起始位置在板子中间
        y = 0;
    }
    
    public static Shape randomShape() {
        return new Shape((int)(Math.random() * SHAPES.length));
    }
    
    public void rotate() {
        int[][] rotated = new int[blocks[0].length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                rotated[j][blocks.length - 1 - i] = blocks[i][j];
            }
        }
        blocks = rotated;
    }
    
    // Getters and setters
    public int[][] getBlocks() { return blocks; }
    public Color getColor() { return color; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
} 