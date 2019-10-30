import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Player {

    private MidiChannel[] channels = null;
    private Synthesizer synth = null;
    private ArrayList<Integer> melody = new ArrayList<>();
    private ArrayList<Integer> notes = new ArrayList<>();

    public Player(int instrument) {
        int drum = 115;
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
            channels[0].programChange(instrument);
            channels[1].programChange(drum);
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generate(int gamma, int tone, int songDuration){
        melody.clear();
        if(gamma == 0)
            setNotesMajor(tone);
        else
            setNotesMinor(tone);

        Random random = new Random();
        int num;
        for (int i = 1; i <= songDuration; i++) {
            num = random.nextInt(notes.size());
            melody.add(notes.get(num));
        }
    }

    private void delay(int duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void playSound(int note, int volume,int duration) {
        channels[0].noteOn(note, volume);
        delay(duration);
        channels[0].noteOff(note);
    }

    private void playSoundAndDrum(int note, int volume,int duration) {
        channels[0].noteOn(note, volume);
        channels[1].noteOn(40, volume+20);
        delay(duration);
        channels[0].noteOff(note);
        channels[1].noteOff(note);
    }

    public void play(int volume, int noteDuration) {
        int i;
        for (i = 0; i <= melody.size()-2; i++) {
            if (i % 8 == 7) {
                playSoundAndDrum(melody.get(i), volume + 10, noteDuration);  //emphasis
            } else {
                playSound(melody.get(i), volume, noteDuration);
            }
        }
        playSound(melody.get(i), volume, 1200);
    }

    public void printMelody(){

    }

    private void setNotesMajor(int tone){
        int tone_ = tone;
        notes.clear();
        for (int i = 0; i <= 6; i++) {
            notes.add(tone_);
            if ((i == 2) || (i == 6)) {
                tone_ += 1;
            } else {
                tone_ += 2;
            }
        }
    }

    private void setNotesMinor(int tone){
        int tone_ = tone;
        notes.clear();
        for (int i = 0; i <= 6; i++) {
            notes.add(tone_);
            if ((i == 1) || (i == 4)) {
                tone_ += 1;
            } else {
                tone_ += 2;
            }
        }
    }

    public void close() {
        synth.close();
    }
}

