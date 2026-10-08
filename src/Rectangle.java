public class Rectangle {

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public Rectangle(Point p1, Point p2, Point p3, Point p4) {

        minX = Math.min(Math.min(p1.getX(), p2.getX()),
                Math.min(p3.getX(), p4.getX()));

        maxX = Math.max(Math.max(p1.getX(), p2.getX()),
                Math.max(p3.getX(), p4.getX()));

        minY = Math.min(Math.min(p1.getY(), p2.getY()),
                Math.min(p3.getY(), p4.getY()));

        maxY = Math.max(Math.max(p1.getY(), p2.getY()),
                Math.max(p3.getY(), p4.getY()));
    }

    public boolean contains(Point point) {
        return point.getX() >= minX
                && point.getX() <= maxX
                && point.getY() >= minY
                && point.getY() <= maxY;
    }
}