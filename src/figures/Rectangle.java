package figures;

import geometry.Point2;
import rendering.DrawingContext;
import java.util.Objects;

/** En rektangel med fyra hörn. Konstruktorn skapar den axelparallell. */
public final class Rectangle implements Figure
  {
  private Point2 topLeft;
  private Point2 topRight;
  private Point2 bottomRight;
  private Point2 bottomLeft;

  public Rectangle(Point2 topLeft, double width, double height)
    {
    this.topLeft = Objects.requireNonNull(topLeft, "topLeft");
    if (!Double.isFinite(width) || width <= 0 || !Double.isFinite(height) || height <= 0)
      throw new IllegalArgumentException("Bredd och höjd måste vara positiva och ändliga.");
    topRight = new Point2(topLeft.x() + width, topLeft.y());
    bottomRight = new Point2(topLeft.x() + width, topLeft.y() + height);
    bottomLeft = new Point2(topLeft.x(), topLeft.y() + height);
    }

  @Override
  public void draw(DrawingContext context)
    {
    context.polygon(java.util.List.of(topLeft, topRight, bottomRight, bottomLeft));
    }

  @Override
  public void move(double dx, double dy)
    {
    Point2 a = topLeft.translated(dx, dy);
    Point2 b = topRight.translated(dx, dy);
    Point2 c = bottomRight.translated(dx, dy);
    Point2 d = bottomLeft.translated(dx, dy);
    topLeft = a;
    topRight = b;
    bottomRight = c;
    bottomLeft = d;
    }

  @Override
  public Point2 getCenter()
    {
    return topLeft.midpoint(bottomRight);
    }

  @Override
  public void rotate(double radians)
    {
    rotate(radians, getCenter());
    
    }

  @Override
  public void rotate(double radians, Point2 pivot)
    {
    if (!Double.isFinite(radians)) {
      throw new IllegalArgumentException("Vinkeln måste vara ändlig.");
    }
    Objects.requireNonNull(pivot, "pivot får inte vara null.");

    // 1. Beräkna nya hörn i temporära variabler
    Point2 a = topLeft.rotated(radians, pivot);
    Point2 b = topRight.rotated(radians, pivot);
    Point2 c = bottomRight.rotated(radians, pivot);
    Point2 d = bottomLeft.rotated(radians, pivot);

    // 2. Kontrollera att ingen punkt orsakade koordinatöverflöd
    if (!Double.isFinite(a.x()) || !Double.isFinite(a.y()) ||
        !Double.isFinite(b.x()) || !Double.isFinite(b.y()) ||
        !Double.isFinite(c.x()) || !Double.isFinite(c.y()) ||
        !Double.isFinite(d.x()) || !Double.isFinite(d.y())) {
      throw new IllegalArgumentException("Koordinatöverflöd vid rotation.");
    }

    // 3. Uppdatera fälten först när alla fyra punkter är godkända
    topLeft = a;
    topRight = b;
    bottomRight = c;
    bottomLeft = d;
    }
  }
