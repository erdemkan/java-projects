/*
Salih Erdem Koçak
17.04.2023
summary: This is the java file of the Bar Class.
 */
import java.awt.Color;
public class Bar {

    public static void showBar(Bar bar, double startTime, double totalGameDuration){  // this method displays the bar
        double passedTime = System.currentTimeMillis() - startTime;
        double passedRatio = passedTime / totalGameDuration;
        double passedRatioAsDistance = (1 - passedRatio) * 16.0;
        int r = 255;
        int g = (int) (255 * (1 - passedRatio));
        int b = 0;
        Color timeBarColor = new Color(r, g, b);
        StdDraw.setPenColor(timeBarColor);
        StdDraw.filledRectangle(passedRatioAsDistance / 2, -0.5, passedRatioAsDistance/2, 0.25);

    }
}
