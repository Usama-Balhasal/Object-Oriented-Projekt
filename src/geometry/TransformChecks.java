package geometry;
import figures.Drawable;
import rendering.DrawingContext;
/** Tillhandahållen förkontroll av hela figurens ritgeometri innan något barn ändras.
 * Förutsätter rena draw-metoder och att figurerna följer transformkontrakten.
 */
public final class TransformChecks
  {
  private TransformChecks() { }
  public static void rotation(Drawable figure, double radians, Point2 pivot)
    {
    java.util.Objects.requireNonNull(pivot, "pivot");
    if (!Double.isFinite(radians)) throw new IllegalArgumentException("Ogiltig vinkel.");
    check(figure, p -> p.rotated(radians, pivot));
    }
  public static void translation(Drawable figure, double dx, double dy)
    {
    if (!Double.isFinite(dx) || !Double.isFinite(dy))
      throw new IllegalArgumentException("Ogiltig förskjutning.");
    check(figure, p -> p.translated(dx, dy));
    }
  private static void check(Drawable figure, java.util.function.UnaryOperator<Point2> transform)
    {
    figure.draw(new DrawingContext()
      {
      public void line(Point2 start, Point2 end) { transform.apply(start); transform.apply(end); }
      public void circle(Point2 center, double radius) { transform.apply(center); }
      public void polygon(java.util.List<Point2> vertices)
        {
        var points = java.util.List.copyOf(vertices);
        if (points.size() < 3) throw new IllegalArgumentException("Minst tre hörn krävs.");
        points.forEach(transform::apply);
        }
      });
    }
  }
