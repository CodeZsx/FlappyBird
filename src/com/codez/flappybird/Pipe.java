package com.codez.flappybird;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by codez on 2017/9/15.
 */
public class Pipe {
    //上下管道间的距离
    private static final float PIPE_LENGHT_BETWEEN_UP_DOWN = 1/5f;
    //上管道的最大高度
    private static final float UP_PIPE_MAX_HEIGHT = 3 / 5f;
    //上管道的最小高度
    private static final float UP_PIPE_MIN_HEIGHT = 1 / 10f;
    //管道的横坐标
    private int x;
    private int pipeWidth;
    //上管道的高度
    private int topPipeHeight;

    private static Random random = new Random();


    private int mGameWidth;
    private int mGameheight;
    private Image imgPipeTop;
    private Image imgPipeBottom;
    private PlayPanel mPanel;
    public Pipe(PlayPanel panel) {
        this.mPanel = panel;
        imgPipeTop = new ImageIcon("imgs/g2.png").getImage();
        imgPipeBottom = new ImageIcon("imgs/g1.png").getImage();
        mGameheight = mPanel.getHeight();
        mGameWidth = mPanel.getWidth();
        pipeWidth = imgPipeTop.getWidth(mPanel);
        //默认从最右边出现
        x = mGameWidth;
        //生成上管道高度
        randomHeight(mGameWidth);
    }

    //随机生成一个高度:Height*(min->max) 即1/5--->2/5
    private void randomHeight(int gameHeight) {
        topPipeHeight = (int) (random.nextInt((int) (gameHeight *
                (UP_PIPE_MAX_HEIGHT - UP_PIPE_MIN_HEIGHT)))
                + gameHeight * UP_PIPE_MIN_HEIGHT);
    }

    //绘制管道
    public void drawPipe(Graphics g) {
        g.drawImage(imgPipeTop, x, 0, pipeWidth, topPipeHeight, mPanel);
        int bottomPipeTopY = (int) (topPipeHeight + mGameheight*PIPE_LENGHT_BETWEEN_UP_DOWN);
        g.drawImage(imgPipeBottom, x, bottomPipeTopY,
                pipeWidth, (int)(mGameheight * Config.PERCENT_FLOOR_Y_POS) - bottomPipeTopY,
                mPanel);
    }
    public boolean touchBird(Bird mBird) {
        //如果bird已经触碰到管道
        if ((mBird.getX() + mBird.getBirdWidth()) > x && (mBird.getY() < topPipeHeight
                || (mBird.getY() + mBird.getBirdHeight()) >
                (topPipeHeight + mGameheight*PIPE_LENGHT_BETWEEN_UP_DOWN))) {

            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getPipeWidth() {
        return pipeWidth;
    }

    public void setPipeWidth(int pipeWidth) {
        this.pipeWidth = pipeWidth;
    }
}
