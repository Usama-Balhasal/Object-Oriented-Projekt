package commands;
import figures.Figure;
import geometry.Point2;
import model.DrawingDocument;
import java.util.Objects;
/** En rotation kring en fast pivot. Undo använder motsatt vinkel och samma pivot.
 * Historiken är sekventiell; flyttalsavrundning kan förekomma.
 */
public final class RotateCommand implements Command
  {
  private final DrawingDocument document;
  private final Figure figure;
  private final double radians;
  private final Point2 pivot;

  public RotateCommand(DrawingDocument document, Figure figure, double radians)
    {
    this(document, figure, radians, Objects.requireNonNull(figure, "figure").getCenter());
    }

  public RotateCommand(DrawingDocument document, Figure figure, double radians, Point2 pivot)
    {
    this.document = Objects.requireNonNull(document, "document");
    this.figure = Objects.requireNonNull(figure, "figure");
    this.pivot = Objects.requireNonNull(pivot, "pivot");
    if (!Double.isFinite(radians)) throw new IllegalArgumentException("Ogiltig vinkel.");
    this.radians = radians;
    }

  @Override
  public void execute()
    {
	      figure.rotate(radians, pivot);
	      document.changed();
    }

  @Override
  public void undo()
	{
		figure.rotate(-radians, pivot);
		document.changed();
    }
  }
