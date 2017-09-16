package com.codez.flappybird;

import javax.swing.*;
import java.awt.*;

/**
 * Created by codez on 2017/9/14.
 */
public class Floor{
    //底板位置位于游戏面板的4/5到底部
    private static final float FLOOR_Y_POS = 4 / 5f;

    //x坐标
    private int x;
    private int y;

    private int mGameWidth;
    private int mGameHeight;
    private Image imgFloor;
    private PlayPanel mPanel;

    public Floor(PlayPanel panel) {
        this.mPanel = panel;
        this.mGameWidth = mPanel.getWidth();
        this.mGameHeight = mPanel.getHeight();
        x=0;
        y = (int) (mGameHeight * Config.PERCENT_FLOOR_Y_POS);
        imgFloor = new ImageIcon("imgs/floor_bg2.png").getImage();
    }

    //绘制floor
    public void drawFloor(Graphics g) {
        int imgWidth = imgFloor.getWidth(mPanel);
        if (-x >= imgWidth) {
            x %= imgWidth;
        }
        for (int i = 0; i < 13;i++) {//为了不出现空缺,多绘制1个
            g.drawImage(imgFloor, imgWidth * i + x, y,
                    imgWidth, imgFloor.getHeight(mPanel), mPanel);
        }
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
}
