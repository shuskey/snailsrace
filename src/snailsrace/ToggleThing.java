package snailsrace;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/** ToggleThing Class */
public class ToggleThing extends Rectangle {
    boolean mode;

    BufferedImage toggleImage[];

    public ToggleThing(int x, int y, int size, String imageOff, String imageOn) {
        this.x = x;
        this.y = y;
        mode = false;
        toggleImage = new BufferedImage[2];

        try {
            toggleImage[0] = ImageIO.read(getClass().getResourceAsStream(imageOff));
            toggleImage[1] = ImageIO.read(getClass().getResourceAsStream(imageOn));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = height = size;
    }

    /**
     * Paint it.
     */
    public void show(Graphics g) {
        g.clearRect(x, y, width, height);
        g.drawImage(toggleImage[(mode ? 1 : 0)], x, y, null);
    }

    /**
     * The user has clicked on the ToggleThing.
     */
    public void toggle(Graphics g) {
        mode = !mode;
        show(g);
    }

    public boolean mode() {
        return mode;
    }
}