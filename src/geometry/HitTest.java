package geometry;
import figures.Figure;
import rendering.DrawingContext;

/** Tillhandahållet konturträfftest. Cirkelns insida räknas också som träff.
 * Rektanglar träffas på kanten; detta är en uttalad förenkling.
 */
public final class HitTest implements DrawingContext
  {
  private final Point2 point;
  private final double tolerance;
  private boolean hit;

  private HitTest(Point2 point, double tolerance)
    {
    this.point = point;
    this.tolerance = tolerance;
    }

  public static boolean hits(Figure figure, Point2 point)
    {
    var test = new HitTest(point, 6);
    figure.draw(test);
    return test.hit;
    }

  @Override
  public void circle(Point2 center, double radius)
    {
    hit |= point.distance(center) <= radius + tolerance;
    }

  @Override
  public void line(Point2 start, Point2 end)
    {
    double dx = end.x() - start.x();
    double dy = end.y() - start.y();
    double length2 = dx * dx + dy * dy;
    double t = length2 == 0 ? 0 : Math.max(0, Math.min(1,
        ((point.x() - start.x()) * dx + (point.y() - start.y()) * dy) / length2));
    hit |= point.distance(new Point2(start.x() + t * dx, start.y() + t * dy)) <= tolerance;
    }
  @Override
  public void polygon(java.util.List<Point2> vertices)
    {
    var points = java.util.List.copyOf(vertices);
    if (points.size() < 3)
      throw new IllegalArgumentException("En polygon behöver minst tre hörn.");
    for (int i = 0; i < points.size(); i++)
      line(points.get(i), points.get((i + 1) % points.size()));
    }
  }
