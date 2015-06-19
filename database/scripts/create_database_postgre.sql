set schema 'public';

/*==============================================================*/
/* Nom de SGBD :  PostgreSQL 8                                  */
/* Date de création :  18/06/2015 20:43:36                      */
/*==============================================================*/


drop index KEYWORD_HAS_DESCRIPTIONS_FK;

drop index DESCRIPTION_LANGUAGE_FK;

drop index DESCRIPTION_PK;

drop table DESCRIPTION;

drop index KEYWORD_PK;

drop table KEYWORD;

drop index LANGUAGE_PK;

drop table LANGUAGE;

drop index PROFILE_PK;

drop table PROFILE;

drop index PROFILES_RIGHTS2_FK;

drop index PROFILES_RIGHTS_FK;

drop index PROFILES_RIGHTS_PK;

drop table PROFILES_RIGHTS;

drop index RIGHT_PK;

drop table "RIGHT";

drop index SEE_ALSO2_FK;

drop index SEE_ALSO_FK;

drop index SEE_ALSO_PK;

drop table SEE_ALSO;

drop index USER_PK;

drop table "USER";

drop index USERS_PROFILES2_FK;

drop index USERS_PROFILES_FK;

drop index USERS_PROFILES_PK;

drop table USERS_PROFILES;

/*==============================================================*/
/* Table : DESCRIPTION                                          */
/*==============================================================*/
create table DESCRIPTION (
   ID                   INT4                 not null,
   KEY_ID               INT4                 null,
   CODE                 CHAR(2)              not null,
   TITLE                VARCHAR(50)          not null,
   CONTENT              VARCHAR(999)         not null,
   constraint PK_DESCRIPTION primary key (ID)
);

/*==============================================================*/
/* Index : DESCRIPTION_PK                                       */
/*==============================================================*/
create unique index DESCRIPTION_PK on DESCRIPTION (
ID
);

/*==============================================================*/
/* Index : DESCRIPTION_LANGUAGE_FK                              */
/*==============================================================*/
create  index DESCRIPTION_LANGUAGE_FK on DESCRIPTION (
CODE
);

/*==============================================================*/
/* Index : KEYWORD_HAS_DESCRIPTIONS_FK                          */
/*==============================================================*/
create  index KEYWORD_HAS_DESCRIPTIONS_FK on DESCRIPTION (
KEY_ID
);

/*==============================================================*/
/* Table : KEYWORD                                              */
/*==============================================================*/
create table KEYWORD (
   ID                   INT4                 not null,
   constraint PK_KEYWORD primary key (ID)
);

/*==============================================================*/
/* Index : KEYWORD_PK                                           */
/*==============================================================*/
create unique index KEYWORD_PK on KEYWORD (
ID
);

/*==============================================================*/
/* Table : LANGUAGE                                             */
/*==============================================================*/
create table LANGUAGE (
   CODE                 CHAR(2)              not null,
   NAME                 VARCHAR(31)          not null,
   constraint PK_LANGUAGE primary key (CODE)
);

/*==============================================================*/
/* Index : LANGUAGE_PK                                          */
/*==============================================================*/
create unique index LANGUAGE_PK on LANGUAGE (
CODE
);

/*==============================================================*/
/* Table : PROFILE                                              */
/*==============================================================*/
create table PROFILE (
   ID                   INT4                 not null,
   TITLE                VARCHAR(30)          not null,
   constraint PK_PROFILE primary key (ID)
);

/*==============================================================*/
/* Index : PROFILE_PK                                           */
/*==============================================================*/
create unique index PROFILE_PK on PROFILE (
ID
);

/*==============================================================*/
/* Table : PROFILES_RIGHTS                                      */
/*==============================================================*/
create table PROFILES_RIGHTS (
   ID                   INT4                 not null,
   CODE                 VARCHAR(30)          not null,
   constraint PK_PROFILES_RIGHTS primary key (ID, CODE)
);

/*==============================================================*/
/* Index : PROFILES_RIGHTS_PK                                   */
/*==============================================================*/
create unique index PROFILES_RIGHTS_PK on PROFILES_RIGHTS (
ID,
CODE
);

/*==============================================================*/
/* Index : PROFILES_RIGHTS_FK                                   */
/*==============================================================*/
create  index PROFILES_RIGHTS_FK on PROFILES_RIGHTS (
ID
);

/*==============================================================*/
/* Index : PROFILES_RIGHTS2_FK                                  */
/*==============================================================*/
create  index PROFILES_RIGHTS2_FK on PROFILES_RIGHTS (
CODE
);

/*==============================================================*/
/* Table : "RIGHT"                                              */
/*==============================================================*/
create table "RIGHT" (
   CODE                 VARCHAR(30)          not null,
   TITLE                VARCHAR(50)          not null,
   constraint PK_RIGHT primary key (CODE)
);

/*==============================================================*/
/* Index : RIGHT_PK                                             */
/*==============================================================*/
create unique index RIGHT_PK on "RIGHT" (
CODE
);

/*==============================================================*/
/* Table : SEE_ALSO                                             */
/*==============================================================*/
create table SEE_ALSO (
   KEY_ID               INT4                 not null,
   ID                   INT4                 not null,
   constraint PK_SEE_ALSO primary key (KEY_ID, ID)
);

/*==============================================================*/
/* Index : SEE_ALSO_PK                                          */
/*==============================================================*/
create unique index SEE_ALSO_PK on SEE_ALSO (
KEY_ID,
ID
);

/*==============================================================*/
/* Index : SEE_ALSO_FK                                          */
/*==============================================================*/
create  index SEE_ALSO_FK on SEE_ALSO (
KEY_ID
);

/*==============================================================*/
/* Index : SEE_ALSO2_FK                                         */
/*==============================================================*/
create  index SEE_ALSO2_FK on SEE_ALSO (
ID
);

/*==============================================================*/
/* Table : "USER"                                               */
/*==============================================================*/
create table "USER" (
   LOGIN                VARCHAR(30)          not null,
   PASSWORD             VARCHAR(30)          not null,
   EMAIL                VARCHAR(255)         not null,
   constraint PK_USER primary key (LOGIN),
   constraint AK_EMAIL_USER unique (EMAIL)
);

/*==============================================================*/
/* Index : USER_PK                                              */
/*==============================================================*/
create unique index USER_PK on "USER" (
LOGIN
);

/*==============================================================*/
/* Table : USERS_PROFILES                                       */
/*==============================================================*/
create table USERS_PROFILES (
   LOGIN                VARCHAR(30)          not null,
   ID                   INT4                 not null,
   constraint PK_USERS_PROFILES primary key (LOGIN, ID)
);

/*==============================================================*/
/* Index : USERS_PROFILES_PK                                    */
/*==============================================================*/
create unique index USERS_PROFILES_PK on USERS_PROFILES (
LOGIN,
ID
);

/*==============================================================*/
/* Index : USERS_PROFILES_FK                                    */
/*==============================================================*/
create  index USERS_PROFILES_FK on USERS_PROFILES (
LOGIN
);

/*==============================================================*/
/* Index : USERS_PROFILES2_FK                                   */
/*==============================================================*/
create  index USERS_PROFILES2_FK on USERS_PROFILES (
ID
);

alter table DESCRIPTION
   add constraint FK_DESCRIPT_DESCRIPTI_LANGUAGE foreign key (CODE)
      references LANGUAGE (CODE)
      on delete restrict on update restrict;

alter table DESCRIPTION
   add constraint FK_DESCRIPT_KEYWORD_H_KEYWORD foreign key (KEY_ID)
      references KEYWORD (ID)
      on delete restrict on update restrict;

alter table PROFILES_RIGHTS
   add constraint FK_PROFILES_PROFILES__PROFILE foreign key (ID)
      references PROFILE (ID)
      on delete restrict on update restrict;

alter table PROFILES_RIGHTS
   add constraint FK_PROFILES_PROFILES__RIGHT foreign key (CODE)
      references "RIGHT" (CODE)
      on delete restrict on update restrict;

alter table SEE_ALSO
   add constraint FK_SEE_ALSO_SEE_ALSO_KEYWORD foreign key (KEY_ID)
      references KEYWORD (ID)
      on delete restrict on update restrict;

alter table SEE_ALSO
   add constraint FK_SEE_ALSO_SEE_ALSO2_KEYWORD foreign key (ID)
      references KEYWORD (ID)
      on delete restrict on update restrict;

alter table USERS_PROFILES
   add constraint FK_USERS_PR_USERS_PRO_USER foreign key (LOGIN)
      references "USER" (LOGIN)
      on delete restrict on update restrict;

alter table USERS_PROFILES
   add constraint FK_USERS_PR_USERS_PRO_PROFILE foreign key (ID)
      references PROFILE (ID)
      on delete restrict on update restrict;

