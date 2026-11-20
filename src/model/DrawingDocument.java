package model;
import figures.Figure;
import java.util.IdentityHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Dokumentets toppnivåordning. Används på en tråd.
 * Kontrollern anropar changed efter geometriska ändringar i figurerna.
 */
public class DrawingDocument
  {
  private List<Figure> figures;
  private final List<Runnable> listeners = new ArrayList<>();

  public DrawingDocument(List<Figure> figures)
    {
    validate(figures);
    this.figures = List.copyOf(figures);
    }

  public List<Figure> figures()
    {
    return figures;
    }

  public void replace(List<Figure> next)
    {
    validate(next);
    figures = List.copyOf(next);
    changed();
    }

  public void add(Figure figure)
    {
    var next = new ArrayList<>(figures);
    next.add(Objects.requireNonNull(figure));
    replace(next);
    }

  public void addListener(Runnable listener)
    {
    listeners.add(Objects.requireNonNull(listener));
    }

  public void changed()
    {
    for (Runnable listener : List.copyOf(listeners))
      listener.run();
    }
  
  private static void validate(List<Figure> roots)
    {
    var seen = new IdentityHashMap<Figure, Boolean>();
    for (Figure figure : roots)
      visit(figure, seen);
    }

  private static void visit(Figure figure, IdentityHashMap<Figure, Boolean> seen)
    {
    Objects.requireNonNull(figure);
    if (seen.put(figure, Boolean.TRUE) != null)
      throw new IllegalArgumentException("Samma figur förekommer flera gånger.");
    for (Figure child : figure.parts())
      visit(child, seen);
    }
  }
