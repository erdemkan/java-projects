/*
Salih Erdem Koçak
17.04.2023
summary: This is the Java File of the Player Class
 */
import java.awt.event.KeyEvent;
public class Player { // Player Class
    final int PERIOD_OF_PLAYER = 6000;
    final double PLAYER_HEIGHT_WIDTH_RATE = 37.0/27.0;
    final double PLAYER_HEIGHT_SCALEY_RATE = 1.0/8.0;
    double x = 8.0;
    double playerHeight = 9.0 * PLAYER_HEIGHT_SCALEY_RATE;
    double playerWidth = playerHeight * (1 / PLAYER_HEIGHT_WIDTH_RATE);
    double y = playerHeight / 2;
    double playerTime = System.currentTimeMillis();
    double stepSize;

    public static void showPlayer(Environment environment, Player player){ // This method displays player
        player.stepSize = 16.0 * (System.currentTimeMillis() - player.playerTime) / player.PERIOD_OF_PLAYER;
        player.playerTime = System.currentTimeMillis();
        StdDraw.picture(player.getX(player), player.getY(player), "images/player_back.png", player.playerWidth, player.playerHeight);
    }

    public static double getX(Player player) { // this method returns the x-position of player, and it updates the x-position according to user inputs

        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && player.x > 0.5) {
            player.x -= player.stepSize;
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && player.x < 15.5) {
            player.x += player.stepSize;
        }
        return player.x;
    }

    public static double getY(Player player){ // this method returns y-position of player
        double height = player.PLAYER_HEIGHT_SCALEY_RATE * 10.0;
        return height/2;
    }
}
