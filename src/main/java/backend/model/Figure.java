package backend.model;

import javafx.scene.canvas.GraphicsContext;

public abstract class Figure {

     public abstract boolean pointBelongs(Point p);

     public abstract void redraw (GraphicsContext gc);

     public abstract boolean equals(Object o);

     public abstract void move( double diffX, double diffY);
}
