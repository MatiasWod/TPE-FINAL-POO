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

public class PaintPane extends BorderPane {

	// BackEnd
	private CanvasState canvasState;

	// Canvas y relacionados
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK;
	private Color fillColor = Color.YELLOW;

	// Botones Barra Izquierda
	private ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private ToggleButton circleButton = new ToggleButton("Círculo");
	private ToggleButton elipseButton = new ToggleButton("Elipse");
	private ToggleButton squareButton = new ToggleButton("Cuadrado");
	private ToggleButton lineButton = new ToggleButton("Linea");

	private Button fondoButton = new Button("Al Fondo");
	private Button frenteButton = new Button("Al Frente");

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private Figure selectedFigure;

	// StatusBar
	private StatusPane statusPane;

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toggleArr = {selectionButton, rectangleButton, circleButton,elipseButton,squareButton,lineButton};
		ButtonBase[] toolsArr = {selectionButton, rectangleButton, circleButton,elipseButton,squareButton,lineButton,fondoButton,frenteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ButtonBase tool : toolsArr) {
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
		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});
		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			if (startPoint == null) {
				return;
			}
			if ( ( endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY() ) && !(lineButton.isSelected()) ) {
				return;
			}
			Figure newFigure = null;
			if (rectangleButton.isSelected()) {//Agregar if para elipse,square,linea
				newFigure = new Rectangle(startPoint, endPoint);
			} else if (circleButton.isSelected()) {
				double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
				newFigure = new Circle(startPoint, circleRadius);
			} else if (elipseButton.isSelected()){
				if( ( endPoint.getX() - startPoint.getX() ) > ( ( endPoint.getY() - startPoint.getY() ) ) ){//Hago estos if para ver cual va como axis mayor/menor
					newFigure = new Elipse(new Point((endPoint.getX() - startPoint.getX() )/2, (endPoint.getY() - startPoint.getY() )/2),endPoint.getX() - startPoint.getX(),endPoint.getY() - startPoint.getY());
				} else{
					newFigure = new Elipse(new Point((endPoint.getX() - startPoint.getX() )/2, (endPoint.getY() - startPoint.getY() )/2),endPoint.getY() - startPoint.getY(),endPoint.getX() - startPoint.getX());
				}
			}else if(squareButton.isSelected()){
				newFigure = new Square(startPoint,new Point(endPoint.getX(), (startPoint.getY()-endPoint.getX()+startPoint.getX())));
			} else if(lineButton.isSelected()) {
				newFigure = new Line(startPoint,endPoint);
			}else{return;}

			canvasState.addFigure(newFigure);
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
						selectedFigure = figure;
						label.append(figure.toString());
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
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
				selectedFigure.move(diffX, diffY);
				redrawCanvas();
			}
		});

		//FALTA HACER QUE AL CLICKEAR EL BUTTON YA PASE ADELANTE/ATRAS DE UNA. CAMBIO DE BOTON CAPAZ SOLUCIONA
		fondoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(hasSelected()){
					canvasState.toBack(selectedFigure);
					redrawCanvas();
				}
			}
		});
		frenteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(hasSelected()){
					canvasState.toFront(selectedFigure);
					redrawCanvas();
				}
			}
		});
		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure.equals(selectedFigure)) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(fillColor);
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
