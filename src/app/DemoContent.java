package app;

import figures.Figure;
import figures.Line;
import figures.Rectangle;
import figures.Square;
import figures.Circle;
import figures.CompositeFigure;
import geometry.Point2;
import java.util.List;

public final class DemoContent {

    private DemoContent() {
    }

    public static List<Figure> create() {

        Figure roof = new CompositeFigure(List.of(
            new Line(new Point2(330, 290), new Point2(400, 240)),
            new Line(new Point2(400, 240), new Point2(470, 290))
        ));

        Figure house = new CompositeFigure(List.of(
            new Rectangle(new Point2(330, 290), 140, 110),
            new Rectangle(new Point2(390, 345), 25, 55),
            new Square(new Point2(350, 315), 25),
            roof
        ));

        return List.of(
            new Line(new Point2(60, 60), new Point2(200, 180)),
            new Circle(new Point2(360, 120), 60.0),
            new Square(new Point2(530, 70), 100.0),
            new Rectangle(new Point2(90, 300), 180.0, 90.0),
            house
        );
    }
}