import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CalculadoraAngulos implements ActionListener {

	private JFrame frame;
	private JTextField pantalla;
	private Angle anguloActual;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculadoraAngulos window = new CalculadoraAngulos();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
	}

	public CalculadoraAngulos() {
		initialize();
		anguloActual = new Angle();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setTitle("Calculadora de Ángulos");

		pantalla = new JTextField();
		pantalla.setFont(new Font("Arial", Font.PLAIN, 24));
		pantalla.setHorizontalAlignment(JTextField.RIGHT);
		pantalla.setBackground(new Color(240, 240, 240));
		frame.getContentPane().add(pantalla, BorderLayout.NORTH);

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(4, 4, 10, 10));
		panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		String[] buttonText = { "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "=", "0", ":", "C", "Borrar" };

		for (String text : buttonText) {
			crearBoton(text, panelBotones);
		}

		frame.getContentPane().add(panelBotones, BorderLayout.CENTER);

		// Menú
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu archivoMenu = new JMenu("Archivo");
		archivoMenu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(archivoMenu);

		JMenuItem aboutMenuItem = new JMenuItem("Acerca de", KeyEvent.VK_B);
		aboutMenuItem.addActionListener(e -> mostrarVentanaAcercaDe());
		archivoMenu.add(aboutMenuItem);
	}

	private void crearBoton(String texto, JPanel panel) {
		JButton boton = null;
		if (texto.equals("Borrar")) {
			boton = new JButton();
			boton.setIcon(new ImageIcon("src/delete.png"));
		} else {
			boton = new JButton(texto);
		}
		boton.setFont(new Font("Arial", Font.BOLD, 18));
		boton.setFocusable(false);
		boton.setName(texto);
		boton.addActionListener(this);
		panel.add(boton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton boton = (JButton) e.getSource();
		String textoBoton = boton.getName();

		if (textoBoton.equals("Borrar")) {
			onBorrarClick();
		} else {
			onButtonClick(textoBoton);
		}
	}

	private void onButtonClick(String text) {
		if (text.equals("=")) {
			calcularResultado();
			anguloActual = new Angle();
		} else if (text.equals("C")) {
			pantalla.setText("");
			anguloActual = new Angle();
		} else {
			pantalla.setText(pantalla.getText() + text);
		}
	}

	private void onBorrarClick() {
		String textoPantalla = pantalla.getText();
		if (!textoPantalla.isEmpty()) {
			pantalla.setText(textoPantalla.substring(0, textoPantalla.length() - 1));
		}
	}

	private void calcularResultado() {
		try {
			String expresion = pantalla.getText().replace(" ", "");
			if (expresion.isEmpty()) {
	            throw new IllegalArgumentException("Expresión vacía. Ingrese una expresión válida.");
	        }
			
			if (!expresion.matches("^[0-9: +-]+$")) {
	            throw new IllegalArgumentException("Expresión no válida. Use solo números, :, + o -.");
	        }
			
			
			// pone espacios a la derecha y a la izquierda del - o + que este detras de un numero
			expresion = expresion.replaceAll("(?<=\\d)([+\\-])", " $1 ").trim();


			String[] partes = expresion.split("\\s+");
			anguloActual = new Angle(partes[0]);

			for (int i = 1; i < partes.length; i += 2) {
				String operador = partes[i];
				Angle siguienteAngulo = new Angle(partes[i + 1]);

				if (operador.equals("+")) {
					anguloActual.sumar(siguienteAngulo);
				} else if (operador.equals("-")) {
					anguloActual.restar(siguienteAngulo);
				} else {
					throw new IllegalArgumentException("Operador no válido. Use solo '+' o '-'.");
				}
			}

			anguloActual.normalizar();

			pantalla.setText(anguloActual.toString());
		} catch (Exception ex) {
			//pantalla.setText("Error");
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		}
	}




	private void mostrarVentanaAcercaDe() {
		JFrame aboutFrame = new JFrame("Acerca de");
		aboutFrame.setSize(300, 200);
		aboutFrame.setLocationRelativeTo(frame);

		JLabel label = new JLabel("Calculadora de Ángulos v1.0");
		label.setHorizontalAlignment(JLabel.CENTER);

		aboutFrame.getContentPane().add(label);
		aboutFrame.setVisible(true);
	}
}
