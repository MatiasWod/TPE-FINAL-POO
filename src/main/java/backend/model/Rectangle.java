package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight , Color bordeColor, double bordeAncho, Color figureColor) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.bordeColor=bordeColor;
        this.bordeAncho = bordeAncho;
        this.figureColor = figureColor;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public boolean pointBelongs(Point p){
       return p.getX() > topLeft.getX() && p.getX() < bottomRight.getX() && p.getY() > topLeft.getY() && p.getY() < bottomRight.getY();
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Rectangle))
            return false;
        Rectangle that = (Rectangle) o;
        return topLeft.equals(that.getTopLeft()) && bottomRight.equals(that.getBottomRight());
    }

    @Override
    public void redraw(GraphicsContext gc){
        gc.fillRect(this.getTopLeft().getX(), this.getTopLeft().getY(),
                Math.abs(this.getTopLeft().getX() - this.getBottomRight().getX()), Math.abs(this.getTopLeft().getY() - this.getBottomRight().getY()));
        gc.strokeRect(this.getTopLeft().getX(), this.getTopLeft().getY(),
                Math.abs(this.getTopLeft().getX() - this.getBottomRight().getX()), Math.abs(this.getTopLeft().getY() - this.getBottomRight().getY()));
    }

    @Override
    public void move( double diffX, double diffY){
        this.getTopLeft().x += diffX;
        this.getBottomRight().x += diffX;
        this.getTopLeft().y += diffY;
        this.getBottomRight().y += diffY;
    }

    public boolean contained(Rectangle r){
        return r.pointBelongs(topLeft) && r.pointBelongs(bottomRight);
    }
}
