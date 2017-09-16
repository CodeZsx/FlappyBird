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
        mFrame.add(playPanel);

        mFrame.setVisible(true);
    }

}
