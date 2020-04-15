insert into `user` (`id`, `password`, `name`, `cellphone`, `cashier`, `client`) values
('user1', 'password', 'USER1', '11111111', 0, 1),
('user2', 'password', 'USER2', '22222222', 0, 1),
('dual', 'password', 'DUAL', '33333333', 1, 1),
('admin', 'password', 'ADMIN', '44444444', 1, 0);

insert into `currency` (`code`, `name`, `conversion`, `interest_rate`) values
('USD','Dólares', 1.0000, 0.30),
('CRC', 'Colones', 570.0790, 0.44),
('EUR', 'Euro', 0.8859, 0.20);

insert into `account` (`owner`, `currency`, `dailylimit`) values
('user1', 'EUR', 10000),
('user1', 'USD', 10000),
('user2', 'CRC', 2000000),
('user2', 'USD', 10000),
('dual', 'EUR', 20000),
('dual', 'CRC', 2000000);

insert into `link` (`owner`, `linked_account`) values
(1, 2),
(2, 1),
(3, 4),
(4, 3),
(5, 6),
(6, 5);

insert into `movement` (`origin`, `destination`, `amount`, `description`, `date`, `type`) values 
(null, 3, 5000, 'movimiento 1', '2020-01-27 00:00:00', 'A'),
(3, null, 5000, 'movimiento 2', '2020-02-09 00:00:00', 'A');

select * from `movement`;
select * from `account`;
select now();
select * from `user`;
select * from `link`;