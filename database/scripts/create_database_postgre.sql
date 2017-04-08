/*==============================================================*/
/* Nom de SGBD :  PostgreSQL 8                                  */
/* Date de création :  20/08/2015 22:10:47                      */
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

drop table "right";

drop index SEE_ALSO2_FK;

drop index SEE_ALSO_FK;

drop index SEE_ALSO_PK;

drop table SEE_ALSO;

drop index USER_PK;

drop table "user";

drop index USERS_PROFILES2_FK;

drop index USERS_PROFILES_FK;

drop index USERS_PROFILES_PK;

drop table USERS_PROFILES;

/*==============================================================*/
/* Table : DESCRIPTION                                          */
/*==============================================================*/
create table DESCRIPTION (
   ID                   INT4                 not null,
   KEYWORD              INT4                 null,
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
KEYWORD
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
   ID                   SERIAL               not null,
   TITLE                VARCHAR(30)          not null,
   constraint PK_PROFILE primary key (ID)
);

comment on table PROFILE is
'A profile is a set of rights.
It describes all authorizations of a functionality for a user.
For example: a "user admin" will have all rights associated to users: show, update, profile association, ...';

comment on column PROFILE.ID is
'A technical ID.';

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
   PROFILE              INT4                 not null,
   "right"              VARCHAR(30)          not null,
   constraint PK_PROFILES_RIGHTS primary key (PROFILE, "right")
);

comment on table PROFILES_RIGHTS is
'Association of Profile/Right.';

comment on column PROFILES_RIGHTS.PROFILE is
'A technical ID.';

comment on column PROFILES_RIGHTS."right" is
'The key used in application to identify a restricted functionality.';

/*==============================================================*/
/* Index : PROFILES_RIGHTS_PK                                   */
/*==============================================================*/
create unique index PROFILES_RIGHTS_PK on PROFILES_RIGHTS (
PROFILE,
"right"
);

/*==============================================================*/
/* Index : PROFILES_RIGHTS_FK                                   */
/*==============================================================*/
create  index PROFILES_RIGHTS_FK on PROFILES_RIGHTS (
PROFILE
);

/*==============================================================*/
/* Index : PROFILES_RIGHTS2_FK                                  */
/*==============================================================*/
create  index PROFILES_RIGHTS2_FK on PROFILES_RIGHTS (
"right"
);

/*==============================================================*/
/* Table : "right"                                              */
/*==============================================================*/
create table "right" (
   CODE                 VARCHAR(30)          not null,
   TITLE                VARCHAR(50)          not null,
   constraint PK_RIGHT primary key (CODE)
);

comment on column "right".CODE is
'The key used in application to identify a restricted functionality.';

comment on column "right".TITLE is
'Description of the right.';

/*==============================================================*/
/* Index : RIGHT_PK                                             */
/*==============================================================*/
create unique index RIGHT_PK on "right" (
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
/* Table : "user"                                               */
/*==============================================================*/
create table "user" (
   LOGIN                VARCHAR(30)          not null,
   PASSWORD             VARCHAR(30)          not null,
   EMAIL                VARCHAR(255)         not null,
   LAST_CONNECTION      TIMESTAMP            not null,
   ACTIVE               BOOL                 not null default true,
   constraint PK_USER primary key (LOGIN),
   constraint AK_EMAIL_USER unique (EMAIL)
);

comment on table "user" is
'Informations about a user.';

comment on column "user".LOGIN is
'Used for webapp login.';

comment on column "user".PASSWORD is
'Used for webapp login.';

comment on column "user".EMAIL is
'Used to send the new password to used, when he forgot it.';

comment on column "user".LAST_CONNECTION is
'Used to know if the account is yet used.
Updated after each login.';

/*==============================================================*/
/* Index : USER_PK                                              */
/*==============================================================*/
create unique index USER_PK on "user" (
LOGIN
);

/*==============================================================*/
/* Table : USERS_PROFILES                                       */
/*==============================================================*/
create table USERS_PROFILES (
   "user"               VARCHAR(30)          not null,
   PROFILE              INT4                 not null,
   constraint PK_USERS_PROFILES primary key ("user", PROFILE)
);

comment on table USERS_PROFILES is
'Association of User/Profile.';

comment on column USERS_PROFILES."user" is
'Used for webapp login.';

comment on column USERS_PROFILES.PROFILE is
'A technical ID.';

/*==============================================================*/
/* Index : USERS_PROFILES_PK                                    */
/*==============================================================*/
create unique index USERS_PROFILES_PK on USERS_PROFILES (
"user",
PROFILE
);

/*==============================================================*/
/* Index : USERS_PROFILES_FK                                    */
/*==============================================================*/
create  index USERS_PROFILES_FK on USERS_PROFILES (
"user"
);

/*==============================================================*/
/* Index : USERS_PROFILES2_FK                                   */
/*==============================================================*/
create  index USERS_PROFILES2_FK on USERS_PROFILES (
PROFILE
);

alter table DESCRIPTION
   add constraint FK_DESCRIPT_DESCRIPTI_LANGUAGE foreign key (CODE)
      references LANGUAGE (CODE)
      on delete restrict on update restrict;

alter table DESCRIPTION
   add constraint FK_DESCRIPT_KEYWORD_H_KEYWORD foreign key (KEYWORD)
      references KEYWORD (ID)
      on delete restrict on update restrict;

alter table PROFILES_RIGHTS
   add constraint FK_PROFILES_PROFILES__PROFILE foreign key (PROFILE)
      references PROFILE (ID)
      on delete restrict on update restrict;

alter table PROFILES_RIGHTS
   add constraint FK_PROFILES_PROFILES__RIGHT foreign key ("right")
      references "right" (CODE)
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
   add constraint FK_USERS_PR_USERS_PRO_USER foreign key ("user")
      references "user" (LOGIN)
      on delete restrict on update restrict;

alter table USERS_PROFILES
   add constraint FK_USERS_PR_USERS_PRO_PROFILE foreign key (PROFILE)
      references PROFILE (ID)
      on delete restrict on update restrict;

