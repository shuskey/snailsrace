package snailsrace;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This is an example program that demonstrates how to play back an audio file
 * using the Clip in Java Sound API.
 * 
 * @author www.codejava.net
 *
 */
public class AudioPlayer implements LineListener {

    /**
     * this flag indicates whether the playback completes or not.
     */
    boolean playCompleted = true;
    Clip audioClip;
    DataLine.Info info;
    AudioInputStream audioStream;
    String whoIsMakingWhatSound;
    File audioFile;

    public AudioPlayer(String whoIsMakingWhatSound, String audioRelativeFilePath) {
        this.whoIsMakingWhatSound = whoIsMakingWhatSound;
        setup(audioRelativeFilePath);
    }

    /**
     * Play a given audio file.
     * 
     * @param audioRelativeFilePath Path of the audio file.
     */
    private void setup(String audioRelativeFilePath) {
        String absolutePath = null;
        try {
            URL resource = SnailsRace.class.getResource(audioRelativeFilePath);
            File file = Paths.get(resource.toURI()).toFile();
            absolutePath = file.getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        audioFile = new File(absolutePath);

        try {
            info = new DataLine.Info(Clip.class, AudioSystem.getAudioInputStream(audioFile).getFormat());
        } catch (UnsupportedAudioFileException ex) {
            System.out.println(whoIsMakingWhatSound + ": The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println(whoIsMakingWhatSound + ": Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    public void play() {
        if (audioClip != null && audioClip.isRunning()) {
            System.out.println(whoIsMakingWhatSound + ": AudioClip still running - we are bailing out!");
            return;
        }
        if (!playCompleted) {
            System.out.println(whoIsMakingWhatSound + ": I FOUND PLAYING IS NOT COMPLETED");
            audioClip.stop();
            audioClip.close();
            playCompleted = true;
        }
        try {
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            playCompleted = false;
            audioClip.start();
        } catch (LineUnavailableException ex) {
            System.out.println(whoIsMakingWhatSound + ": Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            System.out.println(whoIsMakingWhatSound + ": The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println(whoIsMakingWhatSound + ": Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            playCompleted = false;

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            audioClip.close();
        }
    }
}
