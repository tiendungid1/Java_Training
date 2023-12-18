//package rxn.effectivejava.chapter3;
//
//import java.util.Objects;
//
//enum Color {
//  RED, BLUE, BLACK, GREEN
//}
//
//class Point {
//  private final int x;
//  private final int y;
//
//  public Point(int x, int y) {
//    this.x = x;
//    this.y = y;
//  }
//
//  @Override
//  public boolean equals(Object o) {
//    if (!(o instanceof Point p)) {
//      return false;
//    }
//
//    return p.x == x && p.y == y;
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(x, y);
//  }
//}
//
////class ColorPoint extends Point {
////  private final Color color;
////
////  public ColorPoint(int x, int y, Color color) {
////    super(x, y);
////    this.color = color;
////  }
////
////  // Version 1: violates symmetry!
//////  @Override
//////  public boolean equals(Object o) {
//////    if (!(o instanceof ColorPoint)) {
//////      return false;
//////    }
//////
//////    return super.equals(o) && ((ColorPoint) o).color == color;
//////  }
////
////  // Version 2: violates transitive!
////  @Override
////  public boolean equals(Object o) {
////    if (!(o instanceof Point)) {
////      return false;
////    }
////
////    // If o is a normal Point, do a color-blind comparison
////    if (!(o instanceof ColorPoint)) {
////      return o.equals(this);
////    }
////
////    // o is a ColorPoint; do a full comparison
////    return super.equals(o) && ((ColorPoint) o).color == color;
////  }
////}
//
//class ColorPoint {
//  private final Point point;
//  private final Color color;
//
//  public ColorPoint(int x, int y, Color color) {
//    point = new Point(x, y);
//    this.color = Objects.requireNonNull(color);
//  }
//
//  /**
//   * Returns the point-view of this color point.
//   */
//  public Point asPoint() {
//    return point;
//  }
//
//  @Override
//  public boolean equals(Object o) {
//    // 'that' is pattern variable
//    if (!(o instanceof ColorPoint that)) {
//      return false;
//    }
//
//    return that.point.equals(point) && that.color.equals(color);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(point, color);
//  }
//}
//
//public class TransitiveTest {
//  public static void main(String[] args) {
////    ColorPoint colorPoint1 = new ColorPoint(10, 10, Color.BLUE);
////    Point point1 = new Point(10, 10);
////    ColorPoint colorPoint2 = new ColorPoint(10, 10, Color.RED);
////    System.out.println(colorPoint1.equals(point1));
////    System.out.println(point1.equals(colorPoint2));
////    System.out.println(colorPoint1.equals(colorPoint2));
//
//    //  Compare using composition
//    ColorPoint colorPoint1 = new ColorPoint(10, 10, Color.BLUE);
//    Point point1 = colorPoint1.asPoint();
//    Point point = new Point(10, 10);
//    ColorPoint colorPoint2 = new ColorPoint(10, 10, Color.BLUE);
//    Point point2 = colorPoint2.asPoint();
//    System.out.println(point1.equals(point));
//    System.out.println(point.equals(point2));
//    System.out.println(point1.equals(point2));
//    System.out.println(colorPoint1.equals(colorPoint2));
//    System.out.println(colorPoint2.equals(colorPoint1));
//  }
//}
