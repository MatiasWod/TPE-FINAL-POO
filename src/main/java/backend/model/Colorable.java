package backend.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Colorable{

      Color getBordeColor();
      void setBordeColor(Color bordeColor);
      double getBordeAncho();
      void setBordeAncho(double anchoColor);
      Color getFigureColor();
      void setFigureColor(Color figureColor);

}
