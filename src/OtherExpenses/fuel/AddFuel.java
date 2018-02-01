//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package OtherExpenses.fuel;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import main.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddFuel extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel panelBottom;
    private JPanel panelActions;
    private JPanel panelTop;
    private JLabel lblDesc;
    private JTextField txtDesc;
    private JLabel lblLitres;
    private JLabel lblAmount;
    private JTextField txtLitres;
    private JTextField txtAmount;
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public AddFuel() {
        this.$$$setupUI$$$();
        this.setContentPane(this.contentPane);
        this.setModal(true);
        this.getRootPane().setDefaultButton(this.buttonOK);
        this.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddFuel.this.onOK();
            }
        });
        this.buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddFuel.this.onCancel();
            }
        });
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                AddFuel.this.onCancel();
            }
        });
        this.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddFuel.this.onCancel();
            }
        }, KeyStroke.getKeyStroke(27, 0), 1);
    }

    private void onOK() {
        String strDesc = this.txtDesc.getText();
        String strLitres = this.txtLitres.getText();
        String strAmount = this.txtAmount.getText();
        if (strDesc.equals("")) {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid Description", "Error", 0);
        } else if (!strLitres.equals("") && this.isNumber(strLitres)) {
            if (!strAmount.equals("") && this.isNumber(strAmount)) {
                String[] values = new String[]{strDesc, strLitres, strAmount};
                int result = this.databaseConnection.insertFuel(values);
                if (result != 0) {
                    JOptionPane.showInternalMessageDialog(this.getContentPane(), "Fuel added successfully");
                    this.dispose();
                } else {
                    JOptionPane.showInternalMessageDialog(this.getContentPane(), "Something went wrong.", "Error", 0);
                }
            } else {
                JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid Amount", "Error", 0);
            }
        } else {
            JOptionPane.showInternalMessageDialog(this.getContentPane(), "Please enter valid Litres", "Error", 0);
        }

    }

    private void onCancel() {
        this.dispose();
    }

    public boolean isNumber(String num) {
        boolean flag = false;

        try {
            Double number = Double.parseDouble(num);
            flag = true;
        } catch (NumberFormatException var4) {
            var4.printStackTrace();
            flag = false;
        }

        return flag;
    }

    private void $$$setupUI$$$() {
        this.contentPane = new JPanel();
        this.contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        this.panelBottom = new JPanel();
        this.panelBottom.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.contentPane.add(this.panelBottom, new GridConstraints(1, 0, 1, 1, 0, 3, 3, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        Spacer spacer1 = new Spacer();
        this.panelBottom.add(spacer1, new GridConstraints(0, 0, 1, 1, 0, 1, 4, 1, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelActions = new JPanel();
        this.panelActions.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        this.panelBottom.add(this.panelActions, new GridConstraints(0, 1, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.buttonOK = new JButton();
        this.buttonOK.setText("Add");
        this.panelActions.add(this.buttonOK, new GridConstraints(0, 0, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.buttonCancel = new JButton();
        this.buttonCancel.setText("Cancel");
        this.panelActions.add(this.buttonCancel, new GridConstraints(0, 1, 1, 1, 0, 1, 3, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.panelTop = new JPanel();
        this.panelTop.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.contentPane.add(this.panelTop, new GridConstraints(0, 0, 1, 1, 0, 3, 3, 3, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblDesc = new JLabel();
        Font lblDescFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblDesc.getFont());
        if (lblDescFont != null) {
            this.lblDesc.setFont(lblDescFont);
        }

        this.lblDesc.setText("Description");
        this.panelTop.add(this.lblDesc, new GridConstraints(0, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblLitres = new JLabel();
        Font lblLitresFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblLitres.getFont());
        if (lblLitresFont != null) {
            this.lblLitres.setFont(lblLitresFont);
        }

        this.lblLitres.setText("Litres");
        this.panelTop.add(this.lblLitres, new GridConstraints(1, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.lblAmount = new JLabel();
        Font lblAmountFont = this.$$$getFont$$$("Monospaced", 1, 16, this.lblAmount.getFont());
        if (lblAmountFont != null) {
            this.lblAmount.setFont(lblAmountFont);
        }

        this.lblAmount.setText("Amount");
        this.panelTop.add(this.lblAmount, new GridConstraints(2, 0, 1, 1, 4, 0, 0, 0, (Dimension)null, (Dimension)null, (Dimension)null, 0, false));
        this.txtDesc = new JTextField();
        this.txtDesc.setColumns(20);
        this.panelTop.add(this.txtDesc, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, -1), (Dimension)null, 5, false));
        this.txtLitres = new JTextField();
        this.txtLitres.setColumns(20);
        this.panelTop.add(this.txtLitres, new GridConstraints(1, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, -1), (Dimension)null, 5, false));
        this.txtAmount = new JTextField();
        this.txtAmount.setColumns(20);
        this.panelTop.add(this.txtAmount, new GridConstraints(2, 1, 1, 1, 8, 0, 0, 0, (Dimension)null, new Dimension(150, -1), (Dimension)null, 5, false));
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
