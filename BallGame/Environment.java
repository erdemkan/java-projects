/*
Salih Erdem Koçak
17.04.2023
summary: This is the Java File of the Environment Class
 */
import java.util.ArrayList;
import java.awt.Font;
public class Environment {
    int canvas_width = 800;
    int canvas_height = 500;
    final int TOTAL_GAME_DURATION = 40000;
    final int PAUSE_DURATION = 15;
    final double xScale = 16.0;

    public static void showBalls(ArrayList<Ball> ballList){ // This method executes "showBall" method from the Ball class for all balls
        for(int i = 0; i < ballList.size(); i++){
            Ball.showBall(ballList.get(i));
        }
    }

    public static void moveBalls(ArrayList<Ball> ballList, double currentTime){ // this method executes the "moveBall" method from the Ball Class for all balls
        for(int i = 0; i < ballList.size(); i++){
            Ball.moveBall(ballList.get(i), currentTime);
        }
    }

    public static boolean isCollisionBallsPlayer(Player player, ArrayList<Ball> ballList){ // This method executes "isCollisionBallPlayer" method for all balls

        for (int i = 0; i < ballList.size(); i++){
            if(ballList.get(i).level > -1){
                if(isCollisionBallPlayer(player, ballList.get(i))){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isCollisionBallPlayer(Player player, Ball ball){ // this method checks if there is a collision between a ball and a player
        double closestX = helper1(ball.x, player.x, player.playerWidth);
        if (closestX ==  ball.x){
            double closestY = helper2(ball.y, player.y, player.playerHeight);
            double xDifference = ball.x - closestX;
            double yDifference = ball.y - closestY;
            double distanceSquared = xDifference * xDifference + yDifference * yDifference;
            return distanceSquared <= (ball.radius * ball.radius);
        } else {
            double closestY = helper1(ball.y, player.y, player.playerHeight);
            double xDifference = ball.x - closestX;
            double yDifference = ball.y - closestY;
            double distanceSquared = xDifference * xDifference + yDifference * yDifference;
            return distanceSquared <= (ball.radius * ball.radius);
        }


    }

    public static double helper1(double ballCoord, double playerCoord, double playerWidthOrHeight){  // this method is a helper method for the "isCollisionBallPlayer" method
        if (ballCoord < playerCoord + playerWidthOrHeight / 2 && ballCoord > playerCoord - playerWidthOrHeight / 2){
            return ballCoord;
        }
        else {
            if (Math.abs(ballCoord - (playerCoord + playerWidthOrHeight / 2)) > Math.abs(ballCoord - (playerCoord - playerWidthOrHeight / 2))){
                return playerCoord - playerWidthOrHeight/2;
            }
            else{
                return playerCoord + playerWidthOrHeight/2;
            }
        }
    }

    public static double helper2(double ballCoord, double playerCoord, double playerWidthOrHeight){ // this method is a helper method for the "isCollisionBallPlayer" method
        if (Math.abs(ballCoord - (playerCoord + playerWidthOrHeight / 2)) > Math.abs(ballCoord - (playerCoord - playerWidthOrHeight / 2))){
            return playerCoord - playerWidthOrHeight/2;
        }
        else{
            return playerCoord + playerWidthOrHeight/2;
        }
    }

    public static ArrayList<Ball> arrowBallCollisionChecker(ArrayList<Ball> ballList, Arrow arrow, Player player){ // this method executes "arrowBallCollision" method for all balls
        for (int i = 0; i < ballList.size(); i++){
            ballList = arrowBallCollision(ballList.get(i), arrow, ballList, player);
        }
        return ballList;
    }

    public static ArrayList<Ball> arrowBallCollision(Ball ball, Arrow arrow, ArrayList<Ball> ballList, Player player){ // this method checks if there is a collision between a ball and arrow, if there is a collision this method updates the ballList and the arrow
        if (ball.level > -1){
            if(arrow.isActive){
                if((ball.x - ball.radius <= arrow.startPosition) && (ball.x + ball.radius >= arrow.startPosition) && (ball.y - ball.radius < arrow.height)){
                    arrow.isActive = false;
                    arrow.height = 0;
                    int tempBallLevel = ball.level - 1;
                    ball.level -= 5;
                    ballList.add(new Ball(ball.x, ball.y, tempBallLevel, 1, System.currentTimeMillis(), player));
                    ballList.add(new Ball(ball.x, ball.y, tempBallLevel, 0, System.currentTimeMillis(), player));
                    return ballList;
                }
                return ballList;
            }
            return ballList;
        }
        return  ballList;
    }

    public static Boolean isGameWon(ArrayList<Ball> ballList){ // this method checks if the player won the game and returns a boolean value
        int temp = 0;
        for(int i = 0; i < ballList.size(); i++){
            if(ballList.get(i).level < 0){
                temp += 1;
            }
        }
        return temp == ballList.size();
    }

    public static Integer isGameEnd(double startTime, Environment environment, Player player, ArrayList<Ball> ballList){ // this method checks if the game end in any way and it returns an integer value. Here 1 = time is up, 2 = there is a collision between ball and player, 3 = the player won the game, 4 = the game did not end
        if(System.currentTimeMillis() - startTime + 10 >= environment.TOTAL_GAME_DURATION){
            return 1;
        }
        else if(isCollisionBallsPlayer(player, ballList)){
                return 2;
        }
        else if(isGameWon(ballList)){
                return 3;
        } else{
                return 4;
        }


    }

    public static void run(Environment environment, Player player, Bar bar, Arrow arrow, ArrayList<Ball> ballList) { // this method starts everything and runs the game

        double startTime = System.currentTimeMillis();
        StdDraw.setCanvasSize(environment.canvas_width, environment.canvas_height);
        StdDraw.setXscale(0.0, 16.0); // x-axis scale
        StdDraw.setYscale(-1.0, 9.0); // y-axis scale
        StdDraw.enableDoubleBuffering();

        ballList.add(new Ball(environment.xScale/4, 0.5, 0, 1, System.currentTimeMillis(), player));
        ballList.add(new Ball(environment.xScale/3, 0.5, 1, 0, System.currentTimeMillis(), player));
        ballList.add(new Ball(environment.xScale/4, 0.5, 2, 1, System.currentTimeMillis(), player));




        while (true) {


            StdDraw.picture(8.0, 4.0, "images/background.png", 16, 10);
            StdDraw.picture(8.0, -0.5, "images/bar.png", 16, 1);

            double currentTime = System.currentTimeMillis();

            bar.showBar(bar, startTime, environment.TOTAL_GAME_DURATION);
            player.showPlayer(environment, player);



            if (isGameEnd(startTime, environment, player, ballList) == 1){
                StdDraw.picture(8.0, 4.0, "images/game_screen.png", 16.0 / 3.8, 10.0 / 4);
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text(8.0, 4.0, "Game Over!");
                StdDraw.setFont(new Font("Helvetica", Font.ITALIC, 15));
                StdDraw.text(8.0, 9.0/2.5, "To Replay Click \"Y\"");
                StdDraw.text(8.0, 9.0/2.8, "To Quit Click \"N\"");
                StdDraw.show();
                break;
            } else if (isGameEnd(startTime, environment, player, ballList) == 2) {
                StdDraw.picture(8.0, 4.0, "images/game_screen.png", 16.0 / 3.8, 10.0 / 4);
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text(8.0, 4.0, "Game Over!");
                StdDraw.setFont(new Font("Helvetica", Font.ITALIC, 15));
                StdDraw.text(8.0, 9.0/2.5, "To Replay Click \"Y\"");
                StdDraw.text(8.0, 9.0/2.8, "To Quit Click \"N\"");
                StdDraw.show();
                break;
            } else if (isGameWon(ballList)) {
                StdDraw.picture(8.0, 4.0, "images/game_screen.png", 16.0 / 3.8, 10.0 / 4);
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text(8.0, 4.0, "You Won!");
                StdDraw.setFont(new Font("Helvetica", Font.ITALIC, 15));
                StdDraw.text(8.0, 9.0/2.5, "To Replay Click \"Y\"");
                StdDraw.text(8.0, 9.0/2.8, "To Quit Click \"N\"");
                StdDraw.show();
                break;
            }


            arrow.showArrow(arrow, player);
            ballList = arrowBallCollisionChecker(ballList, arrow, player);
            moveBalls(ballList, currentTime);
            showBalls(ballList);

            StdDraw.show();
            StdDraw.clear();



        }
    }

    public static void afterGame(){ // this method listens input from the player and starts the game again or ends the game according to the players key input
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(100);
        }

        char key = StdDraw.nextKeyTyped();

        if (key == 'Y' || key == 'y') {

        } else if (key == 'N' || key == 'n') {
            System.exit(0);
        }
        else{
            afterGame();
        }
    }
}
