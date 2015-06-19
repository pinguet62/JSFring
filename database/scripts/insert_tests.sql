set schema 'public';

delete from SEE_ALSO;
delete from DESCRIPTION;
delete from KEYWORD;
delete from LANGUAGE;

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

insert into PROFILE (ID, TITLE) values (1, 'Profile admin'); /* Administration de profils & Association Profil/Droit */
insert into PROFILES_RIGHTS (ID, CODE) values (1, 'RIGHT_RO');
insert into PROFILES_RIGHTS (ID, CODE) values (1, 'PROFILE_RO');
insert into PROFILES_RIGHTS (ID, CODE) values (1, 'PROFILE_RW');

insert into PROFILE (ID, TITLE) values (2, 'User admin'); /* Administration d'utilisateurs & Association Utilisateur/Profil */
insert into PROFILES_RIGHTS (ID, CODE) values (2, 'PROFILE_RO');
insert into PROFILES_RIGHTS (ID, CODE) values (2, 'USER_RO');
insert into PROFILES_RIGHTS (ID, CODE) values (2, 'USER_RW');

insert into "USER" (LOGIN, PASSWORD, EMAIL) values ('super admin', 'password', 'super.admin@email.org');
insert into USERS_PROFILES (LOGIN, ID) values ('super admin', 1);
insert into USERS_PROFILES (LOGIN, ID) values ('super admin', 2);

insert into "USER" (LOGIN, PASSWORD, EMAIL) values ('admin profile', 'password', 'admin.profile@email.org');
insert into USERS_PROFILES (LOGIN, ID) values ('admin profile', 1);

insert into "USER" (LOGIN, PASSWORD, EMAIL) values ('admin user', 'password', 'admin.user@email.org');
insert into USERS_PROFILES (LOGIN, ID) values ('admin user', 2);


insert into LANGUAGE (CODE, NOM) values ('fr', 'Français');
insert into LANGUAGE (CODE, NOM) values ('en', 'English');

insert into KEYWORD (TITLE) values ('Programmation');
insert into DESCRIPTION (KEYWORD, LANGUAGE, CONTENT) values (1, 1, 'C''est la vie.')

insert into KEYWORD (TITLE) values ('Java');
insert into DESCRIPTION (KEYWORD, LANGUAGE, CONTENT) values (2, 1, 'Le meilleur langage de programmation !')
insert into DESCRIPTION (KEYWORD, LANGUAGE, CONTENT) values (2, 2, 'The best programmation language!')
insert into SEE_ALSO (KEYWORD, ASSOCIATED_KEYWORD) values (2, 1);
