package tcc.stopsAndMoves.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import tcc.stopsAndMoves.Application;
import tcc.stopsAndMoves.SMoT;
import tcc.stopsAndMoves.control.SMoTControl;
import tcc.stopsAndMoves.converters.sql.TrajectorySql;
import weka.gui.ConverterFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

public class MainGUI implements PropertyChangeListener {

	private JFrame frmStopsAndMoves;
	private InputPanel panelTrajectory;
	private InputPanel panelRoI;
	private InputPanel panelRoIPts;
	private OutputPanel panelStops;
	private OutputPanel panelMoves;
	private OutputPanel panelMovePoints;
	private JButton btnRun;
	private JProgressBar progressBar;

	private SMoTControl control;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frmStopsAndMoves.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	class Task extends SwingWorker<Boolean, Void> {

		private SMoTControl control;
		private Exception excepton;

		public Task(SMoTControl c) {
			control = c;
		}

		@Override
		protected Boolean doInBackground() {
			if (false) {
				return doWekaProcessing();
			}
			else {
				return doSQLProcessing();
			}
		}
		
		private boolean doSQLProcessing() {
			boolean result = true;

			try {
				boolean hasNext = true;
				
				TrajectorySql loaderSaver = new TrajectorySql();
				
				loaderSaver.setUserName("giovani");
				loaderSaver.setPassword("giovani123");
				loaderSaver.setDatabaseURL("jdbc:postgresql://localhost:5432/trajetoriasgis");
				
				Application app = loaderSaver.loadApplication();
				
				SMoT control = new SMoT();
				
				control.setApplication(app);
				control.setTrajectoryLoader(loaderSaver);
				control.setTrajectoySaver(loaderSaver);

				while (hasNext == true) {
					hasNext = control.processSome(10);
					setProgress(control.getNumProcessedTrajectories() % 100);
				}
			} catch (Exception e) {
				excepton = e;
				result = false;
			}
			return result;
		}
				
		private boolean doWekaProcessing() {
			boolean result = true;

			try {
				boolean hasNext = true;
				control.createApplication(panelRoI.getLoader(),
						panelRoIPts.getLoader());
				control.createTrajectoryLoader(panelTrajectory.getLoader());
				control.createTrajectorySaver(panelStops.getSaver(),
						panelMoves.getSaver(), panelMovePoints.getSaver());
				control.createSMoT();

				while (hasNext == true) {
					hasNext = control.processSomeTrajectories(10);
					setProgress(control.getNumProcessedTrj() % 100);
				}
			} catch (Exception e) {
				excepton = e;
				result = false;
			}
			return result;
		}

		@Override
		protected void done() {
			super.done();

			try {
				boolean result = get();

				if (result == true) {
					String msg = "Operação concluida com sucesso !\n"
							+ "\nForam processadas "
							+ control.getNumProcessedTrj() + " trajetórias.";
					JOptionPane.showMessageDialog(frmStopsAndMoves, msg,
							"Info", JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (excepton != null) {
						JOptionPane.showMessageDialog(frmStopsAndMoves,
								"Falha no processamento. Exceção:\n" +
								excepton.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
						excepton.printStackTrace();
					}
					else {
						JOptionPane.showMessageDialog(frmStopsAndMoves,
								"Falha no processamento", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			progressBar.setIndeterminate(false);
			progressBar.setString("");
			progressBar.setValue(0);

			btnRun.setEnabled(true);
			panelMovePoints.setEnabled(true);
			panelRoI.setEnabled(true);
			panelMoves.setEnabled(true);
			panelRoIPts.setEnabled(true);
			panelStops.setEnabled(true);
			panelTrajectory.setEnabled(true);

			panelMovePoints.reset();
			panelRoI.reset();
			panelMoves.reset();
			panelRoIPts.reset();
			panelStops.reset();
			panelTrajectory.reset();
		}

	}

	private void execute() {
		btnRun.setEnabled(false);
		panelMovePoints.setEnabled(false);
		panelRoI.setEnabled(false);
		panelMoves.setEnabled(false);
		panelRoIPts.setEnabled(false);
		panelStops.setEnabled(false);
		panelTrajectory.setEnabled(false);

		progressBar.setValue(0);
		progressBar.setString("0");

		control = new SMoTControl();

		Task task = new Task(control);

		task.addPropertyChangeListener(this);

		task.execute();

		progressBar.setIndeterminate(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (int) evt.getNewValue();

			if (progress != 0) {
				progressBar.setIndeterminate(false);
				progressBar.setValue((Integer) evt.getNewValue());
				if (control != null) {
					progressBar.setString("" + control.getNumProcessedTrj());
				} else {
					progressBar.setString("");
				}
			}
		}
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStopsAndMoves = new JFrame();
		frmStopsAndMoves.setTitle(Messages
				.getString("MainGUI.frmStopsAndMoves.title")); //$NON-NLS-1$
		frmStopsAndMoves.setBounds(100, 100, 450, 300);
		frmStopsAndMoves.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmStopsAndMoves.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 210, 144, 0 };
		gbl_panel.rowHeights = new int[] { 265, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panelInputs = new JPanel();
		GridBagConstraints gbc_panelInputs = new GridBagConstraints();
		gbc_panelInputs.fill = GridBagConstraints.BOTH;
		gbc_panelInputs.insets = new Insets(0, 0, 0, 5);
		gbc_panelInputs.gridx = 0;
		gbc_panelInputs.gridy = 0;
		panel.add(panelInputs, gbc_panelInputs);
		panelInputs.setBorder(new TitledBorder(null, Messages
				.getString("MainGUI.panelInputs.borderTitle"),
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panelInputs = new GridBagLayout();
		gbl_panelInputs.columnWidths = new int[] { 200, 0 };
		gbl_panelInputs.rowHeights = new int[] { 81, 81, 81, 0 };
		gbl_panelInputs.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelInputs.rowWeights = new double[] { 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		panelInputs.setLayout(gbl_panelInputs);

		panelTrajectory = new InputPanel();
		panelTrajectory.setDefaultFile(Messages
				.getString("MainGUI.panelTrajectory.defaultFile")); //$NON-NLS-1$
		panelTrajectory.setTitle(Messages
				.getString("MainGUI.panelTrajectory.title")); //$NON-NLS-1$
		GridBagConstraints gbc_panelTrajectory = new GridBagConstraints();
		gbc_panelTrajectory.fill = GridBagConstraints.BOTH;
		gbc_panelTrajectory.insets = new Insets(0, 0, 5, 0);
		gbc_panelTrajectory.gridx = 0;
		gbc_panelTrajectory.gridy = 0;
		panelInputs.add(panelTrajectory, gbc_panelTrajectory);

		panelRoI = new InputPanel();
		panelRoI.setDefaultFile(Messages
				.getString("MainGUI.panelRoI.defaultFile")); //$NON-NLS-1$
		panelRoI.setTitle(Messages.getString("MainGUI.panelRoI.title")); //$NON-NLS-1$
		GridBagConstraints gbc_panelRoI = new GridBagConstraints();
		gbc_panelRoI.fill = GridBagConstraints.BOTH;
		gbc_panelRoI.insets = new Insets(0, 0, 5, 0);
		gbc_panelRoI.gridx = 0;
		gbc_panelRoI.gridy = 1;
		panelInputs.add(panelRoI, gbc_panelRoI);

		panelRoIPts = new InputPanel();
		panelRoIPts.setDefaultFile(Messages
				.getString("MainGUI.panelRoIPts.defaultFile")); //$NON-NLS-1$
		panelRoIPts.setTitle(Messages.getString("MainGUI.inputPanel.title")); //$NON-NLS-1$
		GridBagConstraints gbc_panelRoIPts = new GridBagConstraints();
		gbc_panelRoIPts.fill = GridBagConstraints.BOTH;
		gbc_panelRoIPts.gridx = 0;
		gbc_panelRoIPts.gridy = 2;
		panelInputs.add(panelRoIPts, gbc_panelRoIPts);

		JPanel panelOutputs = new JPanel();
		panelOutputs.setBorder(new TitledBorder(null, "Saidas",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelOutputs = new GridBagConstraints();
		gbc_panelOutputs.fill = GridBagConstraints.BOTH;
		gbc_panelOutputs.gridx = 1;
		gbc_panelOutputs.gridy = 0;
		panel.add(panelOutputs, gbc_panelOutputs);
		GridBagLayout gbl_panelOutputs = new GridBagLayout();
		gbl_panelOutputs.columnWidths = new int[] { 134, 0 };
		gbl_panelOutputs.rowHeights = new int[] { 81, 81, 81, 0 };
		gbl_panelOutputs.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelOutputs.rowWeights = new double[] { 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		panelOutputs.setLayout(gbl_panelOutputs);

		panelStops = new OutputPanel();
		panelStops.setDefaultFile(Messages
				.getString("MainGUI.panelStops.defaultFile")); //$NON-NLS-1$
		panelStops.setTitle(Messages.getString("MainGUI.panelStops.title")); //$NON-NLS-1$
		GridBagConstraints gbc_panelStops = new GridBagConstraints();
		gbc_panelStops.fill = GridBagConstraints.BOTH;
		gbc_panelStops.insets = new Insets(0, 0, 5, 0);
		gbc_panelStops.gridx = 0;
		gbc_panelStops.gridy = 0;
		panelOutputs.add(panelStops, gbc_panelStops);

		panelMoves = new OutputPanel();
		panelMoves.setDefaultFile(Messages
				.getString("MainGUI.panelMoves.defaultFile")); //$NON-NLS-1$
		panelMoves.setTitle(Messages.getString("MainGUI.panelMoves.title")); //$NON-NLS-1$
		GridBagConstraints gbc_panelMoves = new GridBagConstraints();
		gbc_panelMoves.fill = GridBagConstraints.BOTH;
		gbc_panelMoves.insets = new Insets(0, 0, 5, 0);
		gbc_panelMoves.gridx = 0;
		gbc_panelMoves.gridy = 1;
		panelOutputs.add(panelMoves, gbc_panelMoves);

		panelMovePoints = new OutputPanel();
		panelMovePoints.setDefaultFile(Messages
				.getString("MainGUI.panelMovePoints.defaultFile")); //$NON-NLS-1$
		panelMovePoints.setTitle(Messages
				.getString("MainGUI.panelMovePoints.title")); //$NON-NLS-1$
		GridBagConstraints gbc_panelMovePoints = new GridBagConstraints();
		gbc_panelMovePoints.fill = GridBagConstraints.BOTH;
		gbc_panelMovePoints.gridx = 0;
		gbc_panelMovePoints.gridy = 2;
		panelOutputs.add(panelMovePoints, gbc_panelMovePoints);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmStopsAndMoves.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		btnRun = new JButton(Messages.getString("MainGUI.btnRun.text")); //$NON-NLS-1$
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				execute();
			}
		});
		panel_1.add(btnRun);

		Component horizontalStrut = Box.createHorizontalStrut(5);
		panel_1.add(horizontalStrut);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		panel_1.add(progressBar);

		ConverterFileChooser fileChooser = new ConverterFileChooser();

		panelTrajectory.setFileChooser(fileChooser);
		panelRoI.setFileChooser(fileChooser);
		panelRoIPts.setFileChooser(fileChooser);
		panelMoves.setFileChooser(fileChooser);
		panelMovePoints.setFileChooser(fileChooser);
		panelStops.setFileChooser(fileChooser);

		// frmStopsAndMoves.pack();
	}
}
