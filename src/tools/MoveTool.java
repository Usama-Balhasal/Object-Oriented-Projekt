package tools;
import figures.Figure;
import commands.History;
import commands.MoveCommand;
import geometry.HitTest;
import geometry.Point2;
import model.DrawingDocument;
import model.Selection;
import rendering.DrawingContext;

/** Förhandsvisning utan ändring i dokumentet. En gest blir ett kommando. */
public final class MoveTool implements Tool
  {
  private final DrawingDocument document;
  private final Selection selection;
  private Figure target;
  private Point2 last;
  private Point2 start;
  private final History history;

  public MoveTool(DrawingDocument document, Selection selection, History history)
    {
    this.document = document;
    this.history = history;
    this.selection = selection;
    }

  @Override
  public void pressed(Point2 point, boolean extend)
    {
    target = null;
    if (!extend)
      selection.clear();
    var figures = document.figures();
    for (int i = figures.size() - 1; i >= 0; i--)
      if (HitTest.hits(figures.get(i), point))
        {
        target = figures.get(i);
        selection.toggle(target);
        break;
        }
    // Shift används enbart för att bygga markering, inte för flyttning.
    if (extend)
      target = null;
    last = point;
    start = point;
    }

  @Override
  public void dragged(Point2 point)
    {
    last = point;
    }

  @Override
  public void released(Point2 point)
    {
    try
      {
      if (target != null)
        {
        double dx = point.x() - start.x();
        double dy = point.y() - start.y();
        if (dx != 0 || dy != 0)
          history.execute(new MoveCommand(document, target, dx, dy));
        }
      }
    finally
      {
      cancel();
      }
    }

  @Override
  public void cancel()
    {
    target = null;
    }

  @Override
  public void drawPreview(DrawingContext context)
    {
    if (target != null)
      {
      double dx = last.x() - start.x();
      double dy = last.y() - start.y();
      target.draw(new DrawingContext()
        {
        @Override
        public void line(Point2 a, Point2 b)
          {
          context.line(a.translated(dx, dy), b.translated(dx, dy));
          }

        @Override
        public void polygon(java.util.List<Point2> vertices)
          {
          context.polygon(vertices.stream().map(p -> p.translated(dx, dy)).toList());
          }

        @Override
        public void circle(Point2 center, double radius)
          {
          context.circle(center.translated(dx, dy), radius);
          }
        });
      }
    }
  }
