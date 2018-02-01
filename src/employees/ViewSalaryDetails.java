//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package employees;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
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

public class ViewSalaryDetails extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTable tableDetails;
    private JScrollPane scrollSalaryDeetails;
    private JPanel panelBottom;
    DatabaseConnection databaseConnection = new DatabaseConnection();

    public ViewSalaryDetails(String emp_name, int employeeID) {
        this.$$$setupUI$$$();
        this.setContentPane(this.contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(this.buttonCancel);
        String[] column = new String[]{"Date", "Payment Type", "Payment Desc", "Payment Method", "Desc", "Amount", "Bonus"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
        defaultTableModel.setColumnIdentifiers(column);
        this.tableDetails.setModel(defaultTableModel);
        this.setTitle("Salary Details of: " + emp_name);

        try {
            ResultSet resultSet = this.databaseConnection.getEmployeeSalaryDetailsByID(employeeID);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            if (resultSet != null) {
                while(resultSet.next()) {
                    String[] data = new String[]{dateFormat.format(resultSet.getTimestamp("create_at")), resultSet.getString("payment_type"), resultSet.getString("payment_desc"), resultSet.getString("payment_mode"), resultSet.getString("payment_mode_desc"), resultSet.getString("amount"), resultSet.getString("bonus")};
                    defaultTableModel.addRow(data);
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        this.buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewSalaryDetails.this.onCancel();
            }
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ViewSalaryDetails.this.onCancel();
            }
        });
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewSalaryDetails.this.onCancel();
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
        this.contentPane.setLayout(new GridBagLayout());
        this.panelBottom = new JPanel();
        this.panelBottom.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0D;
        gbc.fill = 1;
        this.contentPane.add(this.panelBottom, gbc);
        this.panelBottom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Actions"));
        this.buttonCancel = new JButton();
        Font buttonCancelFont = this.$$$getFont$$$("Monospaced", 1, 16, this.buttonCancel.getFont());
        if (buttonCancelFont != null) {
            this.buttonCancel.setFont(buttonCancelFont);
        }

        this.buttonCancel.setIcon(new ImageIcon(this.getClass().getResource("/images/cancel.PNG")));
        this.buttonCancel.setSelected(true);
        this.buttonCancel.setText("Cancel");
        this.panelBottom.add(this.buttonCancel, new GridConstraints(0, 2, 1, 1, 4, 0, 0, 0, (Dimension)null, new Dimension(100, 30), (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.panelBottom.add(spacer1, new GridConstraints(0, 3, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.panelBottom.add(spacer2, new GridConstraints(0, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer3 = new Spacer();
        this.panelBottom.add(spacer3, new GridConstraints(0, 1, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer4 = new Spacer();
        this.panelBottom.add(spacer4, new GridConstraints(1, 2, 1, 1, 0, 2, 1, 4, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0D;
        gbc.weighty = 1.0D;
        gbc.fill = 1;
        this.contentPane.add(panel1, gbc);
        this.scrollSalaryDeetails = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0D;
        gbc.weighty = 1.0D;
        gbc.fill = 1;
        panel1.add(this.scrollSalaryDeetails, gbc);
        this.scrollSalaryDeetails.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Salary Details"));
        this.tableDetails = new JTable();
        this.tableDetails.setCellSelectionEnabled(false);
        this.tableDetails.setColumnSelectionAllowed(true);
        this.tableDetails.setFillsViewportHeight(true);
        this.tableDetails.setRowSelectionAllowed(false);
        this.scrollSalaryDeetails.setViewportView(this.tableDetails);
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
