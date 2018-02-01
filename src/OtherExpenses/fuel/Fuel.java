//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package OtherExpenses.fuel;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import main.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Fuel extends JInternalFrame {
    private JPanel topPanel;
    private JPanel panelBottom;
    private JTable table1;
    private JPanel mainPanel;
    private JButton addFuelButton;
    private JButton btnCurrentMonth;
    private JPanel panelTotals;
    private JLabel lblLit;
    private JLabel lblAmo;
    private JLabel lblAmount;
    private JLabel lblLitres;
    private JButton btnEditFuel;
    private DefaultTableModel defaultTableModel;
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public Fuel() {
        super("Fuel Info", false, true, false, false);
        this.$$$setupUI$$$();
        String[] column = new String[]{"ID", "Date", "Description", "Litres", "Amount"};
        this.defaultTableModel = new DefaultTableModel(0, 0);
        this.defaultTableModel.setColumnIdentifiers(column);
        this.table1.setModel(this.defaultTableModel);

        try {
            ResultSet resultSet = this.databaseConnection.getFuelDetails();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            if (resultSet != null) {
                while(resultSet.next()) {
                    String[] data = new String[]{String.valueOf(resultSet.getInt("ID")), dateFormat.format(resultSet.getTimestamp("date")), resultSet.getString("description"), resultSet.getString("litres"), resultSet.getString("amount")};
                    this.defaultTableModel.addRow(data);
                }

                this.setTotals();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        this.add(this.mainPanel);
        this.addFuelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog paySalaryDialog = new AddFuel();
                Dimension mainPanelSize = Fuel.this.mainPanel.getSize();
                paySalaryDialog.setSize((int)mainPanelSize.getWidth() / 2, (int)mainPanelSize.getHeight() / 2);
                Dimension dialogSize = paySalaryDialog.getSize();
                paySalaryDialog.setLocation((mainPanelSize.width - dialogSize.width) / 2, (mainPanelSize.height - dialogSize.height) / 2);
                paySalaryDialog.setVisible(true);
            }
        });
        this.btnCurrentMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog paySalaryDialog = new CurrentMonthFuel();
                Dimension mainPanelSize = Fuel.this.mainPanel.getSize();
                paySalaryDialog.setSize((int)mainPanelSize.getWidth() / 2, (int)mainPanelSize.getHeight() / 2);
                Dimension dialogSize = paySalaryDialog.getSize();
                paySalaryDialog.setLocation((mainPanelSize.width - dialogSize.width) / 2, (mainPanelSize.height - dialogSize.height) / 2);
                paySalaryDialog.setVisible(true);
            }
        });
        this.btnEditFuel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = Fuel.this.table1.getSelectedRow();
                if (rowIndex >= 0) {
                    try {
                        int cust_id = Integer.parseInt(String.valueOf(Fuel.this.table1.getValueAt(rowIndex, 0)));
                        String[] values = new String[]{String.valueOf(Fuel.this.table1.getValueAt(rowIndex, 2)), String.valueOf(Fuel.this.table1.getValueAt(rowIndex, 3)), String.valueOf(Fuel.this.table1.getValueAt(rowIndex, 4))};
                        int result = Fuel.this.databaseConnection.updateFuel(values, cust_id);
                        if (result != 0) {
                            JOptionPane.showInternalMessageDialog(Fuel.this.getContentPane(), "success");
                            Fuel.this.setTotals();
                        } else {
                            JOptionPane.showInternalMessageDialog(Fuel.this.getContentPane(), "Something went wrong...", "Error", 0);
                        }
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(Fuel.this.getContentPane(), "Please select an updated row", "Error", 0);
                }

            }
        });
    }

    private void setTotals() {
        try {
            Double totLitres = 0.0D;
            Double totAmount = 0.0D;
            new SimpleDateFormat("dd-MM-yyyy");
            ResultSet resultSet = this.databaseConnection.getFuelDetails();
            if (resultSet != null) {
                while(resultSet.next()) {
                    totLitres = totLitres.doubleValue() + Double.parseDouble(resultSet.getString("litres"));
                    totAmount = totAmount.doubleValue() + Double.parseDouble(resultSet.getString("amount"));
                }
            }

            this.lblLitres.setText("" + totLitres);
            this.lblAmount.setText("" + totAmount);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    private void $$$setupUI$$$() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.topPanel = new JPanel();
        this.topPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.topPanel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fuel Information "));
        JScrollPane scrollPane1 = new JScrollPane();
        this.topPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 1, 0, 3, 5, 5, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.table1 = new JTable();
        this.table1.setAutoResizeMode(4);
        this.table1.setFillsViewportHeight(true);
        scrollPane1.setViewportView(this.table1);
        this.panelBottom = new JPanel();
        this.panelBottom.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.panelBottom, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelBottom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Actions"));
        this.addFuelButton = new JButton();
        Font addFuelButtonFont = this.$$$getFont$$$("Monospaced", 1, 16, this.addFuelButton.getFont());
        if (addFuelButtonFont != null) {
            this.addFuelButton.setFont(addFuelButtonFont);
        }

        this.addFuelButton.setIcon(new ImageIcon(this.getClass().getResource("/images/newreg.png")));
        this.addFuelButton.setText("Add Fuel");
        this.panelBottom.add(this.addFuelButton, new GridConstraints(0, 1, 1, 1, 0, 1, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.btnCurrentMonth = new JButton();
        Font btnCurrentMonthFont = this.$$$getFont$$$("Monospaced", 1, 16, this.btnCurrentMonth.getFont());
        if (btnCurrentMonthFont != null) {
            this.btnCurrentMonth.setFont(btnCurrentMonthFont);
        }

        this.btnCurrentMonth.setIcon(new ImageIcon(this.getClass().getResource("/images/view.png")));
        this.btnCurrentMonth.setText("Current Month Fuel");
        this.panelBottom.add(this.btnCurrentMonth, new GridConstraints(0, 3, 1, 1, 0, 1, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelTotals = new JPanel();
        this.panelTotals.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.panelBottom.add(this.panelTotals, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelTotals.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Totals"));
        this.lblLit = new JLabel();
        Font lblLitFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblLit.getFont());
        if (lblLitFont != null) {
            this.lblLit.setFont(lblLitFont);
        }

        this.lblLit.setForeground(new Color(-4510164));
        this.lblLit.setText("Litres:");
        this.panelTotals.add(this.lblLit, new GridConstraints(0, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblAmo = new JLabel();
        Font lblAmoFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAmo.getFont());
        if (lblAmoFont != null) {
            this.lblAmo.setFont(lblAmoFont);
        }

        this.lblAmo.setForeground(new Color(-4510164));
        this.lblAmo.setText("Amount:");
        this.panelTotals.add(this.lblAmo, new GridConstraints(1, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblLitres = new JLabel();
        Font lblLitresFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblLitres.getFont());
        if (lblLitresFont != null) {
            this.lblLitres.setFont(lblLitresFont);
        }

        this.lblLitres.setForeground(new Color(-12186869));
        this.lblLitres.setText("");
        this.panelTotals.add(this.lblLitres, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblAmount = new JLabel();
        Font lblAmountFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblAmount.getFont());
        if (lblAmountFont != null) {
            this.lblAmount.setFont(lblAmountFont);
        }

        this.lblAmount.setForeground(new Color(-12186869));
        this.lblAmount.setText("");
        this.panelTotals.add(this.lblAmount, new GridConstraints(1, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.btnEditFuel = new JButton();
        Font btnEditFuelFont = this.$$$getFont$$$("Monospaced", 1, 16, this.btnEditFuel.getFont());
        if (btnEditFuelFont != null) {
            this.btnEditFuel.setFont(btnEditFuelFont);
        }

        this.btnEditFuel.setIcon(new ImageIcon(this.getClass().getResource("/images/editmark.png")));
        this.btnEditFuel.setText("Edit");
        this.panelBottom.add(this.btnEditFuel, new GridConstraints(0, 2, 1, 1, 0, 1, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
    }

    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) {
            return null;
        } else {
            String resultName;
            if (fontName == null) {
                resultName = currentFont.getName();
            } else {
                Font testFont = new Font(fontName, 0, 10);
                if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                    resultName = fontName;
                } else {
                    resultName = currentFont.getName();
                }
            }

            return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        }
    }

    public JComponent $$$getRootComponent$$$() {
        return this.mainPanel;
    }
}
