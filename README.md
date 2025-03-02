# 俄罗斯方块

## 项目结构

- `src/`：源代码目录
- `resources/`：资源文件目录
- `README.md`：项目说明文件

## 项目说明

- `src/`：源代码目录
- `resources/`：资源文件目录
- `README.md`：项目说明文件

## 运行

```bash
./run.sh
```


## 说明游戏玩法

- 游戏开始后，方块会从顶部缓慢下落
- 玩家需要通过键盘控制方块的移动，使得方块落地时能够填满一行或多行
- 每填满一行或多行，玩家会获得相应的分数
- 游戏结束时，根据得分进行排名，得分最高的玩家获胜
- 玩家可以随时暂停游戏，并在暂停状态下查看当前得分和排名

## 介绍游戏核心代码

- `TetrisMain.java`：游戏主类，负责初始化游戏界面和逻辑
- `TetrisPanel.java`：游戏面板类，负责绘制游戏界面和处理用户输入
- `TetrisBlock.java`：方块类，负责定义方块的形状和位置
- `TetrisGame.java`：游戏逻辑类，负责处理游戏逻辑和得分计算
- `TetrisScore.java`：得分类，负责处理得分计算和排名

## 介绍游戏界面

- `StartScreen.java`：开始界面类，负责显示开始界面和处理用户输入
- `DifficultySelector.java`：难度选择界面类，负责显示难度选择界面和处理用户输入
- `GameOverScreen.java`：游戏结束界面类，负责显示游戏结束界面和处理用户输入
- `TetrisScore.java`：得分类，负责处理得分计算和排名

## 介绍游戏主界面
- `TetrisMain.java`：游戏主类，负责初始化游戏界面和逻辑
- `TetrisPanel.java`：游戏面板类，负责绘制游戏界面和处理用户输入
- `TetrisBlock.java`：方块类，负责定义方块的形状和位置
- `TetrisGame.java`：游戏逻辑类，负责处理游戏逻辑和得分计算
- `TetrisScore.java`：得分类，负责处理得分计算和排名
