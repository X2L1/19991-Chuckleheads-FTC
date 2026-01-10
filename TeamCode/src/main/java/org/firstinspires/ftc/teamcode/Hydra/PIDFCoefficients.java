package org.firstinspires.ftc.teamcode.Hydra;
import com.bylazar.configurables.annotations.Configurable;
@Configurable
public class PIDFCoefficients {
    public static double outtakeP = 15.0;
    public static double outtakeI = 0.02;
    public static double outtakeD = 0.2;
    public static double outtakeF = 11.75;

    public static double getOuttakeP() {
        return outtakeP;
    }
    public static double getOuttakeI() {
        return outtakeI;
    }
    public static double getOuttakeD() {
        return outtakeD;
}
    public static double getOuttakeF() {
        return outtakeF;
    }
}
