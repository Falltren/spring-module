INSERT INTO departments (id, name)
VALUES (100, 'First'),
       (101, 'Second');

INSERT INTO employees (id, first_name, last_name, position, salary, department_id)
VALUES (100, 'John', 'Doe', 'engineer', '300', 100),
       (101, 'Tom', 'Smith', 'lead engineer', '600', 100),
       (102, 'Jack', 'Sullivan', 'engineer', '400', 100),
       (103, 'Samantha', 'Collins', 'support', '250', 101),
       (104, 'Daniel', 'Crosby', 'manager', '450', 101);