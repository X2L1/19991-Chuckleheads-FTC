package org.firstinspires.ftc.teamcode.V2.utils;
import com.arcrobotics.ftclib.util.LUT;
public class OuttakeLUTs {
    LUT<Double, Double> speeds = new LUT<Double, Double>()
    {{
        add(5.0, 1.0);
        add(4.0, 0.9);
        add(3.0, 0.75);
        add(2.0, 0.5);
        add(1.0, 0.2);
    }};
    LUT<Double, Double> angles = new LUT<Double, Double>()
    {{
        add(5.0, 45.0);
        add(4.0, 40.0);
        add(3.0, 30.0);
        add(2.0, 20.0);
        add(1.0, 10.0);
    }};
    public double getAngle(double distance) {
        return angles.get(distance);
    }
    public double getSpeed(double distance) {
        return speeds.get(distance);
    }
}
