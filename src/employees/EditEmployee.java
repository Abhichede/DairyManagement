//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class EditEmployee extends JInternalFrame {
    private JPanel panelMain;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JLabel lblName;
    private JPanel panelBottom;
    private JButton saveButton;
    private JButton clearButton;
    private JLabel lblAddress;
    private JLabel lblMobile;
    private JLabel lblAllocatedSalary;
    private JLabel lblSalaryStartDate;
    private JLabel lblDesignation;
    private JLabel lblDesiDesc;
    private JLabel lblAccNumber;
    private JLabel lblBankName;
    private JLabel lblBankIfsc;
    private JLabel lblBankBranch;
    private JTextField txtName;
    private JDatePickerImpl txtSalSatrtDate;
    private JTextField txtAddress;
    private JTextField txtMobile;
    private JTextField txtAllocatedSalary;
    private JComboBox txtDesi;
    private JTextField txtDesiDesc;
    private JTextField txtAccNumber;
    private JTextField txtBankName;
    private JTextField txtBankIfsc;
    private JTextField txtBankBranch;
    private JPanel panelTop;
    private JLabel lblSearchName;
    private JComboBox comboBoxSearchName;
    private JButton searchButton;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    private static int searchedID;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    private UtilDateModel model2 = new UtilDateModel();
    Properties p = new Properties();
    private JDatePanelImpl fromDatePanel;

    public EditEmployee() {
        super("Edit Employee", true, true, true, true);
        this.$$$setupUI$$$();
        this.p.put("text.today", "Today");
        this.p.put("text.month", "Month");
        this.p.put("text.year", "Year");
        final JTextField txtSearchName = (JTextField)this.comboBoxSearchName.getEditor().getEditorComponent();
        txtSearchName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        (new ShowEmployee()).comboFilter(txtSearchName.getText(), EditEmployee.this.comboBoxSearchName);
                    }
                });
            }
        });
        this.add(this.panelMain);
        this.searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtSearchName.getText().equals("")) {
                        ResultSet resultSet = EditEmployee.this.databaseConnection.getEmployeeDetailsByName(txtSearchName.getText());
                        if (resultSet.next()) {
                            EditEmployee.searchedID = resultSet.getInt("ID");
                            EditEmployee.this.txtName.setText(resultSet.getString("name"));
                            EditEmployee.this.txtAddress.setText(resultSet.getString("address"));
                            EditEmployee.this.txtMobile.setText(resultSet.getString("mobile"));
                            EditEmployee.this.txtAllocatedSalary.setText(resultSet.getString("allotted_salary"));
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date startDate = dateFormat.parse(resultSet.getString("salary_start_date"));
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(startDate);
                            EditEmployee.this.txtSalSatrtDate.getModel().setDate(calendar.get(1), calendar.get(2), calendar.get(5));
                            EditEmployee.this.txtSalSatrtDate.getModel().setSelected(true);
                            EditEmployee.this.txtSalSatrtDate.getJFormattedTextField().setValue(calendar);
                            EditEmployee.this.txtDesi.setSelectedItem(resultSet.getString("designation"));
                            EditEmployee.this.txtDesiDesc.setText(resultSet.getString("designation_details"));
                            EditEmployee.this.txtAccNumber.setText(resultSet.getString("bank_account_number"));
                            EditEmployee.this.txtBankName.setText(resultSet.getString("bank_name"));
                            EditEmployee.this.txtBankIfsc.setText(resultSet.getString("bank_ifsc"));
                            EditEmployee.this.txtBankBranch.setText(resultSet.getString("bank_branch"));
                        } else {
                            JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Employee not found", "Error", 0);
                        }
                    } else {
                        JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please select or type in name of employee", "Error", 0);
                    }
                } catch (Exception var6) {
                    var6.printStackTrace();
                }

            }
        });
        this.saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strName = EditEmployee.this.txtName.getText();
                String strAddress = EditEmployee.this.txtAddress.getText();
                String strMobile = EditEmployee.this.txtMobile.getText();
                String strAllocatedSalary = EditEmployee.this.txtAllocatedSalary.getText();
                String strStartDate = EditEmployee.this.txtSalSatrtDate.getJFormattedTextField().getText();
                String strDesignation = EditEmployee.this.txtDesi.getItemAt(EditEmployee.this.txtDesi.getSelectedIndex()).toString();
                String strDesignationDetails = EditEmployee.this.txtDesiDesc.getText();
                String strBankAccNumber = EditEmployee.this.txtAccNumber.getText();
                String strBankName = EditEmployee.this.txtBankName.getText();
                String strBankIfsc = EditEmployee.this.txtBankIfsc.getText();
                String strBankBranch = EditEmployee.this.txtBankBranch.getText();
                if (strName.equals("")) {
                    JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Employee's Name", "Error", 0);
                } else if (strAddress.equals("")) {
                    JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Employee's Address", "Error", 0);
                } else if (!strMobile.equals("") && strMobile.length() == 10 && (new CreateEmployee()).isNumber(strMobile)) {
                    if (strAllocatedSalary.equals("")) {
                        JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Employee's Salary", "Error", 0);
                    } else if (strStartDate.equals("")) {
                        JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Employee's Salary Start Sate", "Error", 0);
                    } else if (strDesignation.equals("")) {
                        JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Employee's Designation(What work assigned to employee)", "Error", 0);
                    } else if (strDesignationDetails.equals("")) {
                        JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please Write Something about Designation(Ex. Vehicle Number etc.)", "Error", 0);
                    } else if (!strBankAccNumber.equals("") && (new CreateEmployee()).isNumber(strBankAccNumber)) {
                        if (strBankName.equals("")) {
                            JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Employee's Bank Name", "Error", 0);
                        } else if (strBankIfsc.equals("")) {
                            JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Employee's Bank IFSC", "Error", 0);
                        } else if (strBankBranch.equals("")) {
                            JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Employee's Bank Branch", "Error", 0);
                        } else {
                            String[] employeeData = new String[]{strName, strAddress, strMobile, strAllocatedSalary, strStartDate, strDesignation, strDesignationDetails, strBankAccNumber, strBankName, strBankIfsc, strBankBranch};

                            try {
                                int resultSet = EditEmployee.this.databaseConnection.updateEmployee(employeeData, EditEmployee.searchedID);
                                if (resultSet != 0) {
                                    JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Employee Updated Successfully");
                                    EditEmployee.this.txtName.setText("");
                                    EditEmployee.this.txtAddress.setText("");
                                    EditEmployee.this.txtMobile.setText("");
                                    EditEmployee.this.txtAllocatedSalary.setText("");
                                    EditEmployee.this.txtSalSatrtDate.getJFormattedTextField().setValue((Object)null);
                                    EditEmployee.this.txtSalSatrtDate.getModel().setSelected(false);
                                    EditEmployee.this.txtDesi.setSelectedIndex(0);
                                    EditEmployee.this.txtDesiDesc.setText("");
                                    EditEmployee.this.txtAccNumber.setText("");
                                    EditEmployee.this.txtBankName.setText("");
                                    EditEmployee.this.txtBankIfsc.setText("");
                                    EditEmployee.this.txtBankBranch.setText("");
                                } else {
                                    JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Error while updating employee", "Something went wrong", 0);
                                }
                            } catch (Exception var15) {
                                var15.printStackTrace();
                                JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), var15, "Something went wrong", 0);
                            }
                        }
                    } else {
                        JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Valid Bank Account Number", "Error", 0);
                    }
                } else {
                    JOptionPane.showInternalMessageDialog(EditEmployee.this.getContentPane(), "Please enter Employee's valid Mobile number", "Error", 0);
                }

            }
        });
        this.clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.txtName.setText("");
                EditEmployee.this.txtAddress.setText("");
                EditEmployee.this.txtMobile.setText("");
                EditEmployee.this.txtAllocatedSalary.setText("");
                EditEmployee.this.txtSalSatrtDate.getJFormattedTextField().setValue((Object)null);
                EditEmployee.this.txtSalSatrtDate.getModel().setSelected(false);
                EditEmployee.this.txtDesi.setSelectedIndex(0);
                EditEmployee.this.txtDesiDesc.setText("");
                EditEmployee.this.txtAccNumber.setText("");
                EditEmployee.this.txtBankName.setText("");
                EditEmployee.this.txtBankIfsc.setText("");
                EditEmployee.this.txtBankBranch.setText("");
            }
        });
        this.txtName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtAddress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtMobile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtAllocatedSalary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtSalSatrtDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtDesi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtDesiDesc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtAccNumber.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtBankName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtBankIfsc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
        this.txtBankBranch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditEmployee.this.manager.focusNextComponent();
            }
        });
    }

    private void createUIComponents() {
        this.fromDatePanel = new JDatePanelImpl(this.model2, this.p);
        this.txtSalSatrtDate = new JDatePickerImpl(this.fromDatePanel, new DateLabelFormatter());
    }

    private void $$$setupUI$$$() {
        this.createUIComponents();
        this.panelMain = new JPanel();
        this.panelMain.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.panelLeft = new JPanel();
        this.panelLeft.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.panelMain.add(this.panelLeft, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelLeft.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Personal Details"));
        this.lblName = new JLabel();
        Font lblNameFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblName.getFont());
        if (lblNameFont != null) {
            this.lblName.setFont(lblNameFont);
        }

        this.lblName.setText("Name:");
        this.panelLeft.add(this.lblName, new GridConstraints(0, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblAddress = new JLabel();
        Font lblAddressFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAddress.getFont());
        if (lblAddressFont != null) {
            this.lblAddress.setFont(lblAddressFont);
        }

        this.lblAddress.setText("Address:");
        this.panelLeft.add(this.lblAddress, new GridConstraints(1, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblMobile = new JLabel();
        Font lblMobileFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblMobile.getFont());
        if (lblMobileFont != null) {
            this.lblMobile.setFont(lblMobileFont);
        }

        this.lblMobile.setText("Mobile:");
        this.panelLeft.add(this.lblMobile, new GridConstraints(2, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblAllocatedSalary = new JLabel();
        Font lblAllocatedSalaryFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAllocatedSalary.getFont());
        if (lblAllocatedSalaryFont != null) {
            this.lblAllocatedSalary.setFont(lblAllocatedSalaryFont);
        }

        this.lblAllocatedSalary.setText("Allocated Salary:");
        this.panelLeft.add(this.lblAllocatedSalary, new GridConstraints(3, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblSalaryStartDate = new JLabel();
        Font lblSalaryStartDateFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblSalaryStartDate.getFont());
        if (lblSalaryStartDateFont != null) {
            this.lblSalaryStartDate.setFont(lblSalaryStartDateFont);
        }

        this.lblSalaryStartDate.setText("Salary Start Date:");
        this.panelLeft.add(this.lblSalaryStartDate, new GridConstraints(4, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblDesignation = new JLabel();
        Font lblDesignationFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblDesignation.getFont());
        if (lblDesignationFont != null) {
            this.lblDesignation.setFont(lblDesignationFont);
        }

        this.lblDesignation.setText("Designation:");
        this.panelLeft.add(this.lblDesignation, new GridConstraints(5, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblDesiDesc = new JLabel();
        Font lblDesiDescFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblDesiDesc.getFont());
        if (lblDesiDescFont != null) {
            this.lblDesiDesc.setFont(lblDesiDescFont);
        }

        this.lblDesiDesc.setText("Designation Description:");
        this.panelLeft.add(this.lblDesiDesc, new GridConstraints(6, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.txtName = new JTextField();
        this.txtName.setColumns(20);
        Font txtNameFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtName.getFont());
        if (txtNameFont != null) {
            this.txtName.setFont(txtNameFont);
        }

        this.panelLeft.add(this.txtName, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.txtAddress = new JTextField();
        this.txtAddress.setColumns(20);
        Font txtAddressFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtAddress.getFont());
        if (txtAddressFont != null) {
            this.txtAddress.setFont(txtAddressFont);
        }

        this.panelLeft.add(this.txtAddress, new GridConstraints(1, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.txtMobile = new JTextField();
        this.txtMobile.setColumns(20);
        Font txtMobileFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtMobile.getFont());
        if (txtMobileFont != null) {
            this.txtMobile.setFont(txtMobileFont);
        }

        this.panelLeft.add(this.txtMobile, new GridConstraints(2, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.txtAllocatedSalary = new JTextField();
        this.txtAllocatedSalary.setColumns(20);
        Font txtAllocatedSalaryFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtAllocatedSalary.getFont());
        if (txtAllocatedSalaryFont != null) {
            this.txtAllocatedSalary.setFont(txtAllocatedSalaryFont);
        }

        this.panelLeft.add(this.txtAllocatedSalary, new GridConstraints(3, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.txtDesiDesc = new JTextField();
        this.txtDesiDesc.setColumns(20);
        Font txtDesiDescFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtDesiDesc.getFont());
        if (txtDesiDescFont != null) {
            this.txtDesiDesc.setFont(txtDesiDescFont);
        }

        this.panelLeft.add(this.txtDesiDesc, new GridConstraints(6, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.panelLeft.add(this.txtSalSatrtDate, new GridConstraints(4, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.txtDesi = new JComboBox();
        Font txtDesiFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtDesi.getFont());
        if (txtDesiFont != null) {
            this.txtDesi.setFont(txtDesiFont);
        }

        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Driver");
        defaultComboBoxModel1.addElement("Labour");
        defaultComboBoxModel1.addElement("Helper");
        defaultComboBoxModel1.addElement("Other");
        this.txtDesi.setModel(defaultComboBoxModel1);
        this.panelLeft.add(this.txtDesi, new GridConstraints(5, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 30), (Dimension)null, 0, false));
        this.panelRight = new JPanel();
        this.panelRight.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.panelMain.add(this.panelRight, new GridConstraints(1, 1, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelRight.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Bank Details"));
        this.lblAccNumber = new JLabel();
        Font lblAccNumberFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAccNumber.getFont());
        if (lblAccNumberFont != null) {
            this.lblAccNumber.setFont(lblAccNumberFont);
        }

        this.lblAccNumber.setText("Account Number:");
        this.panelRight.add(this.lblAccNumber, new GridConstraints(0, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblBankName = new JLabel();
        Font lblBankNameFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBankName.getFont());
        if (lblBankNameFont != null) {
            this.lblBankName.setFont(lblBankNameFont);
        }

        this.lblBankName.setText("Bank Name:");
        this.panelRight.add(this.lblBankName, new GridConstraints(1, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblBankIfsc = new JLabel();
        Font lblBankIfscFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBankIfsc.getFont());
        if (lblBankIfscFont != null) {
            this.lblBankIfsc.setFont(lblBankIfscFont);
        }

        this.lblBankIfsc.setText("Bank IFSC:");
        this.panelRight.add(this.lblBankIfsc, new GridConstraints(2, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.lblBankBranch = new JLabel();
        Font lblBankBranchFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblBankBranch.getFont());
        if (lblBankBranchFont != null) {
            this.lblBankBranch.setFont(lblBankBranchFont);
        }

        this.lblBankBranch.setText("Bank Branch:");
        this.panelRight.add(this.lblBankBranch, new GridConstraints(3, 0, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, 40), (Dimension)null, 0, false));
        this.txtAccNumber = new JTextField();
        this.txtAccNumber.setColumns(20);
        Font txtAccNumberFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtAccNumber.getFont());
        if (txtAccNumberFont != null) {
            this.txtAccNumber.setFont(txtAccNumberFont);
        }

        this.panelRight.add(this.txtAccNumber, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.txtBankName = new JTextField();
        this.txtBankName.setColumns(20);
        Font txtBankNameFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtBankName.getFont());
        if (txtBankNameFont != null) {
            this.txtBankName.setFont(txtBankNameFont);
        }

        this.panelRight.add(this.txtBankName, new GridConstraints(1, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.txtBankIfsc = new JTextField();
        this.txtBankIfsc.setColumns(20);
        Font txtBankIfscFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtBankIfsc.getFont());
        if (txtBankIfscFont != null) {
            this.txtBankIfsc.setFont(txtBankIfscFont);
        }

        this.panelRight.add(this.txtBankIfsc, new GridConstraints(2, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.txtBankBranch = new JTextField();
        this.txtBankBranch.setColumns(20);
        Font txtBankBranchFont = this.$$$getFont$$$("Monospaced", 1, 14, this.txtBankBranch.getFont());
        if (txtBankBranchFont != null) {
            this.txtBankBranch.setFont(txtBankBranchFont);
        }

        this.panelRight.add(this.txtBankBranch, new GridConstraints(3, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(200, 40), (Dimension)null, 0, false));
        this.panelBottom = new JPanel();
        this.panelBottom.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        this.panelMain.add(this.panelBottom, new GridConstraints(2, 0, 1, 2, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelBottom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Actions"));
        this.saveButton = new JButton();
        Font saveButtonFont = this.$$$getFont$$$("Monospaced", 1, 16, this.saveButton.getFont());
        if (saveButtonFont != null) {
            this.saveButton.setFont(saveButtonFont);
        }

        this.saveButton.setIcon(new ImageIcon(this.getClass().getResource("/images/save.PNG")));
        this.saveButton.setText("Save");
        this.panelBottom.add(this.saveButton, new GridConstraints(0, 1, 1, 1, 0, 1, 3, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.panelBottom.add(spacer1, new GridConstraints(0, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.clearButton = new JButton();
        Font clearButtonFont = this.$$$getFont$$$("Monospaced", 1, 16, this.clearButton.getFont());
        if (clearButtonFont != null) {
            this.clearButton.setFont(clearButtonFont);
        }

        this.clearButton.setIcon(new ImageIcon(this.getClass().getResource("/images/clear.png")));
        this.clearButton.setText("Clear");
        this.panelBottom.add(this.clearButton, new GridConstraints(0, 2, 1, 1, 0, 1, 3, 0, (Dimension)null, new Dimension(250, 40), (Dimension)null, 0, false));
        Spacer spacer2 = new Spacer();
        this.panelBottom.add(spacer2, new GridConstraints(0, 3, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelTop = new JPanel();
        this.panelTop.setLayout(new FlowLayout(1, 10, 5));
        this.panelMain.add(this.panelTop, new GridConstraints(0, 0, 1, 2, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelTop.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search Employee"));
        this.lblSearchName = new JLabel();
        Font lblSearchNameFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblSearchName.getFont());
        if (lblSearchNameFont != null) {
            this.lblSearchName.setFont(lblSearchNameFont);
        }

        this.lblSearchName.setText("Enter Name:");
        this.panelTop.add(this.lblSearchName);
        this.comboBoxSearchName = new JComboBox();
        this.comboBoxSearchName.setEditable(true);
        this.comboBoxSearchName.setPreferredSize(new Dimension(150, 30));
        this.panelTop.add(this.comboBoxSearchName);
        this.searchButton = new JButton();
        Font searchButtonFont = this.$$$getFont$$$("Monospaced", 1, 14, this.searchButton.getFont());
        if (searchButtonFont != null) {
            this.searchButton.setFont(searchButtonFont);
        }

        this.searchButton.setIcon(new ImageIcon(this.getClass().getResource("/images/view.png")));
        this.searchButton.setText("Search");
        this.panelTop.add(this.searchButton);
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
        return this.panelMain;
    }
}
