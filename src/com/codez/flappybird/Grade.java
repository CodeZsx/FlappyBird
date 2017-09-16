package com.codez.flappybird;

import javax.swing.*;
import java.awt.*;

/**
 * Created by codez on 2017/9/15.
 */
public class Grade {
    //分数
    private String mGrade;

    private int mGameWidth;
    private int mGameHeight;
    private Image[] imgNums;
    private PlayPanel mPanel;

    public Grade(PlayPanel panel) {
        this.mPanel = panel;
        this.mGameWidth = mPanel.getWidth();
        this.mGameHeight = mPanel.getHeight();
        imgNums = new Image[10];
        for (int i = 0; i < imgNums.length; i++) {
            imgNums[i] = new ImageIcon("imgs/n" + i + ".png").getImage();
        }
    }

    // 绘制过程中，每完成一个数字则偏移一个数字的宽度
    public void drawGrade(Graphics g,int grade) {
        //初始化分数，int--->string
        mGrade = grade + "";
        int offsetCenterToLeft = -imgNums[0].getWidth(mPanel) * mGrade.length() / 2;
        for (int i = 0; i < mGrade.length(); i++) {
            int num = Integer.valueOf(mGrade.substring(i, i + 1));
            g.drawImage(imgNums[num], mGameWidth / 2 + offsetCenterToLeft, (int) (mGameHeight * Config.PERCENT_GRADE_Y_POS),
                    mPanel);
            offsetCenterToLeft += imgNums[0].getWidth(mPanel);
        }
    }
}
