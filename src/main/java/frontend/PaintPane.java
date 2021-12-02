package frontend;

import backend.CanvasState;
import backend.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class PaintPane extends BorderPane {

	// BackEnd
	private CanvasState canvasState;

	// Canvas y relacionados
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	// Botones Barra Izquierda
	private ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private ToggleButton circleButton = new ToggleButton("Círculo");
	private ToggleButton elipseButton = new ToggleButton("Elipse");
	private ToggleButton squareButton = new ToggleButton("Cuadrado");
	private ToggleButton lineButton = new ToggleButton("Linea");

	private Button fondoButton = new Button("Al Fondo");
	private Button frenteButton = new Button("Al Frente");


	private Slider bordeSlider = new Slider(0, 50, 1);
	private ColorPicker bordeColor = new ColorPicker(Color.BLACK);
	private ColorPicker rellenoColor = new ColorPicker(Color.YELLOW);

	//private Text texto = new Text("Borde");		Para agregarlo al coso: buttonsBox.getChildren().add(texto);
	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private List<Figure> selectedFigure = new ArrayList<>();

	// StatusBar
	private StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toggleArr = {selectionButton, rectangleButton, circleButton,elipseButton,squareButton,lineButton};
		Control[] toolsArr = {selectionButton, rectangleButton, circleButton,elipseButton,squareButton,lineButton,fondoButton,
				frenteButton,bordeColor,bordeSlider,rellenoColor};
		ToggleGroup tools = new ToggleGroup();
		for (Control tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setCursor(Cursor.HAND);
		}
		for(ToggleButton tool : toggleArr){
			tool.setToggleGroup(tools);
		}

		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		bordeSlider.setShowTickLabels(true); //Cambios del slider
		bordeSlider.setShowTickMarks(true);
		bordeSlider.setMajorTickUnit(50);
		bordeSlider.setMinorTickCount(5);
		bordeSlider.setBlockIncrement(10);
		bordeSlider.setMajorTickUnit(26);

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});
		canvas.setOnMouseReleased(event -> {
			boolean flag=true;
			Point endPoint = new Point(event.getX(), event.getY());
			if (startPoint == null) {
				return;
			}
			if ( ( endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY() ) && !(lineButton.isSelected()) ) {
				return;
			}
			Figure newFigure = null;
			if (rectangleButton.isSelected()) {
				newFigure = new Rectangle(startPoint, endPoint, bordeColor.getValue(), bordeSlider.getValue(), rellenoColor.getValue());
			} else if (circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(startPoint, circleRadius,bordeColor.getValue(), bordeSlider.getValue(), rellenoColor.getValue());
			} else if (elipseButton.isSelected()){
				newFigure = new Elipse(startPoint,Math.abs(startPoint.getY() - endPoint.getY())/2,Math.abs(endPoint.getX() - startPoint.getX())/2,bordeColor.getValue(), bordeSlider.getValue(), rellenoColor.getValue());
			}else if(squareButton.isSelected()){
				newFigure = new Square(startPoint,new Point(endPoint.getX(), (startPoint.getY()+endPoint.getX()-startPoint.getX())),bordeColor.getValue(), bordeSlider.getValue(), rellenoColor.getValue());
			} else if(lineButton.isSelected()) {
				newFigure = new Line(startPoint,endPoint, bordeColor.getValue());
			} else if(selectionButton.isSelected()){
				Rectangle rectSelection = new Rectangle(startPoint, endPoint, bordeColor.getValue(), bordeSlider.getValue(), rellenoColor.getValue());
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				boolean selFlag = false;
				for(Figure figure :canvasState.figures()){
					if(figure.contained(rectSelection)){
						selectedFigure.add(figure);
						label.append(" , ").append(figure.toString());
						selFlag=true;
					}
				}
				if(selFlag){
					statusPane.updateStatus(label.toString());
				} else{
					selectedFigure=null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				flag=false;
			}else{return;}
			if (flag) {
				canvasState.addFigure(newFigure);
			}
			startPoint = null;
			redrawCanvas();
		});
		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for (Figure figure : canvasState.figures()) {
				if (figureBelongs(figure, eventPoint)) {
					found = true;
					label.append(figure.toString());
				}
			}
			if (found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});
		canvas.setOnMouseClicked(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for (Figure figure : canvasState.figures()) {
					if (figureBelongs(figure, eventPoint)) {
						found = true;
						selectedFigure.clear();
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure=null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});
		canvas.setOnMouseDragged(event -> {
			if (selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;
				if(!selectedFigure.isEmpty()){
					for ( Figure figure : selectedFigure ){
						figure.move(diffX, diffY);
					}
				}
				redrawCanvas();
			}
		});
		bordeColor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(hasSelected()){
					refreshFigureColors();
				}
			}
		});
		rellenoColor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(hasSelected()){
					refreshFigureColors();
				}
			}
		});
		bordeSlider.setOnMouseReleased( event -> {
			refreshFigureColors();
		});
		fondoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(hasSelected()){
					for(Figure figure: selectedFigure) {
						canvasState.toBack(figure);
					}
					redrawCanvas();
				}
			}
		});
		frenteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(hasSelected()){
					for(Figure figure: selectedFigure) {
						canvasState.toFront(figure);
					}
					redrawCanvas();
				}
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void refreshFigureColors(){
		for( Figure figure : selectedFigure ){
			figure.setBordeColor(bordeColor.getValue());
			figure.setBordeAncho(bordeSlider.getValue());
			figure.setFigureColor(rellenoColor.getValue());
		}
		redrawCanvas();
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			gc.setLineWidth(figure.getBordeAncho());
			if(figure.equals(selectedFigure)) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(figure.getBordeColor());
			}
			gc.setFill(figure.getFigureColor());
			figure.redraw(gc);
		}
	}

	private boolean figureBelongs(Figure figure, Point eventPoint) {
		return figure.pointBelongs(eventPoint);
		/*boolean found = false;
		if(figure instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) figure;
			found = eventPoint.getX() > rectangle.getTopLeft().getX() && eventPoint.getX() < rectangle.getBottomRight().getX() &&
					eventPoint.getY() > rectangle.getTopLeft().getY() && eventPoint.getY() < rectangle.getBottomRight().getY();
		} else if(figure instanceof Circle) {
			Circle circle = (Circle) figure;
			found = Math.sqrt(Math.pow(circle.getCenterPoint().getX() - eventPoint.getX(), 2) +
					Math.pow(circle.getCenterPoint().getY() - eventPoint.getY(), 2)) < circle.getRadius();
		}
		return found;*/
	}

	private boolean hasSelected(){
		return selectedFigure != null;
	}

}
