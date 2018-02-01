package customers;

import main.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class UpdateCustomer extends JInternalFrame {
    static DatabaseConnection dbConnection = new DatabaseConnection();

    public UpdateCustomer() {
        super("Edit/Update Customer", true, true, false, true);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, 3));
        JPanel panel_1 = new JPanel((LayoutManager)null);
        panel_1.setBorder(BorderFactory.createTitledBorder("Find customer by ID"));
        JPanel panel_2 = new JPanel((LayoutManager)null);
        panel_2.setBorder(BorderFactory.createTitledBorder("Update Info"));
        JScrollPane tableScrollPane = new JScrollPane(panel_2);
        JLabel lblUID = new JLabel("Customer ID");
        lblUID.setBounds(200, 35, 100, 30);
        final JTextField txtUID = new JTextField(10);
        txtUID.setBounds(325, 35, 150, 30);
        txtUID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JButton btnFindUser = new JButton("Find User");
        btnFindUser.setBounds(325, 70, 100, 30);
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
        comboCattleType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JButton btnAddCustomer = new JButton("Edit/Update");
        btnAddCustomer.setBounds(250, 250, 200, 30);
        InputMap im = btnFindUser.getInputMap(0);
        im.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        InputMap im1 = btnAddCustomer.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnFindUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtUID.getText().toString().equals("")) {
                    ResultSet resultSet = UpdateCustomer.dbConnection.getCustomer(Integer.parseInt(txtUID.getText().toString()));

                    try {
                        if (resultSet.next()) {
                            txtCustomerName.setText(resultSet.getString("cust_name"));
                            txtMobileNo.setText(resultSet.getString("cust_contact"));
                            txtAddress.setText(resultSet.getString("cust_address"));
                            comboCattleType.setSelectedItem(resultSet.getString("cattle_type"));
                        } else {
                            JOptionPane.showInternalMessageDialog(UpdateCustomer.this.getContentPane(), "Customer not found", "Not Found", 0);
                        }
                    } catch (Exception var4) {
                        JOptionPane.showInternalMessageDialog(UpdateCustomer.this.getContentPane(), var4, "Something went wrong", 0);
                    }

                    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusNextComponent();
                } else {
                    JOptionPane.showInternalMessageDialog(UpdateCustomer.this.getContentPane(), "Please enter user ID to find user.", "Blank Error", 0);
                }

            }
        });
        btnAddCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtUID.getText().toString().equals("")) {
                    int intUID = Integer.parseInt(txtUID.getText().toString());
                    String strCustomerName = txtCustomerName.getText().toString();
                    String strMobileNo = txtMobileNo.getText().toString();
                    String strCustomerAddress = txtAddress.getText().toString();
                    String strCattleType = ((String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex())).toString();
                    if (!strCustomerName.equals("") && !strMobileNo.equals("") && !strCustomerAddress.equals("")) {
                        if (txtMobileNo.getText().toString().length() != 10) {
                            JOptionPane.showInternalMessageDialog(UpdateCustomer.this.getContentPane(), "Please enter valid mobile number.", "Error", 0);
                        } else {
                            int result = UpdateCustomer.dbConnection.updateCustomer(intUID, strCustomerName, strMobileNo, strCustomerAddress, strCattleType, UpdateCustomer.this.getContentPane());
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
                        JOptionPane.showInternalMessageDialog(UpdateCustomer.this.getContentPane(), "Please fill all the fields.", "Blank Error", 0);
                        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        manager.focusPreviousComponent(txtMobileNo);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(UpdateCustomer.this.getContentPane(), "Please enter customer ID to find user.", "Blank Error", 0);
                }

            }
        });
        panel_1.add(lblUID);
        panel_1.add(txtUID);
        panel_1.add(btnFindUser);
        panel_2.add(lblCustomerName);
        panel_2.add(txtCustomerName);
        panel_2.add(lblMobileNo);
        panel_2.add(txtMobileNo);
        panel_2.add(lblAddress);
        panel_2.add(txtAddress);
        panel_2.add(lblCattleType);
        panel_2.add(comboCattleType);
        panel_2.add(btnAddCustomer);
        container.add("First", panel_1);
        container.add("Center", tableScrollPane);
        this.add(container);
    }
}
