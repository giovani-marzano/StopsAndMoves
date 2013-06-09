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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import weka.core.converters.Saver;
import weka.gui.ConverterFileChooser;

public class OutputPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon okIcon = null;
	private ImageIcon notOkIcon = null;
	private ConverterFileChooser fileChooser = new ConverterFileChooser();
	private Saver saver = null;
	private TitledBorder border;
	private JLabel lblOk;
	private String defaultFile = "";
	private JButton btnArquivo;

	/**
	 * Create the panel.
	 */
	public OutputPanel() {
		notOkIcon = new ImageIcon(
				InputPanel.class
						.getResource("/tcc/stopsAndMoves/gui/img/notOk.png"));
		okIcon = new ImageIcon(
				InputPanel.class
						.getResource("/tcc/stopsAndMoves/gui/img/Ok.png"));

		border = new TitledBorder(null, "<Title>", TitledBorder.LEADING,
				TitledBorder.TOP, null, null);

		setBorder(border);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		btnArquivo = new JButton("Arquivo");
		btnArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateSaverFromFile();
			}
		});
		add(btnArquivo);

		Component horizontalStrut = Box.createHorizontalStrut(5);
		add(horizontalStrut);

		lblOk = new JLabel("");
		lblOk.setIcon(notOkIcon);
		add(lblOk);

	}

	public void CreateSaverFromFile() {
		if (defaultFile != null && defaultFile != "") {
			File fileOri = fileChooser.getSelectedFile();

			if (fileOri != null) {
				File fileNew = new File(fileOri.getParentFile(), defaultFile);
				
				fileChooser.setSelectedFile(fileNew);
			}
		}
		
		String title = border.getTitle();
		if (title != null && title != "") {
			fileChooser.setDialogTitle("Save - " + title);
		}

		if (fileChooser.showSaveDialog(this.getParent()) == JFileChooser.APPROVE_OPTION) {
			setSaver(fileChooser.getSaver());
		}
		
		// Retornando o titulo original
		fileChooser.setDialogTitle(null);
	}

	public Saver getSaver() {
		return saver;
	}

	public void setSaver(Saver saver) {
		if (saver != null) {
			lblOk.setIcon(okIcon);
		} else {
			lblOk.setIcon(notOkIcon);
		}
		this.saver = saver;
	}

	public String getTitle() {
		return border.getTitle();
	}

	public void setTitle(String title) {
		border.setTitle(title);
	}

	public void reset() {
		setSaver(null);
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
		if (defaultFile == null) {
			defaultFile = "";
		}
		this.defaultFile = defaultFile;
	}

	public void setEnabled(boolean enabled) {
		btnArquivo.setEnabled(enabled);
	}
	
	public boolean isEnabled() {
		return btnArquivo.isEnabled();
	}
}
