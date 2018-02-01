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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShowEmployee extends JInternalFrame {
    JComboBox comboBox;
    DatabaseConnection dbConnection = new DatabaseConnection();
    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton searchButton;
    private JPanel centerPanel;
    private JLabel lblComboName;
    private JLabel lblName;
    private JLabel lblAddress;
    private JLabel lblMobile;
    private JLabel lblAllocatedSalary;
    private JLabel lblStartDate;
    private JLabel lblTotalSalary;
    private JLabel lblDesignation;
    private JLabel lblDesignationDetails;
    private JLabel lblBankAccNumber;
    private JLabel lblBankName;
    private JLabel lblIfsc;
    private JLabel lblBranch;
    private JLabel lblNameStr;
    private JLabel lblAddStr;
    private JLabel lblMobStr;
    private JLabel lblAllSalStr;
    private JLabel lblStartStr;
    private JLabel lblCurrentStr;
    private JLabel lblTotalStr;
    private JLabel lblDesiStr;
    private JLabel lblDesiDetStr;
    private JLabel lblAccNumStr;
    private JLabel lblBankNameStr;
    private JLabel lblIfscStr;
    private JLabel lblBranchStr;
    private JButton viewSalaryDetailsButton;
    private JButton paySalaryButton;
    private JLabel lblCurrentSalary;
    private JPanel panelBottom;
    private static int searchedEmployeeId = 0;

    public ShowEmployee() {
        super("Show Employee", true, true, false, false);
        this.$$$setupUI$$$();
        final JTextField textfield = (JTextField)this.comboBox.getEditor().getEditorComponent();
        textfield.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ShowEmployee.this.comboFilter(textfield.getText(), ShowEmployee.this.comboBox);
                    }
                });
            }
        });
        this.add(this.mainPanel);
        this.searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet resultSet = ShowEmployee.this.dbConnection.getEmployeeDetailsByName(textfield.getText());
                    if (resultSet.next()) {
                        ShowEmployee.searchedEmployeeId = resultSet.getInt("ID");
                        ShowEmployee.this.lblNameStr.setText(resultSet.getString("name"));
                        ShowEmployee.this.lblAddStr.setText(resultSet.getString("address"));
                        ShowEmployee.this.lblMobStr.setText(resultSet.getString("mobile"));
                        ShowEmployee.this.lblAllSalStr.setText(resultSet.getString("allotted_salary"));
                        ShowEmployee.this.lblStartStr.setText(resultSet.getString("salary_start_date"));
                        ShowEmployee.this.lblCurrentStr.setText(resultSet.getString("current_month_salary"));
                        ShowEmployee.this.lblTotalStr.setText(resultSet.getString("total_salary"));
                        ShowEmployee.this.lblDesiStr.setText(resultSet.getString("designation"));
                        ShowEmployee.this.lblDesiDetStr.setText(resultSet.getString("designation_details"));
                        ShowEmployee.this.lblAccNumStr.setText(resultSet.getString("bank_account_number"));
                        ShowEmployee.this.lblBankNameStr.setText(resultSet.getString("bank_name"));
                        ShowEmployee.this.lblIfscStr.setText(resultSet.getString("bank_ifsc"));
                        ShowEmployee.this.lblBranchStr.setText(resultSet.getString("bank_branch"));
                    } else {
                        JOptionPane.showInternalMessageDialog(ShowEmployee.this.getContentPane(), "Employee not found", "Error", 0);
                    }
                } catch (Exception var3) {
                    var3.printStackTrace();
                }

            }
        });
        this.paySalaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ShowEmployee.searchedEmployeeId != 0) {
                    JDialog paySalaryDialog = new PayEmployeeSalary(ShowEmployee.this.lblNameStr.getText(), ShowEmployee.searchedEmployeeId);
                    Dimension mainPanelSize = ShowEmployee.this.mainPanel.getSize();
                    paySalaryDialog.setSize((int)mainPanelSize.getWidth() / 2, (int)mainPanelSize.getHeight());
                    Dimension dialogSize = paySalaryDialog.getSize();
                    paySalaryDialog.setLocation((mainPanelSize.width - dialogSize.width) / 2, (mainPanelSize.height - dialogSize.height) / 2);
                    paySalaryDialog.setVisible(true);
                } else {
                    JOptionPane.showInternalMessageDialog(ShowEmployee.this.getContentPane(), "Please Search for employee first by their name.", "Error", 0);
                }

            }
        });
        this.viewSalaryDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ShowEmployee.searchedEmployeeId != 0) {
                    JDialog ViewSalaryDialog = new ViewSalaryDetails(ShowEmployee.this.lblNameStr.getText(), ShowEmployee.searchedEmployeeId);
                    Dimension mainPanelSize = ShowEmployee.this.mainPanel.getSize();
                    ViewSalaryDialog.setSize((int)(mainPanelSize.getWidth() - mainPanelSize.getWidth() / 6.0D), (int)(mainPanelSize.getHeight() - mainPanelSize.getHeight() / 6.0D));
                    Dimension dialogSize = ViewSalaryDialog.getSize();
                    ViewSalaryDialog.setLocation((mainPanelSize.width - dialogSize.width) / 2, (mainPanelSize.height - dialogSize.height) / 2);
                    ViewSalaryDialog.setVisible(true);
                } else {
                    JOptionPane.showInternalMessageDialog(ShowEmployee.this.getContentPane(), "Please Search for employee first by their name.", "Error", 0);
                }

            }
        });
    }

    public void comboFilter(String enteredText, JComboBox jComboBox) {
        List<String> filterArray = new ArrayList();
        String str1 = "";
        if (!enteredText.equals("")) {
            try {
                ResultSet rs = this.dbConnection.getEmployeeName(enteredText);

                while(rs.next()) {
                    str1 = rs.getString("name");
                    filterArray.add(str1);
                }
            } catch (Exception var6) {
                System.out.println("error" + var6);
            }
        } else {
            filterArray.clear();
        }

        if (filterArray.size() > 0) {
            jComboBox.setModel(new DefaultComboBoxModel(filterArray.toArray()));
            jComboBox.setSelectedItem(enteredText);
            jComboBox.showPopup();
        } else {
            jComboBox.setModel(new DefaultComboBoxModel(filterArray.toArray()));
            jComboBox.hidePopup();
        }

    }

    private void $$$setupUI$$$() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout(0, 0));
        this.topPanel = new JPanel();
        this.topPanel.setLayout(new FlowLayout(1, 5, 5));
        this.mainPanel.add(this.topPanel, "North");
        this.topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search Employee"));
        this.lblComboName = new JLabel();
        Font lblComboNameFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblComboName.getFont());
        if (lblComboNameFont != null) {
            this.lblComboName.setFont(lblComboNameFont);
        }

        this.lblComboName.setText("Employee Name :");
        this.topPanel.add(this.lblComboName);
        this.comboBox = new JComboBox();
        this.comboBox.setEditable(true);
        Font comboBoxFont = this.$$$getFont$$$((String)null, -1, -1, this.comboBox.getFont());
        if (comboBoxFont != null) {
            this.comboBox.setFont(comboBoxFont);
        }

        this.comboBox.setPreferredSize(new Dimension(150, 30));
        this.topPanel.add(this.comboBox);
        this.searchButton = new JButton();
        Font searchButtonFont = this.$$$getFont$$$("Courier 10 Pitch", 1, 16, this.searchButton.getFont());
        if (searchButtonFont != null) {
            this.searchButton.setFont(searchButtonFont);
        }

        this.searchButton.setHorizontalAlignment(2);
        this.searchButton.setHorizontalTextPosition(2);
        this.searchButton.setIcon(new ImageIcon(this.getClass().getResource("/images/view.png")));
        this.searchButton.setText("Search");
        this.topPanel.add(this.searchButton);
        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new GridLayoutManager(7, 5, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.centerPanel, "Center");
        this.centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Employee Details"));
        this.lblName = new JLabel();
        Font lblNameFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblName.getFont());
        if (lblNameFont != null) {
            this.lblName.setFont(lblNameFont);
        }

        this.lblName.setText("Name: ");
        this.centerPanel.add(this.lblName, new GridConstraints(0, 0, 1, 2, 0, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblAddress = new JLabel();
        Font lblAddressFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAddress.getFont());
        if (lblAddressFont != null) {
            this.lblAddress.setFont(lblAddressFont);
        }

        this.lblAddress.setText("Address: ");
        this.centerPanel.add(this.lblAddress, new GridConstraints(1, 1, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblMobile = new JLabel();
        Font lblMobileFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblMobile.getFont());
        if (lblMobileFont != null) {
            this.lblMobile.setFont(lblMobileFont);
        }

        this.lblMobile.setText("Mobile: ");
        this.centerPanel.add(this.lblMobile, new GridConstraints(2, 1, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblAllocatedSalary = new JLabel();
        Font lblAllocatedSalaryFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAllocatedSalary.getFont());
        if (lblAllocatedSalaryFont != null) {
            this.lblAllocatedSalary.setFont(lblAllocatedSalaryFont);
        }

        this.lblAllocatedSalary.setText("Allocated Salary: ");
        this.centerPanel.add(this.lblAllocatedSalary, new GridConstraints(3, 1, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblStartDate = new JLabel();
        Font lblStartDateFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblStartDate.getFont());
        if (lblStartDateFont != null) {
            this.lblStartDate.setFont(lblStartDateFont);
        }

        this.lblStartDate.setText("Start Date: ");
        this.centerPanel.add(this.lblStartDate, new GridConstraints(4, 1, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblDesignation = new JLabel();
        Font lblDesignationFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblDesignation.getFont());
        if (lblDesignationFont != null) {
            this.lblDesignation.setFont(lblDesignationFont);
        }

        this.lblDesignation.setText("Designation: ");
        this.centerPanel.add(this.lblDesignation, new GridConstraints(0, 3, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblDesignationDetails = new JLabel();
        Font lblDesignationDetailsFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblDesignationDetails.getFont());
        if (lblDesignationDetailsFont != null) {
            this.lblDesignationDetails.setFont(lblDesignationDetailsFont);
        }

        this.lblDesignationDetails.setText("Designation Details: ");
        this.centerPanel.add(this.lblDesignationDetails, new GridConstraints(1, 3, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblBankAccNumber = new JLabel();
        Font lblBankAccNumberFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBankAccNumber.getFont());
        if (lblBankAccNumberFont != null) {
            this.lblBankAccNumber.setFont(lblBankAccNumberFont);
        }

        this.lblBankAccNumber.setText("Acc. Number: ");
        this.centerPanel.add(this.lblBankAccNumber, new GridConstraints(2, 3, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblBankName = new JLabel();
        Font lblBankNameFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBankName.getFont());
        if (lblBankNameFont != null) {
            this.lblBankName.setFont(lblBankNameFont);
        }

        this.lblBankName.setText("Bank Name: ");
        this.centerPanel.add(this.lblBankName, new GridConstraints(3, 3, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblIfsc = new JLabel();
        Font lblIfscFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblIfsc.getFont());
        if (lblIfscFont != null) {
            this.lblIfsc.setFont(lblIfscFont);
        }

        this.lblIfsc.setText("IFSC: ");
        this.centerPanel.add(this.lblIfsc, new GridConstraints(4, 3, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblBranch = new JLabel();
        Font lblBranchFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBranch.getFont());
        if (lblBranchFont != null) {
            this.lblBranch.setFont(lblBranchFont);
        }

        this.lblBranch.setText("Bank Branch: ");
        this.centerPanel.add(this.lblBranch, new GridConstraints(5, 3, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblNameStr = new JLabel();
        Font lblNameStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblNameStr.getFont());
        if (lblNameStrFont != null) {
            this.lblNameStr.setFont(lblNameStrFont);
        }

        this.lblNameStr.setForeground(new Color(-7270382));
        this.lblNameStr.setText("");
        this.centerPanel.add(this.lblNameStr, new GridConstraints(0, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblAddStr = new JLabel();
        Font lblAddStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblAddStr.getFont());
        if (lblAddStrFont != null) {
            this.lblAddStr.setFont(lblAddStrFont);
        }

        this.lblAddStr.setForeground(new Color(-7270382));
        this.lblAddStr.setText("");
        this.centerPanel.add(this.lblAddStr, new GridConstraints(1, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblMobStr = new JLabel();
        Font lblMobStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblMobStr.getFont());
        if (lblMobStrFont != null) {
            this.lblMobStr.setFont(lblMobStrFont);
        }

        this.lblMobStr.setForeground(new Color(-7270382));
        this.lblMobStr.setText("");
        this.centerPanel.add(this.lblMobStr, new GridConstraints(2, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblAllSalStr = new JLabel();
        Font lblAllSalStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblAllSalStr.getFont());
        if (lblAllSalStrFont != null) {
            this.lblAllSalStr.setFont(lblAllSalStrFont);
        }

        this.lblAllSalStr.setForeground(new Color(-7270382));
        this.lblAllSalStr.setText("");
        this.centerPanel.add(this.lblAllSalStr, new GridConstraints(3, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblStartStr = new JLabel();
        Font lblStartStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblStartStr.getFont());
        if (lblStartStrFont != null) {
            this.lblStartStr.setFont(lblStartStrFont);
        }

        this.lblStartStr.setForeground(new Color(-7270382));
        this.lblStartStr.setText("");
        this.centerPanel.add(this.lblStartStr, new GridConstraints(4, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblDesiStr = new JLabel();
        Font lblDesiStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblDesiStr.getFont());
        if (lblDesiStrFont != null) {
            this.lblDesiStr.setFont(lblDesiStrFont);
        }

        this.lblDesiStr.setForeground(new Color(-7270382));
        this.lblDesiStr.setText("");
        this.centerPanel.add(this.lblDesiStr, new GridConstraints(0, 4, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblDesiDetStr = new JLabel();
        Font lblDesiDetStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblDesiDetStr.getFont());
        if (lblDesiDetStrFont != null) {
            this.lblDesiDetStr.setFont(lblDesiDetStrFont);
        }

        this.lblDesiDetStr.setForeground(new Color(-7270382));
        this.lblDesiDetStr.setText("");
        this.centerPanel.add(this.lblDesiDetStr, new GridConstraints(1, 4, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblAccNumStr = new JLabel();
        Font lblAccNumStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblAccNumStr.getFont());
        if (lblAccNumStrFont != null) {
            this.lblAccNumStr.setFont(lblAccNumStrFont);
        }

        this.lblAccNumStr.setForeground(new Color(-7270382));
        this.lblAccNumStr.setText("");
        this.centerPanel.add(this.lblAccNumStr, new GridConstraints(2, 4, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblBankNameStr = new JLabel();
        Font lblBankNameStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblBankNameStr.getFont());
        if (lblBankNameStrFont != null) {
            this.lblBankNameStr.setFont(lblBankNameStrFont);
        }

        this.lblBankNameStr.setForeground(new Color(-7270382));
        this.lblBankNameStr.setText("");
        this.centerPanel.add(this.lblBankNameStr, new GridConstraints(3, 4, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblIfscStr = new JLabel();
        Font lblIfscStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblIfscStr.getFont());
        if (lblIfscStrFont != null) {
            this.lblIfscStr.setFont(lblIfscStrFont);
        }

        this.lblIfscStr.setForeground(new Color(-7270382));
        this.lblIfscStr.setText("");
        this.centerPanel.add(this.lblIfscStr, new GridConstraints(4, 4, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblBranchStr = new JLabel();
        Font lblBranchStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblBranchStr.getFont());
        if (lblBranchStrFont != null) {
            this.lblBranchStr.setFont(lblBranchStrFont);
        }

        this.lblBranchStr.setForeground(new Color(-7270382));
        this.lblBranchStr.setText("");
        this.centerPanel.add(this.lblBranchStr, new GridConstraints(5, 4, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblTotalSalary = new JLabel();
        Font lblTotalSalaryFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblTotalSalary.getFont());
        if (lblTotalSalaryFont != null) {
            this.lblTotalSalary.setFont(lblTotalSalaryFont);
        }

        this.lblTotalSalary.setText("Total Paid: ");
        this.centerPanel.add(this.lblTotalSalary, new GridConstraints(5, 1, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblTotalStr = new JLabel();
        Font lblTotalStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblTotalStr.getFont());
        if (lblTotalStrFont != null) {
            this.lblTotalStr.setFont(lblTotalStrFont);
        }

        this.lblTotalStr.setForeground(new Color(-7270382));
        this.lblTotalStr.setText("");
        this.centerPanel.add(this.lblTotalStr, new GridConstraints(5, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblCurrentSalary = new JLabel();
        Font lblCurrentSalaryFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblCurrentSalary.getFont());
        if (lblCurrentSalaryFont != null) {
            this.lblCurrentSalary.setFont(lblCurrentSalaryFont);
        }

        this.lblCurrentSalary.setText("Outstandings: ");
        this.centerPanel.add(this.lblCurrentSalary, new GridConstraints(6, 1, 1, 1, 0, 1, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblCurrentStr = new JLabel();
        Font lblCurrentStrFont = this.$$$getFont$$$("Monospaced", 3, 16, this.lblCurrentStr.getFont());
        if (lblCurrentStrFont != null) {
            this.lblCurrentStr.setFont(lblCurrentStrFont);
        }

        this.lblCurrentStr.setForeground(new Color(-7270382));
        this.lblCurrentStr.setText("");
        this.centerPanel.add(this.lblCurrentStr, new GridConstraints(6, 2, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.panelBottom = new JPanel();
        this.panelBottom.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        this.mainPanel.add(this.panelBottom, "South");
        this.panelBottom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Actions"));
        this.paySalaryButton = new JButton();
        Font paySalaryButtonFont = this.$$$getFont$$$("Monospaced", 1, 14, this.paySalaryButton.getFont());
        if (paySalaryButtonFont != null) {
            this.paySalaryButton.setFont(paySalaryButtonFont);
        }

        this.paySalaryButton.setIcon(new ImageIcon(this.getClass().getResource("/images/editmark.png")));
        this.paySalaryButton.setText("Pay Salary");
        this.panelBottom.add(this.paySalaryButton, new GridConstraints(0, 3, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.viewSalaryDetailsButton = new JButton();
        Font viewSalaryDetailsButtonFont = this.$$$getFont$$$("Monospaced", 1, 14, this.viewSalaryDetailsButton.getFont());
        if (viewSalaryDetailsButtonFont != null) {
            this.viewSalaryDetailsButton.setFont(viewSalaryDetailsButtonFont);
        }

        this.viewSalaryDetailsButton.setIcon(new ImageIcon(this.getClass().getResource("/images/notepad.png")));
        this.viewSalaryDetailsButton.setText("Salary Details");
        this.panelBottom.add(this.viewSalaryDetailsButton, new GridConstraints(0, 2, 1, 1, 4, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 1, false));
        Spacer spacer1 = new Spacer();
        this.panelBottom.add(spacer1, new GridConstraints(0, 1, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.panelBottom.add(spacer2, new GridConstraints(1, 2, 1, 1, 0, 2, 1, 4, (Dimension)null, new Dimension(-1, 50), (Dimension)null, 0, false));
        Spacer spacer3 = new Spacer();
        this.panelBottom.add(spacer3, new GridConstraints(0, 4, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
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
