// ============================================================
// COMPLETE HIBERNATE EXAMPLE — Similar to your Infosys project
// ============================================================

// ─────────────────────────────────────────────
// 1. ENTITY CLASS — Maps Java class to DB table
// ─────────────────────────────────────────────
import javax.persistence.*;

@Entity                          // tells Hibernate — this class maps to a DB table!!
@Table(name = "employees")       // maps to "employees" table in DB
public class Employee {

    @Id                                                        // primary key!!
    @GeneratedValue(strategy = GenerationType.IDENTITY)        // auto increment!!
    @Column(name = "emp_id")
    private int id;

    @Column(name = "emp_name")
    private String name;

    @Column(name = "emp_department")
    private String department;

    @Column(name = "emp_salary")
    private double salary;

    // Constructors
    public Employee() { }

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name=" + name +
                ", department=" + department + ", salary=" + salary + "}";
    }
}


// ─────────────────────────────────────────────
// 2. HIBERNATE CONFIGURATION — hibernate.cfg.xml
// ─────────────────────────────────────────────
/*
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <!-- Database connection -->
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/employeedb</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">password</property>

        <!-- Hibernate settings -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="hibernate.hbm2ddl.auto">update</property>  <!-- creates/updates table -->
        <property name="hibernate.show_sql">true</property>         <!-- shows SQL in console -->
        <property name="hibernate.format_sql">true</property>

        <!-- Entity class -->
        <mapping class="com.example.Employee"/>
    </session-factory>
</hibernate-configuration>
*/


// ─────────────────────────────────────────────
// 3. DAO CLASS — All Hibernate operations
// Similar to what you have in your project!!
// ─────────────────────────────────────────────
import org.hibernate.*;
        import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import javax.transaction.Transactional;
import java.util.List;

public class EmployeeDAO {

    // SessionFactory — created once, used throughout application!!
    private static SessionFactory sessionFactory;

    static {
        // Build SessionFactory from hibernate.cfg.xml
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    // ─────────────────────────────────────────────
    // SAVE — INSERT into DB
    // ─────────────────────────────────────────────
    @Transactional
    public void saveEmployee(Employee employee) {
        // Step 1 — Get session (like your project!!)
        Session currentSession = sessionFactory.getCurrentSession();

        // Step 2 — Begin transaction
        Transaction tx = currentSession.beginTransaction();

        try {
            // Step 3 — Save employee
            currentSession.save(employee);

            // Step 4 — Commit transaction
            tx.commit();
            System.out.println("Employee saved successfully!!");

        } catch (Exception e) {
            // Step 5 — Rollback if error!!
            tx.rollback();
            throw new RuntimeException("Error saving employee: " + e.getMessage());
        }
    }


    // ─────────────────────────────────────────────
    // GET BY ID — SELECT by primary key
    // ─────────────────────────────────────────────
    public Employee getEmployeeById(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction tx = currentSession.beginTransaction();

        try {
            // get() — fetches by primary key!!
            Employee employee = currentSession.get(Employee.class, id);
            tx.commit();

            if (employee == null) {
                throw new RuntimeException("Employee not found with id: " + id);
            }
            return employee;

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error fetching employee: " + e.getMessage());
        }
    }


    // ─────────────────────────────────────────────
    // GET ALL — SELECT all records
    // Using HQL — like createQuery in your project!!
    // ─────────────────────────────────────────────
    public List<Employee> getAllEmployees() {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction tx = currentSession.beginTransaction();

        try {
            // HQL — uses Class name not table name!!
            Query<Employee> query = currentSession.createQuery(
                    "FROM Employee", Employee.class
            );
            List<Employee> employees = query.getResultList();
            tx.commit();
            return employees;

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error fetching employees: " + e.getMessage());
        }
    }


    // ─────────────────────────────────────────────
    // GET BY DEPARTMENT — HQL with parameter
    // ─────────────────────────────────────────────
    public List<Employee> getEmployeesByDepartment(String department) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction tx = currentSession.beginTransaction();

        try {
            // HQL with parameter — setParameter!!
            Query<Employee> query = currentSession.createQuery(
                    "FROM Employee WHERE department = :dept", Employee.class
            );
            query.setParameter("dept", department);  // set parameter value!!

            List<Employee> employees = query.getResultList();
            tx.commit();
            return employees;

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    // ─────────────────────────────────────────────
    // createCriteria — LIKE YOUR PROJECT!!
    // Dynamic query building!!
    // ─────────────────────────────────────────────
    public List<Employee> getEmployeesByCriteria(String department, double minSalary) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction tx = currentSession.beginTransaction();

        try {
            // createCriteria — just like your project!!
            Criteria criteria = currentSession.createCriteria(Employee.class);

            // Add conditions dynamically!!
            if (department != null) {
                criteria.add(Restrictions.eq("department", department));
            }
            if (minSalary > 0) {
                criteria.add(Restrictions.gt("salary", minSalary));
            }

            List<Employee> employees = criteria.list();
            tx.commit();
            return employees;

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    // ─────────────────────────────────────────────
    // UPDATE — Update existing record
    // ─────────────────────────────────────────────
    @Transactional
    public void updateEmployee(Employee employee) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction tx = currentSession.beginTransaction();

        try {
            currentSession.update(employee);  // update!!
            tx.commit();
            System.out.println("Employee updated successfully!!");

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error updating: " + e.getMessage());
        }
    }


    // ─────────────────────────────────────────────
    // SAVE OR UPDATE — Insert if new, Update if exists
    // ─────────────────────────────────────────────
    @Transactional
    public void saveOrUpdateEmployee(Employee employee) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction tx = currentSession.beginTransaction();

        try {
            currentSession.saveOrUpdate(employee);  // smart — checks if exists!!
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    // ─────────────────────────────────────────────
    // DELETE — Remove record
    // ─────────────────────────────────────────────
    @Transactional
    public void deleteEmployee(int id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction tx = currentSession.beginTransaction();

        try {
            // First get the employee!!
            Employee employee = currentSession.get(Employee.class, id);

            if (employee != null) {
                currentSession.delete(employee);  // then delete!!
                tx.commit();
                System.out.println("Employee deleted successfully!!");
            } else {
                throw new RuntimeException("Employee not found!!");
            }

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error deleting: " + e.getMessage());
        }
    }


    // ─────────────────────────────────────────────
    // NATIVE SQL QUERY — Raw SQL
    // ─────────────────────────────────────────────
    public List<Employee> getHighSalaryEmployees(double salary) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction tx = currentSession.beginTransaction();

        try {
            // Native SQL — plain SQL query!!
            Query query = currentSession.createNativeQuery(
                    "SELECT * FROM employees WHERE emp_salary > :salary",
                    Employee.class
            );
            query.setParameter("salary", salary);

            List<Employee> employees = query.getResultList();
            tx.commit();
            return employees;

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}


// ─────────────────────────────────────────────
// 4. MAIN CLASS — Test everything!!
// ─────────────────────────────────────────────
public class Main {
    public static void main(String[] args) {

        EmployeeDAO dao = new EmployeeDAO();

        // ── SAVE ──
        Employee emp1 = new Employee("Manjunath", "IT", 75000);
        dao.saveEmployee(emp1);
        // SQL: INSERT INTO employees (emp_name, emp_department, emp_salary)
        //      VALUES ('Manjunath', 'IT', 75000)

        // ── GET BY ID ──
        Employee emp = dao.getEmployeeById(1);
        System.out.println(emp);
        // SQL: SELECT * FROM employees WHERE emp_id = 1

        // ── GET ALL ──
        List<Employee> allEmps = dao.getAllEmployees();
        allEmps.forEach(System.out::println);
        // HQL: FROM Employee
        // SQL: SELECT * FROM employees

        // ── GET BY DEPARTMENT ──
        List<Employee> itEmps = dao.getEmployeesByDepartment("IT");
        // HQL: FROM Employee WHERE department = 'IT'

        // ── CRITERIA — Like your project!! ──
        List<Employee> filtered = dao.getEmployeesByCriteria("IT", 50000);
        // Dynamic query — department = IT AND salary > 50000

        // ── UPDATE ──
        emp.setSalary(80000);
        dao.updateEmployee(emp);
        // SQL: UPDATE employees SET emp_salary = 80000 WHERE emp_id = 1

        // ── DELETE ──
        dao.deleteEmployee(1);
        // SQL: DELETE FROM employees WHERE emp_id = 1

        // ── NATIVE SQL ──
        List<Employee> highSalary = dao.getHighSalaryEmployees(70000);
        // SQL: SELECT * FROM employees WHERE emp_salary > 70000
    }
}


// ─────────────────────────────────────────────
// QUICK REFERENCE — Session Methods
// ─────────────────────────────────────────────
/*
SESSION METHODS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

session.save(obj)              → INSERT
session.get(Class, id)         → SELECT by ID
session.update(obj)            → UPDATE
session.delete(obj)            → DELETE
session.saveOrUpdate(obj)      → INSERT or UPDATE

session.createQuery(hql)       → HQL query (uses class names)
session.createNativeQuery(sql) → Raw SQL query
session.createCriteria(Class)  → Dynamic query builder

TRANSACTION:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
tx.commit()    → save changes to DB
tx.rollback()  → undo changes on error!!

HQL vs SQL:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HQL: "FROM Employee WHERE department = :dept"   (class name!!)
SQL: "SELECT * FROM employees WHERE dept = ?"   (table name!!)
*/
