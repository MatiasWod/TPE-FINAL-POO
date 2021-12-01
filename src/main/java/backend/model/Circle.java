package backend.model;

import javafx.scene.canvas.GraphicsContext;

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

    public void redraw (GraphicsContext gc){
        double diameter = this.getRadius() * 2;
        gc.fillOval(this.getCenterPoint().getX() - this.getRadius(), this.getCenterPoint().getY() - this.getRadius(), diameter, diameter);
        gc.strokeOval(this.getCenterPoint().getX() - this.getRadius(), this.getCenterPoint().getY() - this.getRadius(), diameter, diameter);
    }
}
