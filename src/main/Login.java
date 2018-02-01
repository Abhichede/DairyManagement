package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Login extends JInternalFrame {
    DatabaseConnection dbConnection = new DatabaseConnection();
    Object currentObject = null;

    public Login() {
        super("User Login", false, false, false, false);
        this.currentObject = this;
        JPanel container = new JPanel((LayoutManager)null);
        JLabel lblFrameText = new JLabel("User Login");
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
        JButton btnAddUser = new JButton("Login", new ImageIcon(this.getClass().getResource("/images/login.PNG")));
        btnAddUser.setBounds(250, 150, 100, 30);
        InputMap im1 = btnAddUser.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strUsername = txtUsername.getText().toString();
                String strPassword = txtPassword.getText().toString();
                if (!strUsername.equals("") && !strPassword.equals("")) {
                    ResultSet resultSet = null;

                    try {
                        resultSet = Login.this.dbConnection.getUserByUnamePass(strUsername, strPassword);
                        if (resultSet.next()) {
                            JOptionPane.showInternalMessageDialog(Login.this.getContentPane(), "Login succeed...!!!");
                            int uid = resultSet.getInt("uid");
                            Login.this.dbConnection.updateLoginStatus(true, uid);
                            Main.changeUserLoginStatus(true);
                            System.out.println("Login Status: " + Main.userLoggedInStatus);
                        } else {
                            JOptionPane.showInternalMessageDialog(Login.this.getContentPane(), "Wrong Username or Password...!!!", "Error", 0);
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Login.this.getContentPane(), "Please enter username and password!!!", "Error", 0);
                }

            }
        });
        JButton btnClose = new JButton("Close", new ImageIcon(this.getClass().getResource("/images/cancel.PNG")));
        btnClose.setBounds(400, 150, 100, 30);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        container.add(lblFrameText);
        container.add(lblUsername);
        container.add(txtUsername);
        container.add(lblPassword);
        container.add(txtPassword);
        container.add(btnAddUser);
        container.add(btnClose);
        this.add(container);
    }
}
