package snailsrace;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/** New Game Class */
public class NewGameButton extends Button implements ActionListener {
    BufferedImage newGameImage;
    SnailsRace parentSnailsRace;

    public NewGameButton(SnailsRace parent, int x, int y) {
        parentSnailsRace = parent;
        setBounds(x, y, 64, 32);
        setLocation(x, y);
        addActionListener(this);
        try {
            newGameImage = ImageIO.read(getClass().getResourceAsStream("/res/newgame.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paint it.
     */
    public void paint(Graphics g) {
        g.drawImage(newGameImage, 0, 0, null);
    }

    /**
     * The user has clicked on the dice.
     */
    public void restart(Graphics g) {
        parentSnailsRace.restart(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        restart(getGraphics());
    }

}