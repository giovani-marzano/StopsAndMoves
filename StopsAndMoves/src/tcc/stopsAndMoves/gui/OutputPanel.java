package tcc.stopsAndMoves.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	/**
	 * Create the panel.
	 */
	public OutputPanel() {
		notOkIcon = new ImageIcon(InputPanel.class.getResource("/tcc/stopsAndMoves/gui/img/notOk.png"));
		okIcon = new ImageIcon(InputPanel.class.getResource("/tcc/stopsAndMoves/gui/img/Ok.png"));
		
		border = new TitledBorder(null, "<Title>", TitledBorder.LEADING, TitledBorder.TOP, null, null);
				
		setBorder(border);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JButton btnArquivo = new JButton("Arquivo");
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
		if ( fileChooser.showSaveDialog(this.getParent())
				== JFileChooser.APPROVE_OPTION ) {
			setSaver(fileChooser.getSaver());
		}
	}

	public Saver getSaver() {
		return saver;
	}

	public void setSaver(Saver saver) {
		if (saver != null) {
			lblOk.setIcon(okIcon);
		}
		else {
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
}
