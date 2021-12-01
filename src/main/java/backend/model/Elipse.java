package backend.model;

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
    public boolean pointBelongs(Point p){
        return Math.pow(centerPoint.getX() - p.getX(),2)/(sMayorAxis*sMayorAxis) + Math.pow(centerPoint.getY() - p.getY(),2)/(sMinorAxis*sMinorAxis) <= 1;
    }//Como saber si un punto esta dentro de una elipse:https://www.i-ciencias.com/pregunta/4300/comprueba-si-un-punto-esta-dentro-de-una-elipse

}
