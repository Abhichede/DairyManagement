package main;

import javax.swing.*;
import java.awt.*;
import java.math.RoundingMode;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseConnection {
    static Connection con = null;

    public DatabaseConnection() {
    }

    public Connection getDBConnection(String databaseName, String dbUname, String dbPass) {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, dbUname, dbPass);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return con;
    }

    public void closeDBConnection(Connection con) {
        try {
            con.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public int createDatabase(String dbName, Connection con) {
        int creatingStatus = 0;

        try {
            String createStatement = "CREATE DATABASE IF NOT EXISTS " + dbName;
            Statement stmt = con.createStatement();
            creatingStatus = stmt.executeUpdate(createStatement);
            System.out.println(creatingStatus);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return creatingStatus;
    }

    public void createTable(String tableName, String columnNamesWithDatatype) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + tableName + "( " + columnNamesWithDatatype + " )";

        try {
            Statement stmt = con.createStatement();
            System.out.println("Creating Table " + tableName);
            System.out.println("Executing : " + createTableStatement);
            stmt.executeUpdate(createTableStatement);
            System.out.println("Table has been created...!!!");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static Connection getCon() {
        return con;
    }

    public void createRequiredTables() {
        String createUser = "uid INT(10) NOT NULL AUTO_INCREMENT, username VARCHAR(20), password VARCHAR(20), dairy_name VARCHAR(50), dairy_address VARCHAR(50),login_status boolean DEFAULT false,PRIMARY KEY(uid)";
        String createCustomer = "cust_id INT(10) NOT NULL AUTO_INCREMENT, cust_name VARCHAR(20), cust_contact VARCHAR(10), cust_address VARCHAR(50), cattle_type VARCHAR(10), PRIMARY KEY (cust_id)";
        String createRateChart = "rate_id INT(10) NOT NULL AUTO_INCREMENT, cattle_type VARCHAR(10), snf VARCHAR(10), fat VARCHAR(10), rate VARCHAR(10), PRIMARY KEY (rate_id)";
        String createDailyCollection = "ID INT(10) NOT NULL AUTO_INCREMENT, customer_id INT(10) NOT NULL, date VARCHAR(20), time VARCHAR(20), shift VARCHAR(10), litre VARCHAR(10), fat VARCHAR(10), lacto VARCHAR(10), snf VARCHAR(10), rate VARCHAR(10), total_price VARCHAR(20), create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(ID)";
        String createPayments = "ID INT(10) NOT NULL AUTO_INCREMENT, customer_id INT(10) NOT NULL, days INT(10), total_litres VARCHAR(20), avg_fat VARCHAR(10), avg_snf VARCHAR(10), avg_lacto VARCHAR(10), avg_rate VARCHAR(10), total_amount VARCHAR(20),created_at timestamp, PRIMARY KEY(ID)";
        String createEmployee = "ID INT(10) NOT NULL AUTO_INCREMENT, name VARCHAR(50), address VARCHAR(50), mobile VARCHAR(20), allotted_salary VARCHAR(10), salary_start_date VARCHAR(50), designation VARCHAR(50), designation_details VARCHAR(50), total_salary VARCHAR(50) DEFAULT 0, current_month_salary VARCHAR(50) DEFAULT 0, bank_account_number VARCHAR(50), bank_name VARCHAR(50), bank_ifsc VARCHAR(50), bank_branch VARCHAR(50), created_at timestamp,PRIMARY KEY (ID)";
        String createEmpSalary = "ID INT(10) NOT NULL AUTO_INCREMENT,employee_id INT(10) NOT NULL, payment_type VARCHAR(100), payment_desc VARCHAR(200), payment_mode VARCHAR(100), payment_mode_desc VARCHAR(200), amount VARCHAR(20), bonus VARCHAR(20) DEFAULT '0', create_at timestamp, PRIMARY KEY(ID), FOREIGN KEY(employee_id) REFERENCES employees(ID)";
        String createFuel = "ID INT(10) NOT NULL AUTO_INCREMENT, date timestamp, description VARCHAR(100), litres VARCHAR(50), amount VARCHAR(50), PRIMARY KEY(ID)";
        this.createTable("users", createUser);
        this.createTable("customers", createCustomer);
        this.createTable("rate_chart", createRateChart);
        this.createTable("daily_collection", createDailyCollection);
        this.createTable("payments", createPayments);
        this.createTable("employees", createEmployee);
        this.createTable("emp_salary", createEmpSalary);
        this.createTable("fuel_desc", createFuel);
    }

    public int addUser(String username, String password, String dairy_name, String dairy_address, Component component) {
        String insertUser = "INSERT INTO users( username, password, dairy_name, dairy_address) values(?, ?, ?, ?)";
        int result = 0;

        try {
            System.out.println("Adding user to database");
            PreparedStatement insertUserStatement = con.prepareStatement(insertUser);
            insertUserStatement.setString(1, username);
            insertUserStatement.setString(2, password);
            insertUserStatement.setString(3, dairy_name);
            insertUserStatement.setString(4, dairy_address);
            result = insertUserStatement.executeUpdate();
            if (result == 1) {
                System.out.println("User " + username + " added.");
                JOptionPane.showMessageDialog(component, "User has been added successfully!!!", "Success", 1);
            }
        } catch (Exception var9) {
            JOptionPane.showInternalMessageDialog(component, var9, "Something went wrong", 0);
        }

        return result;
    }

    public int updateUser(int uid, String username, String password, String dairy_name, String dairy_address, Component component) {
        String insertUser = "UPDATE users SET username = ?, password = ?, dairy_name = ?, dairy_address = ? WHERE uid = ?";
        int result = 0;

        try {
            System.out.println("Updating user...");
            PreparedStatement updateUserStatement = con.prepareStatement(insertUser);
            updateUserStatement.setString(1, username);
            updateUserStatement.setString(2, password);
            updateUserStatement.setString(3, dairy_name);
            updateUserStatement.setString(4, dairy_address);
            updateUserStatement.setInt(5, uid);
            result = updateUserStatement.executeUpdate();
            if (result == 1) {
                System.out.println("User " + username + " updated.");
                JOptionPane.showMessageDialog(component, "User has been updated successfully!!!", "Success", 1);
            }
        } catch (Exception var10) {
            JOptionPane.showInternalMessageDialog(component, var10, "Something went wrong", 0);
        }

        return result;
    }

    public int deleteUser(int uid) {
        int result = 0;

        try {
            Statement statement = con.createStatement();
            String strDeleteUser = "DELETE FROM users WHERE uid = " + uid;
            result = statement.executeUpdate(strDeleteUser);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public ResultSet getUser(int uid) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM users WHERE uid = " + uid + " LIMIT 1";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getUserByUnamePass(String username, String password) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1";
            PreparedStatement preparedStatement = con.prepareStatement(strGetUser);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return resultSet;
    }

    public void updateLoginStatus(boolean status, int uid) {
        try {
            String sqlQuery = "UPDATE users SET login_status = ? WHERE uid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setBoolean(1, status);
            preparedStatement.setInt(2, uid);
            preparedStatement.executeUpdate();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public ResultSet getCurrentUser() {
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT * FROM users WHERE login_status = true";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getAllUsers() {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM users";
            System.out.println("Showing all users...");
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public int addCustomer(String customer_name, String customer_contact, String customer_address, String cattle_type, Component component) {
        String insertCustomer = "INSERT INTO customers( cust_name, cust_contact, cust_address, cattle_type) values(?, ?, ?, ?)";
        int result = 0;

        try {
            System.out.println("Adding customer to database");
            PreparedStatement insertCustomerStatement = con.prepareStatement(insertCustomer, 1);
            insertCustomerStatement.setString(1, customer_name);
            insertCustomerStatement.setString(2, customer_contact);
            insertCustomerStatement.setString(3, customer_address);
            insertCustomerStatement.setString(4, cattle_type);
            result = insertCustomerStatement.executeUpdate();
            ResultSet resultSet = insertCustomerStatement.getGeneratedKeys();
            resultSet.next();
            if (result == 1) {
                System.out.println("customer " + customer_name + " added.");
                JOptionPane.showMessageDialog(component, "Customer has been added successfully!!!\n Customer ID/Code: " + resultSet.getInt(1), "Success", 1);
            }
        } catch (Exception var10) {
            JOptionPane.showInternalMessageDialog(component, var10, "Something went wrong", 0);
        }

        return result;
    }

    public int updateCustomer(int cust_id, String customer_name, String customer_contact, String customer_address, String cattle_type, Component component) {
        String insertCustomer = "UPDATE customers SET cust_name = ?, cust_contact = ?, cust_address = ?, cattle_type = ? WHERE cust_id = ?";
        int result = 0;

        try {
            System.out.println("Updating customer...");
            PreparedStatement updateCustomerStatement = con.prepareStatement(insertCustomer);
            updateCustomerStatement.setString(1, customer_name);
            updateCustomerStatement.setString(2, customer_contact);
            updateCustomerStatement.setString(3, customer_address);
            updateCustomerStatement.setString(4, cattle_type);
            updateCustomerStatement.setInt(5, cust_id);
            result = updateCustomerStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Customer " + customer_name + " updated.");
                JOptionPane.showMessageDialog(component, "Customer has been updated successfully!!!", "Success", 1);
            }
        } catch (Exception var10) {
            var10.printStackTrace();
            JOptionPane.showInternalMessageDialog(component, var10, "Something went wrong", 0);
        }

        return result;
    }

    public int deleteCustomer(int cust_id) {
        int result = 0;

        try {
            Statement statement = con.createStatement();
            String strDeleteUser = "DELETE FROM customers WHERE cust_id = " + cust_id;
            result = statement.executeUpdate(strDeleteUser);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public ResultSet getCustomer(int cust_id) {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM customers WHERE cust_id = " + cust_id + " LIMIT 1";
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getAllCustomers() {
        ResultSet resultSet = null;

        try {
            String strGetUser = "SELECT * FROM customers";
            System.out.println("Showing all customers...");
            Statement stmt = con.createStatement();
            resultSet = stmt.executeQuery(strGetUser);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public int addRate(String[] values) {
        int result = 0;
        String insertCommand = "INSERT INTO rate_chart(cattle_type, snf, fat, rate) values(?, ?, ?, ?)";

        try {
            PreparedStatement preparedInsertStatement = con.prepareStatement(insertCommand);
            preparedInsertStatement.setString(1, values[0]);
            preparedInsertStatement.setString(2, values[1]);
            preparedInsertStatement.setString(3, values[2]);
            preparedInsertStatement.setString(4, values[3]);
            result = preparedInsertStatement.executeUpdate();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public ResultSet findRate(String[] values) {
        ResultSet resultSet = null;
        String insertCommand = "SELECT * FROM rate_chart WHERE cattle_type = ? AND snf = ? AND fat = ? LIMIT 1";

        try {
            PreparedStatement preparedFindStatement = con.prepareStatement(insertCommand);
            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);
            Double snf = Double.parseDouble(values[1]) / 4.0D + 0.21D * Double.parseDouble(values[2]) + 0.36D;
            System.out.println("SNF: " + df.format(snf));
            preparedFindStatement.setString(1, values[0]);
            preparedFindStatement.setString(2, df.format(snf));
            preparedFindStatement.setString(3, values[2]);
            resultSet = preparedFindStatement.executeQuery();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getRatesByFatAndSnf(String cattleType, String strFat, String strSnf) {
        ResultSet resultSet = null;

        try {
            String strQuery = "SELECT rate FROM rate_chart WHERE cattle_type = ? AND snf = ? AND fat = ?";
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, cattleType);
            preparedStatement.setString(2, strSnf);
            preparedStatement.setString(3, strFat);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return resultSet;
    }

    public void deleteRate(String cattleType) {
        try {
            String strQuery = "DELETE FROM rate_chart WHERE cattle_type = ?";
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, cattleType);
            preparedStatement.executeUpdate();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public int insertDailyCollection(int customer_id, String[] values) {
        String insertQuery = "INSERT INTO daily_collection(customer_id, date, time, shift, litre, fat, lacto, snf, rate, total_price) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlSelect = "SELECT * FROM daily_collection WHERE customer_id = ? AND shift = ? AND date = ?";
        int result = 0;

        try {
            PreparedStatement preparedStatementSelect = con.prepareStatement(sqlSelect);
            preparedStatementSelect.setInt(1, customer_id);
            preparedStatementSelect.setString(2, values[2]);
            preparedStatementSelect.setString(3, values[0]);
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            if (resultSet.next()) {
                return 0;
            }

            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1, customer_id);
            int j = 2;

            for(int i = 0; i < values.length; ++i) {
                preparedStatement.setString(j, values[i]);
                ++j;
            }

            System.out.println("Query: " + preparedStatement.toString());
            result = preparedStatement.executeUpdate();
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return result;
    }

    public ResultSet getDailyCollectionByDateAndShift(String date, String shift) {
        String strQuery = "SELECT * FROM daily_collection WHERE date = ? AND shift = ?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, shift);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getDailyCollectionByDate(String date) {
        String strQuery = "SELECT * FROM daily_collection WHERE date = ?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, date);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getDailyCollection() {
        String strQuery = "SELECT * FROM daily_collection";
        ResultSet resultSet = null;

        try {
            Statement preparedStatement = con.createStatement();
            resultSet = preparedStatement.executeQuery(strQuery);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getDailyCollectionFromTo(int customer_id, String from, String to) {
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT * FROM daily_collection WHERE customer_id = ? AND create_at BETWEEN ? AND ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, customer_id);
            preparedStatement.setString(2, from);
            preparedStatement.setString(3, to);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getDailyCollectionByCustomer(int customer_id) {
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT * FROM daily_collection WHERE customer_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, customer_id);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getDailyCollectionByCustomerAndDate(int customer_id, String fromDate, String toDate) {
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT * FROM daily_collection WHERE customer_id = ? AND create_at BETWEEN ? AND ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, customer_id);
            preparedStatement.setString(2, fromDate);
            preparedStatement.setString(3, toDate);
            resultSet = preparedStatement.executeQuery();

            System.out.println("@database Dates: " + fromDate + " " + toDate);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return resultSet;
    }

    public int updateDailyCollection(int customer_id, String[] values) {
        String insertQuery = "UPDATE daily_collection SET litre = ?, fat = ?, lacto = ?, snf = ?, rate = ?, total_price = ? WHERE customer_id = ? AND date = ? AND shift = ?";
        int result = 0;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            System.out.println("Litre: " + values[0]);

            for(int i = 0; i < values.length - 1; ++i) {
                preparedStatement.setString(i + 1, values[i]);
            }

            preparedStatement.setInt(7, customer_id);
            preparedStatement.setString(8, values[6]);
            preparedStatement.setString(9, values[7]);
            result = preparedStatement.executeUpdate();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return result;
    }

    public int insertPayments(int customer_id, int days, String[] values) {
        int result = 0;

        try {
            String insertQuery = "INSERT INTO payments(customer_id, days, total_litres, avg_fat, avg_snf, avg_lacto, avg_rate, total_amount) values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1, customer_id);
            preparedStatement.setInt(2, days);
            int c = 3;

            for(int i = 0; i < values.length; ++i) {
                preparedStatement.setString(c, values[i]);
                ++c;
            }

            result = preparedStatement.executeUpdate();
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return result;
    }

    public ResultSet getLastDate(int customer_id) {
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT created_at FROM payments WHERE customer_id = ? ORDER BY created_at DESC LIMIT 1";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, customer_id);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getPaymentHistory(int customer_id) {
        ResultSet resultSet = null;

        try {
            String sqlQuery = "SELECT * FROM payments WHERE customer_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, customer_id);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public int addEmployee(String[] values) {
        String insertEmployee = "INSERT INTO employees( name, address, mobile, allotted_salary, salary_start_date, designation, designation_details, bank_account_number, bank_name, bank_ifsc, bank_branch) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = 0;

        try {
            System.out.println("Adding Employee to database");
            PreparedStatement insertCustomerStatement = con.prepareStatement(insertEmployee);

            for(int i = 0; i < values.length; ++i) {
                insertCustomerStatement.setString(i + 1, values[i]);
            }

            result = insertCustomerStatement.executeUpdate();
            ResultSet resultSet = insertCustomerStatement.getGeneratedKeys();
            resultSet.next();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }

    public int updateEmployee(String[] values, int emp_ID) {
        String insertEmployee = "UPDATE employees SET name = ?, address = ?, mobile = ?, allotted_salary = ?, salary_start_date = ?, designation = ?, designation_details = ?, bank_account_number = ?, bank_name = ?, bank_ifsc = ?, bank_branch = ? WHERE ID = ?";
        int result = 0;

        try {
            System.out.println("updating Employee to database");
            PreparedStatement insertCustomerStatement = con.prepareStatement(insertEmployee);

            int i;
            for(i = 0; i < values.length; ++i) {
                insertCustomerStatement.setString(i + 1, values[i]);
            }

            insertCustomerStatement.setInt(i + 1, emp_ID);
            result = insertCustomerStatement.executeUpdate();
            ResultSet resultSet = insertCustomerStatement.getGeneratedKeys();
            resultSet.next();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return result;
    }

    public ResultSet getEmployeeName(String string) {
        string = "%" + string + "%";
        String strQuery = "SELECT name FROM employees WHERE name LIKE ?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, string);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getEmployeeDetailsByName(String string) {
        String strQuery = "SELECT * FROM employees WHERE name = ? LIMIT 1";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setString(1, string);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getEmployeeDetailsByID(int ID) {
        String strQuery = "SELECT * FROM employees WHERE ID = ? LIMIT 1";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setInt(1, ID);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public int addEmployeeSalary(int emp_id, String[] values) {
        String insertEmployee = "INSERT INTO emp_salary( employee_id, payment_type, payment_desc, payment_mode, payment_mode_desc, amount, bonus) values(?, ?, ?, ?, ?, ?, ?)";
        int result = 1;

        try {
            System.out.println("Adding Employee's salary to database");
            PreparedStatement insertCustomerStatement = con.prepareStatement(insertEmployee);
            insertCustomerStatement.setInt(1, emp_id);

            for(int i = 0; i < values.length; ++i) {
                insertCustomerStatement.setString(i + 2, values[i]);
            }

            result = insertCustomerStatement.executeUpdate();
            if (result != 0) {
                ResultSet employee = this.getEmployeeDetailsByID(emp_id);
                if (employee.next()) {
                    Double total_paid = employee.getString("total_salary").equals("") ? 0.0D : Double.parseDouble(employee.getString("total_salary"));
                    DecimalFormat df = new DecimalFormat("#.##");
                    df.setRoundingMode(RoundingMode.CEILING);
                    String total_salary = df.format(total_paid.doubleValue() + Double.parseDouble(values[4]));
                    String updateEmployee = "UPDATE employees SET total_salary = ?, current_month_salary = ? WHERE ID = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(updateEmployee);
                    Double totalUpTo = 0.0D;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = dateFormat.parse(employee.getString("salary_start_date"));
                    int totalMonths = (int)(((new Date()).getTime() - startDate.getTime()) / 86400000L) / 30;
                    totalUpTo = Double.parseDouble(employee.getString("allotted_salary")) * (double)totalMonths;
                    preparedStatement.setString(1, total_salary);
                    preparedStatement.setString(2, df.format(totalUpTo.doubleValue() - Double.parseDouble(total_salary)));
                    preparedStatement.setInt(3, emp_id);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception var16) {
            var16.printStackTrace();
        }

        return result;
    }

    public ResultSet getEmployeeSalaryDetailsByID(int ID) {
        String strQuery = "SELECT * FROM emp_salary WHERE employee_id = ?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setInt(1, ID);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return resultSet;
    }

    public int insertFuel(String[] values) {
        String strInsertFuel = "INSERT INTO fuel_desc(description, litres, amount) VALUES(?, ?, ?)";
        int result = 0;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strInsertFuel);

            for(int i = 0; i < values.length; ++i) {
                preparedStatement.setString(i + 1, values[i]);
            }

            result = preparedStatement.executeUpdate();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return result;
    }

    public int updateFuel(String[] values, int fuelID) {
        String strInsertFuel = "UPDATE fuel_desc SET description = ?, litres = ?, amount = ? WHERE ID = ?";
        int result = 0;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strInsertFuel);

            for(int i = 0; i < values.length; ++i) {
                preparedStatement.setString(i + 1, values[i]);
            }

            preparedStatement.setInt(4, fuelID);
            result = preparedStatement.executeUpdate();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return result;
    }

    public ResultSet getFuelDetails() {
        String strQuery = "SELECT * FROM fuel_desc";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getCurrentMonthFuelDetails() {
        ResultSet resultSet = null;

        try {
            Date nowDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date monthStartDate = dateFormat.parse("2017-" + String.valueOf(calendar.get(2) + 1) + "-01 00:00:00");
            String strQuery = "SELECT * FROM fuel_desc WHERE date BETWEEN ? AND ?";
            PreparedStatement preparedStatement = con.prepareStatement(strQuery);
            preparedStatement.setTimestamp(1, new Timestamp(monthStartDate.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(nowDate.getTime()));
            resultSet = preparedStatement.executeQuery();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return resultSet;
    }
}
