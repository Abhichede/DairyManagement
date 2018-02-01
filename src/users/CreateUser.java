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

public class CreateUser extends JInternalFrame {
    static DatabaseConnection dbConnection = new DatabaseConnection();

    public CreateUser() {
        super("Create User", false, true, false, true);
        JPanel container = new JPanel((LayoutManager)null);
        JLabel lblFrameText = new JLabel("Create User");
        lblFrameText.setBounds(270, 10, 500, 20);
        Font frameTextFont = new Font("Serif", 1, 12);
        lblFrameText.setFont(frameTextFont);
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
        final JPasswordField txtPassword = new JPasswordField(15);
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
        JButton btnAddUser = new JButton("Add User");
        btnAddUser.setBounds(250, 250, 100, 30);
        InputMap im1 = btnAddUser.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strUsername = txtUsername.getText().toString();
                String strPassword = txtPassword.getText().toString();
                String strDairyName = txtDairyName.getText().toString();
                String strDairyAddress = txtDairyAddress.getText().toString();
                if (!strUsername.equals("") && !strPassword.equals("")) {
                    int result = CreateUser.dbConnection.addUser(strUsername, strPassword, strDairyName, strDairyAddress, CreateUser.this.getContentPane());
                    KeyboardFocusManager managerx;
                    if (result == 1) {
                        txtUsername.setText("");
                        txtPassword.setText("");
                        managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        managerx.focusPreviousComponent(txtPassword);
                    } else {
                        managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                        managerx.focusPreviousComponent(txtPassword);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(CreateUser.this.getContentPane(), "Please fill all the fields.", "Blank Error", 0);
                    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusPreviousComponent(txtPassword);
                }

            }
        });
        container.add(lblFrameText);
        container.add(lblUsername);
        container.add(txtUsername);
        container.add(lblPassword);
        container.add(txtPassword);
        container.add(lblDairyName);
        container.add(txtDairyName);
        container.add(lblDairyAddress);
        container.add(txtDairyAddress);
        container.add(btnAddUser);
        this.add(container);
    }
}
