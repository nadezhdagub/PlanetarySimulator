package simulator.model;

public class LogicManager {

    public double speedX(double speed, double deg) {
        return speed * Math.cos(deg);
    }

    public double speedY(double speed, double deg) {
        return speed * Math.sin(deg);
    }

    public double acceleration(double G, double weight, double starPosition, double planetPosition, double distance){
        return (G * weight * (starPosition - planetPosition) / Math.pow(distance, 3));
    }

    public double distance(double starX, double starY, double x, double y) {
        return Math.sqrt(Math.pow(starX - x, 2) + Math.pow(starY - y, 2));
    }

    public double[] timePortation(double a, double x, double y, double vx, double vy, double sx, double sy, double g, double w) {
        var tpXY = new double[4];
        tpXY[0] = x;
        tpXY[1] = y;
        tpXY[2] = vx;
        tpXY[3] = vy;
        var i = a;

        while (i > 0) {
            var dist = distance(sx, sy, tpXY[0], tpXY[1]);
            tpXY[2] += acceleration(g, w, sx, tpXY[0], dist);
            tpXY[3] += acceleration(g, w, sy, tpXY[1], dist);
            tpXY[0] += tpXY[2];
            tpXY[1] += tpXY[3];

            i -= 0.1;
        }

        return tpXY;
    }
}
