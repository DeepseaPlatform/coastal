package za.ac.sun.cs.coastal.observers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.Reporter.Reportable;
import za.ac.sun.cs.coastal.TaskFactory.TaskManager;
import za.ac.sun.cs.coastal.messages.Tuple;

public class GUIFactory implements ObserverFactory {

	public GUIFactory(COASTAL coastal, Configuration config) {
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

	@SuppressWarnings("serial")
	public static class GUI extends JFrame implements ActionListener, Runnable {

		private static final int INITIAL_WIDTH = 1000;

		private static final int INITIAL_HEIGHT = 600;

		private static COASTAL coastal;

		private JButton stopButton;

		private JLabel doneLabel;

		private JButton quitButton;

		public static class TaskPanel extends JPanel {

			private final Reportable reportable;

			private final JLabel[] nameLabels;

			private final JLabel[] valueLabels;

			TaskPanel(Reportable reportable) {
				this.reportable = reportable;
				// Set the label
				setLayout(new BorderLayout());
				// Create the top label
				final JLabel topLabel = new JLabel(reportable.getName());
				topLabel.setFont(new Font("Arial", Font.BOLD, 14));
				topLabel.setForeground(Color.WHITE);
				topLabel.setHorizontalAlignment(SwingConstants.CENTER);
				final JPanel topPanel = new JPanel(new BorderLayout());
				topPanel.setBackground(new Color(0x336699));
				topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				topPanel.add(topLabel, BorderLayout.CENTER);
				add(topPanel, BorderLayout.PAGE_START);
				// Create a border to reuse
				final Border border = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
						BorderFactory.createEmptyBorder(2, 2, 2, 2));
				final Dimension prefDim = new Dimension(200, 25);
				// Create the property labels
				String[] propertyNames = reportable.getPropertyNames();
				int size = propertyNames.length;
				nameLabels = new JLabel[size];
				for (int i = 0; i < size; i++) {
					nameLabels[i] = new JLabel(propertyNames[i]);
					nameLabels[i].setBackground(Color.WHITE);
					nameLabels[i].setHorizontalAlignment(SwingConstants.LEADING);
					nameLabels[i].setBorder(border);
					nameLabels[i].setMinimumSize(prefDim);
					nameLabels[i].setPreferredSize(prefDim);
					nameLabels[i].setMaximumSize(prefDim);
					nameLabels[i].setFont(new Font("Arial", Font.PLAIN, 14));
				}
				// Create the value labels
				valueLabels = new JLabel[size];
				for (int i = 0; i < size; i++) {
					valueLabels[i] = new JLabel("?");
					valueLabels[i].setBackground(Color.WHITE);
					valueLabels[i].setHorizontalAlignment(SwingConstants.LEADING);
					valueLabels[i].setBorder(border);
					valueLabels[i].setMinimumSize(prefDim);
					valueLabels[i].setPreferredSize(prefDim);
					valueLabels[i].setMaximumSize(prefDim);
					valueLabels[i].setFont(new Font("Arial", Font.PLAIN, 14));
				}
				// Create the table
				final JPanel middlePanel = new JPanel(new GridLayout(0, 2));
				middlePanel.setBackground(Color.WHITE);
				for (int i = 0; i < size; i++) {
					middlePanel.add(nameLabels[i]);
					middlePanel.add(valueLabels[i]);
				}
				add(middlePanel, BorderLayout.CENTER);
			}

			public void refresh() {
				SwingUtilities.invokeLater(() -> {
					Object[] propertyValues = reportable.getPropertyValues();
					for (int i = 0, n = propertyValues.length; i < n; i++) {
						valueLabels[i].setText(propertyValues[i].toString());
					}
				});
			}

		}

		private final List<TaskPanel> tasks = new ArrayList<>();
		private JPanel bottomPanel;

		public GUI(COASTAL coastal) {
			super("COASTAL");
			GUI.coastal = coastal;
		}

		public GUI() {
		}

		@Override
		public void run() {
			SwingUtilities.invokeLater(() -> createAndShowGUI());
		}

		public void createAndShowGUI() {
			final JLabel topLabel = new JLabel(coastal.getConfig().getString("coastal.run-name", "?"));
			topLabel.setFont(new Font("Arial", Font.BOLD, 20));
			topLabel.setForeground(Color.WHITE);
			topLabel.setHorizontalAlignment(SwingConstants.CENTER);
			topLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
			final JPanel topPanel = new JPanel(new BorderLayout());
			topPanel.setBackground(new Color(0x336699));
			topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			topPanel.add(topLabel, BorderLayout.CENTER);

			// Create a reportable for COASTAL
			TaskPanel tp = new TaskPanel(coastal.getCoastalReportable());
			tasks.add(tp);
			// Create a reportable for the path tree
			tp = new TaskPanel(coastal.getPathTreeReportable());
			tasks.add(tp);
			for (TaskManager tm : coastal.getTasks()) {
				tp = new TaskPanel(tm);
				tasks.add(tp);
			}
			for (Tuple fm : coastal.getAllObservers()) {
				ObserverManager m = (ObserverManager) fm.get(1);
				if (m.getName() != null) {
					tp = new TaskPanel(m);
					tasks.add(tp);
				}
			}
			final Dimension middleDim = new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT);
			final JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			middlePanel.setBackground(new Color(0xddddff));
			middlePanel.setMinimumSize(middleDim);
			middlePanel.setPreferredSize(middleDim);
			for (TaskPanel t : tasks) {
				middlePanel.add(t);
			}

			stopButton = new JButton("Stop");
			stopButton.setActionCommand("stop");
			stopButton.addActionListener(this);
			stopButton.setEnabled(true);
			doneLabel = new JLabel("Done");
			doneLabel.setVisible(false);
			doneLabel.setFont(new Font("Arial", Font.BOLD, 16));
			doneLabel.setOpaque(true);
			doneLabel.setForeground(new Color(0x330000));
			doneLabel.setBackground(new Color(0xffff00));
			doneLabel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
			quitButton = new JButton("Quit");
			quitButton.setActionCommand("quit");
			quitButton.addActionListener(this);
			quitButton.setEnabled(true);
			quitButton.setVisible(false);
//			final JPanel bottomPanel = new JPanel(new BorderLayout());
			bottomPanel = new JPanel(new BorderLayout());
			bottomPanel.setBackground(new Color(0x336699));
			bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			bottomPanel.add(stopButton, BorderLayout.LINE_START);
			bottomPanel.add(doneLabel, BorderLayout.CENTER);
			bottomPanel.add(quitButton, BorderLayout.LINE_END);
			final Dimension bottomDim = new Dimension(INITIAL_WIDTH, 60);
			bottomPanel.setMinimumSize(bottomDim);
			bottomPanel.setMaximumSize(bottomDim);
			bottomPanel.setPreferredSize(bottomDim);

			add(topPanel, BorderLayout.PAGE_START);
			add(middlePanel, BorderLayout.CENTER);
			add(bottomPanel, BorderLayout.PAGE_END);

			setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
			pack();
			setVisible(true);
			coastal.getBroker().subscribe("tick", this::tick);
			coastal.getBroker().subscribe("reporting-done", this::isDone);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("stop".equals(e.getActionCommand())) {
				coastal.getBroker().publish("emergency-stop", this);
			} else if ("quit".equals(e.getActionCommand())) {
				setVisible(false);
				dispose();
				System.exit(0); // Platform.exit();
			}
		}

		public void isDone(Object object) {
			stopButton.setEnabled(false);
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
