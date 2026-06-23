// ============================================================
// COMPLETE HIBERNATE RELATIONSHIPS EXAMPLE
// @OneToOne, @OneToMany, @ManyToOne, @ManyToMany
// ============================================================


// ─────────────────────────────────────────────
// 1. ONE TO ONE — Employee has one Address
// ─────────────────────────────────────────────

// Address Entity
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "pincode")
    private String pincode;

    // Constructors
    public Address() { }
    public Address(String street, String city, String pincode) {
        this.street = street;
        this.city = city;
        this.pincode = pincode;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }
}


// Employee Entity — with ONE TO ONE relationship!!
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "emp_name")
    private String name;

    // ─── ONE TO ONE ───
    // One Employee has ONE Address!!
    @OneToOne(cascade = CascadeType.ALL)  // cascade — save employee = save address too!!
    @JoinColumn(name = "address_id")      // foreign key in employees table!!
    private Address address;

    // ─── MANY TO ONE ───
    // Many Employees belong to ONE Department!!
    @ManyToOne
    @JoinColumn(name = "dept_id")         // foreign key in employees table!!
    private Department department;

    // Constructors
    public Employee() { }
    public Employee(String name) {
        this.name = name;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}


// ─────────────────────────────────────────────
// 2. ONE TO MANY — Department has many Employees
//    MANY TO ONE — Many Employees to one Department
// ─────────────────────────────────────────────

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dept_name")
    private String name;

    // ─── ONE TO MANY ───
    // One Department has MANY Employees!!
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    // mappedBy = "department" — refers to field name in Employee class!!
    private List<Employee> employees = new ArrayList<>();

    // Constructors
    public Department() { }
    public Department(String name) {
        this.name = name;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    // Helper method — add employee to department!!
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }
}


// ─────────────────────────────────────────────
// 3. MANY TO MANY — Employee has many Projects
//                   Project has many Employees
// ─────────────────────────────────────────────

// Project Entity
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "project_name")
    private String name;

    // ─── MANY TO MANY ───
    // One Project has MANY Employees!!
    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees = new ArrayList<>();

    // Constructors
    public Project() { }
    public Project(String name) {
        this.name = name;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Employee> getEmployees() { return employees; }
}


// Updated Employee Entity — with MANY TO MANY!!
// Add this to Employee class above!!
/*
    // ─── MANY TO MANY ───
    // One Employee works on MANY Projects!!
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "employee_projects",        // junction/bridge table!!
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects = new ArrayList<>();
*/


// ─────────────────────────────────────────────
// 4. DEMO — How to use relationships
// ─────────────────────────────────────────────
public class RelationshipDemo {

    public static void main(String[] args) {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        try {

            // ── ONE TO ONE ──
            // Create address
            Address address = new Address("123 MG Road", "Bangalore", "560001");

            // Create employee with address
            Employee emp1 = new Employee("Manjunath");
            emp1.setAddress(address);  // one to one!!
            // Save employee — saves address too!! (cascade!!)
            session.save(emp1);
            /*
            SQL generated:
            INSERT INTO addresses (street, city, pincode) VALUES ('123 MG Road', 'Bangalore', '560001')
            INSERT INTO employees (emp_name, address_id) VALUES ('Manjunath', 1)
            */


            // ── ONE TO MANY / MANY TO ONE ──
            // Create department
            Department itDept = new Department("IT");

            // Create employees
            Employee emp2 = new Employee("Raj");
            Employee emp3 = new Employee("Anil");

            // Add employees to department!!
            itDept.addEmployee(emp2);  // sets dept in employee too!!
            itDept.addEmployee(emp3);

            // Save department — saves employees too!! (cascade!!)
            session.save(itDept);
            /*
            SQL generated:
            INSERT INTO departments (dept_name) VALUES ('IT')
            INSERT INTO employees (emp_name, dept_id) VALUES ('Raj', 1)
            INSERT INTO employees (emp_name, dept_id) VALUES ('Anil', 1)
            */


            // ── FETCH employees of a department ──
            Department dept = session.get(Department.class, 1);
            List<Employee> employees = dept.getEmployees();
            // returns all employees in IT department!!
            employees.forEach(e -> System.out.println(e.getName()));
            /*
            Output:
            Raj
            Anil
            */


            // ── FETCH department of an employee ──
            Employee employee = session.get(Employee.class, 1);
            Department empDept = employee.getDepartment();
            System.out.println(empDept.getName()); // IT


            // ── MANY TO MANY ──
            // Create projects
            Project project1 = new Project("Intermodal");
            Project project2 = new Project("BNSF");

            // Create employee
            Employee emp4 = new Employee("Suresh");
            emp4.getProjects().add(project1);  // employee works on project1
            emp4.getProjects().add(project2);  // employee works on project2

            session.save(emp4);
            /*
            SQL generated:
            INSERT INTO projects VALUES ('Intermodal')
            INSERT INTO projects VALUES ('BNSF')
            INSERT INTO employees VALUES ('Suresh')
            INSERT INTO employee_projects VALUES (1, 1)  -- emp1, project1
            INSERT INTO employee_projects VALUES (1, 2)  -- emp1, project2
            */

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }
}


// ─────────────────────────────────────────────
// QUICK REFERENCE — Relationships
// ─────────────────────────────────────────────
/*
RELATIONSHIP SUMMARY:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

@OneToOne
→ One Employee has ONE Address
→ @JoinColumn creates foreign key

@OneToMany
→ One Department has MANY Employees
→ mappedBy refers to field in child class

@ManyToOne
→ Many Employees belong to ONE Department
→ @JoinColumn creates foreign key

@ManyToMany
→ Many Employees work on Many Projects
→ @JoinTable creates junction/bridge table

CASCADE TYPES:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
CascadeType.ALL      → all operations cascade
CascadeType.PERSIST  → save cascades
CascadeType.MERGE    → update cascades
CascadeType.REMOVE   → delete cascades

REAL WORLD EXAMPLES:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
One to One   → Employee — Address
              Employee — Passport
One to Many  → Department — Employees
              Order — Items
Many to One  → Employees — Department
              Items — Order
Many to Many → Students — Courses
              Employees — Projects
*/
