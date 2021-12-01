package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Elipse extends Figure{
    private Point centerPoint;
    private double sMayorAxis;
    private double sMinorAxis;

    public Elipse(Point cp, double mayorA,double minorA){
        this.centerPoint = cp;
        this.sMayorAxis = mayorA;
        this.sMinorAxis = minorA;
    }

    @Override
    public String toString(){
        return String.format("Elipse [Centro: %s, RMayor: %.2f , RMenor %.2f]",centerPoint.toString(),sMayorAxis,sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getMayorAxis() {
        return sMayorAxis;
    }

    public double getMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Elipse))
            return false;
        Elipse that = (Elipse) o;
        return centerPoint.equals(that.getCenterPoint()) && Double.compare(sMayorAxis, that.getMayorAxis()) == 0
                && Double.compare(sMinorAxis, that.getMinorAxis())==0;
    }

    @Override
    public boolean pointBelongs(Point p){
        return Math.pow(centerPoint.getX() - p.getX(),2)/(sMayorAxis*sMayorAxis) + Math.pow(centerPoint.getY() - p.getY(),2)/(sMinorAxis*sMinorAxis) <= 1;
    }//Como saber si un punto esta dentro de una elipse:https://www.i-ciencias.com/pregunta/4300/comprueba-si-un-punto-esta-dentro-de-una-elipse


    public void redraw (GraphicsContext gc){}
    //FALTA IMPLEMENTAR
}
