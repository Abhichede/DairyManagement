//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package milk_management;

import main.DatabaseConnection;
import main.Printsupport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class TotalCollection extends JInternalFrame {
    static DefaultTableModel defaultTableModel;
    static DatabaseConnection dbConnection = new DatabaseConnection();
    static Double avgFat;
    static Double avgLacto;
    static Double avgSnf;
    static Double avgRate;
    static Double totLitres;
    static Double totAmount;
    static int tableRowCount;
    static int strCustomerId;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

    public TotalCollection() {
        super("Profile", true, true, true, true);
        JPanel panelMain = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        new JPanel();
        DecimalFormat decimalFormat = new DecimalFormat("0.00##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        final JTable usersTable = new JTable();
        usersTable.setSelectionMode(0);
        usersTable.setAutoResizeMode(4);
        String[] column = new String[]{"PRD.Name", "Date-Time", "Shift", "Type", "Litre", "FAT", "Lacto", "SNF", "Rate", "Total"};
        String[] footerColumn = new String[]{"", "", "", "", "Tot. Litres", "Avg. FAT", "Avg. Lacto", "Avg. SNF", "Avg. Rate", "Total Amount"};
        defaultTableModel = new DefaultTableModel(0, 0);
        defaultTableModel.setColumnIdentifiers(column);
        usersTable.setModel(defaultTableModel);
        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        usersTable.setFillsViewportHeight(true);
        final JTable footerTable = new JTable();
        DefaultTableModel footerTableModel = new DefaultTableModel(0, 0);
        footerTableModel.setColumnIdentifiers(footerColumn);
        footerTable.setSelectionMode(0);
        footerTable.setAutoResizeMode(4);
        footerTable.setModel(footerTableModel);
        String[] data = new String[10];

        try {
            ResultSet resultSet = dbConnection.getDailyCollection();
            avgFat = 0.0D;
            avgLacto = 0.0D;
            avgSnf = 0.0D;
            avgRate = 0.0D;
            totLitres = 0.0D;
            totAmount = 0.0D;
            tableRowCount = 0;
            if (resultSet != null) {
                while(resultSet.next()) {
                    ResultSet customerResultSet = dbConnection.getCustomer(resultSet.getInt("customer_id"));
                    if (customerResultSet.next()) {
                        data[0] = customerResultSet.getString("cust_name");
                        data[1] = resultSet.getString("date");
                        data[2] = resultSet.getString("shift");
                        data[3] = customerResultSet.getString("cattle_type");
                        data[4] = resultSet.getString("litre");
                        data[5] = resultSet.getString("fat");
                        data[6] = resultSet.getString("lacto");
                        data[7] = resultSet.getString("snf");
                        data[8] = resultSet.getString("rate");
                        data[9] = resultSet.getString("total_price");
                        avgFat = avgFat.doubleValue() + Double.parseDouble(data[5]);
                        avgLacto = avgLacto.doubleValue() + Double.parseDouble(data[6]);
                        avgSnf = avgSnf.doubleValue() + Double.parseDouble(data[7]);
                        avgRate = avgRate.doubleValue() + Double.parseDouble(data[8]);
                        totLitres = totLitres.doubleValue() + Double.parseDouble(data[4]);
                        totAmount = totAmount.doubleValue() + Double.parseDouble(data[9]);
                        ++tableRowCount;
                        defaultTableModel.addRow(data);
                    }
                }
            } else {
                JOptionPane.showInternalMessageDialog(this.getContentPane(), "Empty", "Error", 0);
            }

            avgFat = avgFat.doubleValue() / (double)tableRowCount;
            avgLacto = avgLacto.doubleValue() / (double)tableRowCount;
            avgSnf = avgSnf.doubleValue() / (double)tableRowCount;
            avgRate = totAmount / totLitres;
            String[] data1 = new String[]{"", "", "", "Totals:", "" + decimalFormat.format(totLitres), "" + decimalFormat.format(avgFat), "" + decimalFormat.format(avgLacto), "" + decimalFormat.format(avgSnf), "" + avgRate, "" + decimalFormat.format(totAmount)};
            footerTableModel.addRow(data1);
        } catch (Exception var14) {
            var14.printStackTrace();
            JOptionPane.showInternalMessageDialog(this.getContentPane(), var14, "Error", 0);
        }

        bottomPanel.add(tableScrollPane, "Center");
        bottomPanel.add(footerTable, "South");
        JButton btnPrint = new JButton("Print");
        btnPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dairyName = "";
                String dairyAddress = "";
                ResultSet currentUser = TotalCollection.dbConnection.getCurrentUser();

                try {
                    if (currentUser.next()) {
                        dairyName = currentUser.getString("dairy_name");
                        dairyAddress = currentUser.getString("dairy_address");
                    }
                } catch (Exception var10) {
                    var10.printStackTrace();
                }

                printingJobs.TotalCollection dailyCollectionPrintJob = new printingJobs.TotalCollection(usersTable, footerTable, dairyName, dairyAddress);
                Printsupport ps = new Printsupport();
                PrinterJob pj = PrinterJob.getPrinterJob();
                pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 21.0D, 29.7D, 1));

                try {
                    pj.print();
                } catch (PrinterException var9) {
                    var9.printStackTrace();
                    JOptionPane.showInternalMessageDialog(TotalCollection.this.getContentPane(), var9, "Error", 0);
                }

            }
        });
        panelMain.add(bottomPanel, "Center");
        this.add(panelMain);
    }
}
