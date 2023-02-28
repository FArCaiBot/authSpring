insert into rol(nombre)
values
('ROLE_ADMIN'),
('ROLE_USER');

insert into usuario
(nombre, username, email, password, account_verified)
values
('Farcai','admin','admin@gmail.com', '$2a$11$CisRI9z.Leah321H91HG3uAwNxAaDvTQMBz/j86R4/o/qn16uMUdG',false),
('Farcai User', 'user', 'user@gmail.com','$2a$11$30K2aGQ9Q4vPSCw6xnx6GeeLyyIE/eVN1ZkrH1Hy1or5JAT6MVt/y',false);

insert into usuarios_rol(usuario_id, rol_id)
values
(1,1),
(2,2);
