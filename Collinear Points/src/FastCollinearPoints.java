import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

  private Point[] points;
  private int numSegment = 0;
  private LineSegment[] segments = null;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {
    if (points == null)
      throw new IllegalArgumentException();

    for (Point p : points)
      if (p == null)
        throw new IllegalArgumentException();

    for (int i = 0; i < points.length - 1; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0)
          throw new IllegalArgumentException();
      }
    }

    this.points = points.clone();

    numSegment = segments().length;
  }

  // the number of line segments
  public int numberOfSegments() {
    return numSegment;
  }

  // the line segments
  // public LineSegment[] segments() {
  // if (segments != null)
  // return this.segments.clone();

  // ArrayList<Point[]> arr = new ArrayList<>();

  // Point[] points2 = points.clone();

  // for (int i = 0; i < points.length; i++) {
  // Point p = points[i];
  // Arrays.sort(points2, p.slopeOrder());

  // Point prev, curr, s, e;
  // s = p;
  // e = p;
  // int succCnt = 0;

  // for (int j = 1; j < points.length; j++) {
  // curr = points2[j];
  // prev = points2[j - 1];

  // double s0 = p.slopeTo(prev);
  // double s1 = p.slopeTo(curr);

  // if (s0 == s1) {
  // succCnt++;
  // if (succCnt == 1) {
  // e = curr.compareTo(prev) > 0 ? curr : prev;
  // s = curr.compareTo(prev) < 0 ? curr : prev;
  // e = e.compareTo(p) > 0 ? e : p;
  // s = s.compareTo(p) < 0 ? s : p;
  // } else {
  // e = curr.compareTo(e) > 0 ? curr : e;
  // s = curr.compareTo(s) < 0 ? curr : s;
  // }
  // } else {
  // if (succCnt > 1) {
  // Point[] l = new Point[] { s, e };
  // if (!isLineExists(arr, l)) {
  // arr.add(l);
  // }
  // }
  // succCnt = 0;
  // }
  // if (j == points.length - 1 && s0 == s1) {
  // if (succCnt > 1) {
  // Point[] l = new Point[] { s, e };
  // if (!isLineExists(arr, l)) {
  // arr.add(l);
  // }
  // }
  // }
  // }
  // }
  // this.segments = new LineSegment[arr.size()];
  // int i = 0;
  // for (Point[] ps : arr) {
  // this.segments[i++] = new LineSegment(ps[0], ps[1]);
  // }

  // return this.segments.clone();
  // }

  public LineSegment[] segments() {
    if (segments != null)
      return this.segments.clone();

    ArrayList<Point[]> arr = new ArrayList<>();
    List<Point> points2 = Arrays.asList(points);
    // Point[] points2 = points.clone();

    for (int i = 0; i < points.length; i++) {
      // get a point, remove it from second list
      Point p = points[i];
      points2.remove(i);

      // sort he list according to current point
      points2.sort(p.slopeOrder());
      // Arrays.sort(points2, );
      // if there are less then 4 points there is no segment
      if (points2.size() < 3) {
        return new LineSegment[] {};
      }
      Point curr, s, e;
      s = p;
      e = p;
      double prevSlope = p.slopeTo(points2.get(0));
      double currSlope = p.slopeTo(points2.get(1));
      int collinearCount = 0;

      for (int j = 2; j < points2.size(); j++) {
        if (equals(currSlope, prevSlope)) {
          collinearCount++;
        } else {
          if (collinearCount > 1) {
            
          }
        }
        curr = points2.get(j);
        prevSlope = currSlope;
        currSlope = p.slopeTo(curr);
      }
    }
    this.segments = new LineSegment[arr.size()];
    int i = 0;
    for (Point[] ps : arr) {
      this.segments[i++] = new LineSegment(ps[0], ps[1]);
    }

    return this.segments.clone();
  }

  private boolean equals(double d1, double d2) {
    return Math.abs(d1 - d2) < 0.00001;
  }

  private boolean isLineExists(ArrayList<Point[]> a, Point[] l) {
    for (Point[] ps : a) {
      Point p1 = ps[0];
      Point p2 = ps[1];
      Point p3 = l[0];
      Point p4 = l[1];
      if ((p1.compareTo(p3) == 0 && p2.compareTo(p4) == 0) || (p1.compareTo(p4) == 0 && p2.compareTo(p3) == 0)) {
        return true;
      }
    }
    return false;
  }

  private void printSlopes(Point curr, Point[] arr) {

    System.out.println();
    for (Point p : arr) {
      System.out.print(curr.slopeTo(p) + ", ");
    }
    System.out.println();
  }

  public static void main(String[] args) {

    // read the n points from a file
    In in = new In("txt/equidistant.txt");
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();

    System.out.println("total segments count:" + collinear.numberOfSegments() + " done !");

    // print and draw the line segments
    // BruteCollinearPoints collinear2 = new BruteCollinearPoints(points);
    // for (LineSegment segment : collinear2.segments()) {
    // StdOut.println(segment);
    // segment.draw();
    // }
    StdDraw.show();

  }

}
