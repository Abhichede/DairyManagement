package settings;

import main.APIService;
import main.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MessageHistory extends JInternalFrame{
    private JPanel rootPanel;
    private JLabel lblAvailableMsg;
    private JLabel txtAvailableMessages;
    private JPanel panelAvailableBalance;
    private JPanel panelMessageHistory;
    private JTable tableSentMessages;
    private JButton btnResend;
    private JLabel lblInternetConnection;

    private DefaultTableModel defaultTableModel;
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public MessageHistory() throws IOException, URISyntaxException {
        super("Message History", false, true, false, false);



        this.add($$$getRootComponent$$$());

        String[] column = new String[]{"Date", "Customer ID", "Mobile Number", "Message", "Message Status", "MSG ID"};
        this.defaultTableModel = new DefaultTableModel(0, 0);
        this.defaultTableModel.setColumnIdentifiers(column);
        this.tableSentMessages.setModel(this.defaultTableModel);

        if(APIService.netIsAvailable()){
            lblInternetConnection.setText("Internet Connection is available!");
            lblInternetConnection.setForeground(Color.DARK_GRAY);

            txtAvailableMessages.setText(new APIService().getAvailableBalance());
        }else{
            lblInternetConnection.setText("Internet Connection is not available!");
            lblInternetConnection.setForeground(Color.RED);
        }

        try {
            if (defaultTableModel.getRowCount() > 0) {
                for(int i = defaultTableModel.getRowCount() - 1; i > -1; --i) {
                    defaultTableModel.removeRow(i);
                }
            }
            ResultSet resultSet = this.databaseConnection.getMessageStatusDetails();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String msgStatus = "";
            if (resultSet != null) {
                while(resultSet.next()) {
                    if(resultSet.getString("msg_status").equals("1"))
                        msgStatus = "Success";
                    else if(resultSet.getString("msg_status").equals("2"))
                        msgStatus = "Invalid Credentials";
                    else if(resultSet.getString("msg_status").equals("3"))
                        msgStatus = "Insufficient Balance";
                    else if(resultSet.getString("msg_status").equals("4"))
                        msgStatus = "Error";
                    else if(resultSet.getString("msg_status").equals("5"))
                        msgStatus = "Invalid Sender ID";
                    else if(resultSet.getString("msg_status").equals("6"))
                        msgStatus = "Invalid Route";
                    else if(resultSet.getString("msg_status").equals("7"))
                        msgStatus = "Submission Error";
                    else if(resultSet.getString("msg_status").equals("0"))
                        msgStatus = "Already resubmitted";
                    String[] data = new String[]{dateFormat.format(resultSet.getTimestamp("date")), String.valueOf(resultSet.getInt("cust_id")), resultSet.getString("cust_mobile"), resultSet.getString("message"), msgStatus, resultSet.getString("msgID")};
                    this.defaultTableModel.addRow(data);
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        btnResend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(APIService.netIsAvailable()){
                        ResultSet resultSet = databaseConnection.getMessageStatusDetails();
                        if (resultSet != null) {
                            while(resultSet.next()) {
                                if(Integer.parseInt(resultSet.getString("msg_status")) != 1 && Integer.parseInt(resultSet.getString("msg_status")) != 0)
                                {
                                    databaseConnection.updateMessageStatus(resultSet.getInt("ID"), "0");

                                    new APIService().sendSMS(resultSet.getString("cust_mobile"),
                                            resultSet.getString("message"), resultSet.getInt("cust_id"));
                                    System.out.println("Resent");
                                }
                            }

                            JOptionPane.showInternalMessageDialog(MessageHistory.this.getContentPane(), "Undelivered Messages resent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else{
                        JOptionPane.showInternalMessageDialog(MessageHistory.this.getContentPane(), "No Internet Connection!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception var5) {
                    var5.printStackTrace();
                }
            }
        });
    }



    public JComponent $$$getRootComponent$$$() {
        return this.rootPanel;
    }
}
