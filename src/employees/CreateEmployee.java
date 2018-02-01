package employees;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import main.DatabaseConnection;
import main.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class CreateEmployee extends JInternalFrame {
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTextField txtName;
    private JLabel lblName;
    private JLabel lblAddress;
    private JLabel lblMobile;
    private JLabel lblAllocatedSalary;
    private JLabel lblSalaryStartDate;
    private JLabel lblDesignation;
    private JLabel lblDesignationDetails;
    private JTextField txtAddress;
    private JTextField txtMobile;
    private JTextField txtAllocatedSalary;
    private JComboBox txtDesignation;
    private JTextField txtDesignationDetails;
    private JLabel lblBankAccountNumber;
    private JTextField txtBankAccountNumber;
    private JLabel lblBankName;
    private JLabel lblBankIfsc;
    private JLabel lblBankBranch;
    private JTextField txtBankName;
    private JTextField txtBankIfsc;
    private JTextField txtBankBranch;
    private JButton addEmployeeButton;
    private DatabaseConnection dbConnection = new DatabaseConnection();
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    private UtilDateModel model2 = new UtilDateModel();
    Properties p = new Properties();
    private JDatePanelImpl fromDatePanel;
    private JDatePickerImpl toDate;
    private JPanel panelBottom;
    private JButton btnClear;

    public CreateEmployee() {
        super("Add Employee", true, true, true, true);
        this.$$$setupUI$$$();
        this.p.put("text.today", "Today");
        this.p.put("text.month", "Month");
        this.p.put("text.year", "Year");
        this.addEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strName = CreateEmployee.this.txtName.getText();
                String strAddress = CreateEmployee.this.txtAddress.getText();
                String strMobile = CreateEmployee.this.txtMobile.getText();
                String strAllocatedSalary = CreateEmployee.this.txtAllocatedSalary.getText();
                String strStartDate = CreateEmployee.this.toDate.getJFormattedTextField().getText();
                String strDesignation = CreateEmployee.this.txtDesignation.getItemAt(CreateEmployee.this.txtDesignation.getSelectedIndex()).toString();
                String strDesignationDetails = CreateEmployee.this.txtDesignationDetails.getText();
                String strBankAccNumber = CreateEmployee.this.txtBankAccountNumber.getText();
                String strBankName = CreateEmployee.this.txtBankName.getText();
                String strBankIfsc = CreateEmployee.this.txtBankIfsc.getText();
                String strBankBranch = CreateEmployee.this.txtBankBranch.getText();
                if (strName.equals("")) {
                    JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Employee's Name", "Error", 0);
                } else if (strAddress.equals("")) {
                    JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Employee's Address", "Error", 0);
                } else if (!strMobile.equals("") && strMobile.length() == 10 && CreateEmployee.this.isNumber(strMobile)) {
                    if (strAllocatedSalary.equals("")) {
                        JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Employee's Salary", "Error", 0);
                    } else if (strStartDate.equals("")) {
                        JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Employee's Salary Start Sate", "Error", 0);
                    } else if (strDesignation.equals("")) {
                        JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Employee's Designation(What work assigned to employee)", "Error", 0);
                    } else if (strDesignationDetails.equals("")) {
                        JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please Write Something about Designation(Ex. Vehicle Number etc.)", "Error", 0);
                    } else if (!strBankAccNumber.equals("") && CreateEmployee.this.isNumber(strBankAccNumber)) {
                        if (strBankName.equals("")) {
                            JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Employee's Bank Name", "Error", 0);
                        } else if (strBankIfsc.equals("")) {
                            JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Employee's Bank IFSC", "Error", 0);
                        } else if (strBankBranch.equals("")) {
                            JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Employee's Bank Branch", "Error", 0);
                        } else {
                            String[] employeeData = new String[]{strName, strAddress, strMobile, strAllocatedSalary, strStartDate, strDesignation, strDesignationDetails, strBankAccNumber, strBankName, strBankIfsc, strBankBranch};

                            try {
                                int resultSet = CreateEmployee.this.dbConnection.addEmployee(employeeData);
                                if (resultSet != 0) {
                                    JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Employee Added Successfully");
                                    CreateEmployee.this.txtName.setText("");
                                    CreateEmployee.this.txtAddress.setText("");
                                    CreateEmployee.this.txtMobile.setText("");
                                    CreateEmployee.this.txtAllocatedSalary.setText("");
                                    CreateEmployee.this.toDate.getJFormattedTextField().setValue((Object)null);
                                    CreateEmployee.this.toDate.getModel().setSelected(false);
                                    CreateEmployee.this.txtDesignation.setSelectedIndex(0);
                                    CreateEmployee.this.txtDesignationDetails.setText("");
                                    CreateEmployee.this.txtBankAccountNumber.setText("");
                                    CreateEmployee.this.txtBankName.setText("");
                                    CreateEmployee.this.txtBankIfsc.setText("");
                                    CreateEmployee.this.txtBankBranch.setText("");
                                } else {
                                    JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Error while adding employee", "Something went wrong", 0);
                                }
                            } catch (Exception var15) {
                                var15.printStackTrace();
                                JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), var15, "Something went wrong", 0);
                            }
                        }
                    } else {
                        JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Valid Bank Account Number", "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(CreateEmployee.this.getContentPane(), "Please enter Employee's valid Mobile number", "Error", 0);
                }

            }
        });
        this.add(this.$$$getRootComponent$$$());
        this.txtName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtAddress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtMobile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtAllocatedSalary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.toDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtDesignation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtDesignationDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtBankAccountNumber.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtBankName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtBankIfsc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtBankBranch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.manager.focusNextComponent();
            }
        });
        this.btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateEmployee.this.txtName.setText("");
                CreateEmployee.this.txtAddress.setText("");
                CreateEmployee.this.txtMobile.setText("");
                CreateEmployee.this.txtAllocatedSalary.setText("");
                CreateEmployee.this.toDate.getJFormattedTextField().setValue((Object)null);
                CreateEmployee.this.toDate.getModel().setSelected(false);
                CreateEmployee.this.txtDesignation.setSelectedIndex(0);
                CreateEmployee.this.txtDesignationDetails.setText("");
                CreateEmployee.this.txtBankAccountNumber.setText("");
                CreateEmployee.this.txtBankName.setText("");
                CreateEmployee.this.txtBankIfsc.setText("");
                CreateEmployee.this.txtBankBranch.setText("");
            }
        });
    }

    private void createUIComponents() {
        this.fromDatePanel = new JDatePanelImpl(this.model2, this.p);
        this.toDate = new JDatePickerImpl(this.fromDatePanel, new DateLabelFormatter());
    }

    public boolean isNumber(String number) {
        boolean flag = false;

        try {
            Long n = Long.parseLong(number);
            flag = true;
        } catch (NumberFormatException var4) {
            flag = false;
        }

        return flag;
    }

    private void $$$setupUI$$$() {
        this.createUIComponents();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 50, 0), -1, -1));
        this.leftPanel = new JPanel();
        this.leftPanel.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.leftPanel, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, true));
        this.leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Personal Information", 4, 0));
        this.lblName = new JLabel();
        Font lblNameFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblName.getFont());
        if (lblNameFont != null) {
            this.lblName.setFont(lblNameFont);
        }

        this.lblName.setText("Name");
        this.leftPanel.add(this.lblName, new GridConstraints(0, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtName = new JTextField();
        this.txtName.setText("");
        this.leftPanel.add(this.txtName, new GridConstraints(0, 1, 1, 1, 8, 0, 5, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.lblAddress = new JLabel();
        Font lblAddressFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAddress.getFont());
        if (lblAddressFont != null) {
            this.lblAddress.setFont(lblAddressFont);
        }

        this.lblAddress.setText("Address");
        this.leftPanel.add(this.lblAddress, new GridConstraints(1, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblMobile = new JLabel();
        Font lblMobileFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblMobile.getFont());
        if (lblMobileFont != null) {
            this.lblMobile.setFont(lblMobileFont);
        }

        this.lblMobile.setText("Mobile");
        this.leftPanel.add(this.lblMobile, new GridConstraints(2, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblAllocatedSalary = new JLabel();
        Font lblAllocatedSalaryFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAllocatedSalary.getFont());
        if (lblAllocatedSalaryFont != null) {
            this.lblAllocatedSalary.setFont(lblAllocatedSalaryFont);
        }

        this.lblAllocatedSalary.setText("Allocated Salary");
        this.leftPanel.add(this.lblAllocatedSalary, new GridConstraints(3, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblSalaryStartDate = new JLabel();
        Font lblSalaryStartDateFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblSalaryStartDate.getFont());
        if (lblSalaryStartDateFont != null) {
            this.lblSalaryStartDate.setFont(lblSalaryStartDateFont);
        }

        this.lblSalaryStartDate.setText("Salary Start Date");
        this.leftPanel.add(this.lblSalaryStartDate, new GridConstraints(4, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblDesignation = new JLabel();
        Font lblDesignationFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblDesignation.getFont());
        if (lblDesignationFont != null) {
            this.lblDesignation.setFont(lblDesignationFont);
        }

        this.lblDesignation.setText("Designation");
        this.lblDesignation.setToolTipText("What work assigned to particular employee");
        this.leftPanel.add(this.lblDesignation, new GridConstraints(5, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblDesignationDetails = new JLabel();
        Font lblDesignationDetailsFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblDesignationDetails.getFont());
        if (lblDesignationDetailsFont != null) {
            this.lblDesignationDetails.setFont(lblDesignationDetailsFont);
        }

        this.lblDesignationDetails.setText("Designation Details");
        this.leftPanel.add(this.lblDesignationDetails, new GridConstraints(6, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtAddress = new JTextField();
        this.txtAddress.setText("");
        this.leftPanel.add(this.txtAddress, new GridConstraints(1, 1, 1, 1, 8, 0, 5, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.txtMobile = new JTextField();
        this.txtMobile.setText("");
        this.leftPanel.add(this.txtMobile, new GridConstraints(2, 1, 1, 1, 8, 0, 5, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.txtAllocatedSalary = new JTextField();
        this.leftPanel.add(this.txtAllocatedSalary, new GridConstraints(3, 1, 1, 1, 8, 0, 5, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.txtDesignationDetails = new JTextField();
        this.leftPanel.add(this.txtDesignationDetails, new GridConstraints(6, 1, 1, 1, 8, 0, 5, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.leftPanel.add(this.toDate, new GridConstraints(4, 1, 1, 1, 8, 0, 5, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.txtDesignation = new JComboBox();
        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Driver");
        defaultComboBoxModel1.addElement("Labour");
        defaultComboBoxModel1.addElement("Helper");
        defaultComboBoxModel1.addElement("Other");
        this.txtDesignation.setModel(defaultComboBoxModel1);
        this.leftPanel.add(this.txtDesignation, new GridConstraints(5, 1, 1, 1, 8, 0, 5, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.rightPanel = new JPanel();
        this.rightPanel.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.rightPanel, new GridConstraints(0, 1, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, true));
        this.rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Bank Details", 4, 0));
        this.lblBankAccountNumber = new JLabel();
        Font lblBankAccountNumberFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBankAccountNumber.getFont());
        if (lblBankAccountNumberFont != null) {
            this.lblBankAccountNumber.setFont(lblBankAccountNumberFont);
        }

        this.lblBankAccountNumber.setText("Account Number");
        this.rightPanel.add(this.lblBankAccountNumber, new GridConstraints(0, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtBankAccountNumber = new JTextField();
        this.txtBankAccountNumber.setText("");
        this.rightPanel.add(this.txtBankAccountNumber, new GridConstraints(0, 1, 1, 1, 8, 0, 5, 5, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.lblBankName = new JLabel();
        Font lblBankNameFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBankName.getFont());
        if (lblBankNameFont != null) {
            this.lblBankName.setFont(lblBankNameFont);
        }

        this.lblBankName.setText("Bank Name");
        this.rightPanel.add(this.lblBankName, new GridConstraints(1, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblBankIfsc = new JLabel();
        Font lblBankIfscFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBankIfsc.getFont());
        if (lblBankIfscFont != null) {
            this.lblBankIfsc.setFont(lblBankIfscFont);
        }

        this.lblBankIfsc.setText("Bank IFSC");
        this.rightPanel.add(this.lblBankIfsc, new GridConstraints(2, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtBankName = new JTextField();
        this.txtBankName.setText("");
        this.rightPanel.add(this.txtBankName, new GridConstraints(1, 1, 1, 1, 8, 0, 5, 5, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.txtBankIfsc = new JTextField();
        this.txtBankIfsc.setText("");
        this.rightPanel.add(this.txtBankIfsc, new GridConstraints(2, 1, 1, 1, 8, 0, 5, 5, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.txtBankBranch = new JTextField();
        this.txtBankBranch.setText("");
        this.rightPanel.add(this.txtBankBranch, new GridConstraints(3, 1, 1, 1, 8, 0, 5, 5, (Dimension)null, new Dimension(250, 40), (Dimension)null, 5, false));
        this.lblBankBranch = new JLabel();
        Font lblBankBranchFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBankBranch.getFont());
        if (lblBankBranchFont != null) {
            this.lblBankBranch.setFont(lblBankBranchFont);
        }

        this.lblBankBranch.setText("Bank Branch");
        this.rightPanel.add(this.lblBankBranch, new GridConstraints(3, 0, 2, 1, 8, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelBottom = new JPanel();
        this.panelBottom.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.panelBottom, new GridConstraints(1, 0, 1, 2, 0, 3, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelBottom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Actions"));
        this.btnClear = new JButton();
        Font btnClearFont = this.$$$getFont$$$("Monospaced", 1, 16, this.btnClear.getFont());
        if (btnClearFont != null) {
            this.btnClear.setFont(btnClearFont);
        }

        this.btnClear.setIcon(new ImageIcon(this.getClass().getResource("/images/clear.png")));
        this.btnClear.setIconTextGap(10);
        this.btnClear.setText("Clear");
        this.btnClear.setToolTipText("Add Employee");
        this.panelBottom.add(this.btnClear, new GridConstraints(0, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 0, false));
        this.addEmployeeButton = new JButton();
        Font addEmployeeButtonFont = this.$$$getFont$$$("Monospaced", 1, 16, this.addEmployeeButton.getFont());
        if (addEmployeeButtonFont != null) {
            this.addEmployeeButton.setFont(addEmployeeButtonFont);
        }

        this.addEmployeeButton.setIcon(new ImageIcon(this.getClass().getResource("/images/user.png")));
        this.addEmployeeButton.setIconTextGap(10);
        this.addEmployeeButton.setText("Add Employee");
        this.addEmployeeButton.setToolTipText("Add Employee");
        this.panelBottom.add(this.addEmployeeButton, new GridConstraints(0, 1, 1, 1, 4, 0, 3, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.panelBottom.add(spacer1, new GridConstraints(0, 3, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.panelBottom.add(spacer2, new GridConstraints(0, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
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
