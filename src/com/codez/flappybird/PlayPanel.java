package com.codez.flappybird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Created by codez on 2017/9/13.
 */
public class PlayPanel extends JPanel implements MouseListener{

    //枚举三个状态
    private enum GameStatus{
        WAITTING,RUNNING,STOP,OVER
    }
    //记录游戏的状态
    private GameStatus mStatus = GameStatus.WAITTING;
    //绘制刷新时间间隔
    private static final int TIME_INTERVAL = 50;
    //单位内地板及管道移动距离(dp),即速度
    private static final int MOVE_SPEED = 2;
    //触摸上升的距离，因为是上升，所以为负值
    private static final int TOUCH_UP_SIZE = 16;
    //单位内小鸟下落距离(dp)
    private static final int AUTO_DOWN_SIZE = 2;
    //两个管道间的距离
    private static final int PIPE_DIS_BETWEEN_TWO = 150;
    //在不点击的情况下,不断累积下落距离,产生加速度的效果,即下落越来越快
    private int mBirdDisSum;
    //记录移动的距离，达到PIPE_DIS_BETWEEN_TWO则生成一个管道
    private int mPipeMoveDistance;
    //记录分数
    private int grade = 0;
    //记录已通过的管道数
    private int mRemovePipeCount = 0;

    private int mGameWidth;
    private int mGameHeight;

    //实例化各组件的实体
    private Vector<Pipe> mPipes;
    private Vector<Pipe> mNeedRemovePipes;
    private Bird mBird;
    private Floor mFloor;
    private Grade mGrade;

    private JButton btnStart;
    private JButton btnOk;

    private ImageIcon imgIStart;
    private ImageIcon imgIOk;
    private ImageIcon imgITitle;
    private ImageIcon imgIOver;

    private Sound mSound;
    public PlayPanel(JFrame frame) {
        this.mGameWidth = frame.getWidth();
        this.mGameHeight = frame.getHeight();
        this.setLayout(null);
        this.setBounds(0, 0, mGameWidth, mGameHeight);

        init();
        reset();

        mThread.start();
    }

    private void init() {
        mPipes = new Vector<Pipe>();
        mNeedRemovePipes = new Vector<Pipe>();
        mBird = new Bird(this);
        mFloor = new Floor(this);
        mGrade = new Grade(this);
        imgIStart = new ImageIcon("imgs/btn_start.png");
        imgIOk = new ImageIcon("imgs/btn_ok.png");
        imgITitle = new ImageIcon("imgs/title.png");
        imgIOver = new ImageIcon("imgs/gameover.png");

        mSound = new Sound();

        btnStart = new JButton();
        btnOk = new JButton();
        btnStart.setIcon(imgIStart);
        btnOk.setIcon(imgIOk);
        btnStart.setBounds(mGameWidth / 2 - imgIStart.getIconWidth()/2, (int) (mGameHeight * Config.PERCENT_START_Y_POS),
                imgIStart.getIconWidth(), imgIStart.getIconHeight());
        btnOk.setBounds(mGameWidth / 2 - imgIOk.getIconWidth()/2, (int) (mGameHeight * Config.PERCENT_START_Y_POS),
                imgIOk.getIconWidth(), imgIOk.getIconHeight());
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSound.play("sounds/buttonClick.wav");
                mStatus = GameStatus.RUNNING;
            }
        });
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSound.play("sounds/buttonClick.wav");
                mStatus = GameStatus.WAITTING;
                reset();
            }
        });
        add(btnStart);
        add(btnOk);
        addMouseListener(this);
    }
    private void reset() {
        //清空两个pipe-list数据
        mPipes.clear();
        mNeedRemovePipes.clear();
        //设置pipe移动距离为间隔PIPE_DIS_BETWEEN_TWO,为了刚开始游戏就新建pipe
        mPipeMoveDistance = PIPE_DIS_BETWEEN_TWO;
        //重置鸟的位置
        mBird.resetHeight();
        //重置鸟的下落速度
        mBirdDisSum = 0;
        //重置移除管道数
        mRemovePipeCount = 0;
        //重置grade
        grade = 0;

    }
    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();
                logic();
                repaint();
                long end = System.currentTimeMillis();
                if (end - start < TIME_INTERVAL) {
                    try {
                        Thread.sleep(TIME_INTERVAL - (end - start));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    });
    //逻辑运算
    private void logic() {
        switch (mStatus) {
            //正在游戏
            case RUNNING:
                grade = 0;
                mFloor.setX(mFloor.getX()-MOVE_SPEED);
                mBirdDisSum += AUTO_DOWN_SIZE;
                mBird.setY(mBird.getY() + mBirdDisSum);
                for (Pipe pipe : mPipes) {
                    if (pipe.getX() < -52) {
                        mNeedRemovePipes.add(pipe);
                        mRemovePipeCount++;
                        continue;
                    }
                    pipe.setX(pipe.getX() - MOVE_SPEED);
                }
                mPipes.removeAll(mNeedRemovePipes);
                mPipeMoveDistance +=MOVE_SPEED;
                //生成新管道
                if (mPipeMoveDistance >= PIPE_DIS_BETWEEN_TWO) {
                    mPipes.add(new Pipe(this));
                    mPipeMoveDistance = 0;
                }
                //计算分数
                grade+=mRemovePipeCount;
                for (Pipe pipe : mPipes) {
                    if (pipe.getX() + pipe.getPipeWidth() < mBird.getX()) {
                        //获得鸟左边的管道数(即已通过,未移除)
                        grade++;
                    }
                }
                checkGameOver();
                break;
            case STOP:
                //如果鸟还在空中,先掉下来
                if (mBird.getY() + mBird.getBirdHeight() < mFloor.getY()) {
                    mBirdDisSum += AUTO_DOWN_SIZE;
                    mBird.setY(mBird.getY() + mBirdDisSum);
                }else {
                    mStatus = GameStatus.OVER;
                }
                break;
            default:
                break;
        }
    }

    private void checkGameOver() {
        //与地板:如果碰到地板,gg
        if (mBird.getY() > mFloor.getY() - mBird.getBirdHeight()) {
            mSound.play("sounds/die.wav");
            mStatus = GameStatus.STOP;
            mBird.setY(mBird.getY() + TOUCH_UP_SIZE);//结束前bird弹起,更有动感
        }
        //与管道
        for (Pipe pipe : mPipes) {
            //已经穿过
            if (pipe.getX() + pipe.getPipeWidth() < mBird.getX()) {
                continue;
            }
            //碰到管道
            if (pipe.touchBird(mBird)) {
                mSound.play("sounds/hit.wav");
                mStatus = GameStatus.STOP;
                mBird.setY(mBird.getY() + TOUCH_UP_SIZE);//结束前bird弹起,更有动感
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制背景
        g.drawImage(new ImageIcon("imgs/bg.png").getImage(),0,0, 288, 511, this);
        //绘制bird
        mBird.drawBird(g);
        //绘制管道
        for (Pipe pipe : mPipes) {
            pipe.drawPipe(g);
        }
        //绘制地板
        g.drawImage(new ImageIcon("imgs/ground.png").getImage(), 0, (int) (mGameHeight * Config.PERCENT_FLOOR_Y_POS), this);
        mFloor.drawFloor(g);
        //绘制分数
        if (grade == 0 || grade > mGrade.getGrade()) {
            mSound.play("sounds/point.wav");
            mGrade.setGrade(grade);
        }
        mGrade.drawGrade(g);

        if (mStatus == GameStatus.WAITTING) {
            g.drawImage(imgITitle.getImage(),
                    mGameWidth / 2 - imgITitle.getIconWidth()/2, (int) (mGameHeight * Config.PERCENT_TITLE_Y_POS),this);
            btnStart.setVisible(true);
            btnOk.setVisible(false);
        } else if(mStatus == GameStatus.OVER){
            g.drawImage(imgIOver.getImage(),
                    mGameWidth / 2 - imgIOver.getIconWidth()/2, (int) (mGameHeight * Config.PERCENT_TITLE_Y_POS),this);
            btnStart.setVisible(false);
            btnOk.setVisible(true);
        }else {
            btnStart.setVisible(false);
            btnOk.setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (mStatus) {
            case RUNNING:
                mBirdDisSum = -TOUCH_UP_SIZE;
                mSound.play("sounds/wing.wav");
                break;
            default:
                break;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
