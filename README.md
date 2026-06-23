# Java Interview Preparation 🚀

A comprehensive collection of Java concepts, code examples, and practice questions prepared for Java Developer interviews.

---

## 📚 Topics Covered

### Core Java
- OOPs — Inheritance, Polymorphism, Encapsulation, Abstraction
- Java 8 — Lambda, Streams, Optional, Default Methods
- Collections — List, Set, Map with examples
- Exception Handling — Checked, Unchecked, Custom Exceptions
- String, StringBuilder, StringBuffer
- Access Modifiers, Static, Final
- Wrapper Classes, Autoboxing/Unboxing
- Design Patterns — Singleton, Factory, Observer
- SOLID Principles

### Spring Boot
- Annotations — @RestController, @Service, @Repository, @Autowired
- Request Flow — Controller → Service → Repository → DB
- Dependency Injection — Constructor, Setter, Field
- IoC Container — ApplicationContext, Bean Lifecycle
- Spring Security — JWT Authentication and Authorization
- Auto Configuration internals

### Hibernate
- Session methods — save(), get(), update(), delete()
- HQL — Hibernate Query Language
- Criteria API — Dynamic queries
- Relationships — @OneToOne, @OneToMany, @ManyToOne, @ManyToMany
- Lazy vs Eager Loading

### Microservices
- Monolith vs Microservices
- Service Decomposition
- API Gateway
- Eureka — Service Discovery
- Feign Client — Inter-service communication
- Kafka — Producer, Consumer, Topics, Partitions

### SQL
- CRUD — SELECT, INSERT, UPDATE, DELETE
- Joins — INNER, LEFT, RIGHT
- GROUP BY, HAVING vs WHERE
- Subqueries
- DISTINCT, BETWEEN, IN, IS NULL, EXISTS
- Stored Procedures

### Testing
- JUnit — @Test, @BeforeEach, @AfterEach
- Mockito — @Mock, @InjectMocks, when().thenReturn()
- AAA Pattern — Arrange, Act, Assert

---

## 📁 Project Structure

```
java-interview-prep/
├── src/
│   ├── hibernate/
│   │   ├── HibernateExample.java
│   │   └── HibernateRelationships.java
│   ├── streams/
│   │   └── StreamExamples.java
│   ├── collections/
│   │   └── CollectionsExamples.java
│   ├── oops/
│   │   └── OOPSExamples.java
│   ├── springboot/
│   │   └── EmployeeProject/
│   └── test/
│       └── EmployeeServiceTest.java
├── sql/
│   └── queries.sql
└── README.md
```

---

## 🔑 Key Concepts Quick Reference

### Java 8 — Remember LSODF
| Letter | Feature |
|---|---|
| L | Lambda |
| S | Streams |
| O | Optional |
| D | Default Methods |
| F | Functional Interface |

### Collections
| Collection | Duplicates | Order |
|---|---|---|
| List | ✅ Allowed | ✅ Maintained |
| Set | ❌ Not allowed | ❌ Not maintained |
| Map | Keys unique | ❌ Not maintained |

### SOLID Principles
| Letter | Principle | One liner |
|---|---|---|
| S | Single Responsibility | One class one job |
| O | Open/Closed | Extend don't modify |
| L | Liskov Substitution | Child replaces parent |
| I | Interface Segregation | Don't force unnecessary methods |
| D | Dependency Inversion | Depend on interfaces |

### Design Patterns
| Pattern | One liner |
|---|---|
| Singleton | Only ONE instance — Spring beans!! |
| Factory | Let factory create objects — ApplicationContext!! |
| Observer | State changes — all dependents notified!! |

### Spring Boot Request Flow
```
Client Request
     ↓
@RestController
     ↓
@Service
     ↓
@Repository
     ↓
Database
     ↓
Response (JSON)
```

### Hibernate Session Methods
| Method | SQL |
|---|---|
| save() | INSERT |
| get() | SELECT |
| update() | UPDATE |
| delete() | DELETE |
| createQuery() | HQL |
| createCriteria() | Dynamic query |

---

## 💡 Interview Tips

1. Always relate answers to real project experience
2. Use **AAA pattern** for test cases — Arrange, Act, Assert
3. Honest about what you know and don't know
4. Connect Design Patterns to Spring Boot usage
5. Remember — SOLID is theory, @Autowired implements Dependency Inversion!!

---

## 🛠️ Technologies Used

- Java 8/11
- Spring Boot
- Hibernate/JPA
- MySQL
- JUnit 5
- Mockito
- Apache Kafka
- Git

---

## 👨‍💻 Author

**Manjunath**
Technology Analyst — Infosys Limited
5+ years experience in Java, Spring Boot, Microservices

---

*This repository is created for personal learning and interview preparation.*
