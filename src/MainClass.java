import java.util.Random;

public class MainClass {

    public static void main(String[] args){
        int gamma;
        int tone;
        int instrument = 28;
        Random random = new Random();

        tone = 80 - random.nextInt(30);                  // можливі значення: 0-127
        gamma = random.nextInt(2);                       // 0 - major, 1 - minor

        Player player = new Player(instrument);
        player.generate(gamma, tone, 80);
        player.play(85, 125);
        player.close();
    }
}
