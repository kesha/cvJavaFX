/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kesha.cv;

import java.util.Comparator;
import static kesha.cv.Util.getFirstPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

/**
 *
 * @author Kesha
 */
public class MatOfPointComparator implements Comparator<MatOfPoint> {

    @Override
    public int compare(MatOfPoint o1, MatOfPoint o2) {
        Point p1 = getFirstPoint(o1);
        Point p2 = getFirstPoint(o2);
        if(!Util.approxEqual(p1.y, p2.y)) {
            return Double.compare(p1.y, p2.y);
        } else {
            return Double.compare(p1.x, p2.x);
        }
    }



}
