package resInv.sound;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundPlayer {

	//Plays a sound
	public static void play(Sound sound, float value) {	
		File file = new File(sound.getFilePath());
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(value);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
