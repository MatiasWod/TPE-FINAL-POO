package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class  Square extends Rectangle{

    public Square(Point topLeft, Point bottomRight, Color bordeColor, double bordeAncho, Color figureColor){
        super(topLeft,bottomRight,bordeColor,bordeAncho,figureColor);
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }
}
