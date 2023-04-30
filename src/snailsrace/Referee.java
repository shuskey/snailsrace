package snailsrace;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Referee Class */
public class Referee extends Button implements ActionListener {
    ColoredDice dice;

    boolean rules;
    /** 0 = Rules off, 1 = Rules on */
    boolean ok2move, ok2roll;

    BufferedImage refereeImage[];
    AudioPlayer audioPlayerRulesOn, audioPlayerRulesOff, audioBeBoop;

    public Referee(int x, int y) {
        audioPlayerRulesOn = new AudioPlayer("Referee, Rules On", "/res/audio/ruleson.wav");
        audioPlayerRulesOff = new AudioPlayer("Referee, Rules Off", "/res/audio/rulesoff.wav");
        audioBeBoop = new AudioPlayer("Referee, You can not do that", "/res/audio/beboop.wav");

        setBounds(x, y, 32, 32);
        setLocation(x, y);
        addActionListener(this);
        ok2move = false;
        ok2roll = true;
        rules = true;
        refereeImage = new BufferedImage[2];
        try {
            refereeImage[0] = ImageIO.read(getClass().getResourceAsStream("/res/rulesoff.gif"));
            refereeImage[1] = ImageIO.read(getClass().getResourceAsStream("/res/ruleson.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDice(ColoredDice cdice) {
        dice = cdice;
    }

    /**
     * Paint it.
     */
    public void paint(Graphics g) {
        g.drawImage(refereeImage[(rules ? 1 : 0)], 0, 0, null);
    }

    /**
     * The user has clicked on the referee's whistle.
     */
    public void toggle(Graphics g) {
        rules = !rules;
        if (rules) {
            audioPlayerRulesOn.play();
        } else {
            audioPlayerRulesOff.play();
        }
        paint(g);
    }

    public void imoved() {
        ok2move = false;
        ok2roll = true;
    }

    public void irolled() {
        ok2roll = false;
        ok2move = true;
    }

    public boolean caniroll() {
        if (!rules || ok2roll) {
            return true;
        } else {
            audioBeBoop.play();
            return false;
        }
    }

    public boolean canimove(int scolor) {
        if (!rules || (ok2move && (scolor == dice.whatColor())))
            return true;
        else {
            audioBeBoop.play();
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        toggle(getGraphics());
    }
}