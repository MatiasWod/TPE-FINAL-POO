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
}
