package model;
import figures.Figure;
import java.util.ArrayList;
import java.util.List;

/** Tillfällig markering, utanför dokumentet. Jämför identitet. */
public final class Selection
  {
  private final List<Figure> selected = new ArrayList<>();

  public List<Figure> figures()
    {
    return List.copyOf(selected);
    }

  /** Sant om just detta figurobjekt är markerat; jämför identitet, inte equals. */
  public boolean contains(Figure figure)
    {
    for (Figure selectedFigure : selected)
      if (selectedFigure == figure)
        return true;
    return false;
    }

  public void clear()
    {
    selected.clear();
    }

  public void toggle(Figure figure)
    {
    for (int i = 0; i < selected.size(); i++)
      if (selected.get(i) == figure)
        {
        selected.remove(i);
        return;
        }
    selected.add(figure);
    }
  }
