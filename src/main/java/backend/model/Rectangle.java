package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
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
       return p.getX() > topLeft.getX() && p.getX() < bottomRight.getX() &&
                p.getY() > topLeft.getY() && p.getY() < bottomRight.getY();
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
    };
}
