package snailsrace;

import java.awt.Graphics;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Game Board Class */
public class GameBoard extends Panel {
    static final int x = 0;
    static final int y = 32;
    static final int width = 10 * 64;
    static final int height = 6 * 64;

    private BufferedImage finish;

    private BufferedImage square;

    public GameBoard() {
        try {
            square = ImageIO.read(getClass().getResourceAsStream("/res/grassbackground.gif"));
            finish = ImageIO.read(getClass().getResourceAsStream("/res/rockbackground.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paint it.
     */
    public void paint(Graphics g) {
        int xx, yy;
        for (xx = 0; xx < 10; xx++)
            for (yy = 0; yy < 6; yy++)
                drawCell(g, xx, yy);
    }

    protected void drawCell(Graphics g, int xx, int yy) {
        int startX, startY;
        startX = cell2CoordX(xx);
        startY = cell2CoordY(yy);

        if (xx < 9)
            g.drawImage(square, startX, startY, null);
        else
            g.drawImage(finish, startX, startY, null);

    }

    protected int cell2CoordX(int coordx) {
        return (x + 64 * coordx);
    }

    protected int cell2CoordY(int coordy) {
        return (y + 64 * coordy);
    }

}