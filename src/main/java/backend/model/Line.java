package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Figure{
    private Point startPoint,endPoint;
    private Color bordeColor;

    public Line(Point startPoint, Point endPoint, Color bordeColor){
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        this.bordeColor = bordeColor;
    }

    public Point getStartPoint(){return startPoint;}

    public Point getEndPoint(){return endPoint;}

    @Override
    public boolean pointBelongs(Point p){
        return (p.getY()-startPoint.getY()==((endPoint.getY()-startPoint.getY())/(endPoint.getX()-startPoint.getX()))*(p.getX()-startPoint.getX()));
        //estoy haciendo (y-y1)=m(x-x1) donde m=(y2-y1)/(x2-x1)
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Line))
            return false;
        Line that = (Line) o;
        return that.getStartPoint().equals(startPoint) && that.getEndPoint().equals(endPoint);
    }

    @Override
    public String toString(){
        return String.format("Linea [ %s , %s ]",startPoint,endPoint);
    }

    @Override
    public void redraw(GraphicsContext gc){
        gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
    }

    @Override
    public void move( double diffX, double diffY){
            startPoint.x += diffX;
            startPoint.y += diffY;
            endPoint.x += diffX;
            endPoint.y += diffY;
    }

    @Override
    public double getBordeAncho() {
        return 0.0D;
    }
    @Override
    public void setBordeAncho(double anchoColor) {}
    @Override
    public Color getFigureColor() {
        return Color.WHITE;
    }
    @Override
    public void setFigureColor(Color figureColor) {}

    @Override
    public boolean contained(Rectangle r){
        return r.pointBelongs(startPoint) && r.pointBelongs(endPoint);
    }
}
