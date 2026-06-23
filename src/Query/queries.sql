-- ============================================================
-- SQL PRACTICE QUERIES
-- All important queries for Java Developer interviews
-- ============================================================


-- ─────────────────────────────────────────────
-- SAMPLE TABLES
-- ─────────────────────────────────────────────

CREATE TABLE departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(50)
);

CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    department VARCHAR(50),
    salary DOUBLE,
    dept_id INT,
    manager_id INT,
    FOREIGN KEY (dept_id) REFERENCES departments(id)
);

-- Sample data
INSERT INTO departments VALUES (1, 'IT'), (2, 'HR'), (3, 'Finance');
INSERT INTO employees VALUES
(1, 'Manjunath', 'IT', 75000, 1, NULL),
(2, 'Raj', 'IT', 60000, 1, 1),
(3, 'Anil', 'HR', 55000, 2, NULL),
(4, 'Suresh', 'HR', 50000, 2, 3),
(5, 'Kumar', 'Finance', 80000, 3, NULL);


-- ─────────────────────────────────────────────
-- BASIC CRUD
-- ─────────────────────────────────────────────

-- SELECT all
SELECT * FROM employees;

-- SELECT specific columns
SELECT name, salary FROM employees;

-- INSERT
INSERT INTO employees (name, department, salary) VALUES ('Ravi', 'IT', 65000);

-- UPDATE
UPDATE employees SET salary = 80000 WHERE id = 1;

-- DELETE
DELETE FROM employees WHERE id = 5;


-- ─────────────────────────────────────────────
-- WHERE CLAUSE
-- ─────────────────────────────────────────────

-- Simple condition
SELECT * FROM employees WHERE department = 'IT';

-- Multiple conditions
SELECT * FROM employees WHERE department = 'IT' AND salary > 60000;

-- OR condition
SELECT * FROM employees WHERE department = 'IT' OR department = 'HR';


-- ─────────────────────────────────────────────
-- ORDER BY
-- ─────────────────────────────────────────────

-- Ascending
SELECT * FROM employees ORDER BY salary ASC;

-- Descending
SELECT * FROM employees ORDER BY salary DESC;

-- Top 3 highest paid
SELECT * FROM employees ORDER BY salary DESC LIMIT 3;

-- Second highest salary
SELECT MAX(salary) FROM employees
WHERE salary < (SELECT MAX(salary) FROM employees);

-- Second highest using OFFSET
SELECT salary FROM employees ORDER BY salary DESC LIMIT 1 OFFSET 1;


-- ─────────────────────────────────────────────
-- AGGREGATE FUNCTIONS
-- ─────────────────────────────────────────────

SELECT COUNT(*) FROM employees;                    -- total count
SELECT MAX(salary) FROM employees;                  -- highest salary
SELECT MIN(salary) FROM employees;                  -- lowest salary
SELECT AVG(salary) FROM employees;                  -- average salary
SELECT SUM(salary) FROM employees;                  -- total salary


-- ─────────────────────────────────────────────
-- GROUP BY + HAVING
-- ─────────────────────────────────────────────

-- Count employees per department
SELECT department, COUNT(*) as emp_count
FROM employees
GROUP BY department;

-- Departments with more than 1 employee
SELECT department, COUNT(*) as emp_count
FROM employees
GROUP BY department
HAVING COUNT(*) > 1;

-- Average salary per department
SELECT department, AVG(salary) as avg_salary
FROM employees
GROUP BY department;

-- Departments with avg salary > 60000
SELECT department, AVG(salary) as avg_salary
FROM employees
GROUP BY department
HAVING AVG(salary) > 60000;

-- WHERE vs HAVING
-- WHERE filters BEFORE grouping!!
SELECT department, COUNT(*)
FROM employees
WHERE salary > 50000        -- filter rows first!!
GROUP BY department
HAVING COUNT(*) > 1;        -- then filter groups!!


-- ─────────────────────────────────────────────
-- JOINS
-- ─────────────────────────────────────────────

-- INNER JOIN — only matching records
SELECT e.name, d.dept_name
FROM employees e
INNER JOIN departments d ON e.dept_id = d.id;

-- LEFT JOIN — all employees + matching departments
SELECT e.name, d.dept_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.id;

-- RIGHT JOIN — all departments + matching employees
SELECT e.name, d.dept_name
FROM employees e
RIGHT JOIN departments d ON e.dept_id = d.id;

-- JOIN with condition
SELECT e.name, d.dept_name
FROM employees e
JOIN departments d ON e.dept_id = d.id
WHERE d.dept_name = 'IT';

-- JOIN with GROUP BY
SELECT d.dept_name, COUNT(e.id) as emp_count
FROM employees e
JOIN departments d ON e.dept_id = d.id
GROUP BY d.dept_name
HAVING COUNT(e.id) > 1;


-- ─────────────────────────────────────────────
-- SUBQUERIES
-- ─────────────────────────────────────────────

-- Employees earning more than average
SELECT * FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);

-- Highest salary in each department
SELECT name, department, salary
FROM employees
WHERE (department, salary) IN (
    SELECT department, MAX(salary)
    FROM employees
    GROUP BY department
);

-- Employees in IT department using subquery
SELECT name FROM employees
WHERE dept_id = (SELECT id FROM departments WHERE dept_name = 'IT');


-- ─────────────────────────────────────────────
-- DISTINCT, BETWEEN, IN, LIKE
-- ─────────────────────────────────────────────

-- DISTINCT — unique values
SELECT DISTINCT department FROM employees;

-- BETWEEN — range
SELECT * FROM employees WHERE salary BETWEEN 50000 AND 75000;

-- IN — multiple values
SELECT * FROM employees WHERE department IN ('IT', 'HR');

-- NOT IN
SELECT * FROM employees WHERE department NOT IN ('Finance');

-- LIKE — pattern matching
SELECT * FROM employees WHERE name LIKE 'M%';      -- starts with M
SELECT * FROM employees WHERE name LIKE '%h';       -- ends with h
SELECT * FROM employees WHERE name LIKE '%nju%';    -- contains nju


-- ─────────────────────────────────────────────
-- NULL HANDLING
-- ─────────────────────────────────────────────

-- IS NULL
SELECT * FROM employees WHERE manager_id IS NULL;

-- IS NOT NULL
SELECT * FROM employees WHERE manager_id IS NOT NULL;

-- NEVER use = NULL!!
-- SELECT * FROM employees WHERE manager_id = NULL;  ❌ WRONG!!


-- ─────────────────────────────────────────────
-- EXISTS
-- ─────────────────────────────────────────────

-- Employees who have subordinates
SELECT e.name FROM employees e
WHERE EXISTS (
    SELECT 1 FROM employees e2
    WHERE e2.manager_id = e.id
);


-- ─────────────────────────────────────────────
-- UNION vs UNION ALL
-- ─────────────────────────────────────────────

-- UNION — removes duplicates
SELECT name FROM employees WHERE department = 'IT'
UNION
SELECT name FROM employees WHERE salary > 70000;

-- UNION ALL — keeps duplicates
SELECT name FROM employees WHERE department = 'IT'
UNION ALL
SELECT name FROM employees WHERE salary > 70000;


-- ─────────────────────────────────────────────
-- STORED PROCEDURE
-- ─────────────────────────────────────────────

-- Create stored procedure
DELIMITER //
CREATE PROCEDURE GetEmployeeByDept(IN dept_name VARCHAR(50))
BEGIN
    SELECT * FROM employees WHERE department = dept_name;
END //
DELIMITER ;

-- Call stored procedure
CALL GetEmployeeByDept('IT');

-- Stored procedure with OUT parameter
DELIMITER //
CREATE PROCEDURE GetEmployeeCount(OUT emp_count INT)
BEGIN
    SELECT COUNT(*) INTO emp_count FROM employees;
END //
DELIMITER ;

-- Call with OUT parameter
CALL GetEmployeeCount(@count);
SELECT @count;


-- ─────────────────────────────────────────────
-- QUICK REFERENCE
-- ─────────────────────────────────────────────

/*
DDL vs DML:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DDL (Data Definition Language)  → CREATE, ALTER, DROP
DML (Data Manipulation Language) → SELECT, INSERT, UPDATE, DELETE

ORDER OF CLAUSES:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
SELECT → FROM → JOIN → WHERE → GROUP BY → HAVING → ORDER BY → LIMIT

WHERE vs HAVING:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
WHERE  → filters rows BEFORE grouping
HAVING → filters groups AFTER GROUP BY

JOIN TYPES:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
INNER JOIN → only matching records
LEFT JOIN  → all left + matching right
RIGHT JOIN → all right + matching left
FULL JOIN  → all records from both

NULL RULES:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Use IS NULL / IS NOT NULL
NEVER use = NULL!!
*/
