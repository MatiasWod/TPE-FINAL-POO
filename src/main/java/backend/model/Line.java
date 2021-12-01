package backend.model;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Figure{
    private Point startPoint,endPoint;

    public Line(Point startPoint,Point endPoint){
        this.startPoint=startPoint;
        this.endPoint=endPoint;
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
    public void redraw(GraphicsContext gc){}

    @Override
    public void move( double diffX, double diffY){}   //FALTA IMPLEMENTAR
}
