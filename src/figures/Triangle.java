package figures;

import java.util.Objects;

import geometry.Point2;
import rendering.DrawingContext;

public class Triangle implements Figure {
	private Point2 p1;
	private Point2 p2;
	private Point2 p3;
	
	public Triangle(Point2 p1, Point2 p2, Point2 p3) {
		this.p1 = Objects.requireNonNull(p1, "P1 får inte vara null");
		this.p2 = Objects.requireNonNull(p2, "p2 får inte vara null");
		this.p3 = Objects.requireNonNull(p3, "p3 får inte vara null");
		
		if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
			throw new IllegalArgumentException("Punkterna får inte vara identiska.");
		}
		
		double area = 0.5 * Math.abs((p2.x() - p1.x()) * (p3.y() - p1.y()) - (p3.x() - p1.x()) * (p2.y() - p1.y()));
		if (area == 0) {
			throw new IllegalArgumentException("Punkterna får inte vara kolinjära.");
		}
		
		
	}
	
	@Override
	public void draw(DrawingContext context) {
		context.line(p1, p2);
		context.line(p2, p3);
		context.line(p3, p1);
	}
	
	@Override
	  public void move(double dx, double dy)
	    {
		Point2 nextP1 = p1.translated(dx, dy);
		Point2 nextP2 = p2.translated(dx, dy);
		Point2 nextP3 = p3.translated(dx, dy);
		
		if (!Double.isFinite(nextP1.x()) || !Double.isFinite(nextP1.y()) || !Double.isFinite(nextP2.x()) || !Double.isFinite(nextP2.y()) || !Double.isFinite(nextP3.x()) || !Double.isFinite(nextP3.y())) {
			throw new IllegalArgumentException("Det nya hörnet är inte ändligt.");
		}
		
		Triangle movedTriangle = new Triangle(nextP1, nextP2, nextP3);
		this.p1 = movedTriangle.p1;
		this.p2 = movedTriangle.p2;
		this.p3 = movedTriangle.p3;
	    }
	
	@Override
	public Point2 getCenter() {
		return new Point2(
				(p1.x() + p2.x() + p3.x()) / 3.0, 
				(p1.y() + p2.y() + p3.y()) / 3.0
				);
	}
	
	@Override
	  public void rotate(double radians)
	    {
	    rotate(radians, getCenter());
	    }

	  @Override
	  public void rotate(double radians, Point2 pivot)
	    {
		 if (pivot == null) {
			 throw new NullPointerException("pivot");
		 }
		 p1 = p1.rotated(radians, pivot);
		 p2 = p2.rotated(radians, pivot);
		 p3 = p3.rotated(radians, pivot);
	    }
	
}
