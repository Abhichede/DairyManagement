//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package settings;

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
import java.text.DecimalFormat;

public class RateChart extends JInternalFrame {
    JPanel panelAddRate;
    JPanel panelSeartRate;
    JPanel panelCowRateChart;
    JPanel panelBuffaloRateChart;
    static DatabaseConnection dbConnection = new DatabaseConnection();

    public RateChart() {
        super("Rate Chart", true, true, true, true);
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel panelAddRate = new JPanel((LayoutManager)null);
        JPanel panelSearchRate = new JPanel((LayoutManager)null);
        JPanel panelCowRateChart = new JPanel(new BorderLayout());
        JPanel panelBuffaloRateChart = new JPanel(new BorderLayout());
        JPanel panelRates = new JPanel((LayoutManager)null);
        panelRates.setBounds(250, 160, 600, 400);
        panelRates.setBorder(BorderFactory.createTitledBorder("Rates"));
        tabbedPane.add("Cow Rate Chart", panelCowRateChart);
        tabbedPane.add("Buffalo Rate Chart", panelBuffaloRateChart);
        tabbedPane.add("Add Rate", panelAddRate);
        tabbedPane.add("Find Rate", panelSearchRate);
        Font frameTextFont = new Font("Serif", 1, 14);
        String[] cattleTypes = new String[]{"COW", "BUFFALO"};
        JLabel lblCattleType = new JLabel("Cattle Type");
        lblCattleType.setBounds(300, 30, 100, 30);
        final JComboBox<String> comboCattleType = new JComboBox(cattleTypes);
        comboCattleType.setBounds(450, 30, 200, 30);
        JLabel lblSNF = new JLabel("S.N.F.");
        lblSNF.setBounds(300, 80, 80, 30);
        final JTextField txtSNF = new JTextField(10);
        txtSNF.setText("8.5");
        txtSNF.setEnabled(false);
        txtSNF.setBounds(450, 80, 200, 30);
        txtSNF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblFAT = new JLabel("FAT");
        lblFAT.setBounds(300, 130, 80, 30);
        final JTextField txtFAT = new JTextField(10);
        txtFAT.setEnabled(false);
        txtFAT.setText("3.5");
        txtFAT.setBounds(450, 130, 200, 30);
        JLabel lblRate = new JLabel("Rate");
        lblRate.setBounds(270, 150, 80, 30);
        final JTextField txtRate = new JTextField(5);
        txtRate.setBounds(250, 170, 100, 30);
        JLabel lblTop = new JLabel("Top -");
        lblTop.setFont(frameTextFont);
        lblTop.setBounds(270, 20, 80, 30);
        final JTextField txtTop = new JTextField(5);
        txtTop.setBounds(250, 50, 100, 30);
        txtTop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblBottom = new JLabel("Bottom +");
        lblBottom.setFont(frameTextFont);
        lblBottom.setBounds(270, 260, 80, 30);
        final JTextField txtBottom = new JTextField(5);
        txtBottom.setBounds(250, 290, 100, 30);
        txtBottom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblLeft = new JLabel("Left - (⇦)");
        lblLeft.setFont(frameTextFont);
        lblLeft.setBounds(70, 140, 80, 30);
        final JTextField txtLeft = new JTextField(5);
        txtLeft.setBounds(50, 170, 100, 30);
        txtLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent(txtRate);
            }
        });
        JLabel lblRight = new JLabel("Right +");
        lblRight.setFont(frameTextFont);
        lblRight.setBounds(470, 140, 80, 30);
        final JTextField txtRight = new JTextField(5);
        txtRight.setBounds(450, 170, 100, 30);
        txtRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        comboCattleType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cattleType = ((String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex())).toString();
                if (cattleType.equals("BUFFALO")) {
                    txtSNF.setText("9.0");
                    txtFAT.setText("6.0");
                } else {
                    txtSNF.setText("8.5");
                    txtFAT.setText("3.5");
                }

                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent(txtLeft);
            }
        });
        txtFAT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent(txtLeft);
            }
        });
        txtRate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent(txtFAT);
            }
        });
        JButton btnAddRate = new JButton("Add Rate");
        btnAddRate.setBounds(420, 570, 200, 30);
        InputMap im1 = btnAddRate.getInputMap(0);
        im1.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im1.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        panelAddRate.add(lblCattleType);
        panelAddRate.add(comboCattleType);
        panelAddRate.add(lblSNF);
        panelAddRate.add(txtSNF);
        panelAddRate.add(lblFAT);
        panelAddRate.add(txtFAT);
        panelAddRate.add(panelRates);
        panelRates.add(lblRate);
        panelRates.add(txtRate);
        panelRates.add(lblTop);
        panelRates.add(txtTop);
        panelRates.add(lblBottom);
        panelRates.add(txtBottom);
        panelRates.add(lblLeft);
        panelRates.add(txtLeft);
        panelRates.add(lblRight);
        panelRates.add(txtRight);
        panelAddRate.add(btnAddRate);
        int MAXIMUM = 756;
        if (((String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex())).toString().equals("COW")) {
            MAXIMUM = 756;
        } else {
            MAXIMUM = 966;
        }

        final JProgressBar jProgressBar = new JProgressBar(0, MAXIMUM);
        jProgressBar.setStringPainted(true);
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(jProgressBar, "Center");
        panel.setBorder(BorderFactory.createEmptyBorder(11, 11, 11, 11));
        final JDialog dialog = new JDialog(Frame.getFrames()[0], "Saving...", true);
        dialog.getContentPane().add(panel);
        dialog.setResizable(false);
        dialog.pack();
        dialog.setSize(500, dialog.getHeight());
        dialog.setLocationRelativeTo((Component)null);
        dialog.setDefaultCloseOperation(2);
        btnAddRate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtRate.getText().toString().equals("") && !txtLeft.getText().toString().equals("") && !txtRight.getText().toString().equals("") && !txtTop.getText().toString().equals("") && !txtBottom.getText().toString().equals("")) {
                    RateChart.dbConnection.deleteRate(((String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex())).toString());
                    (new SwingWorker() {
                        protected Object doInBackground() throws Exception {
                            int result = 0;
                            int count = 0;
                            Double dblTop = Double.parseDouble(txtTop.getText().toString());
                            Double dblBottom = Double.parseDouble(txtBottom.getText().toString());
                            Double dblLeft = Double.parseDouble(txtLeft.getText().toString());
                            Double dblRight = Double.parseDouble(txtRight.getText().toString());
                            Double dblSNF = Double.parseDouble(txtSNF.getText().toString());
                            Double dblFAT = Double.parseDouble(txtFAT.getText().toString());
                            String cattleType = ((String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex())).toString();
                            Double left = 0.0D;
                            Double right = 0.0D;
                            Double top = 0.0D;
                            Double bottom = 0.0D;
                            if (cattleType.equals("COW")) {
                                left = 7.0D;
                                right = 9.0D;
                                top = 2.5D;
                                bottom = 6.0D;
                            } else if (cattleType.equals("BUFFALO")) {
                                left = 7.5D;
                                right = 9.5D;
                                top = 5.0D;
                                bottom = 9.4D;
                            }

                            int hCount = 0;
                            int vCount = 0;

                            double ix;
                            for(ix = left.doubleValue(); ix <= right.doubleValue(); ix += 0.1D) {
                                ++vCount;
                            }

                            for(ix = top.doubleValue(); ix <= bottom.doubleValue(); ix += 0.1D) {
                                ++hCount;
                            }

                            String[] valArray = new String[hCount * vCount];
                            Double baseRate = Double.parseDouble(txtRate.getText().toString());
                            DecimalFormat df = new DecimalFormat("#.#");
                            DecimalFormat dfR = new DecimalFormat("#.##");
                            Double currentRate = baseRate;

                            Double rate;
                            double i;
                            double j;
                            String[] values;
                            int resultx;
                            for(i = dblSNF.doubleValue(); i >= left.doubleValue(); i = Double.parseDouble(df.format(i - 0.1D))) {
                                rate = currentRate;

                                for(j = dblFAT.doubleValue(); j >= top.doubleValue(); j = Double.parseDouble(df.format(j - 0.1D))) {
                                    System.out.println("Snf: " + i + "\t FAT: " + j);
                                    values = new String[]{((String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex())).toString(), i + "", j + "", Double.parseDouble(dfR.format(rate)) + ""};
                                    resultx = RateChart.dbConnection.addRate(values);
                                    rate = Double.parseDouble(dfR.format(rate.doubleValue() - dblTop.doubleValue()));
                                    this.updateProgressBar(count);
                                    ++count;
                                }

                                currentRate = Double.parseDouble(dfR.format(currentRate.doubleValue() - dblLeft.doubleValue()));
                            }

                            currentRate = baseRate.doubleValue() + dblRight.doubleValue();

                            for(i = Double.parseDouble(df.format(dblSNF.doubleValue() + 0.1D)); i <= right.doubleValue(); i = Double.parseDouble(df.format(i + 0.1D))) {
                                rate = currentRate;

                                for(j = dblFAT.doubleValue(); j <= bottom.doubleValue(); j = Double.parseDouble(df.format(j + 0.1D))) {
                                    System.out.println("Snf: " + i + "\t FAT: " + j);
                                    values = new String[]{((String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex())).toString(), i + "", j + "", Double.parseDouble(dfR.format(rate)) + ""};
                                    resultx = RateChart.dbConnection.addRate(values);
                                    rate = Double.parseDouble(dfR.format(rate.doubleValue() + dblBottom.doubleValue()));
                                    this.updateProgressBar(count);
                                    ++count;
                                }

                                currentRate = Double.parseDouble(dfR.format(currentRate.doubleValue() + dblRight.doubleValue()));
                            }

                            currentRate = baseRate.doubleValue() + dblBottom.doubleValue();

                            for(i = dblSNF.doubleValue(); i >= left.doubleValue(); i = Double.parseDouble(df.format(i - 0.1D))) {
                                rate = currentRate;

                                for(j = Double.parseDouble(df.format(dblFAT.doubleValue() + 0.1D)); j <= bottom.doubleValue(); j = Double.parseDouble(df.format(j + 0.1D))) {
                                    System.out.println("Snf: " + i + "\t FAT: " + j);
                                    values = new String[]{((String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex())).toString(), i + "", j + "", Double.parseDouble(dfR.format(rate)) + ""};
                                    resultx = RateChart.dbConnection.addRate(values);
                                    rate = Double.parseDouble(dfR.format(rate.doubleValue() + dblBottom.doubleValue()));
                                    this.updateProgressBar(count);
                                    ++count;
                                }

                                currentRate = Double.parseDouble(dfR.format(currentRate.doubleValue() - dblLeft.doubleValue()));
                            }

                            currentRate = baseRate.doubleValue() - dblTop.doubleValue() + dblRight.doubleValue();

                            for(i = Double.parseDouble(df.format(dblSNF.doubleValue() + 0.1D)); i <= right.doubleValue(); i = Double.parseDouble(df.format(i + 0.1D))) {
                                rate = currentRate;

                                for(j = Double.parseDouble(df.format(dblFAT.doubleValue() - 0.1D)); j >= top.doubleValue(); j = Double.parseDouble(df.format(j - 0.1D))) {
                                    System.out.println("Snf: " + i + "\t FAT: " + j);
                                    values = new String[]{((String)comboCattleType.getItemAt(comboCattleType.getSelectedIndex())).toString(), i + "", j + "", Double.parseDouble(dfR.format(rate)) + ""};
                                    resultx = RateChart.dbConnection.addRate(values);
                                    rate = Double.parseDouble(dfR.format(rate.doubleValue() - dblTop.doubleValue()));
                                    this.updateProgressBar(count);
                                    ++count;
                                }

                                currentRate = Double.parseDouble(dfR.format(currentRate.doubleValue() + dblRight.doubleValue()));
                            }

                            return null;
                        }

                        protected void done() {
                            dialog.dispose();
                            JOptionPane.showInternalMessageDialog(RateChart.this.getContentPane(), "Rate Added...", "Success", 1);
                            txtRate.setText("");
                            txtRight.setText("");
                            txtTop.setText("");
                            txtLeft.setText("");
                            txtBottom.setText("");
                            KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                            manager.focusNextComponent(txtLeft);
                            jProgressBar.setValue(0);
                        }

                        void updateProgressBar(int val) {
                            jProgressBar.setValue(val);
                        }
                    }).execute();
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showInternalMessageDialog(RateChart.this.getContentPane(), "Please enter all values", "Error", 0);
                    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusPreviousComponent(txtSNF);
                }

            }
        });
        JLabel lblCattleType_f = new JLabel("Cattle Type");
        lblCattleType_f.setBounds(300, 50, 100, 30);
        final JComboBox<String> comboCattleType_f = new JComboBox(cattleTypes);
        comboCattleType_f.setBounds(450, 50, 200, 30);
        comboCattleType_f.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblSNF_f = new JLabel("Lacto");
        lblSNF_f.setBounds(300, 100, 100, 30);
        final JTextField txtSNF_f = new JTextField(10);
        txtSNF_f.setBounds(450, 100, 200, 30);
        txtSNF_f.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JLabel lblFAT_f = new JLabel("FAT");
        lblFAT_f.setBounds(300, 150, 100, 30);
        final JTextField txtFAT_f = new JTextField(10);
        txtFAT_f.setBounds(450, 150, 200, 30);
        txtFAT_f.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                manager.focusNextComponent();
            }
        });
        JButton btnFindRate = new JButton("Find Rate");
        btnFindRate.setBounds(420, 250, 100, 30);
        InputMap im = btnFindRate.getInputMap(0);
        im.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
        im.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        btnFindRate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!txtSNF_f.getText().toString().equals("") && !txtFAT_f.getText().toString().equals("")) {
                    String[] values = new String[]{((String)comboCattleType_f.getItemAt(comboCattleType_f.getSelectedIndex())).toString(), txtSNF_f.getText().toString(), txtFAT_f.getText().toString()};

                    try {
                        ResultSet result = RateChart.dbConnection.findRate(values);
                        if (result.next()) {
                            JOptionPane.showInternalMessageDialog(RateChart.this.getContentPane(), "Rate : " + result.getString("rate"), "Success", 1);
                            txtSNF_f.setText("");
                            txtFAT_f.setText("");
                            KeyboardFocusManager managerx = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                            managerx.focusPreviousComponent(txtSNF_f);
                        } else {
                            JOptionPane.showInternalMessageDialog(RateChart.this.getContentPane(), "Something went wrong while adding rate!!!", "ERROR", 0);
                        }
                    } catch (Exception var5) {
                        var5.printStackTrace();
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(RateChart.this.getContentPane(), "Please enter all values", "Error", 0);
                    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusPreviousComponent(txtSNF_f);
                }

            }
        });
        panelSearchRate.add(lblCattleType_f);
        panelSearchRate.add(comboCattleType_f);
        panelSearchRate.add(lblSNF_f);
        panelSearchRate.add(txtSNF_f);
        panelSearchRate.add(lblFAT_f);
        panelSearchRate.add(txtFAT_f);
        panelSearchRate.add(btnFindRate);
        String[] rateCowColumns = new String[]{"###", "7.0", "7.1", "7.2", "7.3", "7.4", "7.5", "7.6", "7.7", "7.8", "7.9", "8.0", "8.1", "8.2", "8.3", "8.4", "8.5", "8.6", "8.7", "8.8", "8.9", "9.0"};
        final String[] rateCowColumns1 = new String[]{"###", "7.00", "7.10", "7.20", "7.30", "7.40", "7.50", "7.60", "7.70", "7.80", "7.90", "8.00", "8.10", "8.20", "8.30", "8.40", "8.50", "8.60", "8.70", "8.80", "8.90", "9.00"};
        String[] rateBuffaloColumns = new String[]{"###", "7.5", "7.6", "7.7", "7.8", "7.9", "8.0", "8.1", "8.2", "8.3", "8.4", "8.5", "8.6", "8.7", "8.8", "8.9", "9.0", "9.1", "9.2", "9.3", "9.4", "9.5"};
        final String[] rateBuffaloColumns1 = new String[]{"###", "7.50", "7.60", "7.70", "7.80", "7.90", "8.00", "8.10", "8.20", "8.30", "8.40", "8.50", "8.60", "8.70", "8.80", "8.90", "9.00", "9.10", "9.20", "9.30", "9.40", "9.50"};
        String[] strCowFat = new String[36];
        String[] strBuffaloFat = new String[45];
        DecimalFormat df = new DecimalFormat("#.#");
        double d = 2.5D;

        int i;
        for(i = 0; i < 36; ++i) {
            if (d % 1.0D == 0.0D) {
                strCowFat[i] = df.format(d) + ".0";
            } else {
                strCowFat[i] = df.format(d);
            }

            d = Double.parseDouble(df.format(d + 0.1D));
        }

        d = 5.0D;

        for(i = 0; i < strBuffaloFat.length; ++i) {
            if (d % 1.0D == 0.0D) {
                strBuffaloFat[i] = df.format(d) + ".0";
            } else {
                strBuffaloFat[i] = df.format(d);
            }

            d = Double.parseDouble(df.format(d + 0.1D));
        }

        final JTable usersTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usersTable.setSelectionMode(0);
        usersTable.setAutoResizeMode(4);
        DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
        defaultTableModel.setColumnIdentifiers(rateCowColumns1);
        String[] data = new String[22];
        usersTable.setModel(defaultTableModel);

        try {
            for(i = 0; i < strCowFat.length; ++i) {
                data[0] = strCowFat[i];

                for(int j = 1; j < rateCowColumns.length; ++j) {
                    ResultSet resultSet = dbConnection.getRatesByFatAndSnf("COW", strCowFat[i], rateCowColumns[j]);
                    if (resultSet.next()) {
                        data[j] = resultSet.getString("rate");
                    } else {
                        data[j] = "";
                    }
                }

                defaultTableModel.addRow(data);
            }
        } catch (Exception var60) {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), var60, "Error", 0);
        }

        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        usersTable.setFillsViewportHeight(true);
        JButton btnCowPrint = new JButton("Print", new ImageIcon(this.getClass().getResource("/images/print.PNG")));
        btnCowPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printingJobs.RateChart dailyCollectionPrintJob = new printingJobs.RateChart(usersTable, rateCowColumns1);
                Printsupport ps = new Printsupport();
                PrinterJob pj = PrinterJob.getPrinterJob();
                pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 21.0D, 29.7D, 0));

                try {
                    pj.print();
                } catch (PrinterException var6) {
                    var6.printStackTrace();
                    JOptionPane.showInternalMessageDialog(RateChart.this.getContentPane(), var6, "Error", 0);
                }

            }
        });
        panelCowRateChart.add(tableScrollPane, "Center");
        panelCowRateChart.add(btnCowPrint, "First");
        final JTable usersTable1 = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usersTable1.setSelectionMode(0);
        usersTable1.setAutoResizeMode(4);
        DefaultTableModel defaultTableModel1 = new DefaultTableModel(0, 0);
        defaultTableModel1.setColumnIdentifiers(rateBuffaloColumns1);
        usersTable1.setModel(defaultTableModel1);

        try {
            for(i = 0; i < strBuffaloFat.length; ++i) {
                data[0] = strBuffaloFat[i];

                for(int j = 1; j < rateBuffaloColumns.length; ++j) {
                    ResultSet resultSet = dbConnection.getRatesByFatAndSnf("BUFFALO", strBuffaloFat[i], rateBuffaloColumns[j]);
                    if (resultSet.next()) {
                        data[j] = resultSet.getString("rate");
                    } else {
                        data[j] = "";
                    }
                }

                defaultTableModel1.addRow(data);
            }
        } catch (Exception var59) {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), var59, "Error", 0);
        }

        JScrollPane tableScrollPane1 = new JScrollPane(usersTable1);
        usersTable1.setFillsViewportHeight(true);
        JButton btnBuffaloPrint = new JButton("Print", new ImageIcon(this.getClass().getResource("/images/print.PNG")));
        btnBuffaloPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printingJobs.RateChart dailyCollectionPrintJob = new printingJobs.RateChart(usersTable1, rateBuffaloColumns1);
                Printsupport ps = new Printsupport();
                PrinterJob pj = PrinterJob.getPrinterJob();
                pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 21.0D, 29.7D, 0));

                try {
                    pj.print();
                } catch (PrinterException var6) {
                    var6.printStackTrace();
                    JOptionPane.showInternalMessageDialog(RateChart.this.getContentPane(), var6, "Error", 0);
                }

            }
        });
        panelBuffaloRateChart.add(tableScrollPane1, "Center");
        panelBuffaloRateChart.add(btnBuffaloPrint, "First");
        this.add(tabbedPane);
    }
}
