package figures;
import geometry.Point2;
import java.util.List;
/** Lab3 utökar figurkontraktet med rotation och gemensam åtkomst till barn. */
public interface Figure extends Drawable, Movable, Rotatable
  {
  Point2 getCenter();

  /** Returnerar direkta barn i ritordning utan att ändra figuren.
   * <p>
   * Ett löv har inga barn. Resultatlistans struktur kan inte ändras av klienten,
   * men barnobjekten behåller sin identitet och kan ändra geometri.
   * <pre>
   * List a = kkkk
   * </pre>
   * {@code List<Figure>}
   */
  default List<Figure> parts()
    {
    return List.of();
    }
  }
