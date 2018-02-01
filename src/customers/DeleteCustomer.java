package customers;

import main.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteCustomer extends JInternalFrame {
    static DatabaseConnection dbConnection = new DatabaseConnection();

    public DeleteCustomer() {
        super("Delete Customer", false, true, false, true);
        JPanel container = new JPanel(new GridLayout(0, 1));
        JPanel panel_1 = new JPanel((LayoutManager)null);
        panel_1.setBorder(BorderFactory.createTitledBorder("Find Customer by ID to delete"));
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
        JButton btnFindUser = new JButton("Delete Customer");
        btnFindUser.setBounds(325, 70, 200, 30);
        InputMap im1 = btnFindUser.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnFindUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtUID.getText().toString().equals("")) {
                    int reuslt = DeleteCustomer.dbConnection.deleteCustomer(Integer.parseInt(txtUID.getText().toString()));
                    KeyboardFocusManager manager;
                    if (reuslt != 0) {
                        JOptionPane.showInternalMessageDialog(DeleteCustomer.this.getContentPane(), "Customer deleted!!!", "Success", 1);
                        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        manager.focusPreviousComponent();
                    } else {
                        JOptionPane.showInternalMessageDialog(DeleteCustomer.this.getContentPane(), "customer not found nothing deleted!!!", "ERROR/NOT FOUND", 0);
                        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        manager.focusPreviousComponent();
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(DeleteCustomer.this.getContentPane(), "Please enter customer ID ???", "BLANK ID", 0);
                    KeyboardFocusManager managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    managerx.focusPreviousComponent();
                }

            }
        });
        panel_1.add(lblUID);
        panel_1.add(txtUID);
        panel_1.add(btnFindUser);
        container.add(panel_1);
        this.add(container);
    }
}
