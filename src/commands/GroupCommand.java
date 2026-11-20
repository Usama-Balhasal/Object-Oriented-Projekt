package commands;
import figures.Figure;
import model.DrawingDocument;
import model.Groups;
import model.Selection;
import java.util.List;

/** LABB4: implementera execute och undo. Fält, konstruktor och select är givna.
 * Fångar ordning och identitet, inte djupa kopior.
 * Bara historiken får ändra dokumentet medan kommandot är aktivt.
 */
public final class GroupCommand implements Command
  {
  private final DrawingDocument document;
  private final Selection selection;
  private final List<Figure> before;
  private final List<Figure> selectedBefore;
  private List<Figure> after;
  private List<Figure> selectedAfter;
  private final boolean split;

  public GroupCommand(DrawingDocument document, Selection selection, boolean split)
    {
    this.document = document;
    this.selection = selection;
    this.split = split;
    before = document.figures();
    selectedBefore = selection.figures();
    }

  private void select(List<Figure> figures)
    {
    selection.clear();
    for (Figure figure : figures)
      selection.toggle(figure);
    }

  @Override
  public void execute()
    {
		  
	  if (after == null) {
		  if (split) {
			  Groups.ungroup(document, selection);
	      } else {
	    	  Groups.group(document, selection);
	      }
		  after = document.figures();
		  selectedAfter = selection.figures();
	}
	  else{
	    document.replace(after);
	    select(selectedAfter);
	    }
    }

  @Override
  public void undo()
    {
	  document.replace(before);
	  select(selectedBefore);
    }
  }
