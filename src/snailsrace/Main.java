package snailsrace;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(11 * 64, 7 * 64 + 32));
        window.setResizable(false);
        window.setTitle("Snail's Race");

        SnailsRace snailsRacePanel = new SnailsRace();
        window.add(snailsRacePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

}
