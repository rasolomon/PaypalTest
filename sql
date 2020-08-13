DROP TABLE IF EXISTS Address;
  
CREATE TABLE Address (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  employeeId INT NOT NULL,
  line1 VARCHAR(250) NOT NULL,
  line2 VARCHAR(250) NOT NULL,
  city VARCHAR(250) NOT NULL,
  state VARCHAR(250) NOT NULL,
  country VARCHAR(250) NOT NULL,
  zip_code VARCHAR(250) NOT NULL
);
DROP TABLE IF EXISTS Employee;
  
CREATE TABLE Employee (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  date_of_birth VARCHAR(250) NOT NULL
);

SELECT * FROM Employee e
JOIN Address a ON e.id = a.employeeId
WHERE e.id = 39

select * from employee;
select * from address;
delete from employee;
delete from address;
