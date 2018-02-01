//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package users;

import main.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class UpdateUser extends JInternalFrame {
    static DatabaseConnection dbConnection = new DatabaseConnection();

    public UpdateUser() {
        super("Edit/Update User", false, true, false, true);
        JPanel container = new JPanel(new GridLayout(0, 1));
        JPanel panel_1 = new JPanel((LayoutManager)null);
        panel_1.setBorder(BorderFactory.createTitledBorder("Find User by ID"));
        JPanel panel_2 = new JPanel((LayoutManager)null);
        panel_2.setBorder(BorderFactory.createTitledBorder("Update Info"));
        JLabel lblUID = new JLabel("User ID");
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
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(200, 50, 100, 30);
        final JTextField txtUsername = new JTextField(15);
        txtUsername.setBounds(325, 50, 150, 30);
        lblUsername.setLabelFor(txtUsername);
        txtUsername.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(200, 100, 100, 30);
        final JTextField txtPassword = new JTextField(15);
        txtPassword.setBounds(325, 100, 150, 30);
        lblPassword.setLabelFor(txtPassword);
        txtPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblDairyName = new JLabel("Dairy Name");
        lblDairyName.setBounds(200, 150, 100, 30);
        final JTextField txtDairyName = new JTextField(15);
        txtDairyName.setBounds(325, 150, 150, 30);
        txtDairyName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblDairyAddress = new JLabel("Dairy Address");
        lblDairyAddress.setBounds(200, 200, 100, 30);
        final JTextField txtDairyAddress = new JTextField(15);
        txtDairyAddress.setBounds(325, 200, 150, 30);
        txtDairyAddress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JButton btnAddUser = new JButton("Edit/Update");
        btnAddUser.setBounds(250, 250, 200, 30);
        InputMap im = btnFindUser.getInputMap(0);
        im.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        InputMap im1 = btnAddUser.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnFindUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtUID.getText().toString().equals("")) {
                    ResultSet resultSet = UpdateUser.dbConnection.getUser(Integer.parseInt(txtUID.getText().toString()));

                    try {
                        while(resultSet.next()) {
                            txtUsername.setText(resultSet.getString("username"));
                            txtPassword.setText(resultSet.getString("password"));
                            txtDairyName.setText(resultSet.getString("dairy_name"));
                            txtDairyAddress.setText(resultSet.getString("dairy_address"));
                        }
                    } catch (Exception var4) {
                        JOptionPane.showInternalMessageDialog(UpdateUser.this.getContentPane(), var4, "Something went wrong", 0);
                    }

                    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusNextComponent();
                } else {
                    JOptionPane.showInternalMessageDialog(UpdateUser.this.getContentPane(), "Please enter user ID to find user.", "Blank Error", 0);
                }

            }
        });
        btnAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtUID.getText().toString().equals("")) {
                    int intUID = Integer.parseInt(txtUID.getText().toString());
                    String strUsername = txtUsername.getText().toString();
                    String strPassword = txtPassword.getText().toString();
                    String strDairyAddress = txtDairyAddress.getText().toString();
                    String strDairyName = txtDairyName.getText().toString();
                    if (!strUsername.equals("") && !strPassword.equals("")) {
                        int result = UpdateUser.dbConnection.updateUser(intUID, strUsername, strPassword, strDairyName, strDairyAddress, UpdateUser.this.getContentPane());
                        KeyboardFocusManager managerx;
                        if (result == 1) {
                            txtUsername.setText("");
                            txtPassword.setText("");
                            txtDairyName.setText("");
                            txtDairyAddress.setText("");
                            managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                            managerx.focusPreviousComponent(txtPassword);
                        } else {
                            managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                            managerx.focusPreviousComponent(txtPassword);
                        }
                    } else {
                        JOptionPane.showInternalMessageDialog(UpdateUser.this.getContentPane(), "Please fill all the fields.", "Blank Error", 0);
                        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        manager.focusPreviousComponent(txtPassword);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(UpdateUser.this.getContentPane(), "Please enter user ID to find user.", "Blank Error", 0);
                }

            }
        });
        panel_1.add(lblUID);
        panel_1.add(txtUID);
        panel_1.add(btnFindUser);
        panel_2.add(lblUsername);
        panel_2.add(txtUsername);
        panel_2.add(lblPassword);
        panel_2.add(txtPassword);
        panel_2.add(lblDairyName);
        panel_2.add(txtDairyName);
        panel_2.add(lblDairyAddress);
        panel_2.add(txtDairyAddress);
        panel_2.add(btnAddUser);
        container.add(panel_1);
        container.add(panel_2);
        this.add(container);
    }
}
