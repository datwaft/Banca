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

insert into `movement` (`origin`, `destination`, `amount`, `description`, `date`) values 
(null, 3, 10, 'movimiento 1', localtime()),
(3, null, 10, 'movimiento 2', localtime()),
(4, 3, 10, 'movimiento 3', localtime());

select * from `user`;
select * from `movement`;
SELECT * FROM `movement` obj WHERE obj.origin.id = :id OR obj.destination.id = :id