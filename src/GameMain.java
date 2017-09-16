import com.codez.flappybird.*;

import javax.swing.*;

public class GameMain {

    public static void main(String[] args) {
        JFrame mFrame = new JFrame();
        mFrame.setResizable(false);
        mFrame.setSize(288, 511);
        mFrame.setLocationRelativeTo(null);
        mFrame.setLayout(null);
        mFrame.setTitle("FlappyBirds");
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PlayPanel playPanel = new PlayPanel(mFrame);

//        JButton btn_start = new JButton();
//        btn_start.setIcon(new ImageIcon("imgs/btn_start.png"));
//        btn_start.setBounds(52, 0, 104, 58);
//
//        mFrame.add(btn_start);

        mFrame.add(playPanel);

        mFrame.setVisible(true);
    }

}
