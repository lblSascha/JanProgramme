package picture_generator;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


public class PictureGen_GUI extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2461932396336943637L;

	private JFrame generatorView;

	private JPanel topPanel;

	private JPanel separator;

	private JTextArea textField;

	private JPanel buttonPanel;

	private JPanel buttons;

	public JButton saveButton;
	
	public JButton readyButton;

	private String blub;

	public void setBlub(String blub) {
		this.blub = blub;
	}

	public JTextArea getTextField() {
		return textField;
	}

	public String getBlub() {
		return blub;
	}

	public PictureGen_GUI() {
		blub = "Kein Text eingegeben";

		generatorView = new JFrame("Picture Generator");
		generatorView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		generatorView.setMinimumSize(new Dimension(416, 495));
		generatorView.setResizable(false);
		generatorView.setLocationRelativeTo(null);
		generatorView.setVisible(true);

		createButtons();

		generatorView.add(topPanel, BorderLayout.NORTH);
		generatorView.add(BorderLayout.CENTER, this);
		generatorView.add(buttonPanel, BorderLayout.SOUTH);

		generatorView.pack();
	}

	private void createButtons() {
		buttonPanel = new JPanel(new BorderLayout());
		buttons = new JPanel(new FlowLayout());

		topPanel = new JPanel(new BorderLayout());
		separator = new JPanel(new BorderLayout());

		saveButton = new JButton("Speichern");
		readyButton = new JButton("Fertig");

		textField = new JTextArea("Gewünschten Text eingeben",0,0);
		 textField.getDocument().putProperty("filterNewlines",
	                Boolean.TRUE);
		textField.setSelectionEnd(10);
		
		topPanel.add(textField);
		topPanel.setBorder(BorderFactory.createEmptyBorder(0, // top
				0, // left
				1, // bottom
				0)); // right);
		separator.add(new JSeparator(SwingConstants.HORIZONTAL));

		buttonPanel.add(new JSeparator(SwingConstants.HORIZONTAL),
				BorderLayout.NORTH);
		buttonPanel.add(buttons, BorderLayout.SOUTH);
		buttons.add(readyButton);
		buttons.add(saveButton);

		// On click removes text
		textField.addMouseListener(new MouseListener() {

			private int clicks = 0;

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (clicks < 1) {
					textField.setText("");
				}
				clicks++;
			}
		});

		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_ENTER) {
					blub = blub + "\n";
					
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		
		DocumentFilter filter = new UppercaseDocumentFilter();
		((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);

	}

	public void addActionController(ActionListener controller) {
		saveButton.addActionListener(controller);
		readyButton.addActionListener(controller);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(
	            RenderingHints.KEY_TEXT_ANTIALIASING,
	            RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
	    g2.setRenderingHints(rh);
		
		g2.setBackground(Color.decode("#dc072f"));
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		Font font = new Font("Arial", Font.BOLD, 35);
		g2.setFont(font);

		g2.setColor(Color.WHITE);

		int stringLen = (int) g2.getFontMetrics().getStringBounds(blub, g2)
				.getWidth();

		int start = getWidth() / 2 - stringLen / 2;

		g2.drawString(blub, start, getHeight() / 2);
	}

	public static void saveCanvas(Canvas canvas) {

		JFileChooser chooser = new JFileChooser();
		// Dialog zum Speichern von Dateien anzeigen
		chooser.setSelectedFile(new File(".png")); // TODO erstes wort, als name
		int retrival = chooser.showSaveDialog(null);

		if (retrival == JFileChooser.APPROVE_OPTION) {

			BufferedImage image = new BufferedImage(canvas.getWidth(),
					canvas.getHeight(), BufferedImage.TYPE_INT_RGB);

			Graphics2D g2 = (Graphics2D) image.getGraphics();

			canvas.paint(g2);
			try {
				ImageIO.write(image, "png", new File(chooser.getSelectedFile()
						+ ""));
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Es ist ein Fehler aufgetreten beim Speichern. Ein Neuversuch sollte das Problem lösen :)",
								"Warnung", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Sets the Look & Feel of the interface.
	 */
	public static void systemLookandFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			System.err.println("Unsupported Look and Feel Exception!");
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found Exception!");
		} catch (InstantiationException e) {
			System.err.println("Instantiation Exception!");
		} catch (IllegalAccessException e) {
			System.err.println("Illegal Acces Exception!");
		}
	}
	
	class DocFilter extends DocumentFilter{

        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            fb.insertString(offset, string.replaceAll("\\n", ""), attr); 
        }

       public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
            fb.insertString(offset, string.replaceAll("\\n", ""), attr); 
        }
    }

	class UppercaseDocumentFilter extends DocumentFilter {
		//
		// Override insertString method of DocumentFilter to make the text
		// format
		// to uppercase.
		//
		public void insertString(DocumentFilter.FilterBypass fb, int offset,
				String text, AttributeSet attr) throws BadLocationException {

			fb.insertString(offset, text.toUpperCase(), attr);
		}

		//
		// Override replace method of DocumentFilter to make the text format
		// to uppercase.
		//
		public void replace(DocumentFilter.FilterBypass fb, int offset,
				int length, String text, AttributeSet attrs)
				throws BadLocationException {

			fb.replace(offset, length, text.toUpperCase(), attrs);
		}
	}
}
