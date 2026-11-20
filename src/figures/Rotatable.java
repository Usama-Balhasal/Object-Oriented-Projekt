package figures;
import geometry.Point2;
/** Förmågan att rotera det befintliga objektet. Inga defaultimplementationer. */
public interface Rotatable
  {
  /** Roterar kring objektets getCenter(). */
  void rotate(double radians);
  /** Roterar kring pivot. Positiv vinkel är medurs i skärmens y-nedåt-system.
   * Vinkeln ska vara ändlig, pivot får inte vara null och alla nya koordinater
   * ska vara ändliga. Fel avvisas före ändring (IllegalArgumentException,
   * respektive NullPointerException). Form, storlek och identitet bevaras
   * inom flyttalens precision. Noll vinkel är tillåten.
   */
  void rotate(double radians, Point2 pivot);
  }
