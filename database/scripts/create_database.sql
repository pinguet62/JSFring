/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de création :  21/06/2014 13:08:33                      */
/*==============================================================*/


drop table if exists DESCRIPTION;

drop table if exists KEYWORD;

drop table if exists LANGUAGE;

drop table if exists SEE_ALSO;

/*==============================================================*/
/* Table : DESCRIPTION                                          */
/*==============================================================*/
create table DESCRIPTION
(
   ID                   int not null,
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
   ID                   int not null,
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
/* Table : SEE_ALSO                                             */
/*==============================================================*/
create table SEE_ALSO
(
   KEYWORD              int not null,
   ASSOCIATED_KEYWORD   int not null,
   primary key (KEYWORD, ASSOCIATED_KEYWORD)
);

alter table DESCRIPTION add constraint FK_DESCRIPTION_KEYWORD foreign key (KEYWORD)
      references KEYWORD (ID) on delete restrict on update restrict;

alter table DESCRIPTION add constraint FK_DESCRIPTION_LANGAGE foreign key (LANGUAGE)
      references LANGUAGE (CODE) on delete restrict on update restrict;

alter table SEE_ALSO add constraint FK_SEE_ALSO_ASSOCIATED_KEYWORD foreign key (ASSOCIATED_KEYWORD)
      references KEYWORD (ID) on delete restrict on update restrict;

alter table SEE_ALSO add constraint FK_SEE_ALSO_KEYWORD foreign key (KEYWORD)
      references KEYWORD (ID) on delete restrict on update restrict;

