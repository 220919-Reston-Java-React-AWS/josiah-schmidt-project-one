drop table if exists reimbursements;
drop table if exists users;
drop table if exists role;

create table role (
	id SERIAL primary key, 
	role_name VARCHAR(50)
);

create table users (
	id SERIAL primary key, 
	username VARCHAR(50) not null unique,
	password VARCHAR(50) not null,
	role_id INTEGER references role(id) not NULL
);

create table reimbursements (
	id SERIAL primary key,
	reimbursement_reason VARCHAR(50) not null,
	amount INTEGER check (amount >= 0),  -- try to use numeric, then switch the java to double
	status VARCHAR(20) default 'pending',
	employee_id INTEGER references users(id) not NULL,
	financial_Manager_id INTEGER references users(id)
);

insert into role (role_name)
values
('employee'),
('financial_manager');

insert into users (username, password, role_id)
values
('josiah_schmidt', 'pass12345', 2)
; 


select * from role;
select * from users;
select * from reimbursements;














