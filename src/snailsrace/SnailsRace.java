package snailsrace;
/*
 * 
 * Copyright (c) 1996-2023 Huskey-Doodles, Inc. All Rights Reserved.
 *
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * SnailsRace
 *
 * By Scott Huskey
 */
public class SnailsRace extends JPanel implements MouseListener {
    Thread autoRunThread = null;
    boolean autoRollIt = true;
    boolean threadSuspended = false;
    GameBoard board;
    Referee ref;
    ToggleThing autoSnail;
    ToggleThing autoDice;
    ColoredDice coloredDice;
    Snail s0, s1, s2, s3, s4, s5;
    NewGameButton newGameButton;
    public Font bigFont = null;
    public Font smallFont = null;
    public Font smallestFont = null;
    JLabel label;
    AudioPlayer audioPlayerRestart;

    public SnailsRace() {
        threadSuspended = false;
        autoRollIt = true;
        setLayout(null); // No layout manager
        board = new GameBoard();
        add(ref = new Referee(80, 0));
        autoSnail = new ToggleThing(516, 16, 16, "/res/autosnailoff.gif", "/res/autosnailon.gif");
        autoDice = new ToggleThing(492, 16, 16, "/res/autodiceoff.gif", "/res/autodiceon.gif");
        add(coloredDice = new ColoredDice(ref, 16, 0));
        ref.setDice(coloredDice);

        add(s0 = new Snail(ref, board, 0, 0, 0));
        add(s1 = new Snail(ref, board, 0, 1, 1));
        add(s2 = new Snail(ref, board, 0, 2, 2));
        add(s3 = new Snail(ref, board, 0, 3, 3));
        add(s4 = new Snail(ref, board, 0, 4, 4));
        add(s5 = new Snail(ref, board, 0, 5, 5));
        s0.addMouseListener(this);
        s1.addMouseListener(this);
        s2.addMouseListener(this);
        s3.addMouseListener(this);
        s4.addMouseListener(this);
        s5.addMouseListener(this);
        add(newGameButton = new NewGameButton(this, 640 - 64, 0));
        bigFont = new Font("TimesRoman", Font.BOLD, 24);
        smallFont = new Font("TimesRoman", Font.BOLD, 16);
        smallestFont = new Font("Courier", Font.BOLD, 12);

        audioPlayerRestart = new AudioPlayer("New Game Button, lets clear the board", "/res/audio/return.wav");
    }

    /**
     * Restart the Snails
     */
    public void restart(Graphics g) {
        audioPlayerRestart.play();
        s0.restart(g);
        s1.restart(g);
        s2.restart(g);
        s3.restart(g);
        s4.restart(g);
        s5.restart(g);
        coloredDice.restart(g);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.setFont(bigFont);
        g.drawString("Snail's Race by Scott Huskey", 170, 16);
        g.setFont(smallFont);
        g.drawString("Roll the dice then move the snail", 216, 31);
        board.paint(g);
        // TODO: implement or remove auto dice & auto snail move
        // g.setFont(smallestFont);
        // g.drawString("AUTO:", 496, 12);
        // autoSnail.show(g);
        // autoDice.show(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Snail snailClicked = (Snail) e.getSource();
        snailClicked.move(getGraphics());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
