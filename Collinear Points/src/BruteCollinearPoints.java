import java.util.ArrayList;

public class BruteCollinearPoints {

  private Point[] points;
  private int numSegment = 0;
  private LineSegment[] segments = null;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {

    if (points == null)
      throw new IllegalArgumentException();

    for (Point p : points)
      if (p == null)
        throw new IllegalArgumentException();

    for (int i = 0; i < points.length - 1; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) {
          throw new IllegalArgumentException();
        }
      }
    }

    this.points = points.clone();
    numSegment = segments().length;
  }

  // the number of line segments
  public int numberOfSegments() {
    return numSegment;
  }

  // private Point[] deepCopy(Point[] ps){
  // int size = ps.length;
  // Point[] np = new Point[size];
  //
  // for (int i = 0; i < size; i++){
  // np[i] = getPointFromStr(ps[i].toString());
  // }
  // return np;
  // }

  // private Point getPointFromStr(String s) {
  //   int x = Integer.parseInt(s.split(",")[0].replace("(", "").trim());
  //   int y = Integer.parseInt(s.split(",")[1].replace(")", "").trim());
  //   Point p = new Point(x, y);

  //   return p;
  // }

  // private LineSegment[] deepCopy(LineSegment[] ls){
  // int size = ls.length;
  // LineSegment[] np = new LineSegment[size];
  //
  // for (int i = 0; i < size; i++){
  // String[] s = ls[i].toString().split("->");
  // LineSegment l = new LineSegment(getPointFromStr(s[0]),
  // getPointFromStr(s[1]));
  // np[i] = l;
  // }
  // return np;
  // }

  // the line segments
  public LineSegment[] segments() {
    if (this.segments != null)
      return this.segments.clone();

    ArrayList<LineSegment> arr = new ArrayList<>();

    int s = points.length;

    double[][] slopes = new double[s][s];
    int[][] compares = new int[s][s];

    for (int i = 0; i < s; i++) {
      for (int j = 0; j < s; j++) {
        slopes[i][j] = points[i].slopeTo(points[j]);
        compares[i][j] = points[i].compareTo(points[j]);
      }
    }

    for (int i = 0; i < s; i++) {
      for (int j = 0; j < s; j++) {
        for (int k = 0; k < s; k++) {
          for (int n = 0; n < s; n++) {
            Point p1 = points[i];
            Point p4 = points[n];
            double m = slopes[i][j];
            if (m == slopes[i][k] && m == slopes[i][n]) {
              if (compares[i][j] < 0 && compares[j][k] < 0 && compares[k][n] < 0)
                arr.add(new LineSegment(p1, p4));
            }
          }
        }
      }
    }

    this.segments = arr.toArray(new LineSegment[0]);

    return this.segments.clone();
  }
}
