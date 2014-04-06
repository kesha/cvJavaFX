package kesha.cv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import static kesha.cv.Util.approxEqual;
import static kesha.cv.Util.euclideanDist;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Kesha
 */
public class ComputerVision {

    public static String info = "Not initialized.";

    static {
        String workingDir = System.getProperty("user.dir");
        String openCvLibDir = "\\opencv\\";
        switch (System.getProperty("sun.arch.data.model")) {
            case "32":
                openCvLibDir += "x86\\";
                break;
            case "64":
                openCvLibDir += "x64\\";
                break;
        }
        System.load(workingDir + openCvLibDir + Core.NATIVE_LIBRARY_NAME + ".dll");
        info = "OpenCV lib is loaded sucessfully (" + System.getProperty("sun.arch.data.model") + " bit)";
    }

    public static byte[] doComputerVision() {
        Mat source = Highgui.imread("test.png");
        Mat image = source.clone();
        MatOfByte matOfByte = new MatOfByte();
        List<MatOfPoint> cells = getCells(image);
        Collections.sort(cells, new MatOfPointComparator());
        for (MatOfPoint matOfPoint : cells) {
            System.out.println(Arrays.toString(matOfPoint.toArray()));
        }
        Imgproc.drawContours(source, cells, -1, new Scalar(0, 0, 255));
        for (int i = 0; i < cells.size(); i++) {
            MatOfPoint matOfPoint = cells.get(i);
            Core.putText(source, Integer.toString(i+1), Util.getFirstPoint(matOfPoint), 1, 1, new Scalar(0, 0, 255), 2);           
        }
        System.out.println("Found " + cells.size() + " cells.");
        Highgui.imencode(".png", source, matOfByte);
        return matOfByte.toArray();

    }

    public static List<MatOfPoint> getCells(Mat image) {
        List<MatOfPoint> cells = new ArrayList<>();

        Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(image, image, 0, 50);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (Iterator<MatOfPoint> it = contours.iterator(); it.hasNext();) {
            MatOfPoint contour = it.next();
            MatOfPoint2f approx = new MatOfPoint2f();
            double arcLength = Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) * 0.03;
            Imgproc.approxPolyDP(new MatOfPoint2f(contour.toArray()), approx,
                    arcLength, true);
            if (!Imgproc.isContourConvex(new MatOfPoint(approx.toArray()))
                    || Imgproc.contourArea(contour) < 1500) {
                it.remove();
                continue;
            }
            if (approx.toList().size() == 4) {
                Point[] points = approx.toArray();
                double ab = euclideanDist(points[0], points[1]);
                double bc = euclideanDist(points[1], points[2]);
                double cd = euclideanDist(points[2], points[3]);
                double da = euclideanDist(points[3], points[0]);
                if (approxEqual(ab, bc, cd, da)) {
                    cells.add(new MatOfPoint(approx.toArray()));
                }     
            }
        }
        return cells;
    }

}
