package geometry;

/** Oföränderligt koordinatvärde, y växer nedåt på ritytan. */
public record Point2(double x, double y)
  {
  public Point2
    {
    if (!Double.isFinite(x) || !Double.isFinite(y))
      throw new IllegalArgumentException("Koordinater måste vara ändliga.");
    }

  /** Euklidiskt avstånd; punkterna ändras inte. Null avvisas. */
  public double distance(Point2 other)
    {
    java.util.Objects.requireNonNull(other, "other");
    return Math.hypot(x - other.x, y - other.y);
    }

  public Point2 translated(double dx, double dy)
    {
    return new Point2(x + dx, y + dy);
    }

  /** Härlett mittvärde utan överflöd vid addition av stora koordinater. */
  public Point2 midpoint(Point2 other)
    {
    java.util.Objects.requireNonNull(other, "other");
    return new Point2(x / 2 + other.x / 2, y / 2 + other.y / 2);
    }

  /** Tillhandahållen punktrotation; ändrar aldrig den gamla punkten. */
  public Point2 rotated(double radians, Point2 pivot)
    {
    java.util.Objects.requireNonNull(pivot, "pivot");
    if (!Double.isFinite(radians))
      throw new IllegalArgumentException("Vinkeln måste vara ändlig.");
    if (radians == 0 || equals(pivot))
      return this;
    double dx = x - pivot.x;
    double dy = y - pivot.y;
    if (!Double.isFinite(dx) || !Double.isFinite(dy))
      throw new IllegalArgumentException("För stora relativa koordinater.");
    double cosine = Math.cos(radians);
    double sine = Math.sin(radians);
    return new Point2(pivot.x + dx * cosine - dy * sine,
        pivot.y + dx * sine + dy * cosine);
    }
  }
