package figures;

import geometry.Point2;
import rendering.DrawingContext;

/** En kvadrat som återanvänder rektangelns ritning genom komposition.
 * Den interna rektangeln exponeras inte för klienterna.
 */
public final class Square implements Figure
  {
  private final Rectangle shape;

  public Square(Point2 topLeft, double side)
    {
    shape = new Rectangle(topLeft, side, side);
    }

  @Override
  public void draw(DrawingContext context)
    {
    shape.draw(context);
    }

  @Override
  public void move(double dx, double dy)
    {
    shape.move(dx, dy);
    }

  @Override
  public Point2 getCenter()
    {
    return shape.getCenter();
    }

  @Override
  public void rotate(double radians)
    {
	  shape.rotate(radians);
    }

  @Override
  public void rotate(double radians, Point2 pivot)
    {
	  if (pivot == null) {
		  throw new NullPointerException("pivot");
	  }
	  shape.rotate(radians, pivot);
    }
  }
