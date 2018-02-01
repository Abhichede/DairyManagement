package main;

import OtherExpenses.fuel.Fuel;
import customers.*;
import employees.CreateEmployee;
import employees.EditEmployee;
import employees.ShowEmployee;
import milk_management.*;
import settings.RateChart;
import users.CreateUser;
import users.DeleteUser;
import users.UpdateUser;
import users.ViewUsers;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;

public class Main extends JFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static JDesktopPane desktopPane;
    public static JInternalFrame internalFrame;
    static Font frameTextFont;
    static boolean userLoggedInStatus = false;
    static JMenuBar mainMenuBar;
    static JFrame mainFrame;
    static DatabaseConnection dbConnectionObject;

    public Main() {
    }

    public Main(String appName) {
        super(appName);
        this.setDefaultTheme();
        this.setSize(screenSize);
        this.setExtendedState(6);
        this.setIconImage((new ImageIcon(this.getClass().getResource("/images/appIcon.png"))).getImage());
        final URL imgURL = this.getClass().getResource("/images/cow_img.jpg");
        desktopPane = new JDesktopPane() {
            ImageIcon icon = new ImageIcon(imgURL);
            Image image;
            Image newImage;

            {
                this.image = this.icon.getImage();
                this.newImage = this.image.getScaledInstance(Main.screenSize.width, Main.screenSize.height, 4);
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.newImage, 0, 0, this);
            }
        };
        mainMenuBar = this.mainMenuBar();
        this.setContentPane(desktopPane);
    }

    public static void main(String[] a) {
        databaseInit();
        frameTextFont = new Font("Serif", 1, 16);
        mainFrame = new Main("Dairy Management System");
        mainFrame.setDefaultCloseOperation(3);
        mainFrame.setVisible(true);
        mainMenuBar.setEnabled(false);
        userLogin();
    }

    public static void databaseInit() {
        dbConnectionObject = new DatabaseConnection();

        try {
            Connection con = dbConnectionObject.getDBConnection("", "root", "root");
            int dbStatus = dbConnectionObject.createDatabase("dairy_management", con);
            if (dbStatus != 0) {
                System.out.println("Database Created Successfully");
                con = dbConnectionObject.getDBConnection("dairy_management", "root", "root");
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        dbConnectionObject.createRequiredTables();
    }

    public void setDefaultTheme() {
        try {
            LookAndFeelInfo[] var1 = UIManager.getInstalledLookAndFeels();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                LookAndFeelInfo info = var1[var3];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception var5) {
            ;
        }

    }

    public JMenuBar mainMenuBar() {
        JMenuBar jMainMenuBar = new JMenuBar();
        JMenu menuHome = new JMenu("Home");
        menuHome.setFont(frameTextFont);
        JMenu menuUsers = new JMenu("Users");
        menuUsers.setFont(frameTextFont);
        JMenu menuCustomer = new JMenu("Customer");
        menuCustomer.setFont(frameTextFont);
        JMenu menuMilkMenu = new JMenu("Milk Menu");
        menuMilkMenu.setFont(frameTextFont);
        JMenu menuEmployee = new JMenu("Employees");
        menuEmployee.setFont(frameTextFont);
        JMenu menuSettings = new JMenu("Settings");
        menuSettings.setFont(frameTextFont);
        JMenu menuFuel = new JMenu("Other Expenses");
        menuFuel.setFont(frameTextFont);
        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.setFont(frameTextFont);
        itemExit.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        itemExit.setAccelerator(KeyStroke.getKeyStroke(88, 8));
        JMenuItem itemLogout = new JMenuItem("Logout");
        itemLogout.setFont(frameTextFont);
        itemLogout.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        itemLogout.setAccelerator(KeyStroke.getKeyStroke(76, 8));
        JMenuItem itemAddUser = new JMenuItem("Add");
        itemAddUser.setFont(frameTextFont);
        itemAddUser.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemEditUser = new JMenuItem("Edit");
        itemEditUser.setFont(frameTextFont);
        itemEditUser.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemViewUser = new JMenuItem("View");
        itemViewUser.setFont(frameTextFont);
        itemViewUser.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemDeleteUser = new JMenuItem("Delete");
        itemDeleteUser.setFont(frameTextFont);
        itemDeleteUser.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemAddCustomer = new JMenuItem("Add");
        itemAddCustomer.setFont(frameTextFont);
        itemAddCustomer.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemEditCustomer = new JMenuItem("Edit");
        itemEditCustomer.setFont(frameTextFont);
        itemEditCustomer.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemViewCustomer = new JMenuItem("View");
        itemViewCustomer.setFont(frameTextFont);
        itemViewCustomer.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemDeleteCustomer = new JMenuItem("Delete");
        itemDeleteCustomer.setFont(frameTextFont);
        itemDeleteCustomer.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemCustomerProfile = new JMenuItem("Customer Payments");
        itemCustomerProfile.setFont(frameTextFont);
        itemCustomerProfile.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemPaymentHistory = new JMenuItem("Payments History");
        itemPaymentHistory.setFont(frameTextFont);
        itemPaymentHistory.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemMilkCollection = new JMenuItem("Milk Collection");
        itemMilkCollection.setFont(frameTextFont);
        itemMilkCollection.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemAllCollection = new JMenuItem("Total Collection");
        itemAllCollection.setFont(frameTextFont);
        itemAllCollection.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemDailyReport = new JMenuItem("Daily Report");
        itemDailyReport.setFont(frameTextFont);
        itemDailyReport.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemPaymentRegister = new JMenuItem("Payment Register");
        itemPaymentRegister.setFont(frameTextFont);
        itemPaymentRegister.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemAddEmployee = new JMenuItem("Add Employee");
        itemAddEmployee.setFont(frameTextFont);
        itemAddEmployee.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemShowEmployee = new JMenuItem("Show Employees");
        itemShowEmployee.setFont(frameTextFont);
        itemShowEmployee.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemEditEmployee = new JMenuItem("Edit Employees");
        itemEditEmployee.setFont(frameTextFont);
        itemEditEmployee.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemRateChart = new JMenuItem("Rate Chart");
        itemRateChart.setFont(frameTextFont);
        itemRateChart.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        JMenuItem itemFuel = new JMenuItem("Fuel");
        itemFuel.setFont(frameTextFont);
        itemFuel.setPreferredSize(new Dimension(200, itemExit.getPreferredSize().height + 10));
        menuHome.add(itemLogout);
        menuHome.add(itemExit);
        menuHome.setFont(frameTextFont);
        menuUsers.add(itemAddUser);
        menuUsers.add(itemEditUser);
        menuUsers.add(itemViewUser);
        menuUsers.add(itemDeleteUser);
        menuCustomer.add(itemAddCustomer);
        menuCustomer.add(itemEditCustomer);
        menuCustomer.add(itemViewCustomer);
        menuMilkMenu.add(itemMilkCollection);
        menuMilkMenu.add(itemCustomerProfile);
        menuMilkMenu.add(itemPaymentHistory);
        menuMilkMenu.add(itemAllCollection);
        menuMilkMenu.add(itemDailyReport);
        menuMilkMenu.add(itemPaymentRegister);
        menuEmployee.add(itemAddEmployee);
        menuEmployee.add(itemEditEmployee);
        menuEmployee.add(itemShowEmployee);
        menuSettings.add(itemRateChart);
        menuFuel.add(itemFuel);
        itemLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResultSet currentUser = Main.dbConnectionObject.getCurrentUser();
                int uid = 0;

                try {
                    if (currentUser.next()) {
                        uid = currentUser.getInt("uid");
                    }
                } catch (Exception var8) {
                    var8.printStackTrace();
                }

                Main.dbConnectionObject.updateLoginStatus(false, uid);
                Main.mainFrame.setJMenuBar((JMenuBar)null);
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new Login();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ResultSet currentUser = Main.dbConnectionObject.getCurrentUser();
                int uid = 0;

                try {
                    if (currentUser.next()) {
                        uid = currentUser.getInt("uid");
                    }
                } catch (Exception var5) {
                    var5.printStackTrace();
                }

                Main.dbConnectionObject.updateLoginStatus(false, uid);
                System.exit(0);
            }
        });
        itemAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new CreateUser();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemEditUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new UpdateUser();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemDeleteUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new DeleteUser();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemViewUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new ViewUsers();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 2);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemAddCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new CreateCustomer();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemEditCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new UpdateCustomer();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemViewCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new ViewCustomers();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() - Main.desktopPane.getHeight() / 4);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemDeleteCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new DeleteCustomer();
                Main.internalFrame.setSize(Main.desktopPane.getWidth() / 2, Main.desktopPane.getHeight() / 4);
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemMilkCollection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new MilkCollection();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemRateChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new RateChart();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemCustomerProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new CustomerProfile();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemPaymentHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new PaymentHistory();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemAllCollection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new TotalCollection();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemPaymentRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Main.internalFrame = new PaymentRegister();
                        Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                        Dimension desktopSize = Main.desktopPane.getSize();
                        Dimension jInternalFrameSize = Main.internalFrame.getSize();
                        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                        Main.internalFrame.setLocation(width, height);
                        Main.internalFrame.setVisible(true);
                        Main.desktopPane.add(Main.internalFrame);
                    }
                });
            }
        });
        itemDailyReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new DailyReport();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemAddEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new CreateEmployee();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemEditEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new EditEmployee();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemFuel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new Fuel();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        itemShowEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.internalFrame != null) {
                    Main.internalFrame.dispose();
                }

                Main.internalFrame = new ShowEmployee();
                Main.internalFrame.setSize(Main.desktopPane.getWidth(), Main.desktopPane.getHeight());
                Dimension desktopSize = Main.desktopPane.getSize();
                Dimension jInternalFrameSize = Main.internalFrame.getSize();
                int width = (desktopSize.width - jInternalFrameSize.width) / 2;
                int height = (desktopSize.height - jInternalFrameSize.height) / 2;
                Main.internalFrame.setLocation(width, height);
                Main.internalFrame.setVisible(true);
                Main.desktopPane.add(Main.internalFrame);
            }
        });
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuHome);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuUsers);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuCustomer);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuMilkMenu);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuEmployee);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuSettings);
        jMainMenuBar.add(Box.createRigidArea(new Dimension(15, 30)));
        jMainMenuBar.add(menuFuel);
        return jMainMenuBar;
    }

    public static void userLogin() {
        System.out.println("User login....");
        internalFrame = new Login();
        internalFrame.setSize(desktopPane.getWidth() / 2, desktopPane.getHeight() / 2);
        Dimension desktopSize = desktopPane.getSize();
        Dimension jInternalFrameSize = internalFrame.getSize();
        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
        internalFrame.setLocation(width, height);
        internalFrame.setVisible(true);
        desktopPane.add(internalFrame);
    }

    public static void changeUserLoginStatus(boolean userLoggedInStatus1) {
        userLoggedInStatus = userLoggedInStatus1;
        if (userLoggedInStatus) {
            mainFrame.setJMenuBar(mainMenuBar);
            internalFrame.dispose();
        }

    }
}
