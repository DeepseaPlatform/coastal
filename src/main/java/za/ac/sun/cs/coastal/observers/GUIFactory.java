package za.ac.sun.cs.coastal.observers;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Reporter.Reportable;
import za.ac.sun.cs.coastal.TaskFactory.TaskManager;

public class GUIFactory implements ObserverFactory {

	public GUIFactory(COASTAL coastal) {
	}

	@Override
	public int getFrequency() {
		return ObserverFactory.ONCE_PER_RUN;
	}

	@Override
	public ObserverManager createManager(COASTAL coastal) {
		return new GUIManager(coastal);
	}

	@Override
	public Observer createObserver(COASTAL coastal, ObserverManager manager) {
		return null;
	}

	// ======================================================================
	//
	// MANAGER FOR GUI
	//
	// ======================================================================

	private static class GUIManager implements ObserverManager {

		GUIManager(COASTAL coastal) {
			new Thread(new GUI(coastal)).start();
		}

	}

	public static class GUI extends Application implements Runnable {

		private static COASTAL coastal;

		private Button doneButton;

		public static class XTableView<S> extends TableView<S> {
			@Override
			public void resize(double width, double height) {
				super.resize(width, height);
				Pane header = (Pane) lookup("TableHeaderRow");
				header.setMinHeight(0);
				header.setPrefHeight(0);
				header.setMaxHeight(0);
				header.setVisible(false);
			}
		}

		public static final class Statistic {
			private final SimpleStringProperty key;
			private final SimpleStringProperty value;

			private Statistic(String key, String value) {
				this.key = new SimpleStringProperty(key);
				this.value = new SimpleStringProperty(value);
			}

			public String getKey() {
				return key.get();
			}

			public void setKey(String key) {
				this.key.set(key);
			}

			public SimpleStringProperty keyProperty() {
				return key;
			}

			public String getValue() {
				return value.get();
			}

			public void setValue(String value) {
				this.value.set(value);
			}

			public SimpleStringProperty valueProperty() {
				return value;
			}
		}

		public static class TaskPanel extends BorderPane {

			private final Reportable reportable;

			private final XTableView<Statistic> table;

			TaskPanel(Reportable reportable) {
				this.reportable = reportable;
				// Create the top label
				final Label topLabel = new Label(reportable.getName());
				topLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
				final HBox topBox = new HBox();
				topBox.setPadding(new Insets(6, 0, 6, 0));
				topBox.setAlignment(Pos.CENTER);
				topBox.getChildren().add(topLabel);
				topBox.setStyle("-fx-background-color:#99ccff");
				setTop(topBox);
				// Create the data items
				String[] propertyNames = reportable.getPropertyNames();
				int size = propertyNames.length;
				final Statistic[] rows = new Statistic[size];
				for (int i = 0; i < size; i++) {
					rows[i] = new Statistic(propertyNames[i], "?");
				}
				// Create the table
				table = new XTableView<Statistic>();
				table.setEditable(true);
				TableColumn<Statistic, String> keyCol = new TableColumn<>("KEY");
				TableColumn<Statistic, String> valueCol = new TableColumn<>("VALUE");
				keyCol.setCellValueFactory(new PropertyValueFactory<Statistic, String>("key"));
				valueCol.setCellValueFactory(new PropertyValueFactory<Statistic, String>("value"));
				table.getColumns().add(keyCol);
				table.getColumns().add(valueCol);
				final ObservableList<Statistic> data = FXCollections.observableArrayList(rows);
				table.setItems(data);
				table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
				table.setFixedCellSize(25);
				table.prefHeightProperty()
						.bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(5));
				// Add the table and a border
				setCenter(table);
				setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
						BorderWidths.DEFAULT)));
			}

			public void refresh() {
				Platform.runLater(() -> {
					int index = 0;
					for (Object propertyValue : reportable.getPropertyValues()) {
						table.getItems().get(index++).setValue(propertyValue.toString());
					}
				});
			}

		}

		private final List<TaskPanel> tasks = new ArrayList<>();

		public GUI(COASTAL coastal) {
			GUI.coastal = coastal;
		}

		public GUI() {
		}

		@Override
		public void run() {
			launch();
		}

		@Override
		public void start(Stage primaryStage) throws Exception {
			primaryStage.setTitle("COASTAL");
			final AnchorPane root = new AnchorPane();
			Scene scene = new Scene(root);

			final Label topLabel = new Label(coastal.getConfig().getString("run-name", "?"));
			topLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
			topLabel.setStyle("-fx-text-fill:#ffffff");
			final HBox topBox = new HBox();
			topBox.setPadding(new Insets(10, 0, 10, 0));
			topBox.setAlignment(Pos.CENTER);
			topBox.getChildren().add(topLabel);
			topBox.setStyle("-fx-background-color:#336699");

			final TilePane tile = new TilePane();
			tile.setPadding(new Insets(0, 0, 0, 0));
			tile.setVgap(0);
			tile.setHgap(0);
			tile.setPrefColumns(2);
			tile.setStyle("-fx-background-color:#ddddff");
			for (TaskManager tm : coastal.getTasks()) {
				TaskPanel tp = new TaskPanel(tm);
				tasks.add(tp);
				tile.getChildren().add(tp);
			}
			// Create a reportable for the path tree
			TaskPanel tp = new TaskPanel(coastal.getPathTreeReportable());
			tasks.add(tp);
			tile.getChildren().add(tp);

			doneButton = new Button("Done");
			doneButton.setOnAction((ActionEvent e) -> {
				primaryStage.close();
			});
			doneButton.setDisable(true);
			final HBox bottomBox = new HBox();
			bottomBox.setPadding(new Insets(10, 10, 10, 10));
			bottomBox.setAlignment(Pos.CENTER_RIGHT);
			bottomBox.getChildren().add(doneButton);
			bottomBox.setStyle("-fx-background-color:#336699");

			final BorderPane border = new BorderPane();
			border.setTop(topBox);
			border.setCenter(tile);
			border.setBottom(bottomBox);
			border.prefWidthProperty().bind(root.widthProperty());
			border.prefHeightProperty().bind(root.heightProperty());

			root.getChildren().addAll(border);

			primaryStage.setScene(scene);
			primaryStage.setWidth(600);
			primaryStage.setHeight(500);
			primaryStage.show();
			coastal.getBroker().subscribe("tick", this::tick);
			coastal.getBroker().subscribe("reporting-done", this::isDone);
		}

		public void isDone(Object object) {
			doneButton.setDisable(false);
			update(true);
		}

		public void tick(Object object) {
			update();
		}

		private void update() {
			update(false);
		}

		private void update(boolean lastUpdate) {
			for (TaskPanel tp : tasks) {
				tp.refresh();
			}
		}

	}

}
