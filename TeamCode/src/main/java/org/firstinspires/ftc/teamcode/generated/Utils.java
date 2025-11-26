package org.firstinspires.ftc.teamcode.generated;

public class Utils {
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            //do nothing
        }
    }
    public static void sleep()
    {
        sleep(200);
    }

}
