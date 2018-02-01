//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package employees;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import main.DatabaseConnection;
import main.Printsupport;
import printingJobs.EmployeeSalarySlip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;

public class PayEmployeeSalary extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblPaymentType;
    private JLabel lblPaymentDesc;
    private JLabel lblPaymentMethod;
    private JLabel lblPaymentMethodDesc;
    private JLabel lblAmount;
    private JLabel lblBonus;
    private JComboBox txtPaymentType;
    private JTextField txtPaymentDesc;
    private JComboBox txtPaymentMethod;
    private JTextField txtPaymentMethodDesc;
    private JTextField txtAmount;
    private JTextField txtBonus;
    private JLabel lblEmployeeName;
    private JPanel panelTop;
    private JPanel panelBottom;
    private int employee_ID;
    private String employee_Name;
    private DatabaseConnection dbConnection = new DatabaseConnection();

    public PayEmployeeSalary(String employeeName, int employeeID) {
        this.$$$setupUI$$$();
        this.setContentPane(this.contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(this.buttonOK);
        this.employee_ID = employeeID;
        this.employee_Name = employeeName;
        this.lblEmployeeName.setText(employeeName);
        this.setTitle("Payment To: " + employeeID + " - " + employeeName);
        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PayEmployeeSalary.this.onOK();
            }
        });
        this.buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PayEmployeeSalary.this.onCancel();
            }
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PayEmployeeSalary.this.onCancel();
            }
        });
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PayEmployeeSalary.this.onCancel();
            }
        }, KeyStroke.getKeyStroke(27, 0), 1);
    }

    private void onOK() {
        boolean closeFlag = false;
        String strPaymentType = this.txtPaymentType.getItemAt(this.txtPaymentType.getSelectedIndex()).toString();
        String strPaymentDescription = this.txtPaymentDesc.getText();
        String strPaymentMethod = this.txtPaymentMethod.getItemAt(this.txtPaymentMethod.getSelectedIndex()).toString();
        String strPaymentMethodDesc = this.txtPaymentMethodDesc.getText();
        String strAmount = this.txtAmount.getText();
        String strBonus = this.txtBonus.getText();
        if (strPaymentType.equals("")) {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid payment type", "Error", 0);
        } else if (strPaymentDescription.equals("")) {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter payment Description", "Error", 0);
        } else if (strPaymentMethod.equals("")) {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please Select payment Method", "Error", 0);
        } else if (strPaymentMethodDesc.equals("")) {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter payment Method description", "Error", 0);
        } else if (!strAmount.equals("") && this.isNumber(strAmount)) {
            if (!strBonus.equals("") && this.isNumber(strBonus)) {
                String[] values = new String[]{strPaymentType, strPaymentDescription, strPaymentMethod, strPaymentMethodDesc, strAmount, strBonus};
                int result = this.dbConnection.addEmployeeSalary(this.employee_ID, values);
                if (result != 0) {
                    closeFlag = true;
                    JOptionPane.showInternalMessageDialog(this.getContentPane(), "salary added successfully");
                    String dairyName = "";
                    String dairyAaddress = "";

                    try {
                        ResultSet currentUser = this.dbConnection.getCurrentUser();
                        if (currentUser.next()) {
                            dairyName = currentUser.getString("dairy_name");
                            dairyAaddress = currentUser.getString("dairy_address");
                        }
                    } catch (Exception var20) {
                        var20.printStackTrace();
                    }

                    String balance_amount = "";

                    try {
                        ResultSet employeeResult = this.dbConnection.getEmployeeDetailsByID(this.employee_ID);
                        if (employeeResult.next()) {
                            balance_amount = employeeResult.getString("current_month_salary");
                        }
                    } catch (Exception var19) {
                        var19.printStackTrace();
                    }

                    String[] values1 = new String[]{this.employee_Name, strAmount, strPaymentType, strPaymentDescription, strPaymentMethod, strPaymentMethodDesc, strBonus, balance_amount};
                    EmployeeSalarySlip dailyCollectionPrintJob = new EmployeeSalarySlip(values1, dairyName, dairyAaddress);
                    Printsupport ps = new Printsupport();
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(dailyCollectionPrintJob, ps.getPageFormat(pj, 15.0D, 15.0D, 1));

                    try {
                        pj.print();
                    } catch (PrinterException var18) {
                        var18.printStackTrace();
                        JOptionPane.showInternalMessageDialog(this.getContentPane(), var18, "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(this.getContentPane(), "Salary not added", "Something went wrong", 0);
                }
            } else {
                JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid Bonus Amount", "Error", 0);
            }
        } else {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid Amount", "Error", 0);
        }

        if (closeFlag) {
            this.dispose();
        }

    }

    private void onCancel() {
        this.dispose();
    }

    public boolean isNumber(String number) {
        boolean flag = false;

        try {
            Double num = Double.parseDouble(number);
            flag = true;
        } catch (NumberFormatException var4) {
            var4.printStackTrace();
            flag = false;
        }

        return flag;
    }

    private void $$$setupUI$$$() {
        this.contentPane = new JPanel();
        this.contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        this.panelTop = new JPanel();
        this.panelTop.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
        this.contentPane.add(this.panelTop, new GridConstraints(0, 0, 1, 1, 0, 3, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelTop.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Payment Details"));
        this.lblPaymentType = new JLabel();
        Font lblPaymentTypeFont = this.$$$getFont$$$("Monospaced", 1, 14, this.lblPaymentType.getFont());
        if (lblPaymentTypeFont != null) {
            this.lblPaymentType.setFont(lblPaymentTypeFont);
        }

        this.lblPaymentType.setText("Payment Type");
        this.panelTop.add(this.lblPaymentType, new GridConstraints(0, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblPaymentDesc = new JLabel();
        Font lblPaymentDescFont = this.$$$getFont$$$("Monospaced", 1, 14, this.lblPaymentDesc.getFont());
        if (lblPaymentDescFont != null) {
            this.lblPaymentDesc.setFont(lblPaymentDescFont);
        }

        this.lblPaymentDesc.setText("Payment Description");
        this.panelTop.add(this.lblPaymentDesc, new GridConstraints(1, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblPaymentMethod = new JLabel();
        Font lblPaymentMethodFont = this.$$$getFont$$$("Monospaced", 1, 14, this.lblPaymentMethod.getFont());
        if (lblPaymentMethodFont != null) {
            this.lblPaymentMethod.setFont(lblPaymentMethodFont);
        }

        this.lblPaymentMethod.setText("Payment Method");
        this.panelTop.add(this.lblPaymentMethod, new GridConstraints(2, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblPaymentMethodDesc = new JLabel();
        Font lblPaymentMethodDescFont = this.$$$getFont$$$("Monospaced", 1, 14, this.lblPaymentMethodDesc.getFont());
        if (lblPaymentMethodDescFont != null) {
            this.lblPaymentMethodDesc.setFont(lblPaymentMethodDescFont);
        }

        this.lblPaymentMethodDesc.setText("Payment Method Desc.");
        this.panelTop.add(this.lblPaymentMethodDesc, new GridConstraints(3, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblAmount = new JLabel();
        Font lblAmountFont = this.$$$getFont$$$("Monospaced", 1, 14, this.lblAmount.getFont());
        if (lblAmountFont != null) {
            this.lblAmount.setFont(lblAmountFont);
        }

        this.lblAmount.setText("Amount");
        this.panelTop.add(this.lblAmount, new GridConstraints(4, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblBonus = new JLabel();
        Font lblBonusFont = this.$$$getFont$$$("Monospaced", 1, 14, this.lblBonus.getFont());
        if (lblBonusFont != null) {
            this.lblBonus.setFont(lblBonusFont);
        }

        this.lblBonus.setText("Bonus (if any)");
        this.panelTop.add(this.lblBonus, new GridConstraints(5, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.txtPaymentType = new JComboBox();
        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Salary");
        defaultComboBoxModel1.addElement("Advance");
        this.txtPaymentType.setModel(defaultComboBoxModel1);
        this.panelTop.add(this.txtPaymentType, new GridConstraints(0, 2, 1, 1, 8, 1, 2, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.panelTop.add(spacer1, new GridConstraints(0, 1, 1, 1, 0, 1, 4, 1, (Dimension)null, new Dimension(50, -1), (Dimension)null, 0, false));
        this.txtPaymentDesc = new JTextField();
        this.panelTop.add(this.txtPaymentDesc, new GridConstraints(1, 2, 1, 1, 8, 1, 4, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.txtPaymentMethodDesc = new JTextField();
        this.panelTop.add(this.txtPaymentMethodDesc, new GridConstraints(3, 2, 1, 1, 8, 1, 4, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.txtAmount = new JTextField();
        this.panelTop.add(this.txtAmount, new GridConstraints(4, 2, 1, 1, 8, 1, 4, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.txtBonus = new JTextField();
        this.txtBonus.setText("0");
        this.txtBonus.setToolTipText("Add bonus if Any");
        this.panelTop.add(this.txtBonus, new GridConstraints(5, 2, 1, 1, 8, 1, 4, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.txtPaymentMethod = new JComboBox();
        DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("CASH");
        defaultComboBoxModel2.addElement("CHEQUE");
        defaultComboBoxModel2.addElement("RTGS");
        defaultComboBoxModel2.addElement("NEFT");
        defaultComboBoxModel2.addElement("IMPS");
        defaultComboBoxModel2.addElement("OTHER");
        this.txtPaymentMethod.setModel(defaultComboBoxModel2);
        this.panelTop.add(this.txtPaymentMethod, new GridConstraints(2, 2, 1, 1, 8, 1, 2, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.panelBottom = new JPanel();
        this.panelBottom.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.contentPane.add(this.panelBottom, new GridConstraints(1, 0, 1, 1, 0, 3, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelBottom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Actions"));
        this.buttonOK = new JButton();
        Font buttonOKFont = this.$$$getFont$$$("Monospaced", 1, 16, this.buttonOK.getFont());
        if (buttonOKFont != null) {
            this.buttonOK.setFont(buttonOKFont);
        }

        this.buttonOK.setIcon(new ImageIcon(this.getClass().getResource("/images/save.PNG")));
        this.buttonOK.setText("Save / Print");
        this.panelBottom.add(this.buttonOK, new GridConstraints(0, 2, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.buttonCancel = new JButton();
        Font buttonCancelFont = this.$$$getFont$$$("Monospaced", 1, 16, this.buttonCancel.getFont());
        if (buttonCancelFont != null) {
            this.buttonCancel.setFont(buttonCancelFont);
        }

        this.buttonCancel.setIcon(new ImageIcon(this.getClass().getResource("/images/cancel.PNG")));
        this.buttonCancel.setText("Cancel");
        this.panelBottom.add(this.buttonCancel, new GridConstraints(0, 3, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblEmployeeName = new JLabel();
        Font lblEmployeeNameFont = this.$$$getFont$$$("Monospaced", 1, 14, this.lblEmployeeName.getFont());
        if (lblEmployeeNameFont != null) {
            this.lblEmployeeName.setFont(lblEmployeeNameFont);
        }

        this.lblEmployeeName.setForeground(new Color(-7270382));
        this.lblEmployeeName.setText("");
        this.panelBottom.add(this.lblEmployeeName, new GridConstraints(0, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.panelBottom.add(spacer2, new GridConstraints(0, 1, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
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
