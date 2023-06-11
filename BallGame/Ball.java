/*
Salih Erdem Koçak
17.04.2023
summary: This is the Java File of the Ball Class
 */
public class Ball {
    final int PERIOD_OF_BALL = 15000;
    final double HEIGHT_MULTIPLIER = 1.75;
    double minPossibleHeight;
    final double RADIUS_MULTIPLIER = 2.0;
    double minPossibleRadius = 10.0 * 0.0175;
    double currentTime;
    double startTime;
    double GRAVITY = 0.03 * 10.0;
    double thePaceAfterHittingGround;
    double x;
    double y;
    int level;
    int xDirection; // 1 is right and 0 is left
    int yDirection; // 1 is up and 0 is down
    double xPace = (PERIOD_OF_BALL / 1000.0) / 16.0;
    double yPace;
    double radius;

    public Ball(double _x, double _y, int _level, int _xDirection, double _startTime, Player player){
        this.x = _x;
        this.y = _y;
        this.level = _level;
        this.currentTime = _startTime;
        this.xDirection = _xDirection;
        this.minPossibleHeight = player.playerHeight * 1.4;
        for (int i = 0; i < _level; i++) {
            this.minPossibleHeight = this.minPossibleHeight * HEIGHT_MULTIPLIER;
        }
        if(_level == 0){
            this.radius = minPossibleRadius;
        } else if (_level == 1) {
            this.radius = minPossibleRadius * RADIUS_MULTIPLIER;
        } else {
            this.radius = minPossibleRadius * RADIUS_MULTIPLIER * RADIUS_MULTIPLIER;
        }
        this.yPace = Math.sqrt(2 * GRAVITY * minPossibleHeight);
        this.yDirection = 1;
        this.thePaceAfterHittingGround = Math.sqrt(2 * GRAVITY * minPossibleHeight);
    }



    public static void showBall(Ball ball){
        if(ball.level > -1){
            StdDraw.picture(ball.x, ball.y, "images/ball.png", ball.radius * 2, ball.radius * 2);
        }
    }

    public static void moveBall(Ball ball, double currentTime){
        double t = (currentTime - ball.currentTime) / 1000.0;
        ball.currentTime = currentTime;

        if (ball.xDirection == 1){
            ball.x = ball.x + ball.xPace * t;
            if (ball.x + ball.radius > 16) {
                ball.xDirection = 0;
                ball.x = 16 - ball.radius;

            }
        } else {
            ball.x = ball.x - ball.xPace * t;
            if (ball.x - ball.radius < 0) {
                ball.xDirection = 1;
                ball.x = ball.radius;

            }
        }

        if (ball.yDirection == 1){
            ball.y = ball.y + ball.yPace * t;
        } else {
            ball.y = ball.y - ball.yPace * t;

        }

        if (ball.yDirection == 1){
            ball.yPace = ball.yPace - ball.GRAVITY * t;
        } else {
            ball.yPace = ball.yPace + ball.GRAVITY * t;
        }

        if (ball.y - ball.radius < 0) {
            ball.yDirection = 1;
            ball.y = ball.radius;
            ball.yPace = ball.thePaceAfterHittingGround;

        }


    }

}
