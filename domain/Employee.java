package mhl.domain;

/**
 * 这是一个javabean 和 数据库的 employee对应
 * 	id INT PRIMARY KEY AUTO_INCREMENT,
 * 	empId VARCHAR(50) NOT NULL DEFAULT '',
 * 	pwd CHAR(32) NOT NULL DEFAULT '', #密码md5
 * 	NAME VARCHAR(50) NOT NULL DEFAULT '',
 * 	job VARCHAR(50) NOT NULL DEFAULT ''
 */
public class Employee {
    private Integer id;
    private String empId;
    private String pwd;
    private String name;
    private String job;

    public Employee() { // 无参构造器，底层apache-dbutils反射需要
    }

    public Employee(Integer id, String empId, String pwd, String name, String job) {
        this.id = id;
        this.empId = empId;
        this.pwd = pwd;
        this.name = name;
        this.job = job;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
