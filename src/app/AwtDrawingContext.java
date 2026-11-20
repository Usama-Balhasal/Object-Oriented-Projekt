package app;

import geometry.Point2;
import rendering.DrawingContext;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Objects;

/** Infrastruktur: översätter ritportens operationer till Java2D. */
public final class AwtDrawingContext implements DrawingContext
  {
  private final Graphics2D graphics;

  public AwtDrawingContext(Graphics2D graphics)
    {
    this.graphics = Objects.requireNonNull(graphics);
    }

  @Override
  public void line(Point2 start, Point2 end)
    {
    graphics.draw(new Line2D.Double(start.x(), start.y(), end.x(), end.y()));
    }

  @Override
  public void circle(Point2 center, double radius)
    {
    graphics.draw(new Ellipse2D.Double(center.x() - radius, center.y() - radius,
        2 * radius, 2 * radius));
    }
  @Override
  public void polygon(java.util.List<Point2> vertices)
    {
    var points = java.util.List.copyOf(vertices);
    if (points.size() < 3)
      throw new IllegalArgumentException("En polygon behöver minst tre hörn.");
    var path = new java.awt.geom.Path2D.Double();
    path.moveTo(points.get(0).x(), points.get(0).y());
    for (int i = 1; i < points.size(); i++)
      path.lineTo(points.get(i).x(), points.get(i).y());
    path.closePath();
    graphics.draw(path);
    }
  }
