package frontend;

import backend.CanvasState;
import backend.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
	private final CanvasState canvasState;

	// Canvas y relacionados
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();

	// Botones Barra Izquierda
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton elipseButton = new ToggleButton("Elipse");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");
	private final ToggleButton lineButton = new ToggleButton("Linea");
	private final Button removeButton = new Button("Borrar");
	private final Button fondoButton = new Button("Al Fondo");
	private final Button frenteButton = new Button("Al Frente");

	private final Text bordeText = new Text("Borde:");
	private final Text rellenoText = new Text("Relleno:");

	private final Slider bordeSlider = new Slider(0, 50, 1);
	private final ColorPicker bordeColor = new ColorPicker(Color.BLACK);
	private final ColorPicker rellenoColor = new ColorPicker(Color.YELLOW);

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private final List<Figure> selectedFigure = new ArrayList<>();

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		ToggleButton[] toggleArr = {selectionButton, rectangleButton, circleButton, elipseButton, squareButton, lineButton};
		Control[] toolsArr = {selectionButton, rectangleButton, circleButton, elipseButton, squareButton, lineButton, removeButton, fondoButton,
				frenteButton, bordeColor, bordeSlider, rellenoColor};
		Node[] nodesArr = {selectionButton, rectangleButton, circleButton, elipseButton, squareButton, lineButton, removeButton, fondoButton,
				frenteButton, bordeText, bordeColor, bordeSlider, rellenoText, rellenoColor};
		ToggleGroup tools = new ToggleGroup();
		for (Control tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setCursor(Cursor.HAND);
		}
		for (ToggleButton tool : toggleArr) {
			tool.setToggleGroup(tools);
		}

		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(nodesArr);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

        //Cambios del slider
		bordeSlider.setShowTickLabels(true);
		bordeSlider.setShowTickMarks(true);
		bordeSlider.setMajorTickUnit(50);
		bordeSlider.setMinorTickCount(5);
		bordeSlider.setBlockIncrement(10);
		bordeSlider.setMajorTickUnit(26);


		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));
		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());
			try {
				if ((endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) && !(lineButton.isSelected())) {
					return;
				}
				Figure newFigure;
				if (rectangleButton.isSelected()) {
					newFigure = new Rectangle(startPoint, endPoint, bordeColor.getValue(), bordeSlider.getValue(), rellenoColor.getValue());
				} else if (circleButton.isSelected()) {
					double circleRadius = Math.sqrt(Math.pow(endPoint.getX() - startPoint.getX(), 2) + Math.pow(endPoint.getY() - startPoint.getY(), 2));
					newFigure = new Circle(startPoint, circleRadius, bordeColor.getValue(), bordeSlider.getValue(), rellenoColor.getValue());
				} else if (elipseButton.isSelected()) {
					newFigure = new Elipse(startPoint, Math.abs(startPoint.getY() - endPoint.getY()) / 2, Math.abs(endPoint.getX() - startPoint.getX()) / 2, bordeColor.getValue(), bordeSlider.getValue(), rellenoColor.getValue());
				} else if (squareButton.isSelected()) {
					newFigure = new Square(startPoint, new Point(endPoint.getX(), (startPoint.getY() + endPoint.getX() - startPoint.getX())), bordeColor.getValue(), bordeSlider.getValue(), rellenoColor.getValue());
				} else if (lineButton.isSelected()) {
					newFigure = new Line(startPoint, endPoint, bordeColor.getValue(), bordeSlider.getValue());
				} else {
					return;
				}
			canvasState.addFigure(newFigure);
			redrawCanvas();
			}catch (Exception e){
				statusPane.updateStatus(e.getMessage());
			}
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for (Figure figure : canvasState.figures()) {
				if (figure.pointBelongs(eventPoint)) {
					found = true;
					label.append(figure);
				}
			}
			if (found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			if(selectionButton.isSelected()) {
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				boolean found = false;
				boolean pressed= false;
				Rectangle rectSelection = new Rectangle(startPoint, eventPoint, Color.BLACK, 0, Color.BLACK);
					for (Figure figure : canvasState.figures()) {
						if (figure.contained(rectSelection)) {
							pressed=true;
							found = true;
							selectedFigure.add(figure);
							label.append(" , ").append(figure);
						}
					}
				if(!pressed) {
					for (Figure figure : canvasState.figures()) {
						if (figure.pointBelongs(eventPoint)) {
							found = true;
							selectedFigure.clear();
							selectedFigure.add(figure);
							label.append(figure);
						}
					}
				}
				if (found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure.clear();
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
                for (Figure figure : selectedFigure) {
                    figure.move(diffX, diffY);
                }
                redrawCanvas();
			}
		});

		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
                for (Figure figure : selectedFigure) {
                    canvasState.remove(figure);
                }
                redrawCanvas();
			}
		});

		bordeColor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
                for (Figure figure : selectedFigure) {
                    refreshFigureColors(figure);
                }
			}
		});

		rellenoColor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
                for (Figure figure : selectedFigure) {
                    refreshFigureColors(figure);
                }
			}
		});

		bordeSlider.setOnMouseReleased( event -> {
			for (Figure figure:selectedFigure)
				refreshFigureColors(figure);
		});

		fondoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
                for(Figure figure: selectedFigure) {
                    canvasState.toBack(figure);
                }
                redrawCanvas();
			}
		});

		frenteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
                for(Figure figure: selectedFigure) {
                    canvasState.toFront(figure);
                }
                redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void refreshFigureColors(Figure figure){
            figure.setBordeColor(bordeColor.getValue());
            figure.setBordeAncho(bordeSlider.getValue());
            figure.setFigureColor(rellenoColor.getValue());
        	redrawCanvas();
	}

	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			gc.setLineWidth(figure.getBordeAncho());
			if(selectedFigure.contains(figure)) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(figure.getBordeColor());
			}
			gc.setFill(figure.getFigureColor());
			figure.redraw(gc);
		}
	}

}
