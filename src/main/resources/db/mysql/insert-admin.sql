insert into User(`name`, `password`) values ('admin', 'admin');
insert into UserGroup(`name`) values ('管理员');
insert into UserPriviledge(`name`) values ('all');
insert into UserWithGroup(`userId`, `groupId`) values (1, 1);
insert into GroupWithPriviledge(`groupId`, `priviledgeId`) values (1, 1);
