package uk.ac.cam.gr3.weather;

public class Util {

    public static double clamp(double value, double min, double max) {

        if (value <= min)
            return min;
        if (value >= max)
            return max;
        return value;
    }
}
