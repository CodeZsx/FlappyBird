package com.codez.flappybird;

import javax.swing.*;
import java.awt.*;

/**
 * Created by codez on 2017/9/15.
 */
public class Bird {
    //鸟的横坐标、纵坐标
    private int x;
    private int y;
    private int birdWidth;
    private int birdHeight;

    private int mGameheight;
    private Image imgBird;
    private int imgIndex;
    private PlayPanel mPanel;

    public Bird(PlayPanel panel) {
        this.mPanel = panel;
        mGameheight = panel.getHeight();
        resetHeight();
        birdWidth = imgBird.getWidth(mPanel)*2/3;
        birdHeight = imgBird.getHeight(mPanel)*2/3;

        x = mPanel.getWidth() / 2 - birdWidth / 2;
        y = (int) (mGameheight * Config.PERCENT_BIRD_Y_POS);
    }

    public void resetHeight() {
        y = (int) (mGameheight * Config.PERCENT_BIRD_Y_POS);
        imgIndex = (int) (Math.random()*7+1);
        imgBird = new ImageIcon("imgs/b" + imgIndex + ".png").getImage();
    }

    //绘制bird
    public void drawBird(Graphics g) {
        g.drawImage(imgBird , x, y,birdWidth, birdHeight, mPanel);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getBirdWidth() {
        return birdWidth;
    }

    public int getBirdHeight() {
        return birdHeight;
    }
}
