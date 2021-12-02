package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Elipse extends Figure{
    private Point centerPoint;
    private Point topLeft;
    private double heightAxis;
    private double widthAxis;

    public Elipse(Point tL, double heightAxis,double widthAxis){
        this.centerPoint = new Point(tL.getX() + widthAxis,tL.getY() - heightAxis);
        System.out.println(centerPoint.toString());
        this.topLeft = tL;
        this.heightAxis = heightAxis;
        this.widthAxis = widthAxis;
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
        double mayorAxis = Math.max(widthAxis, heightAxis);
        double minorAxis = Math.min(widthAxis,heightAxis);
        return Math.pow(centerPoint.getX() - p.getX(),2)/(mayorAxis*mayorAxis) + Math.pow(centerPoint.getY() - p.getY(),2)/(minorAxis*minorAxis) <= 1;
    }//Como saber si un punto esta dentro de una elipse:https://www.i-ciencias.com/pregunta/4300/comprueba-si-un-punto-esta-dentro-de-una-elipse


    @Override
    public void redraw (GraphicsContext gc){
        gc.fillOval(topLeft.getX(), topLeft.getY(),widthAxis ,heightAxis );
        gc.strokeOval(topLeft.getX(), topLeft.getY(), widthAxis, heightAxis);
    }


    @Override
    public void move(double diffX , double diffY ){}
    //FALTA IMPLEMENTAR
}
