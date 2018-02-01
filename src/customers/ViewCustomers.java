//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package customers;

import main.DatabaseConnection;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class ViewCustomers extends JInternalFrame {
    static DatabaseConnection dbConnection = new DatabaseConnection();

    public ViewCustomers() {
        super("Customers", false, true, false, true);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        panel.setBorder(new LineBorderUIResource(Color.gray, 1));
        String[] column = new String[]{"Customer ID", "Customer Name", "Mobile No", "Address", "Cattle Type"};
        String[] data = new String[5];
        JTable usersTable = new JTable();
        usersTable.setSelectionMode(0);
        usersTable.setAutoResizeMode(4);
        DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
        defaultTableModel.setColumnIdentifiers(column);
        usersTable.setModel(defaultTableModel);

        try {
            ResultSet resultSet = dbConnection.getAllCustomers();

            while(resultSet.next()) {
                data[0] = resultSet.getString("cust_id");
                data[1] = resultSet.getString("cust_name");
                data[2] = resultSet.getString("cust_contact");
                data[3] = resultSet.getString("cust_address");
                data[4] = resultSet.getString("cattle_type");
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
