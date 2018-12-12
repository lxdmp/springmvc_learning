insert into User(`name`, `password`) values ('admin', 'admin');
insert into UserGroup(`name`) values ('管理员');
insert into UserWithGroup(`userId`, `groupId`) values (1, 1);
insert into UserPriviledge(`name`) values ('基本权限');
insert into GroupWithPriviledge(`groupId`, `priviledgeId`) values (1, 1);

--insert into UserPriviledge(`name`) values ('ADD_PRODUCT');
--insert into GroupWithPriviledge(`groupId`, `priviledgeId`) values (1, 2);
insert into UserPriviledge(`name`) values ('CUSTOM_FORMAT');
insert into GroupWithPriviledge(`groupId`, `priviledgeId`) values (1, 2);
