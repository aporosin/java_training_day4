package com.aptitude.training.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements AutoCloseable {

    public static void main(String[] args) {
        try {

        EmployeeService service = new EmployeeService();

        if(service.getConnection() != null)
            System.out.println("Connected sucessfully");

            service.createSchema();

            // create employee
            service.createEmployee(new Employee("Zuza", "pm", "Wroclaw", 3, 3 ));
            service.createEmployee(new Employee("Jan", "pm", "Wroclaw", 4, 4555));
            service.raise("Ania Nowak", 2000);
            service.promotion("Ola", Positions.admin);
            service.fire("Jan");
            service.listEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createSchema() throws SQLException {
        boolean result = connection.createStatement().execute("CREATE TABLE IF NOT EXISTS employees(id int auto_increment, " +
                "name varchar(255), " +
                "position varchar(255), " +
                "city varchar(255)," +
                "salary int)");

        updateSchema();
    }

    private void updateSchema() throws  SQLException {
        //connection.createStatement().execute("ALTER TABLE employees ADD managerId int");
        connection.createStatement().execute("ALTER TABLE employees ADD FOREIGN KEY (managerId) REFERENCES employees(id)");

    }


    public Connection getConnection() {
        return connection;
    }

    Connection connection;

    public EmployeeService() {

        // get onnection here?
        try {
            connection = SetupDb.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> listEmployees() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT name, position, city, salary, managerId, id FROM employees");
        List<Employee> list = new ArrayList<>();
        while(resultSet.next())
        {
            Employee e  = new Employee();
            e.setName(resultSet.getString(1));
            e.setPosition(resultSet.getString(2));
            e.setCity(resultSet.getString(3));
            e.setSalary(resultSet.getInt(4));
            e.setManagerId(resultSet.getInt(5));
            e.setId(resultSet.getInt(6));
            list.add(e);
            System.out.println(e);
        }

        return list;
    }

    public void createEmployee(Employee e) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employees(name, position, city, salary, managerId) VALUES(?,?,?,?,?)");
        preparedStatement.setString(1, e.getName());
        preparedStatement.setString(2, e.getPosition());
        preparedStatement.setString(3, e.getCity());
        preparedStatement.setInt(4, e.getSalary());
        preparedStatement.setInt(5, e.getManagerId());
        preparedStatement.executeUpdate();
    }

    public void raise(String name, int payRaise) throws SQLException {
        // find employess
        PreparedStatement find = connection.prepareStatement("UPDATE employees set salary = salary + ? WHERE name = ?");
        find.setInt(1, payRaise);
        find.setString(2, name);
        find.executeUpdate();
    }

    private enum Positions  {tester, dev, pm, headOfSth, admin};

    public void promotion(String name, Positions position) throws SQLException {

        PreparedStatement find = connection.prepareStatement("UPDATE employees set position = ? WHERE name = ?");
        find.setString(1, position.name());
        find.setString(2, name);
        find.executeUpdate();
    }

    public void holiday() {

    }

    public void fire(String name) throws SQLException {

        PreparedStatement delete = connection.prepareStatement("DELETE FROM employees WHERE name = ?");
        delete.setString(1, name);
        delete.executeUpdate();
    }

    public void attachSubordinate() {

    }


    @Override
    public void close() throws Exception {
        if(connection != null)
            connection.close();
    }
}
