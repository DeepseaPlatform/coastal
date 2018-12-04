package za.ac.sun.cs.coastal.observers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import za.ac.sun.cs.coastal.COASTAL;

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

		private final COASTAL coastal;

		private JFrame frame;
		
		GUIManager(COASTAL coastal) {
			this.coastal = coastal;
			coastal.getBroker().subscribe("coastal-start", this::start);
			coastal.getBroker().subscribe("gui-stop", this::stop);
		}

		public void start(Object object) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame = new JFrame("COASTAL: " + coastal.getConfig().getString("run-name", "?"));
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					GUI gui = new GUI(coastal);
					gui.setOpaque(true);
					frame.setContentPane(gui);
					frame.pack();
					frame.setVisible(true);
				}
			});
		}

		public void stop(Object object) {
			frame.setVisible(false);
			frame.dispose();
		}

	}

	// ======================================================================
	//
	// GUI
	//
	// ======================================================================

	@SuppressWarnings("serial")
	private static class GUI extends JPanel implements ActionListener {

		private final COASTAL coastal;

		private final JButton doneButton;

		private final JPanel infoPanel;

		private final JTextField elapsedField;

		private final JTextField diveCountField;

		private final JTextField modelCountField;
		
		private final JTextField pcCountField;
		
		public GUI(COASTAL coastal) {
			super(new BorderLayout());
			this.coastal = coastal;
			coastal.getBroker().subscribe("coastal-stop", this::isDone);
			coastal.getBroker().subscribe("tick", this::tick);
			coastal.getBroker().subscribe("tock", this::tick);
			doneButton = new JButton("Done");
			doneButton.setEnabled(false);
			doneButton.setActionCommand("closedown");
			doneButton.addActionListener(this);
			add(doneButton, BorderLayout.PAGE_END);
			infoPanel = new JPanel(new SpringLayout());
			// Elapsed time
			JLabel elapsedLabel = new JLabel("Elapsed", JLabel.TRAILING);
			infoPanel.add(elapsedLabel);
			elapsedField = new JTextField(20);
			elapsedField.setEditable(false);
			elapsedLabel.setLabelFor(elapsedField);
			infoPanel.add(elapsedField);
			// Number of dives
			JLabel diveCountLabel = new JLabel("#dives", JLabel.TRAILING);
			infoPanel.add(diveCountLabel);
			diveCountField = new JTextField(20);
			diveCountField.setEditable(false);
			diveCountLabel.setLabelFor(diveCountField);
			infoPanel.add(diveCountField);
			// Number of models
			JLabel modelCountLabel = new JLabel("#models", JLabel.TRAILING);
			infoPanel.add(modelCountLabel);
			modelCountField = new JTextField(20);
			modelCountField.setEditable(false);
			modelCountLabel.setLabelFor(modelCountField);
			infoPanel.add(modelCountField);
			// Number of path conditions
			JLabel pcCountLabel = new JLabel("#pcs", JLabel.TRAILING);
			infoPanel.add(pcCountLabel);
			pcCountField = new JTextField(20);
			pcCountField.setEditable(false);
			pcCountLabel.setLabelFor(pcCountField);
			infoPanel.add(pcCountField);
			// Tidy up the labels and fields
			makeCompactGrid(infoPanel, 4, 2, 6, 6, 6, 6);
			// Add info to this frame
			add(infoPanel, BorderLayout.CENTER);
		}

		public void isDone(Object object) {
			doneButton.setEnabled(true);
		}

		public void tick(Object object) {
			long t1 = System.currentTimeMillis() - coastal.getStartingTime();
			long dc = coastal.getDiveCount();
			long mc = coastal.getModelQueueLength();
			long pc = coastal.getPcQueueLength();
			elapsedField.setText("" + t1 + " ms");
			diveCountField.setText(String.format("%d (%.1f/sec)", dc, dc * 1000.0 / t1));
			modelCountField.setText("" + mc);
			pcCountField.setText("" + pc);
			repaint();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("closedown".equals(e.getActionCommand())) {
				coastal.getBroker().publish("gui-stop", null);
			}
		}

	}

	/**
	 * Aligns the first <code>rows</code> * <code>cols</code> components of
	 * <code>parent</code> in a grid. Each component in a column is as wide as
	 * the maximum preferred width of the components in that column; height is
	 * similarly determined for each row. The parent is made just big enough to
	 * fit them all.
	 *
	 * @param rows
	 *            number of rows
	 * @param cols
	 *            number of columns
	 * @param initialX
	 *            x location to start the grid at
	 * @param initialY
	 *            y location to start the grid at
	 * @param xPad
	 *            x padding between cells
	 * @param yPad
	 *            y padding between cells
	 */
	public static void makeCompactGrid(Container parent, int rows, int cols, int initialX, int initialY, int xPad,
			int yPad) {
		SpringLayout layout;
		try {
			layout = (SpringLayout) parent.getLayout();
		} catch (ClassCastException exc) {
			System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
			return;
		}

		//Align all cells in each column and make them the same width.
		Spring x = Spring.constant(initialX);
		for (int c = 0; c < cols; c++) {
			Spring width = Spring.constant(0);
			for (int r = 0; r < rows; r++) {
				width = Spring.max(width, getConstraintsForCell(r, c, parent, cols).getWidth());
			}
			for (int r = 0; r < rows; r++) {
				SpringLayout.Constraints constraints = getConstraintsForCell(r, c, parent, cols);
				constraints.setX(x);
				constraints.setWidth(width);
			}
			x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
		}

		//Align all cells in each row and make them the same height.
		Spring y = Spring.constant(initialY);
		for (int r = 0; r < rows; r++) {
			Spring height = Spring.constant(0);
			for (int c = 0; c < cols; c++) {
				height = Spring.max(height, getConstraintsForCell(r, c, parent, cols).getHeight());
			}
			for (int c = 0; c < cols; c++) {
				SpringLayout.Constraints constraints = getConstraintsForCell(r, c, parent, cols);
				constraints.setY(y);
				constraints.setHeight(height);
			}
			y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
		}

		//Set the parent's size.
		SpringLayout.Constraints pCons = layout.getConstraints(parent);
		pCons.setConstraint(SpringLayout.SOUTH, y);
		pCons.setConstraint(SpringLayout.EAST, x);
	}

	/* Used by makeCompactGrid. */
	private static SpringLayout.Constraints getConstraintsForCell(int row, int col, Container parent, int cols) {
		SpringLayout layout = (SpringLayout) parent.getLayout();
		Component c = parent.getComponent(row * cols + col);
		return layout.getConstraints(c);
	}

}
