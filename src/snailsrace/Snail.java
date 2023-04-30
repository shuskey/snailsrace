package snailsrace;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Snail extends JPanel {
    Referee referee;
    GameBoard board;
    int snailColorIndex;
    int cellX, cellY;
    long winCount;
    boolean iWon;

    BufferedImage snailImage;

    public String[] imageFileNames = {
            "/res/bluesnail.gif",
            "/res/pinksnail.gif",
            "/res/yellowsnail.gif",
            "/res/orangesnail.gif",
            "/res/greensnail.gif",
            "/res/redsnail.gif"
    };

    public String[] soundFileNames = {
            "/res/audio/blueg.wav",
            "/res/audio/pinkg.wav",
            "/res/audio/yellowg.wav",
            "/res/audio/orangeg.wav",
            "/res/audio/greeng.wav",
            "/res/audio/redg.wav",
    };
    AudioPlayer audioPlayerMyColor, audioPlayerIWin;

    public String[] snailName = {
            "blue", "pink", "yellow", "orange", "green", "red"
    };

    public Snail(Referee ref, GameBoard gboard, int cellX, int cellY, int color) {

        referee = ref;
        board = gboard;
        this.cellX = cellX;
        this.cellY = cellY;
        snailColorIndex = color;

        setBounds(cellX, cellY, 64, 64);
        setLocation(board.cell2CoordX(cellX), board.cell2CoordY(cellY));

        try {
            snailImage = ImageIO.read(getClass().getResourceAsStream(imageFileNames[snailColorIndex]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        iWon = false;
        winCount = 0;
        audioPlayerIWin = new AudioPlayer(snailName[snailColorIndex] + " snail, I Win", "/res/audio/iwings.wav");
        audioPlayerMyColor = new AudioPlayer(snailName[snailColorIndex] + " snail, my color",
                soundFileNames[snailColorIndex]);
    }

    /**
     * Did this snail win?
     */
    public boolean didIWin() {
        return iWon;
    }

    /**
     * The user has clicked on the snail.
     */
    public boolean move(Graphics g) {
        if (referee.canimove(snailColorIndex)) {
            if (iWon) {
                audioPlayerMyColor.play();
                referee.imoved();
            } else {
                // board.drawCell(g, cellX, cellY);
                cellX++;
                if (cellX >= 9) {
                    iWon = true;
                    winCount++;
                    audioPlayerIWin.play();
                } else {
                    audioPlayerMyColor.play();
                }
                setLocation(board.cell2CoordX(cellX), board.cell2CoordY(cellY));
                referee.imoved();
            }
            return true;
        }
        return false;
    }

    /**
     * Restart the snail.
     */
    public void restart(Graphics g) {
        cellX = 0;
        iWon = false;
        setLocation(board.cell2CoordX(cellX), board.cell2CoordY(cellY));
        referee.imoved();
    }

    public void paint(Graphics g) {
        g.drawImage(snailImage, 0, 0, null);
    }

}