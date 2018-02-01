//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package users;

import main.DatabaseConnection;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class ViewUsers extends JInternalFrame {
    static DatabaseConnection dbConnection = new DatabaseConnection();

    public ViewUsers() {
        super("Create User", false, true, false, true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        panel.setBorder(new LineBorderUIResource(Color.gray, 1));
        String[] column = new String[]{"UID", "Username", "Password"};
        String[] data = new String[3];
        JTable usersTable = new JTable();
        usersTable.setSelectionMode(0);
        usersTable.setAutoResizeMode(4);
        DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
        defaultTableModel.setColumnIdentifiers(column);
        usersTable.setModel(defaultTableModel);

        try {
            ResultSet resultSet = dbConnection.getAllUsers();

            while(resultSet.next()) {
                data[0] = resultSet.getString("uid");
                data[1] = resultSet.getString("username");
                data[2] = resultSet.getString("password");
                defaultTableModel.addRow(data);
            }
        } catch (Exception var7) {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), var7, "Error", 0);
        }

        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        usersTable.setFillsViewportHeight(true);
        panel.add(tableScrollPane);
        this.add(panel);
    }
}
