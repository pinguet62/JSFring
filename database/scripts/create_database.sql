/*==============================================================*/
/* Nom de SGBD :  Microsoft SQL Server 2008                     */
/* Date de création :  19/06/2014 22:08:37                      */
/*==============================================================*/


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
   TITLE                varchar(50)          not null,
   CONTENT              varchar(999)         not null,
   constraint PK_DESCRIPTION primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table : KEYWORD                                              */
/*==============================================================*/
create table KEYWORD (
   ID                   int                  not null,
   constraint PK_KEYWORD primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table : LANGUAGE                                             */
/*==============================================================*/
create table LANGUAGE (
   CODE                 char(2)              not null,
   NAME                 varchar(31)          not null,
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

