// ============================================================
// COMPLETE JUNIT + MOCKITO EXAMPLE
// Testing Employee Service
// ============================================================


// ─────────────────────────────────────────────
// 1. EMPLOYEE CLASS
// ─────────────────────────────────────────────
public class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee() { }

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
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
}


// ─────────────────────────────────────────────
// 2. CUSTOM EXCEPTION
// ─────────────────────────────────────────────
public class EmployeeException extends RuntimeException {
    public EmployeeException(String message) {
        super(message);
    }
}


// ─────────────────────────────────────────────
// 3. EMPLOYEE REPOSITORY — Interface
// ─────────────────────────────────────────────
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Employee save(Employee employee);
    Optional<Employee> findById(int id);
    List<Employee> findAll();
    List<Employee> findByDepartment(String department);
    void deleteById(int id);
    boolean existsById(int id);
}


// ─────────────────────────────────────────────
// 4. EMPLOYEE SERVICE — Business Logic
// This is what we will TEST!!
// ─────────────────────────────────────────────
import java.util.List;

public class EmployeeService {

    // dependency — we will MOCK this in tests!!
    private EmployeeRepository employeeRepository;

    // Constructor injection
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Get employee by ID
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> 
                    new EmployeeException("Employee not found with id: " + id));
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Save employee
    public Employee saveEmployee(Employee employee) {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new EmployeeException("Employee name cannot be empty!!");
        }
        return employeeRepository.save(employee);
    }

    // Get by department
    public List<Employee> getEmployeesByDepartment(String department) {
        if (department == null || department.isEmpty()) {
            throw new EmployeeException("Department cannot be empty!!");
        }
        return employeeRepository.findByDepartment(department);
    }

    // Delete employee
    public void deleteEmployee(int id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // Calculate bonus — 10% of salary
    public double calculateBonus(int id) {
        Employee employee = getEmployeeById(id);
        return employee.getSalary() * 0.10;
    }
}


// ─────────────────────────────────────────────
// 5. EMPLOYEE SERVICE TEST — JUnit + Mockito
// ─────────────────────────────────────────────
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // enables Mockito!!
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;  // MOCK — no real DB!!

    @InjectMocks
    EmployeeService employeeService;  // inject mock into service!!

    // Test data
    Employee emp1;
    Employee emp2;
    Employee emp3;

    // ─────────────────────────────────────────────
    // @BeforeEach — runs before EVERY test!!
    // Set up test data here!!
    // ─────────────────────────────────────────────
    @BeforeEach
    void setUp() {
        emp1 = new Employee(1, "Manjunath", "IT", 75000);
        emp2 = new Employee(2, "Raj", "HR", 60000);
        emp3 = new Employee(3, "Anil", "IT", 80000);
        System.out.println("Test setup done!!");
    }

    // ─────────────────────────────────────────────
    // @AfterEach — runs after EVERY test!!
    // ─────────────────────────────────────────────
    @AfterEach
    void tearDown() {
        System.out.println("Test completed!!");
    }


    // ══════════════════════════════════════════════
    // TEST 1 — getEmployeeById — SUCCESS scenario!!
    // ══════════════════════════════════════════════
    @Test
    void testGetEmployeeById_Success() {
        // ── ARRANGE — set up mock behaviour!!
        when(employeeRepository.findById(1))
            .thenReturn(Optional.of(emp1));  // mock returns emp1!!

        // ── ACT — call the method!!
        Employee result = employeeService.getEmployeeById(1);

        // ── ASSERT — verify result!!
        assertNotNull(result);                          // result should not be null!!
        assertEquals(1, result.getId());                // id should be 1!!
        assertEquals("Manjunath", result.getName());    // name should be Manjunath!!
        assertEquals("IT", result.getDepartment());     // department should be IT!!
        assertEquals(75000, result.getSalary());        // salary should be 75000!!

        // Verify repository was called once!!
        verify(employeeRepository, times(1)).findById(1);

        System.out.println("Test 1 passed — getEmployeeById success!!");
    }


    // ══════════════════════════════════════════════
    // TEST 2 — getEmployeeById — FAILURE scenario!!
    // Employee not found!!
    // ══════════════════════════════════════════════
    @Test
    void testGetEmployeeById_NotFound() {
        // ── ARRANGE — mock returns empty!!
        when(employeeRepository.findById(99))
            .thenReturn(Optional.empty());  // no employee found!!

        // ── ACT + ASSERT — expect exception!!
        EmployeeException exception = assertThrows(
            EmployeeException.class,
            () -> employeeService.getEmployeeById(99)  // should throw exception!!
        );

        // Verify exception message!!
        assertEquals("Employee not found with id: 99", exception.getMessage());

        System.out.println("Test 2 passed — employee not found!!");
    }


    // ══════════════════════════════════════════════
    // TEST 3 — getAllEmployees — SUCCESS
    // ══════════════════════════════════════════════
    @Test
    void testGetAllEmployees_Success() {
        // ── ARRANGE
        List<Employee> empList = Arrays.asList(emp1, emp2, emp3);
        when(employeeRepository.findAll())
            .thenReturn(empList);  // mock returns list!!

        // ── ACT
        List<Employee> result = employeeService.getAllEmployees();

        // ── ASSERT
        assertNotNull(result);
        assertEquals(3, result.size());           // should have 3 employees!!
        assertEquals("Manjunath", result.get(0).getName());
        assertEquals("Raj", result.get(1).getName());
        assertEquals("Anil", result.get(2).getName());

        verify(employeeRepository, times(1)).findAll();

        System.out.println("Test 3 passed — getAllEmployees success!!");
    }


    // ══════════════════════════════════════════════
    // TEST 4 — saveEmployee — SUCCESS
    // ══════════════════════════════════════════════
    @Test
    void testSaveEmployee_Success() {
        // ── ARRANGE
        Employee newEmp = new Employee(4, "Suresh", "Finance", 70000);
        when(employeeRepository.save(newEmp))
            .thenReturn(newEmp);  // mock returns saved employee!!

        // ── ACT
        Employee result = employeeService.saveEmployee(newEmp);

        // ── ASSERT
        assertNotNull(result);
        assertEquals("Suresh", result.getName());
        assertEquals("Finance", result.getDepartment());

        verify(employeeRepository, times(1)).save(newEmp);

        System.out.println("Test 4 passed — saveEmployee success!!");
    }


    // ══════════════════════════════════════════════
    // TEST 5 — saveEmployee — FAILURE
    // Empty name!!
    // ══════════════════════════════════════════════
    @Test
    void testSaveEmployee_EmptyName() {
        // ── ARRANGE — employee with empty name!!
        Employee invalidEmp = new Employee(5, "", "IT", 50000);

        // ── ACT + ASSERT — expect exception!!
        EmployeeException exception = assertThrows(
            EmployeeException.class,
            () -> employeeService.saveEmployee(invalidEmp)
        );

        assertEquals("Employee name cannot be empty!!", exception.getMessage());

        // Verify repository was NEVER called!!
        verify(employeeRepository, never()).save(any());

        System.out.println("Test 5 passed — empty name validation!!");
    }


    // ══════════════════════════════════════════════
    // TEST 6 — getEmployeesByDepartment — SUCCESS
    // ══════════════════════════════════════════════
    @Test
    void testGetEmployeesByDepartment_Success() {
        // ── ARRANGE
        List<Employee> itEmployees = Arrays.asList(emp1, emp3);
        when(employeeRepository.findByDepartment("IT"))
            .thenReturn(itEmployees);

        // ── ACT
        List<Employee> result = employeeService.getEmployeesByDepartment("IT");

        // ── ASSERT
        assertNotNull(result);
        assertEquals(2, result.size());  // 2 IT employees!!
        assertEquals("IT", result.get(0).getDepartment());
        assertEquals("IT", result.get(1).getDepartment());

        System.out.println("Test 6 passed — getByDepartment success!!");
    }


    // ══════════════════════════════════════════════
    // TEST 7 — deleteEmployee — SUCCESS
    // ══════════════════════════════════════════════
    @Test
    void testDeleteEmployee_Success() {
        // ── ARRANGE
        when(employeeRepository.existsById(1))
            .thenReturn(true);  // employee exists!!
        doNothing().when(employeeRepository).deleteById(1);  // delete does nothing!!

        // ── ACT
        employeeService.deleteEmployee(1);

        // ── ASSERT — verify delete was called!!
        verify(employeeRepository, times(1)).existsById(1);
        verify(employeeRepository, times(1)).deleteById(1);

        System.out.println("Test 7 passed — deleteEmployee success!!");
    }


    // ══════════════════════════════════════════════
    // TEST 8 — deleteEmployee — FAILURE
    // Employee not found!!
    // ══════════════════════════════════════════════
    @Test
    void testDeleteEmployee_NotFound() {
        // ── ARRANGE
        when(employeeRepository.existsById(99))
            .thenReturn(false);  // employee doesn't exist!!

        // ── ACT + ASSERT
        EmployeeException exception = assertThrows(
            EmployeeException.class,
            () -> employeeService.deleteEmployee(99)
        );

        assertEquals("Employee not found with id: 99", exception.getMessage());

        // Verify deleteById was NEVER called!!
        verify(employeeRepository, never()).deleteById(any());

        System.out.println("Test 8 passed — delete not found!!");
    }


    // ══════════════════════════════════════════════
    // TEST 9 — calculateBonus — SUCCESS
    // 10% of salary!!
    // ══════════════════════════════════════════════
    @Test
    void testCalculateBonus_Success() {
        // ── ARRANGE
        when(employeeRepository.findById(1))
            .thenReturn(Optional.of(emp1));  // salary = 75000

        // ── ACT
        double bonus = employeeService.calculateBonus(1);

        // ── ASSERT
        assertEquals(7500.0, bonus);  // 10% of 75000 = 7500!!

        System.out.println("Test 9 passed — calculateBonus success!!");
    }


    // ══════════════════════════════════════════════
    // TEST 10 — verify mock interactions!!
    // ══════════════════════════════════════════════
    @Test
    void testMockInteractions() {
        // ── ARRANGE
        when(employeeRepository.findById(1))
            .thenReturn(Optional.of(emp1));

        // ── ACT — call multiple times!!
        employeeService.getEmployeeById(1);
        employeeService.getEmployeeById(1);
        employeeService.getEmployeeById(1);

        // ── ASSERT — verify called exactly 3 times!!
        verify(employeeRepository, times(3)).findById(1);

        System.out.println("Test 10 passed — mock interactions verified!!");
    }
}


// ─────────────────────────────────────────────
// QUICK REFERENCE — JUnit + Mockito
// ─────────────────────────────────────────────
/*
JUNIT ANNOTATIONS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
@Test              → marks method as test case
@BeforeEach        → runs before each test
@AfterEach         → runs after each test
@BeforeAll         → runs once before all tests
@AfterAll          → runs once after all tests
@DisplayName       → custom test name

JUNIT ASSERTIONS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
assertEquals(expected, actual)     → check values equal
assertNotNull(object)              → check not null
assertNull(object)                 → check is null
assertTrue(condition)              → check true
assertFalse(condition)             → check false
assertThrows(Exception, lambda)    → check exception thrown

MOCKITO ANNOTATIONS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
@Mock              → create mock object
@InjectMocks       → inject mocks into class
@Spy               → partial mock
@Captor            → capture arguments

MOCKITO METHODS:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
when().thenReturn()     → define mock behaviour
when().thenThrow()      → mock throws exception
doNothing().when()      → mock void method
verify(mock, times(n))  → verify method called n times
verify(mock, never())   → verify method never called
any()                   → any argument matcher

AAA PATTERN — Always follow this!!
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Arrange  → set up mock data and behaviour
Act      → call the method being tested
Assert   → verify the result
*/
