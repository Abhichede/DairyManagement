package customers;

import main.DatabaseConnection;
import main.Printsupport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class PaymentHistory extends JInternalFrame {
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

    public PaymentHistory() {
        super("Payment History", true, true, true, true);
        JPanel panelMain = new JPanel(new BorderLayout(10, 20));
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel lblCustomerId = new JLabel("Customer ID/Code: ");
        final JTextField txtCustomerId = new JTextField(10);
        txtCustomerId.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaymentHistory.this.manager.focusNextComponent();
            }
        });
        JButton btnSearch = new JButton("Search");
        JButton btnPrint = new JButton("Print", new ImageIcon(this.getClass().getResource("/images/print.PNG")));
        final JTable usersTable = new JTable();
        usersTable.setSelectionMode(0);
        usersTable.setAutoResizeMode(4);
        String[] column = new String[]{"Date", "Litres", "FAT", "SNF", "Lacto", "Rate", "Total"};
        defaultTableModel = new DefaultTableModel(0, 0);
        defaultTableModel.setColumnIdentifiers(column);
        usersTable.setModel(defaultTableModel);
        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        usersTable.setFillsViewportHeight(true);
        InputMap im1 = btnSearch.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtCustomerId.getText().toString().equals("")) {
                    JOptionPane.showInternalMessageDialog(PaymentHistory.this.getContentPane(), "Please enter Customer Code.", "Error", 0);
                } else {
                    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                    ResultSet resultSet = null;
                    PaymentHistory.strCustomerId = Integer.parseInt(txtCustomerId.getText().toString());
                    String[] data = new String[7];
                    resultSet = PaymentHistory.dbConnection.getPaymentHistory(PaymentHistory.strCustomerId);
                    ResultSet customerResultSet = PaymentHistory.dbConnection.getCustomer(PaymentHistory.strCustomerId);
                    String strCattleType = "";
                    if (PaymentHistory.defaultTableModel.getRowCount() > 0) {
                        for(int i = PaymentHistory.defaultTableModel.getRowCount() - 1; i > -1; --i) {
                            PaymentHistory.defaultTableModel.removeRow(i);
                        }
                    }

                    try {
                        if (customerResultSet.next()) {
                            strCattleType = customerResultSet.getString("cattle_type");
                        }

                        while(resultSet.next()) {
                            data[0] = myFormat.format(myFormat.parse(resultSet.getString("created_at")));
                            data[1] = resultSet.getString("total_litres");
                            data[2] = resultSet.getString("avg_fat");
                            data[3] = resultSet.getString("avg_snf");
                            data[4] = resultSet.getString("avg_lacto");
                            data[5] = resultSet.getString("avg_rate");
                            data[6] = resultSet.getString("total_amount");
                            ++PaymentHistory.tableRowCount;
                            PaymentHistory.defaultTableModel.addRow(data);
                        }
                    } catch (Exception var8) {
                        var8.printStackTrace();
                    }
                }

            }
        });
        btnPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtCustomerId.getText().toString().equals("")) {
                    String custoemrName = "";
                    String dairyName = "";
                    String dairyAddress = "";
                    ResultSet resultSet = PaymentHistory.dbConnection.getCustomer(Integer.parseInt(txtCustomerId.getText().toString()));
                    ResultSet currentUser = PaymentHistory.dbConnection.getCurrentUser();

                    try {
                        if (resultSet.next()) {
                            custoemrName = resultSet.getString("cust_name");
                        }

                        if (currentUser.next()) {
                            dairyName = currentUser.getString("dairy_name");
                            dairyAddress = currentUser.getString("dairy_address");
                        }
                    } catch (Exception var12) {
                        var12.printStackTrace();
                    }

                    printingJobs.PaymentHistory dailyCollectionPrintJob = new printingJobs.PaymentHistory(usersTable, dairyName, dairyAddress, custoemrName);
                    Printsupport ps = new Printsupport();
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 21.0D, 29.7D, 1));

                    try {
                        pj.print();
                    } catch (PrinterException var11) {
                        var11.printStackTrace();
                        JOptionPane.showInternalMessageDialog(PaymentHistory.this.getContentPane(), var11, "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(PaymentHistory.this.getContentPane(), "Please enter customer ID / Code.", "Error", 0);
                }

            }
        });
        topPanel.add(lblCustomerId);
        topPanel.add(txtCustomerId);
        topPanel.add(btnSearch);
        topPanel.add(btnPrint);
        bottomPanel.add(tableScrollPane, "Center");
        panelMain.add("First", topPanel);
        panelMain.add("Center", bottomPanel);
        this.add(panelMain);
    }
}
