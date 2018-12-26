package za.ac.sun.cs.coastal.observers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.ImmutableConfiguration;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Reporter.Reportable;
import za.ac.sun.cs.coastal.TaskFactory.TaskManager;
import za.ac.sun.cs.coastal.messages.Tuple;

public class GUIFactory implements ObserverFactory {

	public GUIFactory(COASTAL coastal, ImmutableConfiguration options) {
	}

	@Override
	public int getFrequencyflags() {
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

		@Override
		public String getName() {
			return null;
		}

		@Override
		public String[] getPropertyNames() {
			return null;
		}

		@Override
		public Object[] getPropertyValues() {
			return null;
		}

	}

	public static class GUI extends Application implements Runnable {

		private static final int INITIAL_WIDTH = 1000;

		private static final int INITIAL_HEIGHT = 600;

		private static COASTAL coastal;

		private Button stopButton;

		private Label doneLabel;

		private Button quitButton;

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
					Object[] propertyValues = reportable.getPropertyValues();
					for (int i = 0, n = propertyValues.length; i < n; i++) {
						table.getItems().get(i).setValue(propertyValues[i].toString());
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
			// Create a reportable for COASTAL
			TaskPanel tp = new TaskPanel(coastal.getCoastalReportable());
			tasks.add(tp);
			tile.getChildren().add(tp);
			// Create a reportable for the path tree
			tp = new TaskPanel(coastal.getPathTreeReportable());
			tasks.add(tp);
			tile.getChildren().add(tp);
			for (TaskManager tm : coastal.getTasks()) {
				tp = new TaskPanel(tm);
				tasks.add(tp);
				tile.getChildren().add(tp);
			}
			for (Tuple fm : coastal.getAllObservers()) {
				ObserverManager m = (ObserverManager) fm.get(1);
				if (m.getName() != null) {
					tp = new TaskPanel(m);
					tasks.add(tp);
					tile.getChildren().add(tp);
				}
			}

			stopButton = new Button("Stop");
			stopButton.setOnAction(e -> coastal.getBroker().publish("emergency-stop", this));
			stopButton.setDisable(false);
			doneLabel = new Label("Done");
			doneLabel.setVisible(false);
			doneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			doneLabel.setStyle("-fx-text-fill:#330000;-fx-background-color:#ffff00;-fx-padding:10 30");
			quitButton = new Button("Quit");
			quitButton.setOnAction(e -> Platform.exit());
			quitButton.setDisable(false);
			quitButton.setVisible(false);
			Region region1 = new Region();
			HBox.setHgrow(region1, Priority.ALWAYS);
			Region region2 = new Region();
			HBox.setHgrow(region2, Priority.ALWAYS);
			final HBox bottomBox = new HBox();
			bottomBox.setPadding(new Insets(10, 10, 10, 10));
			bottomBox.setAlignment(Pos.CENTER_RIGHT);
			bottomBox.getChildren().addAll(stopButton, region1, doneLabel, region2, quitButton);
			bottomBox.setStyle("-fx-background-color:#336699");

			final BorderPane border = new BorderPane();
			border.setTop(topBox);
			border.setCenter(tile);
			border.setBottom(bottomBox);
			border.prefWidthProperty().bind(root.widthProperty());
			border.prefHeightProperty().bind(root.heightProperty());

			root.getChildren().addAll(border);

			primaryStage.setScene(scene);
			primaryStage.setWidth(INITIAL_WIDTH);
			primaryStage.setHeight(INITIAL_HEIGHT);
			primaryStage.show();
			coastal.getBroker().subscribe("tick", this::tick);
			coastal.getBroker().subscribe("reporting-done", this::isDone);
		}

		public void isDone(Object object) {
			stopButton.setDisable(true);
			doneLabel.setVisible(true);
			quitButton.setVisible(true);
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
