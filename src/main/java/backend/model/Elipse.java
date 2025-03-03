package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Elipse extends Figure{
    private final Point centerPoint;
    private final Point topLeft;
    private final double heightAxis;
    private final double widthAxis;

    public Elipse(Point tL, double heightAxis, double widthAxis, Color bordeColor, double bordeAncho, Color figureColor){
        this.centerPoint = new Point(tL.getX() + widthAxis,Math.abs(tL.getY() + heightAxis));
        this.topLeft = tL;
        this.heightAxis = heightAxis;
        this.widthAxis = widthAxis;
        setBordeAncho(bordeAncho);
        setBordeColor(bordeColor);
        setFigureColor(figureColor);
    }

    @Override
    public String toString(){
        return String.format("Elipse [Centro: %s, RAltura: %.2f , RAncho %.2f]",centerPoint.toString(),heightAxis,widthAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getHeightAxis() {
        return heightAxis;
    }

    public double getWidthAxis() {
        return widthAxis;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Elipse))
            return false;
        Elipse that = (Elipse) o;
        return centerPoint.equals(that.getCenterPoint()) && Double.compare(heightAxis, that.getHeightAxis()) == 0
                && Double.compare(widthAxis, that.getWidthAxis())==0;
    }

    @Override
    public boolean pointBelongs(Point p){
        return ( (Math.pow(p.getX()-centerPoint.getX(),2) / Math.pow(widthAxis,2)) + ( Math.pow(p.getY()-centerPoint.getY(),2) / Math.pow(heightAxis,2)) <= 1 );
    }

    @Override
    public void redraw (GraphicsContext gc){
        gc.fillOval(topLeft.getX(), topLeft.getY(),widthAxis*2 ,heightAxis*2 );
        gc.strokeOval(topLeft.getX(), topLeft.getY(), widthAxis*2, heightAxis*2);
    }

    @Override
    public void move( double diffX , double diffY ){
        centerPoint.move(diffX,diffY);
        topLeft.move(diffX,diffY);
    }

    @Override
    public boolean contained(Rectangle r){
        return r.pointBelongs(new Point(centerPoint.getX() - widthAxis,centerPoint.getY()))
                && r.pointBelongs(new Point(centerPoint.getX() + widthAxis,centerPoint.getY()))
                && r.pointBelongs(new Point(centerPoint.getX(),centerPoint.getY() + heightAxis))
                && r.pointBelongs(new Point(centerPoint.getX(),centerPoint.getY() - heightAxis));
    }
}
