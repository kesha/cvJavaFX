package kesha.cv;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

/**
 *
 * @author Kesha
 */
public class Util {

    public static double euclideanDist(Point p, Point q) {
        Point diff = new Point(p.x - q.x, p.y - q.y);
        return Math.sqrt(diff.x * diff.x + diff.y * diff.y);
    }
    public static boolean approxEqual(double ab, double bc, double cd, double da) {
        return approxEqual(ab, bc) && approxEqual(bc, cd) && approxEqual(cd, da) && approxEqual(da, ab);
    }

    public static boolean approxEqual(double ab, double bc) {
        return Math.abs(ab - bc) < ab * 0.1;
    }    
    
    public static Point getFirstPoint(MatOfPoint o) {
        Point zero = new Point(0, 0);
        Point first = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        double minDist = Double.MAX_VALUE;
        for (Point point : o.toList()) {
            double dist = Util.euclideanDist(zero, point);
            if (dist < minDist) {
                first = point;
                minDist = dist;
            }
        }
        return first;
    }    
}
