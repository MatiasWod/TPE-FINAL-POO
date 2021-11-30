package backend.model;

public class Circle extends Elipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint,radius,radius);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getMayorAxis());
    }

    public double getRadius() {
        return getMayorAxis();
    }

}
