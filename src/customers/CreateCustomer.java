package customers;

import main.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateCustomer extends JInternalFrame {
    static DatabaseConnection dbConnection = new DatabaseConnection();
    private DefaultListSelectionModel model = new DefaultListSelectionModel();

    public CreateCustomer() {
        super("Create Customer", false, true, false, true);
        JPanel container = new JPanel((LayoutManager)null);
        JLabel lblFrameText = new JLabel("Create Customer");
        lblFrameText.setBounds(270, 10, 500, 20);
        Font frameTextFont = new Font("Serif", 1, 12);
        lblFrameText.setFont(frameTextFont);
        JLabel lblCustomerName = new JLabel("Customer Name");
        lblCustomerName.setBounds(200, 50, 100, 30);
        final JTextField txtCustomerName = new JTextField(15);
        txtCustomerName.setBounds(325, 50, 150, 30);
        lblCustomerName.setLabelFor(txtCustomerName);
        txtCustomerName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblMobileNo = new JLabel("Mobile No");
        lblMobileNo.setBounds(200, 100, 100, 30);
        final JTextField txtMobileNo = new JTextField(15);
        txtMobileNo.setBounds(325, 100, 150, 30);
        lblMobileNo.setLabelFor(txtMobileNo);
        txtMobileNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(200, 150, 100, 30);
        final JTextField txtAddress = new JTextField(15);
        txtAddress.setBounds(325, 150, 150, 30);
        lblAddress.setLabelFor(txtAddress);
        txtAddress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        String[] cattleTypes = new String[]{"COW", "BUFFALO"};
        JLabel lblCattleType = new JLabel("Cattle Type");
        lblCattleType.setBounds(200, 200, 100, 30);
        final JComboBox<String> comboCattleType = new JComboBox(cattleTypes);
        comboCattleType.setBounds(325, 200, 150, 30);
        JButton btnAddUser = new JButton("Add Customer");
        btnAddUser.setBounds(270, 280, 150, 30);
        InputMap im1 = btnAddUser.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strCustomerName = txtCustomerName.getText().toString();
                String strMobileNo = txtMobileNo.getText().toString();
                String strCustomerAddress = txtAddress.getText().toString();
                String strCattleType = (String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex());
                if (!strCustomerName.equals("") && !strMobileNo.equals("") && !strCustomerAddress.equals("")) {
                    if (txtMobileNo.getText().toString().length() != 10) {
                        JOptionPane.showInternalMessageDialog(CreateCustomer.this.getContentPane(), "Please enter valid mobile no.", "Blank Error", 0);
                    } else {
                        int result = CreateCustomer.dbConnection.addCustomer(strCustomerName, strMobileNo, strCustomerAddress, strCattleType, CreateCustomer.this.getContentPane());
                        KeyboardFocusManager managerx;
                        if (result == 1) {
                            txtCustomerName.setText("");
                            txtMobileNo.setText("");
                            txtAddress.setText("");
                            managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                            managerx.focusPreviousComponent(txtMobileNo);
                        } else {
                            managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                            managerx.focusPreviousComponent(txtMobileNo);
                        }
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(CreateCustomer.this.getContentPane(), "Please fill all the fields.", "Blank Error", 0);
                    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusPreviousComponent(txtMobileNo);
                }

            }
        });
        container.add(lblFrameText);
        container.add(lblCustomerName);
        container.add(txtCustomerName);
        container.add(lblMobileNo);
        container.add(txtMobileNo);
        container.add(lblAddress);
        container.add(txtAddress);
        container.add(lblCattleType);
        container.add(comboCattleType);
        container.add(btnAddUser);
        this.add(container);
    }
}
