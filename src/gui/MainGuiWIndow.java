package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import main.Main;
import utils.FilterType;

public class MainGuiWIndow {

	private JFrame frame;
	private JTextField txtSource;
	private JTextField txtResult;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGuiWIndow window = new MainGuiWIndow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGuiWIndow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JTextArea txtResultString = new JTextArea();
		txtResultString.setEditable(false);
		txtResultString.setWrapStyleWord(true);
		txtResultString.setLineWrap(true);
		txtResultString.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtResultString
				.setText("Gotowy do rozpocz\u0119cia konwersji. Wybierz plik \u017Ar\u00F3d\u0142owy i naci\u015Bnij rzycisk start.");
		frame.getContentPane().add(txtResultString, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 1, 0, 0));

		final JTextPane txtSrcblackbuckbmp1 = new JTextPane();
		txtSrcblackbuckbmp1.setEditable(false);
		txtSrcblackbuckbmp1
				.setText("Podaj nazw\u0119 pliku \u017Ar\u00F3d\u0142owego:");
		panel.add(txtSrcblackbuckbmp1);

		txtSource = new JTextField();
		txtSource.setText("src/blackbuck (1).bmp");
		panel.add(txtSource);
		txtSource.setColumns(10);

		JTextPane txtField_1 = new JTextPane();
		txtField_1.setEditable(false);
		txtField_1.setText("Podaj nazw\u0119 pliku wynikowego:");
		panel.add(txtField_1);

		txtResult = new JTextField();
		txtResult.setText("src/testPNG.png");
		panel.add(txtResult);
		txtResult.setColumns(10);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(2, 1, 0, 0));

		JTextPane txtpnTypFiltru = new JTextPane();
		txtpnTypFiltru.setEditable(false);
		txtpnTypFiltru.setText("Typ Filtru:");
		panel_1.add(txtpnTypFiltru);

		final JComboBox<FilterType> comboBox = new JComboBox<FilterType>();
		comboBox.setModel(new DefaultComboBoxModel<FilterType>(FilterType
				.values()));
		panel_1.add(comboBox);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterType type = (FilterType) comboBox.getSelectedItem();
				String result = Main.run(txtSource.getText(), type.ordinal(),
						txtResult.getText());
				txtResultString.setText(result);
				// JFrame png = new PictureWindow(txtResult.getSelectedText());
				// Thread.sleep(15);
				// png.setVisible(true);
			}
		});
		frame.getContentPane().add(btnNewButton, BorderLayout.EAST);

	}
}
