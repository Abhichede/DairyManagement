//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package milk_management;

import main.DatabaseConnection;
import main.DateLabelFormatter;
import main.Printsupport;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import printingJobs.PaymentTablePrint;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class CustomerProfile extends JInternalFrame {
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

    public CustomerProfile() {
        super("Profile", true, true, true, true);
        JPanel panelMain = new JPanel(new BorderLayout(10, 20));
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        UtilDateModel model1 = new UtilDateModel();
        UtilDateModel model2 = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl toDatePanel = new JDatePanelImpl(model1, p);
        JDatePanelImpl fromDatePanel = new JDatePanelImpl(model2, p);
        final JDatePickerImpl toDate = new JDatePickerImpl(toDatePanel, new DateLabelFormatter());
        final JDatePickerImpl fromDate = new JDatePickerImpl(fromDatePanel, new DateLabelFormatter());
        JLabel lblToDate = new JLabel("To: ");
        JLabel lblFromDate = new JLabel("From: ");
        JLabel lblCustomerId = new JLabel("Customer ID/Code: ");
        final JTextField txtCustomerId = new JTextField(10);
        txtCustomerId.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CustomerProfile.this.manager.focusNextComponent();
            }
        });
        JButton btnSearch = new JButton("Search");
        final JButton btnPay = new JButton("Pay");
        JButton btnPrint = new JButton("Print", new ImageIcon(this.getClass().getResource("/images/print.PNG")));
        final JTable usersTable = new JTable();
        usersTable.setSelectionMode(0);
        usersTable.setAutoResizeMode(4);
        String[] column = new String[]{"Date-Time", "Shift", "Cattle", "Litre", "FAT", "Lacto", "SNF", "Rate", "Total"};
        String[] footerColumn = new String[]{"", "", "", "Tot. Litres", "Avg. FAT", "Avg. Lacto", "Avg. SNF", "Avg. Rate", "Total Amount"};
        defaultTableModel = new DefaultTableModel(0, 0);
        defaultTableModel.setColumnIdentifiers(column);
        usersTable.setModel(defaultTableModel);
        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        usersTable.setFillsViewportHeight(true);
        final JTable footerTable = new JTable();
        final DefaultTableModel footerTableModel = new DefaultTableModel(0, 0);
        footerTableModel.setColumnIdentifiers(footerColumn);
        footerTable.setSelectionMode(0);
        footerTable.setAutoResizeMode(4);
        footerTable.setModel(footerTableModel);
        InputMap im1 = btnSearch.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!fromDate.getJFormattedTextField().getText().toString().equals("") && !toDate.getJFormattedTextField().getText().toString().equals("") && !txtCustomerId.getText().toString().equals("")) {
                    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String strFromDate = fromDate.getJFormattedTextField().getText().toString() + " 00:00:00";
                    String strToDate = toDate.getJFormattedTextField().getText().toString() + " 23:59:59";
                    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                    ResultSet resultSet = CustomerProfile.dbConnection.getLastDate(Integer.parseInt(txtCustomerId.getText().toString()));

                    try {
                        if (resultSet.next()) {
                            Date previousDate = myFormat.parse(resultSet.getTimestamp("created_at").toString());
                            System.out.println("prev Date: " + previousDate);
                            System.out.println("from Date: " + previousDate.before(myFormat.parse(strToDate)));
                            if (!myFormat.parse(strToDate).equals(previousDate) && !myFormat.parse(strFromDate).equals(previousDate) && (!previousDate.after(myFormat.parse(strFromDate)) || !previousDate.before(myFormat.parse(strToDate)))) {
                                btnPay.setEnabled(true);
                            } else {
                                btnPay.setEnabled(false);
                            }
                        }
                    } catch (Exception var12) {
                        var12.printStackTrace();
                    }

                    CustomerProfile.strCustomerId = Integer.parseInt(txtCustomerId.getText().toString());
                    String[] data = new String[9];
                    resultSet = CustomerProfile.dbConnection.getDailyCollectionFromTo(CustomerProfile.strCustomerId, strFromDate, strToDate);
                    ResultSet customerResultSet = CustomerProfile.dbConnection.getCustomer(CustomerProfile.strCustomerId);
                    String strCattleType = "";
                    int i;
                    if (CustomerProfile.defaultTableModel.getRowCount() > 0) {
                        for(i = CustomerProfile.defaultTableModel.getRowCount() - 1; i > -1; --i) {
                            CustomerProfile.defaultTableModel.removeRow(i);
                        }
                    }

                    if (footerTableModel.getRowCount() > 0) {
                        for(i = footerTableModel.getRowCount() - 1; i > -1; --i) {
                            footerTableModel.removeRow(i);
                        }
                    }

                    try {
                        CustomerProfile.avgFat = 0.0D;
                        CustomerProfile.avgLacto = 0.0D;
                        CustomerProfile.avgSnf = 0.0D;
                        CustomerProfile.avgRate = 0.0D;
                        CustomerProfile.totLitres = 0.0D;
                        CustomerProfile.totAmount = 0.0D;
                        CustomerProfile.tableRowCount = 0;
                        if (customerResultSet.next()) {
                            strCattleType = customerResultSet.getString("cattle_type");
                        }

                        while(resultSet.next()) {
                            data[0] = resultSet.getString("date");
                            data[1] = resultSet.getString("shift");
                            data[2] = strCattleType;
                            data[3] = resultSet.getString("litre");
                            data[4] = resultSet.getString("fat");
                            data[5] = resultSet.getString("lacto");
                            data[6] = resultSet.getString("snf");
                            data[7] = resultSet.getString("rate");
                            data[8] = resultSet.getString("total_price");
                            CustomerProfile.avgFat = CustomerProfile.avgFat.doubleValue() + Double.parseDouble(data[4]);
                            CustomerProfile.avgLacto = CustomerProfile.avgLacto.doubleValue() + Double.parseDouble(data[5]);
                            CustomerProfile.avgSnf = CustomerProfile.avgSnf.doubleValue() + Double.parseDouble(data[6]);
                            //CustomerProfile.avgRate = CustomerProfile.avgRate.doubleValue() + Double.parseDouble(data[7]);
                            CustomerProfile.totLitres = CustomerProfile.totLitres.doubleValue() + Double.parseDouble(data[3]);
                            CustomerProfile.totAmount = CustomerProfile.totAmount.doubleValue() + Double.parseDouble(data[8]);
                            ++CustomerProfile.tableRowCount;
                            CustomerProfile.defaultTableModel.addRow(data);
                        }

                        CustomerProfile.avgFat = CustomerProfile.avgFat.doubleValue() / (double)CustomerProfile.tableRowCount;
                        CustomerProfile.avgLacto = CustomerProfile.avgLacto.doubleValue() / (double)CustomerProfile.tableRowCount;
                        CustomerProfile.avgSnf = CustomerProfile.avgSnf.doubleValue() / (double)CustomerProfile.tableRowCount;
                        CustomerProfile.avgRate = CustomerProfile.totAmount / CustomerProfile.totLitres;//CustomerProfile.avgRate.doubleValue() / (double)CustomerProfile.tableRowCount;
                        String[] data1 = new String[]{"", "", "Totals:", "" + decimalFormat.format(CustomerProfile.totLitres), "" + decimalFormat.format(CustomerProfile.avgFat), "" + decimalFormat.format(CustomerProfile.avgLacto), "" + decimalFormat.format(CustomerProfile.avgSnf), "" + decimalFormat.format(CustomerProfile.avgRate), "" + decimalFormat.format(CustomerProfile.totAmount)};
                        footerTableModel.addRow(data1);
                    } catch (Exception var11) {
                        var11.printStackTrace();
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(CustomerProfile.this.getContentPane(), "Please fill all fields.", "Error", 0);
                }

            }
        });
        btnPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numberOfdays = 0;
                if (!txtCustomerId.getText().toString().equals("") && usersTable.getRowCount() > 0) {
                    SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yy");
                    String inputString1 = CustomerProfile.defaultTableModel.getValueAt(0, 0).toString();
                    String inputString2 = CustomerProfile.defaultTableModel.getValueAt(usersTable.getRowCount() - 1, 0).toString();
                    DecimalFormat df = new DecimalFormat("#.##");
                    df.setRoundingMode(RoundingMode.CEILING);

                    try {
                        Date date1 = myFormat.parse(inputString1);
                        Date date2 = myFormat.parse(inputString2);
                        long diff = date2.getTime() - date1.getTime();
                        numberOfdays = (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    } catch (ParseException var21) {
                        var21.printStackTrace();
                    }

                    String[] values = new String[]{df.format(CustomerProfile.totLitres), df.format(CustomerProfile.avgFat), df.format(CustomerProfile.avgSnf), df.format(CustomerProfile.avgLacto), df.format(CustomerProfile.avgRate), df.format(CustomerProfile.totAmount)};
                    int customerID = Integer.parseInt(txtCustomerId.getText().toString());
                    int result = CustomerProfile.dbConnection.insertPayments(customerID, numberOfdays, values);
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(CustomerProfile.this.getContentPane(), "Payment done successfuly!!!!");
                        String custoemrName = "";
                        String dairyName = "";
                        String dairyAddress = "";
                        ResultSet resultSet = CustomerProfile.dbConnection.getCustomer(Integer.parseInt(txtCustomerId.getText().toString()));
                        ResultSet currentUser = CustomerProfile.dbConnection.getCurrentUser();

                        try {
                            if (resultSet.next()) {
                                custoemrName = resultSet.getString("cust_name");
                            }

                            if (currentUser.next()) {
                                dairyName = currentUser.getString("dairy_name");
                                dairyAddress = currentUser.getString("dairy_address");
                            }
                        } catch (Exception var20) {
                            var20.printStackTrace();
                        }

                        PaymentTablePrint dailyCollectionPrintJob = new PaymentTablePrint(usersTable, footerTable, dairyName, dairyAddress, custoemrName);
                        Printsupport ps = new Printsupport();
                        PrinterJob pj = PrinterJob.getPrinterJob();
                        pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 21.0D, 30.0D, 1));

                        try {
                            pj.print();
                        } catch (PrinterException var19) {
                            var19.printStackTrace();
                            JOptionPane.showInternalMessageDialog(CustomerProfile.this.getContentPane(), var19, "Error", 0);
                        }

                        int i;
                        if (CustomerProfile.defaultTableModel.getRowCount() > 0) {
                            for(i = CustomerProfile.defaultTableModel.getRowCount() - 1; i > -1; --i) {
                                CustomerProfile.defaultTableModel.removeRow(i);
                            }
                        }

                        if (footerTableModel.getRowCount() > 0) {
                            for(i = footerTableModel.getRowCount() - 1; i > -1; --i) {
                                footerTableModel.removeRow(i);
                            }
                        }

                        txtCustomerId.setText("");
                    } else {
                        JOptionPane.showInternalMessageDialog(CustomerProfile.this.getContentPane(), "Something went wrong...", "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(CustomerProfile.this.getContentPane(), "Please check customer Code or dates(Nothing to pay).", "Error", 0);
                }

            }
        });
        btnPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtCustomerId.getText().toString().equals("") && usersTable.getRowCount() > 0) {
                    String custoemrName = "";
                    String dairyName = "";
                    String dairyAddress = "";
                    ResultSet resultSet = CustomerProfile.dbConnection.getCustomer(Integer.parseInt(txtCustomerId.getText().toString()));
                    ResultSet currentUser = CustomerProfile.dbConnection.getCurrentUser();

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

                    PaymentTablePrint dailyCollectionPrintJob = new PaymentTablePrint(usersTable, footerTable, dairyName, dairyAddress, custoemrName);
                    Printsupport ps = new Printsupport();
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 21.0D, 30.0D, 1));

                    try {
                        pj.print();
                    } catch (PrinterException var11) {
                        var11.printStackTrace();
                        JOptionPane.showInternalMessageDialog(CustomerProfile.this.getContentPane(), var11, "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(CustomerProfile.this.getContentPane(), "Please check customer Code or dates(Nothing to Print).", "Error", 0);
                }

            }
        });
        topPanel.add(lblFromDate);
        topPanel.add(fromDate);
        topPanel.add(lblToDate);
        topPanel.add(toDate);
        topPanel.add(lblCustomerId);
        topPanel.add(txtCustomerId);
        topPanel.add(btnSearch);
        topPanel.add(btnPay);
        topPanel.add(btnPrint);
        bottomPanel.add(tableScrollPane, "Center");
        bottomPanel.add(footerTable, "South");
        panelMain.add("First", topPanel);
        panelMain.add("Center", bottomPanel);
        this.add(panelMain);
    }
}
