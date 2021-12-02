package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Elipse {

    public Circle(Point centerPoint, double radius, Color bordeColor, double bordeAncho, Color figureColor) {
        super(new Point(centerPoint.getX()- radius, centerPoint.getY()+radius ),radius,radius,bordeColor,bordeAncho,figureColor);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), getHeightAxis());
    }

    public double getRadius() {
        return getHeightAxis();
    }

    @Override
    public void redraw (GraphicsContext gc){
        double diameter = this.getRadius() * 2;
        gc.fillOval(this.getCenterPoint().getX() - this.getRadius(), this.getCenterPoint().getY() - this.getRadius(), diameter, diameter);
        gc.strokeOval(this.getCenterPoint().getX() - this.getRadius(), this.getCenterPoint().getY() - this.getRadius(), diameter, diameter);
    }

}
