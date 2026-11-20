package tools;
import figures.CompositeFigure;
import figures.Figure;
import geometry.HitTest;
import geometry.Point2;
import model.DrawingDocument;
import model.Selection;
import rendering.DrawingContext;

/** Gemensam gesttolkning i labb 3 och 4. Ändrar markering, men aldrig figurgeometri.
 * State: IDLE --pressed utanför pivot--> DRAGGING --cancel/released--> IDLE.
 * Vinkeln mäts med atan2; själva punktrotationen finns enbart i Point2.rotated.
 */
public final class RotationGesture
  {
  private enum State { IDLE, DRAGGING }
  private final DrawingDocument document;
  private final Selection selection;
  private State state = State.IDLE;
  private Figure target;
  private Point2 pivot;
  private Point2 pointer;
  private double lastAngle;
  private double radians;

  public RotationGesture(DrawingDocument document, Selection selection)
    {
    this.document = document;
    this.selection = selection;
    }

  public void begin(Point2 point, boolean extend)
    {
    cancel();
    Figure hit = null;
    var roots = document.figures();
    for (int i = roots.size() - 1; i >= 0; i--)
      if (HitTest.hits(roots.get(i), point))
        {
        hit = roots.get(i);
        break;
        }
    if (extend)
      {
      if (hit != null) selection.toggle(hit);
      return;
      }
    // Ett klick på en redan markerad figur behåller flermarkeringen.
    // Tom yta kan användas som handtag när något redan är markerat.
    if (hit != null && !selection.contains(hit))
      {
      selection.clear();
      selection.toggle(hit);
      }
    var selected = roots.stream()
        .filter(selection::contains).toList();
    if (selected.isEmpty()) return;
    target = selected.size() == 1 ? selected.get(0) : new CompositeFigure(selected);
    pivot = target.getCenter();
    if (atPivot(point))
      {
      cancel();
      return;
      }
    pointer = point;
    lastAngle = angle(point);
    radians = 0;
    state = State.DRAGGING;
    }

  /** Uppdatera totalvinkeln och returnera förändringen sedan föregående muspunkt. */
  public double advance(Point2 point)
    {
    if (!active()) return 0;
    pointer = point;
    // Vinkeln är odefinierad exakt i pivoten. Behåll föregående riktning där.
    if (atPivot(point)) return 0;
    double next = angle(point);
    // Undvik ett nästan helt varvs hopp över atan2:s gräns vid +/- pi.
    double delta = Math.IEEEremainder(next - lastAngle, 2 * Math.PI);
    lastAngle = next;
    radians += delta;
    return delta;
    }

  private boolean atPivot(Point2 point)
    {
    return point.x() == pivot.x() && point.y() == pivot.y();
    }

  private double angle(Point2 point)
    {
    return Math.atan2(point.y() - pivot.y(), point.x() - pivot.x());
    }

  public boolean active() { return state == State.DRAGGING; }
  public Figure target() { return target; }
  public Point2 pivot() { return pivot; }
  public double radians() { return radians; }

  public void cancel()
    {
    state = State.IDLE;
    target = null;
    pivot = null;
    pointer = null;
    radians = 0;
    }

  public void drawGuide(DrawingContext context)
    {
    if (!active()) return;
    context.line(pivot.translated(-5, 0), pivot.translated(5, 0));
    context.line(pivot.translated(0, -5), pivot.translated(0, 5));
    context.line(pivot, pointer);
    }
  }
