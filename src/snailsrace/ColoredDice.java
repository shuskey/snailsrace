package snailsrace;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Colored Dice Class */
public class ColoredDice extends Button implements ActionListener {
    Referee referee;
    int diceColor;

    BufferedImage diceImage[];

    private String[] imageFileNames = {
            "/res/bluedice.gif",
            "/res/pinkdice.gif",
            "/res/yellowdice.gif",
            "/res/orangedice.gif",
            "/res/greendice.gif",
            "/res/reddice.gif"
    };

    private String[] soundFileNames = {
            "/res/audio/blueo.wav",
            "/res/audio/pinko.wav",
            "/res/audio/yellowo.wav",
            "/res/audio/orangeo.wav",
            "/res/audio/greeno.wav",
            "/res/audio/redo.wav"
    };
    AudioPlayer[] audioPlayerDiceColor;

    public String[] colorName = {
            "blue", "pink", "yellow", "orange", "green", "red"
    };

    public ColoredDice(Referee ref, int x, int y) {
        setBounds(x, y, 32, 32);
        setLocation(x, y);
        addActionListener(this);
        int i;
        referee = ref;
        this.diceColor = (int) (Math.random() * 6);
        diceImage = new BufferedImage[6];
        try {
            for (i = 0; i < 6; i++)
                diceImage[i] = ImageIO.read(getClass().getResourceAsStream(imageFileNames[i]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        audioPlayerDiceColor = new AudioPlayer[6];
        for (i = 0; i < 6; i++) {
            audioPlayerDiceColor[i] = new AudioPlayer(
                    soundFileNames[i] + "Dice " + colorName[i] + ", " + colorName[i],
                    soundFileNames[i]);
        }
    }

    public void restart(Graphics g) {
        this.diceColor = (int) (Math.random() * 6);
        paint(g);
        referee.imoved(); // Ready to roll next
    }

    /**
     * Paint it.
     */
    public void paint(Graphics g) {
        g.drawImage(diceImage[diceColor], 0, 0, null);
    }

    /**
     * The user has clicked on the dice.
     */
    public boolean roll(Graphics g) {
        if (referee.caniroll()) {
            diceColor = (int) (Math.random() * 6);
            audioPlayerDiceColor[diceColor].play();
            paint(g);
            referee.irolled();
            return true;
        }
        return false;
    }

    /**
     * Tell me what color you are.
     */
    public int whatColor() {
        return diceColor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        roll(getGraphics());
    }
}
