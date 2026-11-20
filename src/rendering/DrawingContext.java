package rendering;

import geometry.Point2;

/** Ritport i modellkoordinater. Ingen metod ändrar figurernas tillstånd.
 * Plattformen bestämmer färg och linjebredd i denna första version.
 */
public interface DrawingContext
  {
  /** Ritar sträckan från start till end. */
  void line(Point2 start, Point2 end);

  /** Ritar en cirkelkontur med givet centrum och positiv, ändlig radie. */
  void circle(Point2 center, double radius);
  /** Ritar en sluten polygonkontur utan fyllning. Minst tre icke-null-hörn i kantordning.
   * Sista hörnet kopplas till första; upprepa inte första hörnet sist.
   * Anropet ändrar aldrig listan eller punkterna. Riktning är valfri.
   */
  void polygon(java.util.List<Point2> vertices);
  }
