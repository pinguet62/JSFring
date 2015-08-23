set schema 'public';


delete from USERS_PROFILES;
delete from PROFILES_RIGHTS;
delete from "USER";
delete from PROFILE;
delete from "RIGHT";


insert into "RIGHT" (CODE, TITLE) values ('RIGHT_RO', 'Affichage des droits');
insert into "RIGHT" (CODE, TITLE) values ('PROFILE_RO', 'Affichage des profils');
insert into "RIGHT" (CODE, TITLE) values ('PROFILE_RW', 'Edition des profils');
insert into "RIGHT" (CODE, TITLE) values ('USER_RO', 'Affichage des utilisateurs');
insert into "RIGHT" (CODE, TITLE) values ('USER_RW', 'Edition des utilisateurs');


/* Administration de profils & Association Profil/Droit */
insert into PROFILE (ID, TITLE) values (DEFAULT/*1*/, 'Profile admin');

insert into PROFILES_RIGHTS (PROFILE, "RIGHT") values ((select id from PROFILE where title = 'Profile admin'), 'RIGHT_RO');
insert into PROFILES_RIGHTS (PROFILE, "RIGHT") values ((select id from PROFILE where title = 'Profile admin'), 'PROFILE_RO');
insert into PROFILES_RIGHTS (PROFILE, "RIGHT") values ((select id from PROFILE where title = 'Profile admin'), 'PROFILE_RW');

/* Administration d'utilisateurs & Association Utilisateur/Profil */
insert into PROFILE (ID, TITLE) values (DEFAULT/*2*/, 'User admin');

insert into PROFILES_RIGHTS (PROFILE, "RIGHT") values ((select id from PROFILE where title = 'User admin'), 'PROFILE_RO');
insert into PROFILES_RIGHTS (PROFILE, "RIGHT") values ((select id from PROFILE where title = 'User admin'), 'USER_RO');
insert into PROFILES_RIGHTS (PROFILE, "RIGHT") values ((select id from PROFILE where title = 'User admin'), 'USER_RW');


insert into "USER" (LOGIN, PASSWORD, EMAIL, ACTIVE) values ('super admin', 'password', 'super.admin@email.org', true);
insert into USERS_PROFILES ("USER", PROFILE) values ('super admin', (select id from PROFILE where title = 'Profile admin'));
insert into USERS_PROFILES ("USER", PROFILE) values ('super admin', (select id from PROFILE where title = 'User admin'));

insert into "USER" (LOGIN, PASSWORD, EMAIL, ACTIVE) values ('admin profile', 'password', 'admin.profile@email.org', true);
insert into USERS_PROFILES ("USER", PROFILE) values ('admin profile', (select id from PROFILE where title = 'Profile admin'));

insert into "USER" (LOGIN, PASSWORD, EMAIL, ACTIVE) values ('admin user', 'password', 'admin.user@email.org', true);
insert into USERS_PROFILES ("USER", PROFILE) values ('admin user', (select id from PROFILE where title = 'User admin'));
