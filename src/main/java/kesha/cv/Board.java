package kesha.cv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

/**
 *
 * @author v.kuravskiy
 */
public class Board {
    
    public Map<Integer,Cell> cells = new HashMap<>();

    public Board(Mat image, List<MatOfPoint> contours) {
        int size = contours.size();
        for (int i = 0; i < size; i++) {
            Cell cell = new Cell();
            cell.contour = contours.get(i);
            cell.n = i;
            cell.row = i / size;
            cell.column = i % size;
            
            cells.put(i, cell);
        }
    }
    
}
