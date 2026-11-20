package figures;
import geometry.Point2;
import rendering.DrawingContext;
import java.util.List;

/** Sammansättning i världskoordinater. Strukturen är fast efter konstruktion;
 * barnens geometri kan ändras. Inga add/remove-metoder på Figure.
 */
public final class CompositeFigure implements Figure
  {
  private final List<Figure> parts;

  public CompositeFigure(List<Figure> parts)
    {
    this.parts = List.copyOf(parts);
    }

  @Override
  public List<Figure> parts()
    {
    return parts;
    }

  @Override
  public void draw(DrawingContext context)
    {
		for (Figure part : parts) {
			part.draw(context);
		}
    }

  @Override
  public void move(double dx, double dy) {
      int i = 0;
      try {
          for (; i < parts.size(); i++) {
              parts.get(i).move(dx, dy);
          }
      } catch (RuntimeException e) {	
          for (int j = 0; j < i; j++) {
              parts.get(j).move(-dx, -dy);
          }
          throw e;
      }
  }

  @Override
  public Point2 getCenter()
    {
	  if (parts.isEmpty()) {
		  return new Point2(0, 0);
	  }
      double sumX = 0;
      double sumY = 0;
		for (Figure part : parts) {
			Point2 center = part.getCenter();
			sumX += center.x();
			sumY += center.y();
		}
		int count = parts.size();
		return new Point2(sumX / count, sumY / count);
    }

  @Override
  public void rotate(double radians)
    {
    if (!Double.isFinite(radians)) {
        throw new IllegalArgumentException("vinkeln måste vara ändlig");
    }
    rotate(radians, getCenter());
    }

  @Override
  public void rotate(double radians, Point2 pivot)
    {
    if (!Double.isFinite(radians)) {
        throw new IllegalArgumentException("vinkeln måste vara ändlig");
    }
    if (pivot == null) {
        throw new NullPointerException("pivot får inte vara null");
    }

    int i = 0;
    try {
        for (; i < parts.size(); i++) {
            parts.get(i).rotate(radians, pivot);
        }
    } catch (RuntimeException e) {
        for (int j = 0; j < i; j++) {
            parts.get(j).rotate(-radians, pivot);
        }
        throw e;
    }
    }
  }
