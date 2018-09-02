import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SecretMessagesGUI extends JFrame {
	private JTextField txtKey;
	private JTextArea txtIn;
	private JTextArea txtOut;
	private JSlider slider;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	public String encode (String message, int keyVal)	{ //kodowanie tekstu, ponieważ char przesuwa się po unicodzie 
		//i może wyskoczyć po za zakres znaków łacinskich czy cyfr wiec są ify żeby się tak nie działo, metoda ma zdefiniowane parametry
		
		String output= "";


		char key = (char) keyVal;



		for (int x = 0; x  <message.length(); x++) {

			char input =message.charAt(x);
			if (input >= 'A' && input <='Z') {

				input+=key;
				if (input >'Z')
					input -=26;
				if (input <'A')
					input +=26;
			}
			else if (input >= 'a' && input <='z') {
				input+=key;
				if (input >'z')
					input -=26;
				if (input <'a')
					input +=26;	
			}
			else if (input >= '0' && input <='9') {
				input+=(keyVal%10);
				if (input > '9')
					input -=10;
				if (input <'0')
					input +=10;


			}
			output +=input;

		}

		return output; //metoda zwraca informacje
	}
	public SecretMessagesGUI() {
		getContentPane().setBackground(new Color(105, 105, 105));

		JList list = new JList();
		getContentPane().setSize(new Dimension(600, 400));
		setTitle("Sekretnik Cezara");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 574, 105);
		getContentPane().add(scrollPane);
		txtIn = new JTextArea();
		scrollPane.setViewportView(txtIn);
		txtIn.setWrapStyleWord(true);
		txtIn.setLineWrap(true);
		txtIn.setFont(new Font("Tibetan Machine Uni", Font.PLAIN, 18));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 177, 574, 105);
		getContentPane().add(scrollPane_1);
		txtOut = new JTextArea();
		scrollPane_1.setViewportView(txtOut);
		txtOut.setTabSize(15);
		txtOut.setWrapStyleWord(true); //zwijanie tekstu, to sie robi w properties w windows builiderze
		txtOut.setLineWrap(true); //zwuijanie tekstu jw
		txtOut.setFont(new Font("Tibetan Machine Uni", Font.PLAIN, 18));
		txtKey = new JTextField();
		txtKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				int key=0;
				try {
					key = Integer.parseInt(txtKey.getText());
				} catch (NumberFormatException e) {
					System.out.println("plese write number");
				}
				slider.setValue(key);
			}
		});
		txtKey.setBounds(263, 129, 42, 19);
		getContentPane().add(txtKey);
		txtKey.setColumns(10);

		JLabel lblNewLabel = new JLabel("Key:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(214, 129, 31, 19);
		getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Encode / Decode");
		btnNewButton.setBackground(new Color(220, 20, 60));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					String message = txtIn.getText();
					int key = Integer.parseInt(txtKey.getText());
					String output = encode(message,  key); //tu metoda jest przywołaana i ma parametry
					txtOut.setText(output);
				} catch (NumberFormatException e) { //wyskakujace okienko informujace ze coś jest źle
					JOptionPane.showMessageDialog(null, "Please kill me enter the whole number value for the encryption key.");
					txtKey.requestFocus();
					txtKey.selectAll(); 
				}

			}
		});
		btnNewButton.setBounds(317, 129, 150, 25);
		getContentPane().add(btnNewButton);

		slider = new JSlider();  //suwak do ustawiania klucza
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				txtKey.setText(""+slider.getValue());
				String message = txtIn.getText();
				int key = slider.getValue();
				String output = encode(message,  key); // to całe pozwala w trybie live podgladac output przyy zmieniajacym sie kluczu
				txtOut.setText(output);} //tryb live - poglad tego jak sie szyfruje przy przesuwaniu suwaka
		});
		slider.setValue(3);
		slider.setMaximum(26);
		slider.setMajorTickSpacing(13);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(-26);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBackground(new Color(105, 105, 105));
		slider.setBounds(12, 129, 200, 36);
		getContentPane().add(slider);
		
		JButton btnNewButton_1 = new JButton("Move up ^");
		btnNewButton_1.setBackground(new Color(0, 191, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
String message1= txtOut.getText();
				txtIn.setText(message1);
				int sliderVal = slider.getValue();
				int reverseSliderValue  = -sliderVal;
				slider.setValue(reverseSliderValue);
				
				
			}
		});
		btnNewButton_1.setBounds(479, 129, 107, 25);
		getContentPane().add(btnNewButton_1);


	}

	public static void main(String[] args) { //uruchomienie machiny-okna
		SecretMessagesGUI theApp = new SecretMessagesGUI();
		theApp.setSize(new java.awt.Dimension(600,400));
		theApp.setVisible(true);

	}
}
