package geometry;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
/** Numeriskt stöd: medelvärde utan överflöd i summan. Ingen figurrepresentation. */
public final class Centers
  {
  private Centers() { }
  public static Point2 average(List<Point2> points)
    {
    if (points.isEmpty()) return new Point2(0, 0);
    BigDecimal x = BigDecimal.ZERO;
    BigDecimal y = BigDecimal.ZERO;
    for (Point2 p : points)
      {
      x = x.add(BigDecimal.valueOf(p.x()));
      y = y.add(BigDecimal.valueOf(p.y()));
      }
    var n = BigDecimal.valueOf(points.size());
    return new Point2(x.divide(n, MathContext.DECIMAL128).doubleValue(),
        y.divide(n, MathContext.DECIMAL128).doubleValue());
    }
  }
