package picture_generator;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PictureGen_GUI extends Canvas implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2461932396336943637L;

	private JFrame generatorView;

	private JPanel topPanel;

	private JPanel utilityPanel;

	private JPanel separator;

	private JTextField textField;

	private JPanel buttonPanel;

	private JPanel buttons;

	public JButton saveButton;

	private String blub;

	public PictureGen_GUI() {
		generatorView = new JFrame("Not a Virus");
		generatorView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		generatorView.setMinimumSize(new Dimension(400, 400));
		generatorView.setLocationRelativeTo(null);
		generatorView.setVisible(true);

		createButtons();

		generatorView.add(topPanel, BorderLayout.NORTH);
		generatorView.add(BorderLayout.CENTER, this);
		generatorView.add(buttonPanel, BorderLayout.SOUTH);

		generatorView.pack();
	}

	public JTextField getTextField() {
		return textField;
	}

	public JPanel getutilityPanel() {
		return utilityPanel;
	}

	private void createButtons() {
		buttonPanel = new JPanel(new BorderLayout());
		buttons = new JPanel(new FlowLayout());

		topPanel = new JPanel(new BorderLayout());
		separator = new JPanel(new BorderLayout());

		saveButton = new JButton("Speichern");

		textField = new JTextField("Gewünschten Text eingeben");
		topPanel.add(textField);
		topPanel.setBorder(BorderFactory.createEmptyBorder(0, // top
				0, // left
				1, // bottom
				0)); // right);
		separator.add(new JSeparator(SwingConstants.HORIZONTAL));

		buttonPanel.add(new JSeparator(SwingConstants.HORIZONTAL),
				BorderLayout.NORTH);
		buttonPanel.add(buttons, BorderLayout.SOUTH);
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
					blub = textField.getText();
					repaint();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER) {
					blub = textField.getText();
				}
			}
		});
	}

	public void addActionController(ActionListener controller) {
		saveButton.addActionListener(controller);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.decode("#dc072f"));
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		Font font = new Font("Arial", Font.BOLD, 12);
		g.setFont(font);
		blub = "Kein Text eingegeben";
		g2.setColor(Color.BLACK);

		int stringLen = (int) g2.getFontMetrics().getStringBounds(blub, g2)
				.getWidth();

		int start = getWidth() / 2 - stringLen / 2;

		g2.drawString(blub, start, getHeight() / 2);
	}

	public void update(Observable arg0, Object arg1) {

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
				ImageIO.write(image, "png", new File(chooser.getSelectedFile() + ""));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Es ist ein Fehler aufgetreten beim Speichern. Ein Neuversuch sollte das Problem lösen :)", "Warnung",
				        JOptionPane.WARNING_MESSAGE);
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
}
