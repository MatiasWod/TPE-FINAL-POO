package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure {

     public abstract Color getBordeColor();
     public abstract void setBordeColor(Color bordeColor);
     public abstract double getBordeAncho();
     public abstract void setBordeAncho(double anchoColor);
     public abstract Color getFigureColor();
     public abstract void setFigureColor(Color figureColor);

     public abstract boolean pointBelongs(Point p);

     public abstract void redraw (GraphicsContext gc);

     public abstract boolean equals(Object o);

     public abstract void move( double diffX, double diffY);

     public abstract String toString();

     public abstract boolean contained(Rectangle r);
}
