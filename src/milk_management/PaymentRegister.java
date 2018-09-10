//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package milk_management;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import main.DatabaseConnection;
import main.Printsupport;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
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
import java.util.Calendar;
import java.util.Properties;

public class PaymentRegister extends JInternalFrame {
    private JPanel mainPanel;
    private JPanel centerPanel;
    private JPanel panelTotals;
    private JTable totalsTable;
    private JTable registerTable;
    private JScrollPane tableRegister;
    private JPanel topPanel;
    private JButton printRegisterButton;
    private JDatePickerImpl fromDate;
    private JDatePickerImpl toDate;
    private JButton btnFilter;
    static DefaultTableModel defaultTableModel;
    static DatabaseConnection dbConnection = new DatabaseConnection();
    static Double avgFat;
    static Double avgSnf;
    static Double avgRate;
    static Double totLitres;
    static Double totAmount;
    static Double Fat;
    static Double Snf;
    static Double Rate;
    static Double Litres;
    static Double Amount;
    private UtilDateModel model1;
    private UtilDateModel model2;
    private Properties p;
    private JDatePanelImpl toDatePanel;
    private JDatePanelImpl fromDatePanel;

    public PaymentRegister() {
        super("Payment Register", false, true, false, false);
        String[] column = new String[]{"Code", "PRD.Name", "Litre", "FAT", "SNF", "Rate", "Total"};
        String[] footerColumn = new String[]{"", "Tot. Litres", "Avg. FAT", "Avg. SNF", "Avg. Rate", "Total Amount"};
        defaultTableModel = new DefaultTableModel(0, 0);
        this.$$$setupUI$$$();
        defaultTableModel.setColumnIdentifiers(column);
        final DecimalFormat decimalFormat = new DecimalFormat("0.00##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        this.registerTable.setModel(defaultTableModel);
        final DefaultTableModel footerTableModel = new DefaultTableModel(0, 0);
        footerTableModel.setColumnIdentifiers(footerColumn);
        this.totalsTable.setModel(footerTableModel);
        this.btnFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strToDate = PaymentRegister.this.toDate.getJFormattedTextField().getText() + " 23:59:59";
                String strFromDate = PaymentRegister.this.fromDate.getJFormattedTextField().getText() + " 00:00:00";
                System.out.println("Dates: " + strToDate + " " + strFromDate);
                if (!strToDate.equals("") && !strFromDate.equals("")) {
                    int i;
                    if (PaymentRegister.defaultTableModel.getRowCount() > 0) {
                        for(i = PaymentRegister.defaultTableModel.getRowCount() - 1; i > -1; --i) {
                            PaymentRegister.defaultTableModel.removeRow(i);
                        }
                    }

                    if (footerTableModel.getRowCount() > 0) {
                        for(i = footerTableModel.getRowCount() - 1; i > -1; --i) {
                            footerTableModel.removeRow(i);
                        }
                    }

                    try {
                        ResultSet customers = PaymentRegister.dbConnection.getAllCustomers();
                        int mainCounter = 0;
                        String[] data;
                        if (customers != null) {
                            PaymentRegister.avgFat = 0.0D;
                            PaymentRegister.avgSnf = 0.0D;
                            PaymentRegister.avgRate = 0.0D;
                            PaymentRegister.totLitres = 0.0D;
                            PaymentRegister.totAmount = 0.0D;

                            label52:
                            while(true) {
                                while(true) {
                                    if (!customers.next()) {
                                        break label52;
                                    }

                                    data = new String[7];
                                    PaymentRegister.Fat = 0.0D;
                                    PaymentRegister.Snf = 0.0D;
                                    PaymentRegister.Rate = 0.0D;
                                    PaymentRegister.Litres = 0.0D;
                                    PaymentRegister.Amount = 0.0D;
                                    ResultSet resultSet = PaymentRegister.dbConnection.getDailyCollectionByCustomerAndDate(customers.getInt("cust_id"), strFromDate, strToDate);
                                    if (resultSet != null) {
                                        Double counter;
                                        for(counter = 0.0D; resultSet.next(); ) {
                                            PaymentRegister.Fat = PaymentRegister.Fat + Double.parseDouble(resultSet.getString("fat"));
                                            PaymentRegister.Snf = PaymentRegister.Snf + Double.parseDouble(resultSet.getString("snf"));
                                            PaymentRegister.Litres = PaymentRegister.Litres + Double.parseDouble(resultSet.getString("litre"));
                                            PaymentRegister.Amount = PaymentRegister.Amount + Double.parseDouble(resultSet.getString("total_price"));
//                                            PaymentRegister.Rate = PaymentRegister.Rate + Double.parseDouble(resultSet.getString("rate"));

                                            if(Double.parseDouble(resultSet.getString("total_price")) > 0.0) {
                                                counter = counter + 1.0D;
                                            }
                                        }


                                        if (PaymentRegister.Litres != 0.0D) {
                                            data[0] = customers.getString("cust_id");
                                            data[1] = customers.getString("cust_name");
                                            data[2] = decimalFormat.format(PaymentRegister.Litres);
                                            data[6] = decimalFormat.format(PaymentRegister.Amount);
                                            data[3] = decimalFormat.format(PaymentRegister.Fat / counter);
                                            data[4] = decimalFormat.format(PaymentRegister.Snf / counter);
                                            data[5] = decimalFormat.format(PaymentRegister.Amount / PaymentRegister.Litres);
                                            PaymentRegister.avgFat = PaymentRegister.avgFat + PaymentRegister.Fat / counter;
                                            PaymentRegister.avgSnf = PaymentRegister.avgSnf + PaymentRegister.Snf / counter;
//                                            PaymentRegister.avgRate = PaymentRegister.avgRate + PaymentRegister.Rate / counter;

                                            PaymentRegister.totLitres = PaymentRegister.totLitres + Double.parseDouble(data[2]);
                                            PaymentRegister.totAmount = PaymentRegister.totAmount + Double.parseDouble(data[6]);
                                            PaymentRegister.avgRate = PaymentRegister.totAmount / PaymentRegister.totLitres;

                                            ++mainCounter;
                                            PaymentRegister.defaultTableModel.addRow(data);
                                        }
//                                        else {
//                                            data[3] = decimalFormat.format(0.0D);
//                                            data[4] = decimalFormat.format(0.0D);
//                                            data[5] = decimalFormat.format(0.0D);
//                                            data[6] = decimalFormat.format(0.0D);
//                                            PaymentRegister.avgFat = PaymentRegister.avgFat + 0.0D;
//                                            PaymentRegister.avgSnf = PaymentRegister.avgSnf + 0.0D;
////                                            PaymentRegister.avgRate = PaymentRegister.avgRate + 0.0D;
//                                        }

//                                        if(Double.parseDouble(data[6]) > 0.0) {
//                                            ++mainCounter;
//                                        }
//                                        PaymentRegister.defaultTableModel.addRow(data);
                                    } else {
                                        JOptionPane.showInternalMessageDialog(PaymentRegister.this.getContentPane(), "Empty", "Error", 0);
                                    }
                                }
                            }
                        }

                        data = new String[]{"Totals:", "" + decimalFormat.format(PaymentRegister.totLitres), "" + decimalFormat.format(PaymentRegister.avgFat / (double)mainCounter), "" + decimalFormat.format(PaymentRegister.avgSnf / (double)mainCounter), "" + new DecimalFormat("0.00####").format(PaymentRegister.avgRate), "" + decimalFormat.format(PaymentRegister.totAmount)};
                        footerTableModel.addRow(data);
                    } catch (Exception var11) {
                        var11.printStackTrace();
                        JOptionPane.showInternalMessageDialog(PaymentRegister.this.getContentPane(), var11, "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(PaymentRegister.this.getContentPane(), "Please enter To & From dates", "ERROR", 0);
                }

            }
        });
        this.add(this.$$$getRootComponent$$$());
        this.printRegisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (PaymentRegister.defaultTableModel.getRowCount() > 0) {
                    String dairyName = "SHIVKRUPA DOODH SANKALAN ANI SHEET KENDRA";
                    String dairyAddress = "MADKIJAMB";
                    String strToDate = PaymentRegister.this.fromDate.getJFormattedTextField().getText();
                    String strFromDate = PaymentRegister.this.toDate.getJFormattedTextField().getText();
                    printingJobs.PaymentRegister dailyCollectionPrintJob = new printingJobs.PaymentRegister(PaymentRegister.this.registerTable, PaymentRegister.this.totalsTable, dairyName, dairyAddress, strToDate, strFromDate);
                    Printsupport ps = new Printsupport();
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 21.0D, 29.7D, 1));

                    try {
                        pj.print();
                    } catch (PrinterException var10) {
                        var10.printStackTrace();
                        JOptionPane.showInternalMessageDialog(PaymentRegister.this.getContentPane(), var10, "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(PaymentRegister.this.getContentPane(), "Please First Filter", "Error", 0);
                }

            }
        });
    }

    private void createUIComponents() {
        this.model1 = new UtilDateModel();
        this.model2 = new UtilDateModel();
        this.p = new Properties();
        this.p.put("text.today", "Today");
        this.p.put("text.month", "Month");
        this.p.put("text.year", "Year");
        this.toDatePanel = new JDatePanelImpl(this.model1, this.p);
        this.fromDatePanel = new JDatePanelImpl(this.model2, this.p);
        this.fromDate = new JDatePickerImpl(this.toDatePanel, new AbstractFormatter() {
            private String datePattern = "yyyy-MM-dd";
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
        this.toDate = new JDatePickerImpl(this.fromDatePanel, new AbstractFormatter() {
            private String datePattern = "yyyy-MM-dd";
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
    }

    private void $$$setupUI$$$() {
        this.createUIComponents();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.centerPanel, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelTotals = new JPanel();
        this.panelTotals.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.centerPanel.add(this.panelTotals, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.totalsTable = new JTable();
        this.panelTotals.add(this.totalsTable, new GridConstraints(0, 0, 1, 1, 0, 3, 4, 4, (Dimension)null, new Dimension(150, 50), (Dimension)null, 0, false));
        this.tableRegister = new JScrollPane();
        this.centerPanel.add(this.tableRegister, new GridConstraints(0, 0, 1, 1, 0, 3, 5, 5, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.registerTable = new JTable();
        this.tableRegister.setViewportView(this.registerTable);
        this.topPanel = new JPanel();
        this.topPanel.setLayout(new GridLayoutManager(1, 8, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.topPanel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.printRegisterButton = new JButton();
        this.printRegisterButton.setText("Print Register");
        this.topPanel.add(this.printRegisterButton, new GridConstraints(0, 6, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.topPanel.add(spacer1, new GridConstraints(0, 7, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.topPanel.add(this.fromDate, new GridConstraints(0, 2, 1, 1, 0, 0, 0, 0, (Dimension)null, new Dimension(250, -1), (Dimension)null, 0, false));
        this.topPanel.add(this.toDate, new GridConstraints(0, 4, 1, 1, 0, 0, 0, 0, (Dimension)null, new Dimension(250, -1), (Dimension)null, 0, false));
        this.btnFilter = new JButton();
        this.btnFilter.setText("Filter");
        this.topPanel.add(this.btnFilter, new GridConstraints(0, 5, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.topPanel.add(spacer2, new GridConstraints(0, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        JLabel label1 = new JLabel();
        label1.setText("From date");
        this.topPanel.add(label1, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        JLabel label2 = new JLabel();
        label2.setText("To date");
        this.topPanel.add(label2, new GridConstraints(0, 3, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return this.mainPanel;
    }
}
