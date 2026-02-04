package org.firstinspires.ftc.teamcode.V2.utils;
import com.arcrobotics.ftclib.util.LUT;
public class OuttakeLUTs {
    LUT<Double, Double> speeds = new LUT<Double, Double>()
    {{
        add(5.0, 1800.0);
        add(4.0, 1700.0);
        add(3.0, 1600.0);
        add(2.0, 1500.0);
        add(1.0, 1400.0);
    }};
    LUT<Double, Double> angles = new LUT<Double, Double>()
    {{
        add(5.0, .5);
        add(4.0, .5);
        add(3.0, .5);
        add(2.0, .5);
        add(1.0, .5);
    }};
    public double getAngle(double distance) {
        return angles.getClosest(distance);
    }
    public double getSpeed(double distance) {
        return speeds.getClosest(distance);
    }
}
