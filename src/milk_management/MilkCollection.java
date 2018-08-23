//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package milk_management;

import main.ClockLabel;
import main.DatabaseConnection;
import main.Printsupport;
import printingJobs.DailyCollectionPrintJob;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MilkCollection extends JInternalFrame {
    SimpleDateFormat sdf;
    static DatabaseConnection dbConnection = new DatabaseConnection();
    static ResultSet customerResultSet = null;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    static DefaultTableModel defaultTableModel;
    String shift = "Morning";
    JButton btnEdit;
    JButton btnPrint;

    public MilkCollection() {
        super("Milk Collection", false, true, false, true);
        new JPanel(new BorderLayout());
        Font frameTextFont = new Font("Serif", 1, 16);
        JPanel headerPanel = new JPanel(new GridLayout(1, 0));
        JPanel footerPanel = new JPanel();
        JPanel leftPanel = new JPanel(new GridLayout(1, 1));
        JPanel rightPanel = new JPanel(new BorderLayout());
        ClockLabel lblTodaysDate = new ClockLabel("date");
        ClockLabel lblCurrentTime = new ClockLabel("time");
        JLabel lblShiftText = new JLabel("Shift: ");
        JLabel lblDateText = new JLabel("Date: ");
        JLabel lblTimeText = new JLabel("Time: ");
        JLabel lblShift = new JLabel();
        this.sdf = new SimpleDateFormat("a");
        Date nowDate = new Date();
        if (this.sdf.format(nowDate).equals("AM")) {
            lblShift.setText("Morning");
        } else if (this.sdf.format(nowDate).equals("PM")) {
            lblShift.setText("Evening");
        }

        lblTodaysDate.setFont(frameTextFont);
        lblCurrentTime.setFont(frameTextFont);
        lblShiftText.setFont(frameTextFont);
        lblShift.setFont(frameTextFont);
        lblDateText.setFont(frameTextFont);
        lblTimeText.setFont(frameTextFont);
        headerPanel.add(lblDateText);
        headerPanel.add(lblTodaysDate);
        headerPanel.add(lblTimeText);
        headerPanel.add(lblCurrentTime);
        headerPanel.add(lblShiftText);
        headerPanel.add(lblShift);
        headerPanel.setPreferredSize(new Dimension(this.getContentPane().getWidth(), 30));
        JPanel leftChild_2 = new JPanel(new GridLayout(0, 2, 10, 10));
        new JPanel(new GridLayout(1, 1));
        JLabel lblCustomerID = new JLabel("Customer Code/ID:");
        final JTextField txtCustomerID = new JTextField(10);
        txtCustomerID.setFont(frameTextFont);
        JLabel lblCustomerName = new JLabel("Customer Name:");
        final JTextField txtCustomerName = new JTextField(20);
        txtCustomerName.setFont(frameTextFont);
        txtCustomerName.setBackground(Color.lightGray);
        txtCustomerName.setForeground(Color.black);
        JLabel lblCattleType = new JLabel("Cattle Type:");
        final JTextField txtCattleType = new JTextField(20);
        txtCattleType.setFont(frameTextFont);
        txtCattleType.setBackground(Color.lightGray);
        txtCattleType.setForeground(Color.black);
        leftChild_2.add(lblCustomerID);
        leftChild_2.add(txtCustomerID);
        leftChild_2.add(lblCustomerName);
        leftChild_2.add(txtCustomerName);
        leftChild_2.add(lblCattleType);
        leftChild_2.add(txtCattleType);
        JLabel lblLitre = new JLabel("Litre: ");
        final JTextField txtLitre = new JTextField();
        txtLitre.setFont(frameTextFont);
        JLabel lblFAT = new JLabel("FAT: ");
        final JTextField txtFAT = new JTextField();
        txtFAT.setFont(frameTextFont);
        JLabel lblLacto = new JLabel("Lacto: ");
        final JTextField txtLacto = new JTextField();
        txtLacto.setFont(frameTextFont);
        JLabel lblSNF = new JLabel("S.N.F.: ");
        final JTextField txtSNF = new JTextField();
        txtSNF.setBackground(Color.lightGray);
        txtSNF.setForeground(Color.black);
        txtSNF.setFont(frameTextFont);
        JLabel lblRate = new JLabel("Rate: ");
        final JTextField txtRate = new JTextField();
        txtRate.setBackground(Color.lightGray);
        txtRate.setForeground(Color.black);
        txtRate.setFont(frameTextFont);
        JLabel lblTotalPrice = new JLabel("Total Price: ");
        final JTextField txtTotalPrice = new JTextField();
        txtTotalPrice.setFont(frameTextFont);
        txtTotalPrice.setBackground(Color.lightGray);
        txtTotalPrice.setForeground(Color.black);
        final JButton btnAddCollection = new JButton("O.K.");
        btnAddCollection.setFont(frameTextFont);
        leftChild_2.add(lblLitre);
        leftChild_2.add(txtLitre);
        leftChild_2.add(lblFAT);
        leftChild_2.add(txtFAT);
        leftChild_2.add(lblLacto);
        leftChild_2.add(txtLacto);
        leftChild_2.add(lblSNF);
        leftChild_2.add(txtSNF);
        leftChild_2.add(lblRate);
        leftChild_2.add(txtRate);
        leftChild_2.add(lblTotalPrice);
        leftChild_2.add(txtTotalPrice);
        leftChild_2.add(Box.createRigidArea(new Dimension(15, 30)));
        leftChild_2.add(btnAddCollection);
        lblCustomerID.setFont(frameTextFont);
        lblCustomerName.setFont(frameTextFont);
        lblCattleType.setFont(frameTextFont);
        lblLitre.setFont(frameTextFont);
        lblFAT.setFont(frameTextFont);
        lblLacto.setFont(frameTextFont);
        lblSNF.setFont(frameTextFont);
        lblRate.setFont(frameTextFont);
        lblTotalPrice.setFont(frameTextFont);
        txtCustomerName.setEditable(false);
        txtRate.setEditable(false);
        txtSNF.setEditable(false);
        txtTotalPrice.setEditable(false);
        txtFAT.setEditable(false);
        txtLitre.setEditable(false);
        txtLacto.setEditable(false);
        btnAddCollection.setEnabled(false);
        txtCustomerID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtCustomerID.getText().toString().equals("")) {
                    int custID = Integer.parseInt(txtCustomerID.getText().toString());
                    MilkCollection.customerResultSet = MilkCollection.dbConnection.getCustomer(custID);

                    try {
                        if (MilkCollection.customerResultSet.next()) {
                            btnAddCollection.setEnabled(true);
                            txtCustomerName.setText(MilkCollection.customerResultSet.getString("cust_name"));
                            txtCattleType.setText(MilkCollection.customerResultSet.getString("cattle_type"));
                            MilkCollection.this.manager.focusNextComponent(txtCattleType);
                            txtFAT.setEditable(true);
                            txtLitre.setEditable(true);
                            txtLacto.setEditable(true);
                        } else {
                            JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "Please enter valid Customer Code / ID", "Error", 0);
                            btnAddCollection.setEnabled(false);
                            MilkCollection.this.manager.focusPreviousComponent(txtCustomerName);
                        }
                    } catch (Exception var4) {
                        var4.printStackTrace();
                        JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), var4, "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "Please enter Customer Code / ID", "Error", 0);
                    MilkCollection.this.manager.focusPreviousComponent(txtCustomerName);
                    btnAddCollection.setEnabled(false);
                }

            }
        });
        txtLitre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtLitre.getText().toString().equals("") && MilkCollection.this.isDouble(txtLitre.getText().toString())) {
                    btnAddCollection.setEnabled(true);
                    MilkCollection.this.manager.focusNextComponent();
                } else {
                    JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "Please enter valid Litre of milk.", "ERROR", 0);
                    btnAddCollection.setEnabled(false);
                }

            }
        });
        txtFAT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtFAT.getText().toString().equals("") && MilkCollection.this.isDouble(txtFAT.getText().toString())) {
                    btnAddCollection.setEnabled(true);
                    MilkCollection.this.manager.focusNextComponent();
                } else {
                    JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "Please enter valid FAT.", "ERRPR", 0);
                    btnAddCollection.setEnabled(false);
                }

            }
        });
        txtLacto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtLacto.getText().toString().equals("") && MilkCollection.this.isDouble(txtLacto.getText().toString())) {
                    btnAddCollection.setEnabled(true);
                    Double noLitre = Double.parseDouble(txtLitre.getText().toString());
                    Double noFAT = Double.parseDouble(txtFAT.getText().toString());
                    Double noLacto = Double.parseDouble(txtLacto.getText().toString());
                    Double noSNF = noLacto.doubleValue() / 4.0D + 0.21D * noFAT.doubleValue() + 0.36D;
                    DecimalFormat df = new DecimalFormat("#.#");
                    df.setRoundingMode(RoundingMode.CEILING);
                    Double noRate = 0.0D;
                    String strsnf;
                    if (Double.parseDouble(df.format(noSNF)) % 1.0D == 0.0D) {
                        strsnf = df.format(noSNF) + ".0";
                        System.out.println(strsnf);
                    } else {
                        strsnf = df.format(noSNF);
                        System.out.println(strsnf);
                    }

                    try {
                        MilkCollection.customerResultSet.first();
                        String usersCattleType = MilkCollection.customerResultSet.getString("cattle_type");
                        ResultSet rateResultSet = MilkCollection.dbConnection.getRatesByFatAndSnf(usersCattleType, txtFAT.getText().toString(), strsnf);
                        if (rateResultSet.next()) {
                            noRate = Double.parseDouble(rateResultSet.getString("rate"));
                            MilkCollection.this.manager.focusNextComponent(txtTotalPrice);
                        } else {
                            JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "Rate for this SNF was not added.\n Please add Rate first from settings page", "Error", 0);
                        }
                    } catch (Exception var11) {
                        var11.printStackTrace();
                    }

                    txtSNF.setText(strsnf);
                    txtRate.setText("" + noRate);
                    txtTotalPrice.setText("" + noLitre.doubleValue() * noRate.doubleValue());
                } else {
                    JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "Please enter valid Lacto.", "ERRPR", 0);
                    btnAddCollection.setEnabled(false);
                }

            }
        });
        InputMap im1 = btnAddCollection.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnAddCollection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (MilkCollection.this.isDouble(txtLitre.getText().toString()) && MilkCollection.this.isDouble(txtFAT.getText().toString()) && MilkCollection.this.isDouble(txtLacto.getText().toString())) {
                    MilkCollection.this.sdf = new SimpleDateFormat("dd-MM-yy");
                    Date nowDate = new Date();
                    String date = MilkCollection.this.sdf.format(nowDate);
                    MilkCollection.this.sdf = new SimpleDateFormat("HH:mm:ss");
                    nowDate = new Date();
                    String time = MilkCollection.this.sdf.format(nowDate);
                    MilkCollection.this.sdf = new SimpleDateFormat("a");
                    nowDate = new Date();
                    MilkCollection.this.shift = "";
                    if (MilkCollection.this.sdf.format(nowDate).equals("AM")) {
                        MilkCollection.this.shift = "Morning";
                    } else if (MilkCollection.this.sdf.format(nowDate).equals("PM")) {
                        MilkCollection.this.shift = "Evening";
                    }

                    int customer_id = 0;

                    try {
                        customer_id = MilkCollection.customerResultSet.getInt("cust_id");
                    } catch (Exception var27) {
                        var27.printStackTrace();
                    }

                    String strLitre = txtLitre.getText().toString();
                    String strfat = txtFAT.getText().toString();
                    String strLacto = txtLacto.getText().toString();
                    String strSnf = txtSNF.getText().toString();
                    String strRate = txtRate.getText().toString();
                    String strTotalPrice = txtTotalPrice.getText().toString();
                    String customerName = txtCustomerName.getText().toString();
                    String cattleType = txtCattleType.getText().toString();
                    String[] values = new String[]{date, time, MilkCollection.this.shift, strLitre, strfat, strLacto, strSnf, strRate, strTotalPrice};
                    int result = MilkCollection.dbConnection.insertDailyCollection(customer_id, values);
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "success");
                        String[] data = new String[]{txtCustomerID.getText().toString(), txtCustomerName.getText().toString(), txtCattleType.getText().toString(), strLitre, strfat, strLacto, strSnf, strRate, strTotalPrice};
                        MilkCollection.defaultTableModel.addRow(data);

                        try {
                            String dairyName = "";
                            String dairyAaddress = "";
                            ResultSet currentUser = MilkCollection.dbConnection.getCurrentUser();
                            if (currentUser.next()) {
                                dairyName = currentUser.getString("dairy_name");
                                dairyAaddress = currentUser.getString("dairy_address");
                            }

                            String[] values1 = new String[]{MilkCollection.this.shift, customerName, "" + customer_id, cattleType, strLitre, strfat, strSnf, strLacto, strRate, strTotalPrice};
                            DailyCollectionPrintJob dailyCollectionPrintJob = new DailyCollectionPrintJob(values1, dairyName, dairyAaddress);
                            Printsupport ps = new Printsupport();
                            PrinterJob pj = PrinterJob.getPrinterJob();
                            pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 15.0D, 15.0D, 1));

                            try {
                                pj.print();
                            } catch (PrinterException var25) {
                                var25.printStackTrace();
                                JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), var25, "Error", 0);
                            }
                        } catch (Exception var26) {
                            var26.printStackTrace();
                        }

                        txtCustomerID.setText("");
                        txtCustomerName.setText("");
                        txtCattleType.setText("");
                        txtLitre.setText("");
                        txtFAT.setText("");
                        txtLacto.setText("");
                        txtSNF.setText("");
                        txtRate.setText("");
                        txtTotalPrice.setText("");
                        txtFAT.setEditable(false);
                        txtLitre.setEditable(false);
                        txtLacto.setEditable(false);
                        btnAddCollection.setEnabled(false);
                        MilkCollection.this.manager.focusPreviousComponent(txtCustomerName);
                    } else {
                        JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "Something went wrong \n You might have already collected milk for this customer...\n please delete this entry or add milk for next shift", "Error", 0);
                        txtCustomerID.setText("");
                        txtCustomerName.setText("");
                        txtCattleType.setText("");
                        txtLitre.setText("");
                        txtFAT.setText("");
                        txtLacto.setText("");
                        txtSNF.setText("");
                        txtRate.setText("");
                        txtTotalPrice.setText("");
                        MilkCollection.this.manager.focusPreviousComponent(txtCustomerName);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "Invalid FAT / Litres / Lacto", "Error", 0);
                    MilkCollection.this.manager.focusNextComponent(txtCattleType);
                }

            }
        });
        leftPanel.add(leftChild_2);
        final JTable usersTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4 || column == 5;
            }
        };
        usersTable.setSelectionMode(0);
        usersTable.setAutoResizeMode(4);
        String[] column = new String[]{"Code", "Customer", "Cattle", "Litre", "FAT", "Lacto", "SNF", "Rate", "Total"};
        defaultTableModel = new DefaultTableModel(0, 0);
        defaultTableModel.setColumnIdentifiers(column);
        usersTable.setModel(defaultTableModel);
        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        usersTable.setFillsViewportHeight(true);
        this.sdf = new SimpleDateFormat("dd-MM-yy");
        nowDate = new Date();
        final String date = this.sdf.format(nowDate);
        this.sdf = new SimpleDateFormat("a");
        nowDate = new Date();
        this.shift = "";
        if (this.sdf.format(nowDate).equals("AM")) {
            this.shift = "Morning";
        } else if (this.sdf.format(nowDate).equals("PM")) {
            this.shift = "Evening";
        }

        String[] data = new String[9];

        try {
            ResultSet resultSet = dbConnection.getDailyCollectionByDateAndShift(date, this.shift);

            while(resultSet.next()) {
                int cust_id = resultSet.getInt("customer_id");
                ResultSet customerResult = dbConnection.getCustomer(cust_id);
                data[0] = cust_id + "";
                if (customerResult.next()) {
                    data[1] = customerResult.getString("cust_name");
                    data[2] = customerResult.getString("cattle_type");
                }

                data[3] = resultSet.getString("litre");
                data[4] = resultSet.getString("fat");
                data[5] = resultSet.getString("lacto");
                data[6] = resultSet.getString("snf");
                data[7] = resultSet.getString("rate");
                data[8] = resultSet.getString("total_price");
                defaultTableModel.addRow(data);
            }
        } catch (Exception var44) {
            var44.printStackTrace();
        }

        rightPanel.add(tableScrollPane, "Center");
        JPanel panelButtons = new JPanel(new GridLayout(1, 2, 10, 10));
        this.btnEdit = new JButton("Update", new ImageIcon(this.getClass().getResource("/images/update.png")));
        this.btnPrint = new JButton("Print", new ImageIcon(this.getClass().getResource("/images/print.PNG")));
        panelButtons.add(this.btnEdit);
        panelButtons.add(this.btnPrint);
        rightPanel.add(panelButtons, "First");
        usersTable.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                int currentRowx = 0;
                if (!usersTable.getSelectionModel().isSelectionEmpty()) {
                    int currentRow = usersTable.getSelectedRow();
                    if (!String.valueOf(usersTable.getValueAt(currentRow, 3)).equals("") && !String.valueOf(usersTable.getValueAt(currentRow, 4)).equals("") && !String.valueOf(usersTable.getValueAt(currentRow, 5)).equals("")) {
                        MilkCollection.this.updateTableRow(usersTable);
                    } else {
                        JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "This cant be blank...", "Error", 0);
                    }
                }

            }
        });
        this.btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = usersTable.getSelectedRow();

                try {
                    int cust_id = Integer.parseInt(String.valueOf(usersTable.getValueAt(rowIndex, 0)));
                    String[] values = new String[]{String.valueOf(usersTable.getValueAt(rowIndex, 3)), String.valueOf(usersTable.getValueAt(rowIndex, 4)), String.valueOf(usersTable.getValueAt(rowIndex, 5)), String.valueOf(usersTable.getValueAt(rowIndex, 6)), String.valueOf(usersTable.getValueAt(rowIndex, 7)), String.valueOf(usersTable.getValueAt(rowIndex, 8)), date, MilkCollection.this.shift};
                    int result = MilkCollection.dbConnection.updateDailyCollection(cust_id, values);
                    if (result != 0) {
                        JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "success");
                    } else {
                        JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), "Something went wrong...", "Error", 0);
                    }
                } catch (Exception var6) {
                    var6.printStackTrace();
                }

            }
        });
        this.btnPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String dairyName = "";
                    String dairyAaddress = "";
                    ResultSet currentUser = MilkCollection.dbConnection.getCurrentUser();
                    if (currentUser.next()) {
                        dairyName = currentUser.getString("dairy_name");
                        dairyAaddress = currentUser.getString("dairy_address");
                    }

                    int rowIndex = usersTable.getSelectedRow();
                    String[] values1 = new String[]{MilkCollection.this.shift, String.valueOf(usersTable.getValueAt(rowIndex, 1)), String.valueOf(usersTable.getValueAt(rowIndex, 0)), String.valueOf(usersTable.getValueAt(rowIndex, 2)), String.valueOf(usersTable.getValueAt(rowIndex, 3)), String.valueOf(usersTable.getValueAt(rowIndex, 4)), String.valueOf(usersTable.getValueAt(rowIndex, 6)), String.valueOf(usersTable.getValueAt(rowIndex, 5)), String.valueOf(usersTable.getValueAt(rowIndex, 7)), String.valueOf(usersTable.getValueAt(rowIndex, 8))};
                    DailyCollectionPrintJob dailyCollectionPrintJob = new DailyCollectionPrintJob(values1, dairyName, dairyAaddress);
                    Printsupport ps = new Printsupport();
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 15.0D, 15.0D, 1));

                    try {
                        pj.print();
                    } catch (PrinterException var11) {
                        var11.printStackTrace();
                        JOptionPane.showInternalMessageDialog(MilkCollection.this.getContentPane(), var11, "Error", 0);
                    }
                } catch (Exception var12) {
                    var12.printStackTrace();
                }

            }
        });
        this.getContentPane().add(headerPanel, "First");
        this.getContentPane().add(footerPanel, "Last");
        this.getContentPane().add(leftPanel, "Before");
        this.getContentPane().add(rightPanel, "Center");
    }

    public void updateTableRow(JTable table) {
        int rowIndex = table.getSelectedRow();
        if (this.isDouble(String.valueOf(table.getValueAt(rowIndex, 3))) && this.isDouble(String.valueOf(table.getValueAt(rowIndex, 4))) && this.isDouble(String.valueOf(table.getValueAt(rowIndex, 5)))) {
            this.btnEdit.setEnabled(true);
            Double noLitre = Double.parseDouble(String.valueOf(table.getValueAt(rowIndex, 3)));
            Double noFAT = Double.parseDouble(String.valueOf(table.getValueAt(rowIndex, 4)));
            Double noLacto = Double.parseDouble(String.valueOf(table.getValueAt(rowIndex, 5)));
            Double noSNF = noLacto.doubleValue() / 4.0D + 0.21D * noFAT.doubleValue() + 0.36D;
            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);
            Double noRate = 0.0D;
            String strsnf;
            if (Double.parseDouble(df.format(noSNF)) % 1.0D == 0.0D) {
                strsnf = df.format(noSNF) + ".0";
            } else {
                strsnf = df.format(noSNF);
            }

            try {
                String usersCattleType = String.valueOf(table.getValueAt(rowIndex, 2));
                ResultSet rateResultSet = dbConnection.getRatesByFatAndSnf(usersCattleType, noFAT.toString(), strsnf);
                if (rateResultSet.next()) {
                    noRate = Double.parseDouble(rateResultSet.getString("rate"));
                } else {
                    JOptionPane.showInternalMessageDialog(this.getContentPane(), "Rate for this SNF was not added.\n Please add Rate first from settings page", "Error", 0);
                }
            } catch (Exception var12) {
                var12.printStackTrace();
            }

            table.setValueAt(strsnf, rowIndex, 6);
            table.setValueAt(df.format(noRate), rowIndex, 7);
            table.setValueAt(df.format(noLitre.doubleValue() * noRate.doubleValue()), rowIndex, 8);
        } else if (this.isDouble(String.valueOf(table.getValueAt(rowIndex, 3)))) {
            this.btnEdit.setEnabled(false);
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid Litres", "Error", 0);
        } else if (this.isDouble(String.valueOf(table.getValueAt(rowIndex, 4)))) {
            this.btnEdit.setEnabled(false);
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid FAT", "Error", 0);
        } else if (this.isDouble(String.valueOf(table.getValueAt(rowIndex, 5)))) {
            this.btnEdit.setEnabled(false);
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid Lacto", "Error", 0);
        }

    }

    public boolean isDouble(String val) {
        boolean number = false;

        try {
            Double val1 = Double.parseDouble(val);
            number = true;
        } catch (NumberFormatException var4) {
            number = false;
        }

        return number;
    }
}
