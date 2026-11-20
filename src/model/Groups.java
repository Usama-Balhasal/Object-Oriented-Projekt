package model;
import figures.CompositeFigure;
import figures.Figure;
import java.util.ArrayList;
import java.util.List;

/** Gruppoperationer. Identitet och barnens ordning bevaras. */
public final class Groups
  {
  /** Grupperar minst två markerade toppnivåfigurer, annars sker ingen ändring.
   * Barnen återanvänds i dokumentets ritordning. Gruppen ersätter de valda
   * figurerna vid den översta valda figurens plats och blir ensam markerad.
   * Koordinater ändras inte. Dokumentets replace validerar den nya strukturen.
   */
	public static void group(DrawingDocument document, Selection selection) {
	    List<Figure> selectedFigures = new ArrayList<>();
	    for (Figure figure : document.figures()) {
	        if (selection.contains(figure)) {
	            selectedFigures.add(figure);
	        }
	    }

	    if (selectedFigures.size() < 2) {
	        return;
	    }

	    CompositeFigure group = new CompositeFigure(new ArrayList<>(selectedFigures));

	    Figure topSelected = selectedFigures.get(selectedFigures.size() - 1);

	    List<Figure> next = new ArrayList<>();
	    for (Figure figure : document.figures()) {
	        if (figure == topSelected) {
	            next.add(group);
	        } else if (!selection.contains(figure)) {
	            next.add(figure);
	        }
	    }

	    document.replace(next);
	    selection.clear();
	    selection.toggle(group);
	}

  /** Ersätter varje markerad toppnivåfigur {@linkplain Figure} med dess direkta barn i ritordning.
   * En figur utan barn, även en tom sammansättning, blir kvar på samma plats.
   * Nästlade grupper förblir grupper. Identitet och koordinater bevaras.
   * Ersättningsfigurerna och markerade figurer utan barn blir markerade;
   * övriga figurer ligger kvar. Figuren och dess barnlista ändras inte.
   * Dokumentet ändras med replace först när hela nästa toppnivålista är byggd.
   * 
   * @param document Det är värde
   * @param selection Det är väre
   */
	public static void ungroup(DrawingDocument document, Selection selection)
    {
    List<Figure> next = new ArrayList<>();
    List<Figure> newSelection = new ArrayList<>();

    for (Figure figure : document.figures()) {
      if (selection.contains(figure) && !figure.parts().isEmpty()) {
        next.addAll(figure.parts());
        newSelection.addAll(figure.parts());
      } else {
        next.add(figure);
        if (selection.contains(figure)) {
          newSelection.add(figure);
        }
      }
    }

    document.replace(next);

    selection.clear();
    for (Figure figure : newSelection) {
      selection.toggle(figure);
    }
  }
  }
