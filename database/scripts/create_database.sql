/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de création :  22/08/2014 23:17:44                      */
/*==============================================================*/


drop table if exists DESCRIPTION;

drop table if exists KEYWORD;

drop table if exists LANGUAGE;

drop table if exists PROFILE;

drop table if exists PROFILES_RIGHTS;

drop table if exists `RIGHT`;

drop table if exists SEE_ALSO;

drop table if exists USER;

drop table if exists USERS_PROFILES;

/*==============================================================*/
/* Table : DESCRIPTION                                          */
/*==============================================================*/
create table DESCRIPTION
(
   ID                   int not null auto_increment,
   KEYWORD              int,
   LANGUAGE             char(2) not null,
   TITLE                varchar(50) not null,
   CONTENT              varchar(999) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table : KEYWORD                                              */
/*==============================================================*/
create table KEYWORD
(
   ID                   int not null auto_increment,
   primary key (ID)
);

/*==============================================================*/
/* Table : LANGUAGE                                             */
/*==============================================================*/
create table LANGUAGE
(
   CODE                 char(2) not null,
   NAME                 varchar(31) not null,
   primary key (CODE)
);

/*==============================================================*/
/* Table : PROFILE                                              */
/*==============================================================*/
create table PROFILE
(
   ID                   int not null auto_increment,
   TITLE                varchar(30) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table : PROFILES_RIGHTS                                      */
/*==============================================================*/
create table PROFILES_RIGHTS
(
   PROFILE              int not null,
   `RIGHT`                varchar(30) not null,
   primary key (PROFILE, `RIGHT`)
);

/*==============================================================*/
/* Table : RIGHT                                                */
/*==============================================================*/
create table `RIGHT`
(
   CODE                 varchar(30) not null,
   TITLE                varchar(50) not null,
   primary key (CODE)
);

/*==============================================================*/
/* Table : SEE_ALSO                                             */
/*==============================================================*/
create table SEE_ALSO
(
   KEYWORD              int not null,
   ASSOCIATED_KEYWORD   int not null,
   primary key (KEYWORD, ASSOCIATED_KEYWORD)
);

/*==============================================================*/
/* Table : USER                                                 */
/*==============================================================*/
create table USER
(
   LOGIN                varchar(30) not null,
   PASSWORD             varchar(30) not null,
   EMAIL                varchar(255) not null,
   primary key (LOGIN),
   key AK_EMAIL (EMAIL)
);

/*==============================================================*/
/* Table : USERS_PROFILES                                       */
/*==============================================================*/
create table USERS_PROFILES
(
   USER                 varchar(30) not null,
   PROFILE              int not null,
   primary key (USER, PROFILE)
);

alter table DESCRIPTION add constraint FK_DESCRIPTION_KEYWORD foreign key (KEYWORD)
      references KEYWORD (ID) on delete restrict on update restrict;

alter table DESCRIPTION add constraint FK_DESCRIPTION_LANGAGE foreign key (LANGUAGE)
      references LANGUAGE (CODE) on delete restrict on update restrict;

alter table PROFILES_RIGHTS add constraint FK_PROFILES_RIGHTS_PROFILE foreign key (PROFILE)
      references PROFILE (ID) on delete restrict on update restrict;

alter table PROFILES_RIGHTS add constraint FK_PROFILES_RIGHTS_RIGHT foreign key (`RIGHT`)
      references `RIGHT` (CODE) on delete restrict on update restrict;

alter table SEE_ALSO add constraint FK_SEE_ALSO_ASSOCIATED_KEYWORD foreign key (ASSOCIATED_KEYWORD)
      references KEYWORD (ID) on delete restrict on update restrict;

alter table SEE_ALSO add constraint FK_SEE_ALSO_KEYWORD foreign key (KEYWORD)
      references KEYWORD (ID) on delete restrict on update restrict;

alter table USERS_PROFILES add constraint FK_USERS_PROFILES_PROFILE foreign key (PROFILE)
      references PROFILE (ID) on delete restrict on update restrict;

alter table USERS_PROFILES add constraint FK_USERS_PROFILES_USER foreign key (USER)
      references USER (LOGIN) on delete restrict on update restrict;

