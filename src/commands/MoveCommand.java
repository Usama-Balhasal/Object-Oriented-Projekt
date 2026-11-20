package commands;
import figures.Figure;
import model.DrawingDocument;

/** Flyttning i en sekventiell historik. Invers förskjutning används i labben;
 * flyttal kan ge avrundningsskillnader. Exakt geometriskt före-/eftertillstånd
 * är ett möjligt refaktoreringssteg i projektet, inte del av Figure-kontraktet.
 */
public final class MoveCommand implements Command
  {
  private final DrawingDocument document;
  private final Figure figure;
  private final double dx;
  private final double dy;

  public MoveCommand(DrawingDocument document, Figure figure, double dx, double dy)
    {
    if (!Double.isFinite(dx) || !Double.isFinite(dy))
      throw new IllegalArgumentException("Ogiltig förskjutning.");
    this.document = document;
    this.figure = figure;
    this.dx = dx;
    this.dy = dy;
    }

  @Override
  public void execute()
    {
	      figure.move(dx, dy);
	      document.changed();
    }

  @Override
  public void undo()
    {
		figure.move(-dx, -dy);
		document.changed();
    }
  }
