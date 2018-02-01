//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package milk_management;

import main.DatabaseConnection;
import main.Printsupport;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import printingJobs.DailyReportPrint;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class DailyReport extends JInternalFrame {
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

    public DailyReport() {
        super("Daily Report", true, true, true, true);
        JPanel panelMain = new JPanel(new BorderLayout(10, 20));
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        UtilDateModel model1 = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl toDatePanel = new JDatePanelImpl(model1, p);
        final JDatePickerImpl toDate = new JDatePickerImpl(toDatePanel, new AbstractFormatter() {
            private String datePattern = "dd-MM-yy";
            private SimpleDateFormat dateFormatter;

            {
                this.dateFormatter = new SimpleDateFormat(this.datePattern);
            }

            public Object stringToValue(String text) throws ParseException {
                return this.dateFormatter.parseObject(text);
            }

            public String valueToString(Object value) throws ParseException {
                if (value != null) {
                    Calendar cal = (Calendar)value;
                    return this.dateFormatter.format(cal.getTime());
                } else {
                    return "";
                }
            }
        });
        JLabel lblToDate = new JLabel("Date: ");
        JButton btnSearch = new JButton("Search");
        JButton btnPay = new JButton("Print", new ImageIcon(this.getClass().getResource("/images/print.PNG")));
        final JTable usersTable = new JTable();
        usersTable.setSelectionMode(0);
        usersTable.setAutoResizeMode(4);
        String[] column = new String[]{"PRD. Name", "Shift", "Cattle", "Litre", "FAT", "Lacto", "SNF", "Rate", "Total"};
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
                if (toDate.getJFormattedTextField().getText().toString().equals("")) {
                    JOptionPane.showInternalMessageDialog(DailyReport.this.getContentPane(), "Please fill all fields.", "Error", 0);
                } else {
                    int i;
                    if (DailyReport.defaultTableModel.getRowCount() > 0) {
                        for(i = DailyReport.defaultTableModel.getRowCount() - 1; i > -1; --i) {
                            DailyReport.defaultTableModel.removeRow(i);
                        }
                    }

                    if (footerTableModel.getRowCount() > 0) {
                        for(i = footerTableModel.getRowCount() - 1; i > -1; --i) {
                            footerTableModel.removeRow(i);
                        }
                    }

                    String strToDate = toDate.getJFormattedTextField().getText().toString();
                    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                    ResultSet resultSet = null;
                    String[] data = new String[9];
                    resultSet = DailyReport.dbConnection.getDailyCollectionByDate(strToDate);

                    try {
                        DailyReport.avgFat = 0.0D;
                        DailyReport.avgLacto = 0.0D;
                        DailyReport.avgSnf = 0.0D;
                        DailyReport.avgRate = 0.0D;
                        DailyReport.totLitres = 0.0D;
                        DailyReport.totAmount = 0.0D;
                        DailyReport.tableRowCount = 0;

                        while(resultSet.next()) {
                            ResultSet customerResultSet = DailyReport.dbConnection.getCustomer(resultSet.getInt("customer_id"));
                            if (customerResultSet.first()) {
                                data[0] = customerResultSet.getString("cust_name");
                                data[1] = resultSet.getString("shift");
                                data[2] = customerResultSet.getString("cattle_type");
                                data[3] = resultSet.getString("litre");
                                data[4] = resultSet.getString("fat");
                                data[5] = resultSet.getString("lacto");
                                data[6] = resultSet.getString("snf");
                                data[7] = resultSet.getString("rate");
                                data[8] = resultSet.getString("total_price");
                                DailyReport.avgFat = DailyReport.avgFat.doubleValue() + Double.parseDouble(data[4]);
                                DailyReport.avgLacto = DailyReport.avgLacto.doubleValue() + Double.parseDouble(data[5]);
                                DailyReport.avgSnf = DailyReport.avgSnf.doubleValue() + Double.parseDouble(data[6]);
                                DailyReport.avgRate = DailyReport.avgRate.doubleValue() + Double.parseDouble(data[7]);
                                DailyReport.totLitres = DailyReport.totLitres.doubleValue() + Double.parseDouble(data[3]);
                                DailyReport.totAmount = DailyReport.totAmount.doubleValue() + Double.parseDouble(data[8]);
                                ++DailyReport.tableRowCount;
                                DailyReport.defaultTableModel.addRow(data);
                            }
                        }

                        DailyReport.avgFat = DailyReport.avgFat.doubleValue() / (double)DailyReport.tableRowCount;
                        DailyReport.avgLacto = DailyReport.avgLacto.doubleValue() / (double)DailyReport.tableRowCount;
                        DailyReport.avgSnf = DailyReport.avgSnf.doubleValue() / (double)DailyReport.tableRowCount;
                        DailyReport.avgRate = DailyReport.avgRate.doubleValue() / (double)DailyReport.tableRowCount;
                        String[] data1 = new String[]{"", "", "Totals:", "" + decimalFormat.format(DailyReport.totLitres), "" + decimalFormat.format(DailyReport.avgFat), "" + decimalFormat.format(DailyReport.avgLacto), "" + decimalFormat.format(DailyReport.avgSnf), "" + decimalFormat.format(DailyReport.avgRate), "" + decimalFormat.format(DailyReport.totAmount)};
                        footerTableModel.addRow(data1);
                    } catch (Exception var7) {
                        var7.printStackTrace();
                    }
                }

            }
        });
        btnPay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String custoemrName = "";
                String dairyName = "";
                String dairyAddress = "";
                ResultSet currentUser = DailyReport.dbConnection.getCurrentUser();

                try {
                    if (currentUser.next()) {
                        dairyName = currentUser.getString("dairy_name");
                        dairyAddress = currentUser.getString("dairy_address");
                    }
                } catch (Exception var11) {
                    var11.printStackTrace();
                }

                custoemrName = toDate.getJFormattedTextField().getText().toString();
                DailyReportPrint dailyCollectionPrintJob = new DailyReportPrint(usersTable, footerTable, dairyName, dairyAddress, custoemrName);
                Printsupport ps = new Printsupport();
                PrinterJob pj = PrinterJob.getPrinterJob();
                pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 21.0D, 30.0D, 1));

                try {
                    pj.print();
                } catch (PrinterException var10) {
                    var10.printStackTrace();
                    JOptionPane.showInternalMessageDialog(DailyReport.this.getContentPane(), var10, "Error", 0);
                }

            }
        });
        topPanel.add(lblToDate);
        topPanel.add(toDate);
        topPanel.add(btnSearch);
        topPanel.add(btnPay);
        bottomPanel.add(tableScrollPane, "Center");
        bottomPanel.add(footerTable, "South");
        panelMain.add("First", topPanel);
        panelMain.add("Center", bottomPanel);
        this.add(panelMain);
    }
}
