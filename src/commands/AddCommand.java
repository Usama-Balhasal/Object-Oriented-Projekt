package commands;
import figures.Figure;
import model.DrawingDocument;
import java.util.ArrayList;
import java.util.List;

/** Färdigt exempel: samma objekt återinförs vid redo. */
public final class AddCommand implements Command
  {
  private final DrawingDocument document;
  private final List<Figure> before;
  private final List<Figure> after;

  public AddCommand(DrawingDocument document, Figure figure)
    {
    this.document = document;
    before = document.figures();
    var next = new ArrayList<>(before);
    next.add(figure);
    after = List.copyOf(next);
    }

  @Override
  public void execute()
    {
    document.replace(after);
    }

  @Override
  public void undo()
    {
    document.replace(before);
    }
  }
