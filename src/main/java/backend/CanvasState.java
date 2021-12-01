package backend;

import backend.model.Figure;

import java.util.ArrayList;
import java.util.List;

public class CanvasState {

    private final ArrayList<Figure> list = new ArrayList<>();

    public void toFront(Figure figure){
        list.remove(figure);
        list.add(figure);
    }

    public void toBack(Figure figure){
        list.remove(figure);
        list.add(0,figure);
    }

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public Iterable<Figure> figures() {
        return new ArrayList<>(list);
    }

}
