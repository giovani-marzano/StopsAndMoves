package tcc.stopsAndMoves.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import weka.core.converters.DatabaseLoader;
import weka.core.converters.Loader;
import weka.gui.ConverterFileChooser;
import weka.gui.sql.SqlViewerDialog;

public class InputPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Loader loader = null;

	private ConverterFileChooser fileChooser = new ConverterFileChooser();
	private ImageIcon okIcon = null;
	private ImageIcon notOkIcon = null;
	private JLabel lblOkIcon;
	private TitledBorder border;
	private String defaultFile = null;
	private JButton btnArquivo;
	private JButton btnSql;
	private JFrame parentFrame = null;

	/**
	 * Create the panel.
	 */
	public InputPanel() {
		border = new TitledBorder(null, "<Title>", TitledBorder.LEADING,
				TitledBorder.TOP, null, null);
		setBorder(border);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		btnArquivo = new JButton(
				Messages.getString("InputPanel.btnArquivo.text")); //$NON-NLS-1$
		btnArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createLoaderFromFile();
			}
		});
		add(btnArquivo);

		Component horizontalStrut = Box.createHorizontalStrut(5);
		add(horizontalStrut);

		btnSql = new JButton(Messages.getString("InputPanel.btnSql.text")); //$NON-NLS-1$
		btnSql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createLoaderFromSQL();
			}
		});
		add(btnSql);

		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		add(horizontalStrut_1);

		notOkIcon = new ImageIcon(
				InputPanel.class
						.getResource("/tcc/stopsAndMoves/gui/img/notOk.png"));
		okIcon = new ImageIcon(
				InputPanel.class
						.getResource("/tcc/stopsAndMoves/gui/img/Ok.png"));

		lblOkIcon = new JLabel("");
		lblOkIcon.setIcon(notOkIcon);
		lblOkIcon.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblOkIcon);
	}

	private void createLoaderFromSQL() {
		SqlViewerDialog dialog = new SqlViewerDialog(parentFrame);

		dialog.setVisible(true);
		if (dialog.getReturnValue() == JOptionPane.OK_OPTION) {
			
			String keys = null;
			
			keys = JOptionPane.showInputDialog(parentFrame,
					"Forneça o nome das colunas que identificam unicamente\n" +
					"os resultados da query separados por vírgula");
			
			try {
				DatabaseLoader dbLoader = new DatabaseLoader();
				dbLoader.setSource(dialog.getURL(), dialog.getUser(),
						dialog.getPassword());
				dbLoader.setQuery(dialog.getQuery());
				
				if (keys != null && keys.length() > 0) {
					dbLoader.setKeys(keys);
				}
				setLoader(dbLoader);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(parentFrame, 
						e.getMessage(),
						"Falha na criação do Loader",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void createLoaderFromFile() {
		if (defaultFile != null && defaultFile != "") {
			File fileOri = fileChooser.getSelectedFile();

			if (fileOri != null) {
				File fileNew = new File(fileOri.getParentFile(), defaultFile);

				fileChooser.setSelectedFile(fileNew);
			}
		}

		String title = border.getTitle();
		if (title != null && title != "") {
			fileChooser.setDialogTitle("Open - " + title);
		}

		if (fileChooser.showOpenDialog(this.getParent()) == JFileChooser.APPROVE_OPTION) {
			setLoader(fileChooser.getLoader());
		}

		// Retornando o titulo original
		fileChooser.setDialogTitle(null);
	}

	public Loader getLoader() {
		return loader;
	}

	public void setLoader(Loader loader) {
		if (loader != null) {
			lblOkIcon.setIcon(okIcon);
		} else {
			lblOkIcon.setIcon(notOkIcon);
		}
		this.loader = loader;
	}

	public String getTitle() {
		return border.getTitle();
	}

	public void setTitle(String title) {
		border.setTitle(title);
	}

	public void reset() {
		setLoader(null);
	}

	public ConverterFileChooser getFileChooser() {
		return fileChooser;
	}

	public void setFileChooser(ConverterFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}

	public String getDefaultFile() {
		return defaultFile;
	}

	public void setDefaultFile(String defaultFile) {
		this.defaultFile = defaultFile;
	}

	public void setEnabled(boolean enabled) {
		btnArquivo.setEnabled(enabled);
		btnSql.setEnabled(enabled);
	}

	public boolean isEnabled() {
		return btnArquivo.isEnabled();
	}

	public JFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}
}
