/*
Salih Erdem Koçak
17.04.2023
summary: This is the Java File of the Arrow Class
 */
import java.awt.event.KeyEvent;

public class Arrow { // arrow class
    final int PERIOD_OF_ARROW = 1500;
    boolean isActive;
    double startPosition;
    double startTimeOfArrow;
    double height;

    public Arrow() {
        isActive = false;
    }

    public static void showArrow(Arrow arrow, Player player){ // this method displays arrow
        if (arrow.isActive){
            if((System.currentTimeMillis() - arrow.startTimeOfArrow) < arrow.PERIOD_OF_ARROW){
                double rate1 = (System.currentTimeMillis() - arrow.startTimeOfArrow) / arrow.PERIOD_OF_ARROW;
                arrow.height = 1.0 + rate1 * 9.0;
                StdDraw.picture(arrow.startPosition, arrow.height / 2, "images/arrow.png", 0.2, arrow.height);
            }

            else{
                arrow.isActive = false;
            }

        }
        else{
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                arrow.isActive = true;
                arrow.startPosition = player.x;
                arrow.startTimeOfArrow = System.currentTimeMillis();
            }
        }


    }
}
