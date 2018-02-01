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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CurrentMonthFuel extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTable table1;
    private JPanel panelTotals;
    private JLabel lblLit;
    private JLabel lblAmount;
    private JLabel lblShowLitres;
    private JLabel lblShowAmount;
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public CurrentMonthFuel() {
        this.$$$setupUI$$$();
        this.setContentPane(this.contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(this.buttonCancel);
        this.setTitle("Current Month Fuel");
        String[] column = new String[]{"Date", "Description", "Litres", "Amount"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(0, 4);
        defaultTableModel.setColumnIdentifiers(column);
        this.table1.setModel(defaultTableModel);

        try {
            ResultSet resultSet = this.databaseConnection.getCurrentMonthFuelDetails();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Double totLitres = 0.0D;
            Double totAmount = 0.0D;
            if (resultSet != null) {
                while(resultSet.next()) {
                    String[] data = new String[]{dateFormat.format(resultSet.getTimestamp("date")), resultSet.getString("description"), resultSet.getString("litres"), resultSet.getString("amount")};
                    totLitres = totLitres.doubleValue() + Double.parseDouble(resultSet.getString("litres"));
                    totAmount = totAmount.doubleValue() + Double.parseDouble(resultSet.getString("amount"));
                    defaultTableModel.addRow(data);
                }
            }

            this.lblShowLitres.setText("" + totLitres);
            this.lblShowAmount.setText("" + totAmount);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        this.buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrentMonthFuel.this.onCancel();
            }
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                CurrentMonthFuel.this.onCancel();
            }
        });
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrentMonthFuel.this.onCancel();
            }
        }, KeyStroke.getKeyStroke(27, 0), 1);
    }

    private void onOK() {
        this.dispose();
    }

    private void onCancel() {
        this.dispose();
    }

    private void $$$setupUI$$$() {
        this.contentPane = new JPanel();
        this.contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.buttonCancel = new JButton();
        this.buttonCancel.setText("Cancel");
        panel2.add(this.buttonCancel, new GridConstraints(0, 0, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelTotals = new JPanel();
        this.panelTotals.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(this.panelTotals, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelTotals.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Totals"));
        this.lblLit = new JLabel();
        Font lblLitFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblLit.getFont());
        if (lblLitFont != null) {
            this.lblLit.setFont(lblLitFont);
        }

        this.lblLit.setForeground(new Color(-7270382));
        this.lblLit.setText("Litres:");
        this.panelTotals.add(this.lblLit, new GridConstraints(0, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblAmount = new JLabel();
        Font lblAmountFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAmount.getFont());
        if (lblAmountFont != null) {
            this.lblAmount.setFont(lblAmountFont);
        }

        this.lblAmount.setForeground(new Color(-7270382));
        this.lblAmount.setText("Amount:");
        this.panelTotals.add(this.lblAmount, new GridConstraints(1, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblShowLitres = new JLabel();
        Font lblShowLitresFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblShowLitres.getFont());
        if (lblShowLitresFont != null) {
            this.lblShowLitres.setFont(lblShowLitresFont);
        }

        this.lblShowLitres.setForeground(new Color(-12186869));
        this.lblShowLitres.setText("");
        this.panelTotals.add(this.lblShowLitres, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblShowAmount = new JLabel();
        Font lblShowAmountFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblShowAmount.getFont());
        if (lblShowAmountFont != null) {
            this.lblShowAmount.setFont(lblShowAmountFont);
        }

        this.lblShowAmount.setForeground(new Color(-12186869));
        this.lblShowAmount.setText("");
        this.panelTotals.add(this.lblShowAmount, new GridConstraints(1, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, 0, 3, 5, 5, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.table1 = new JTable();
        scrollPane1.setViewportView(this.table1);
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
        return this.contentPane;
    }
}
