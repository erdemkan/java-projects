/*
Salih Erdem Koçak
17.04.2023
summary: This is the java file of the Main class
 */

import java.util.ArrayList;
public class BallGame {

    public static void main(String[] args) { // main function
        while (true) {
            Environment environmentMain = new Environment();
            Player playerMain = new Player();
            Bar barMain = new Bar();
            Arrow arrowMain = new Arrow();
            ArrayList<Ball> ballList = new ArrayList<Ball>();
            environmentMain.run(environmentMain, playerMain, barMain, arrowMain, ballList);
            environmentMain.afterGame();
        }
    }
}