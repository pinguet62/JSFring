insert into LANGUAGE (CODE, NOM) values ('fr', 'Français');
insert into LANGUAGE (CODE, NOM) values ('en', 'English');

insert into KEYWORD (TITLE) values ('Programmation');
insert into DESCRIPTION (KEYWORD, LANGUAGE, CONTENT) values (1, 1, 'C''est la vie.')

insert into KEYWORD (TITLE) values ('Java');
insert into DESCRIPTION (KEYWORD, LANGUAGE, CONTENT) values (2, 1, 'Le meilleur langage de programmation !')
insert into DESCRIPTION (KEYWORD, LANGUAGE, CONTENT) values (2, 2, 'The best programmation language!')
insert into SEE_ALSO (KEYWORD, ASSOCIATED_KEYWORD) values (2, 1);

insert into `RIGHT` (CODE, TITLE) values ('PROFILE_EDIT', 'Edition des profils');
insert into `RIGHT` (CODE, TITLE) values ('USER_SHOW', 'Affichage des utilisateurs');
insert into `RIGHT` (CODE, TITLE) values ('USER_EDIT', 'Edition des utilisateurs');

insert into PROFILE (ID, TITLE) values (1, 'Profile admin'); /* Administration de profils & Association Profil/Droit */
insert into PROFILE (ID, TITLE) values (2, 'User admin'); /* Administration d'utilisateurs & Association Utilisateur/Profil */