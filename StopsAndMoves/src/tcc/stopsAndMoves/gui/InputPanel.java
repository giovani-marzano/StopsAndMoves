package tcc.stopsAndMoves.gui;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.Box;

import weka.core.converters.Loader;
import weka.gui.ConverterFileChooser;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

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
	
	/**
	 * Create the panel.
	 */
	public InputPanel(String title) {
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JButton btnArquivo = new JButton("Arquivo");
		btnArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createLoaderFromFile();
			}
		});
		add(btnArquivo);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		add(horizontalStrut);
		
		JButton btnSql = new JButton("SQL");
		add(btnSql);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		add(horizontalStrut_1);
		
		notOkIcon = new ImageIcon(InputPanel.class.getResource("/tcc/stopsAndMoves/gui/img/notOk.png"));
		okIcon = new ImageIcon(InputPanel.class.getResource("/tcc/stopsAndMoves/gui/img/Ok.png"));
		
		lblOkIcon = new JLabel("");
		lblOkIcon.setIcon(notOkIcon);
		lblOkIcon.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblOkIcon);
	}
	
	private void createLoaderFromFile() {
		if ( fileChooser.showOpenDialog(this.getParent())
				== JFileChooser.APPROVE_OPTION ) {
			setLoader(fileChooser.getLoader());
		}
	}

	public Loader getLoader() {
		return loader;
	}

	public void setLoader(Loader loader) {
		if ( loader != null ) {
			lblOkIcon.setIcon(okIcon);
		}
		else {
			lblOkIcon.setIcon(notOkIcon);
		}
		this.loader = loader;
	}
}
