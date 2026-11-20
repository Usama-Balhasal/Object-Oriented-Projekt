package tools;
import figures.Circle;
import commands.History;
import commands.AddCommand;
import geometry.Point2;
import model.DrawingDocument;
import rendering.DrawingContext;

/** Centrum vid nedtryckning, radie från avståndet till aktuell musposition. */
public final class CircleTool implements Tool
  {
  private final DrawingDocument document;
  private final History history;
  private Point2 center;
  private Point2 end;

  public CircleTool(DrawingDocument document, History history)
    {
    this.document = document;
    this.history = history;
    }

  @Override
  public void pressed(Point2 point, boolean extend)
    {
    center = point;
    end = point;
    }

  @Override
  public void dragged(Point2 point)
    {
    end = point;
    }

  @Override
  public void released(Point2 point)
    {
    if (center != null)
      {
      double radius = center.distance(point);
      if (radius > 0)
        history.execute(new AddCommand(document, new Circle(center, radius)));
      }
    cancel();
    }

  @Override
  public void cancel()
    {
    center = null;
    end = null;
    }

  @Override
  public void drawPreview(DrawingContext context)
    {
    if (center != null && end != null)
      {
      double radius = center.distance(end);
      if (radius > 0)
        context.circle(center, radius);
      }
    }
  }
