package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure implements Colorable,Movable {

     private Color bordeColor;
     private double bordeAncho;
     private Color figureColor;

     public abstract boolean pointBelongs(Point p);

     public abstract void redraw (GraphicsContext gc);

     public abstract boolean equals(Object o);

     public abstract void move( double diffX, double diffY);

     public abstract String toString();

     public abstract boolean contained(Rectangle r);


     @Override
     public Color getBordeColor() {
          return bordeColor;
     }
     @Override
     public void setBordeColor(Color bordeColor) {
          this.bordeColor = bordeColor;
     }
     @Override
     public double getBordeAncho() {
          return bordeAncho;
     }
     @Override
     public void setBordeAncho(double anchoColor) {
          this.bordeAncho = anchoColor;
     }
     @Override
     public Color getFigureColor() {
          return figureColor;
     }
     @Override
     public void setFigureColor(Color figureColor) {
          this.figureColor = figureColor;
     }
}
