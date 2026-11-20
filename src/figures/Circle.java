package figures;

import geometry.Point2;
import rendering.DrawingContext;
import java.util.Objects;

/** En cirkel med centrum och positiv, ändlig radie. */
public final class Circle implements Figure
  {
  private Point2 center;
  private final double radius;

  public Circle(Point2 center, double radius)
    {
    this.center = Objects.requireNonNull(center, "center");
    if (!Double.isFinite(radius) || radius <= 0)
      throw new IllegalArgumentException("Radien måste vara positiv och ändlig.");
    this.radius = radius;
    }

  @Override
  public void draw(DrawingContext context)
    {
    context.circle(center, radius);
    }

  @Override
  public void move(double dx, double dy)
    {
    center = center.translated(dx, dy);
    }

  @Override
  public Point2 getCenter()
    {
    return center;
    }

  @Override
  public void rotate(double radians)
    {
    rotate(radians, getCenter());
    }

  @Override
  public void rotate(double radians, Point2 pivot) {
      if (!Double.isFinite(radians)) {
          throw new IllegalArgumentException("Vinkeln måste vara ändlig");
      }
      Objects.requireNonNull(pivot, "pivot");

      Point2 nextCenter = center.rotated(radians, pivot);
      if (!Double.isFinite(nextCenter.x()) || !Double.isFinite(nextCenter.y())) {
          throw new IllegalArgumentException("Koordinatöverflöd");
      }

      this.center = nextCenter;
  }
  }
