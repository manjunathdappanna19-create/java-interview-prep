// ============================================================
// SPRING BOOT EXAMPLES
// All important annotations with examples
// ============================================================


// ─────────────────────────────────────────────
// 1. MAIN CLASS
// ─────────────────────────────────────────────
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // @Configuration + @EnableAutoConfiguration + @ComponentScan
public class EmployeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }
}


// ─────────────────────────────────────────────
// 2. ENTITY CLASS
// ─────────────────────────────────────────────
import javax.persistence.*;

@Entity                         // maps to DB table!!
@Table(name = "employees")      // table name!!
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "emp_name")
    private String name;

    @Column(name = "emp_department")
    private String department;

    @Column(name = "emp_salary")
    private double salary;

    // Constructors, Getters, Setters
    public Employee() { }

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}


// ─────────────────────────────────────────────
// 3. REPOSITORY — Database layer
// ─────────────────────────────────────────────
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Spring auto generates SQL for these!!
    List<Employee> findByDepartment(String department);
    List<Employee> findBySalaryGreaterThan(double salary);
    Employee findByName(String name);
}


// ─────────────────────────────────────────────
// 4. CUSTOM EXCEPTION
// ─────────────────────────────────────────────
public class EmployeeException extends RuntimeException {
    public EmployeeException(String message) {
        super(message);
    }
}


// ─────────────────────────────────────────────
// 5. SERVICE — Business Logic
// ─────────────────────────────────────────────
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired                          // inject repository!!
    private EmployeeRepository employeeRepository;

    @PostConstruct                      // runs after bean created!!
    public void init() {
        System.out.println("EmployeeService initialized!!");
    }

    @PreDestroy                         // runs before bean destroyed!!
    public void cleanup() {
        System.out.println("EmployeeService destroyed!!");
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get by ID
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeException("Employee not found: " + id));
    }

    // Save employee
    @Transactional                      // rollback on failure!!
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Update employee
    @Transactional
    public Employee updateEmployee(int id, Employee employee) {
        Employee existing = getEmployeeById(id);
        existing.setName(employee.getName());
        existing.setSalary(employee.getSalary());
        existing.setDepartment(employee.getDepartment());
        return employeeRepository.save(existing);
    }

    // Delete employee
    @Transactional
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    // Get by department
    public List<Employee> getByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }
}


// ─────────────────────────────────────────────
// 6. CONTROLLER — REST API layer
// ─────────────────────────────────────────────
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import java.util.List;

@RestController                         // handles REST requests!!
@RequestMapping("/api/employees")       // base URL!!
public class EmployeeController {

    @Autowired                          // inject service!!
    private EmployeeService employeeService;

    @Value("${app.name}")               // reads from application.properties!!
    private String appName;

    // GET all employees
    // URL: GET /api/employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // GET employee by ID
    // URL: GET /api/employees/1
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    // GET employees by department
    // URL: GET /api/employees?department=IT
    @GetMapping("/search")
    public List<Employee> getByDepartment(@RequestParam String department) {
        return employeeService.getByDepartment(department);
    }

    // POST create employee
    // URL: POST /api/employees
    // Body: {"name": "Manjunath", "department": "IT", "salary": 75000}
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    // PUT update employee
    // URL: PUT /api/employees/1
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable int id,
                                   @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    // DELETE employee
    // URL: DELETE /api/employees/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully!!");
    }
}


// ─────────────────────────────────────────────
// 7. GLOBAL EXCEPTION HANDLER
// ─────────────────────────────────────────────
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice                       // handles exceptions globally!!
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<String> handleEmployeeException(EmployeeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointer(NullPointerException e) {
        return new ResponseEntity<>("Null value error!!", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception e) {
        return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


// ─────────────────────────────────────────────
// 8. APPLICATION PROPERTIES
// ─────────────────────────────────────────────
/*
# application.properties

# Server
server.port=8080
app.name=Employee Management System

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/employeedb
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.format-sql=true
*/


// ─────────────────────────────────────────────
// QUICK REFERENCE — Spring Boot Annotations
// ─────────────────────────────────────────────
/*
CLASS LEVEL ANNOTATIONS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
@SpringBootApplication  → Main class
@RestController         → REST API controller
@Service                → Business logic
@Repository             → Database layer
@Component              → Generic bean
@Configuration          → Config class
@ControllerAdvice       → Global exception handler
@Entity                 → DB table mapping

METHOD LEVEL ANNOTATIONS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
@GetMapping             → GET request (SELECT)
@PostMapping            → POST request (INSERT)
@PutMapping             → PUT request (UPDATE)
@DeleteMapping          → DELETE request (DELETE)
@ExceptionHandler       → Handle specific exception
@PostConstruct          → Runs after bean created
@PreDestroy             → Runs before bean destroyed
@Transactional          → DB transaction management
@Bean                   → Manual bean definition

PARAMETER ANNOTATIONS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
@PathVariable           → From URL path /employees/1
@RequestParam           → From query string ?dept=IT
@RequestBody            → From request body (JSON)
@Autowired              → Inject dependency
@Value                  → Read from properties file

REQUEST FLOW:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Client → @RestController → @Service → @Repository → DB → Response
*/
