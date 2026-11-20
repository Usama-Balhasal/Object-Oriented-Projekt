package tools;
import commands.History;
import commands.RotateCommand;
import geometry.Point2;
import model.DrawingDocument;
import model.Selection;
import rendering.DrawingContext;
import java.util.List;

/** Labb 4: roterad förhandsvisning utan modelländring. Ett kommando vid musupp.
 * Escape och verktygsbyte avbryter utan att ändra modell eller historik.
 */
public final class RotateTool implements Tool
  {
  private final DrawingDocument document;
  private final History history;
  private final RotationGesture gesture;

  public RotateTool(DrawingDocument document, Selection selection, History history)
    {
    this.document = document;
    this.history = history;
    gesture = new RotationGesture(document, selection);
    }

  @Override
  public void pressed(Point2 point, boolean extend) { gesture.begin(point, extend); }

  @Override
  public void dragged(Point2 point) { gesture.advance(point); }

  @Override
  public void released(Point2 point)
    {
    if (!gesture.active()) return;
    gesture.advance(point);
    var target = gesture.target();
    var pivot = gesture.pivot();
    double radians = gesture.radians();
    cancel();
    if (radians != 0)
      history.execute(new RotateCommand(document, target, radians, pivot));
    }

  @Override
  public void cancel() { gesture.cancel(); }

  @Override
  public void drawPreview(DrawingContext context)
    {
    if (!gesture.active()) return;
    double radians = gesture.radians();
    var pivot = gesture.pivot();
    gesture.target().draw(new DrawingContext()
      {
      @Override
      public void line(Point2 a, Point2 b)
        {
        context.line(a.rotated(radians, pivot), b.rotated(radians, pivot));
        }

      @Override
      public void circle(Point2 center, double radius)
        {
        context.circle(center.rotated(radians, pivot), radius);
        }

      @Override
      public void polygon(List<Point2> vertices)
        {
        context.polygon(vertices.stream().map(p -> p.rotated(radians, pivot)).toList());
        }
      });
    gesture.drawGuide(context);
    }
  }
