/*==============================================================*/
/* Nom de SGBD :  Microsoft SQL Server 2008                     */
/* Date de création :  14/06/2014 19:48:07                      */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DESCRIPTION') and o.name = 'FK_DESCRIPTION_KEYWORD')
alter table DESCRIPTION
   drop constraint FK_DESCRIPTION_KEYWORD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('DESCRIPTION') and o.name = 'FK_DESCRIPTION_LANGAGE')
alter table DESCRIPTION
   drop constraint FK_DESCRIPTION_LANGAGE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('KEYWORD') and o.name = 'FK_KEYWORD_DESCRIPTION')
alter table KEYWORD
   drop constraint FK_KEYWORD_DESCRIPTION
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('SEE_ALSO') and o.name = 'FK_SEE_ALSO_ASSOCIATED_KEYWORD')
alter table SEE_ALSO
   drop constraint FK_SEE_ALSO_ASSOCIATED_KEYWORD
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('SEE_ALSO') and o.name = 'FK_SEE_ALSO_KEYWORD')
alter table SEE_ALSO
   drop constraint FK_SEE_ALSO_KEYWORD
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DESCRIPTION')
            and   type = 'U')
   drop table DESCRIPTION
go

if exists (select 1
            from  sysobjects
           where  id = object_id('KEYWORD')
            and   type = 'U')
   drop table KEYWORD
go

if exists (select 1
            from  sysobjects
           where  id = object_id('LANGUAGE')
            and   type = 'U')
   drop table LANGUAGE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SEE_ALSO')
            and   type = 'U')
   drop table SEE_ALSO
go

/*==============================================================*/
/* Table : DESCRIPTION                                          */
/*==============================================================*/
create table DESCRIPTION (
   ID                   int                  not null,
   KEYWORD              int                  null,
   LANGUAGE             char(2)              not null,
   CONTENT              varchar(999)         not null,
   constraint PK_DESCRIPTION primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table : KEYWORD                                              */
/*==============================================================*/
create table KEYWORD (
   ID                   int                  not null,
   TITLE                varchar(99)          not null,
   DESCRIPTION          int                  not null,
   constraint PK_KEYWORD primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table : LANGUAGE                                             */
/*==============================================================*/
create table LANGUAGE (
   CODE                 char(2)              not null,
   NOM                  varchar(31)          not null,
   constraint PK_LANGUAGE primary key nonclustered (CODE)
)
go

/*==============================================================*/
/* Table : SEE_ALSO                                             */
/*==============================================================*/
create table SEE_ALSO (
   KEYWORD              int                  not null,
   ASSOCIATED_KEYWORD   int                  not null,
   constraint PK_SEE_ALSO primary key nonclustered (KEYWORD, ASSOCIATED_KEYWORD)
)
go

alter table DESCRIPTION
   add constraint FK_DESCRIPTION_KEYWORD foreign key (KEYWORD)
      references KEYWORD (ID)
go

alter table DESCRIPTION
   add constraint FK_DESCRIPTION_LANGAGE foreign key (LANGUAGE)
      references LANGUAGE (CODE)
go

alter table KEYWORD
   add constraint FK_KEYWORD_DESCRIPTION foreign key (DESCRIPTION)
      references DESCRIPTION (ID)
go

alter table SEE_ALSO
   add constraint FK_SEE_ALSO_ASSOCIATED_KEYWORD foreign key (ASSOCIATED_KEYWORD)
      references KEYWORD (ID)
go

alter table SEE_ALSO
   add constraint FK_SEE_ALSO_KEYWORD foreign key (KEYWORD)
      references KEYWORD (ID)
go

