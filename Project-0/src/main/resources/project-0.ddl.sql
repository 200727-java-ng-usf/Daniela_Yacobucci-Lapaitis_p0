CREATE USER project_0_app
WITH password 'project-0';

grant all privileges
on database postgres
to project_0_app;

create table user_roles(

	role_id serial,
	name 	varchar (25) not null,
	
	constraint user_roles_pk
	primary key (role_id)
	
);

create table app_users(

	user_id 	serial,
	first_name 	varchar(25) not null,
	middle_name varchar (50),
	last_name 	varchar(50) not null,
	username 	varchar (25) unique not null,
	password 	varchar (256) not null,
	email 		varchar(256) not null,
	role_id 	int not null,
	
	constraint app_users_pk
	primary key (user_id),
	
	constraint role_id_fk
	foreign key (role_id)
	references user_roles
	
);

create table accounts(

	account_number 	serial,
	balance 		money not null,
	
	constraint accounts_pk
	primary key (account_number)
);

alter table accounts alter column balance set default 0.0;

create table app_users__accounts_jt(
	
	user_id int,
	account_number int,
	
	constraint user_id_fk
	foreign key (user_id)
	references app_users,
	
	constraint account_number_fk
	foreign key (account_number)
	references accounts
		
);