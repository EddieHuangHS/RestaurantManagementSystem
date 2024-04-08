package mhl.service;

import mhl.dao.EmployeeDAO;
import mhl.domain.Employee;

/**
 * 该类完成对employee表的各种操作（通过调用EmployeeDAO对象完成）
 */
public class EmployeeService {

    // 定义一个 EmployeeDAO 属性
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    // 方法，根据empId 和 pwd 返回一个 Employee 对象
    public Employee getEmployeeByIdAndPwd(String empId, String pwd){
        Employee employee =
                employeeDAO.querySingle("select * from employee where empId = ? and pwd = md5(?)", Employee.class, empId, pwd);
        return employee;
    }
}
