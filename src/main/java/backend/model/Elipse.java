package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Elipse extends Figure{
    private Point centerPoint;
    private Point topLeft;
    private double heightAxis;
    private double widthAxis;

    public Elipse(Point tL, double heightAxis, double widthAxis, Color bordeColor, double bordeAncho, Color figureColor){
        this.centerPoint = new Point(tL.getX() + widthAxis,Math.abs(tL.getY() + heightAxis));
        this.topLeft = tL;
        this.heightAxis = heightAxis;
        this.widthAxis = widthAxis;
        System.out.printf("%s",this);
        this.bordeColor=bordeColor;
        this.bordeAncho = bordeAncho;
        this.figureColor = figureColor;
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
    }//Como saber si un punto esta dentro de una elipse:https://www.i-ciencias.com/pregunta/4300/comprueba-si-un-punto-esta-dentro-de-una-elipse

    @Override
    public void redraw (GraphicsContext gc){
        gc.fillOval(topLeft.getX(), topLeft.getY(),widthAxis*2 ,heightAxis*2 );
        gc.strokeOval(topLeft.getX(), topLeft.getY(), widthAxis*2, heightAxis*2);
    }

    @Override
    public void move( double diffX , double diffY ){
        centerPoint.x += diffX;
        centerPoint.y += diffY;
        topLeft.x+=diffX;
        topLeft.y+=diffY;
    }

    @Override
    public boolean contained(Rectangle r){
        return r.pointBelongs(new Point(centerPoint.getX() - widthAxis,centerPoint.getY()))
                && r.pointBelongs(new Point(centerPoint.getX() + widthAxis,centerPoint.getY()))
                && r.pointBelongs(new Point(centerPoint.getX(),centerPoint.getY() + heightAxis))
                && r.pointBelongs(new Point(centerPoint.getX(),centerPoint.getY() - heightAxis));
    }
}
