INSERT INTO AUTHORITIES (authority,username) VALUES ('ROLE_ADMIN','admin');
INSERT INTO AUTHORITIES (authority,username) VALUES ('ROLE_USER','admin');
INSERT INTO USERS (username,enabled,password) VALUES ('admin',1,'{bcrypt}$2a$10$NMiV0EALT4KiTvfDVROYQO1e1NPfbbHEhRoQ218c/5jYqGSqEMbs.');
INSERT INTO ACCOUNT (id,create_at,update_at,account_number,address,avatar,birthday,email,first_name,last_name,gender,phone,username) VALUES ('11111111-1111-1111-1111-111111111111',CURRENT_TIMESTAMP,NULL,111111111,'Ha Noi',NULL,'1998-01-18','admin@gmail.com','Admin','Admin',1,'0355111616','admin');
INSERT INTO RECEIVE_ADDRESS (id,create_at,update_at,address,address_name,name,phone,is_default,account_id) VALUES ('11111111-1111-1111-1111-111111111111',CURRENT_TIMESTAMP,NULL,'Ha Noi','Dia chi 1','Anh Admin','0355111616',1,'11111111-1111-1111-1111-111111111111');