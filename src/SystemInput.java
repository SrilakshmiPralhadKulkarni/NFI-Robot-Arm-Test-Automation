import java.util.List;

public class SystemInput {

    private Rectangle rectangle;
    private List<Point> points;

    public SystemInput(Rectangle rectangle, List<Point> points) {
        this.rectangle = rectangle;
        this.points = points;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public List<Point> getPoints() {
        return points;
    }
}