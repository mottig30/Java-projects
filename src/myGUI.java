
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Label;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class myGUI extends JFrame {

	private JPanel contentPane;
	private JTextField numOfCommanders;
	private JTextField workTime;
	private JTextField numOfMotorCycles;
	private JTextField numOfCars;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myGUI frame = new myGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public myGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLUE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel("Welcome To BGU Police Station");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 19));
		lblNewLabel.setBounds(65, 11, 323, 47);
		contentPane.add(lblNewLabel);
		numOfCommanders = new JTextField();
		numOfCommanders.setText("0");
		numOfCommanders.setBounds(53, 89, 86, 20);
		contentPane.add(numOfCommanders);
		numOfCommanders.setColumns(10);

		Label label = new Label("number of event commanders to add");
		label.setForeground(Color.WHITE);
		label.setBounds(27, 64, 205, 19);
		contentPane.add(label);

		Label label_1 = new Label("work time for station worker");
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(240, 64, 160, 19);
		contentPane.add(label_1);

		workTime = new JTextField();
		workTime.setText("1");
		workTime.setBounds(263, 89, 86, 20);
		contentPane.add(workTime);
		workTime.setColumns(10);

		numOfMotorCycles = new JTextField();
		numOfMotorCycles.setText("0");
		numOfMotorCycles.setBounds(53, 169, 86, 20);
		contentPane.add(numOfMotorCycles);
		numOfMotorCycles.setColumns(10);

		numOfCars = new JTextField();
		numOfCars.setText("0");
		numOfCars.setBounds(263, 169, 86, 20);
		contentPane.add(numOfCars);
		numOfCars.setColumns(10);

		Label label_2 = new Label("number of police Motorcycles to add");
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(27, 129, 200, 19);
		contentPane.add(label_2);

		Label label_3 = new Label("number of police cars to add");
		label_3.setForeground(Color.WHITE);
		label_3.setBounds(225, 129, 175, 19);
		contentPane.add(label_3);

		Button button = new Button("START");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int workingTime = (int) Double.parseDouble(workTime.getText());
				int motorcycle = Integer.parseInt(numOfMotorCycles.getText());
				int cars = Integer.parseInt(numOfCars.getText());
				int Commanders = Integer.parseInt(numOfCommanders.getText());
				main PoliceStation = new main(Commanders, motorcycle, cars, workingTime);
				try {
					PoliceStation.startStation(); // starting the station
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		button.setBounds(70, 212, 69, 19);
		contentPane.add(button);
		Button button_1 = new Button("EXIT");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button_1.setBounds(273, 212, 69, 19);
		contentPane.add(button_1);
	}
}