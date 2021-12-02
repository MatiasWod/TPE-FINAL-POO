package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Square extends Rectangle{

    public Square(Point topLeft,Point bottomRight){
        super(topLeft,bottomRight);
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    @Override
    public void redraw (GraphicsContext gc){
        super.redraw(gc);
    }

    @Override
    public boolean pointBelongs(Point p){
        return p.getX() > getTopLeft().getX() && p.getX() < getBottomRight().getX() &&
                p.getY() > getTopLeft().getY() && p.getY() < getBottomRight().getY();
    }

}
