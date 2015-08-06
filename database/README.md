# SQL scripts

## Heroku

Heroku use PostgreSQL database.

### Execute script

Execute file in command line:
`psql -h server -p port -d database -U username -f pathToScript`
and enter the password.

For the actual database:
`psql -h ec2-184-73-165-195.compute-1.amazonaws.com -p 5432 -d d54utom7qlqe15 -U lveshvmnnklrze -f create_database_postgre.sql`

## Schema

To set default **schema** used to the current script, add this line at beginning of script:
`set schema 'public';`

## Reserved keyword & Case

The **reserved keywords** need to be surrounded by **double-quotes**.
For example : table `"USER"` or `"right"`

PowerAMC generates *admin script* with upper-case names.
Created objects (tables and columns) have name in lower-case, except name surrounded by quotes.

So for reserved keyword, JPA annotations values need respect the case.
For example : `@JoinColumn(name = "\"USER\"", nullable = false, updatable = false)` or `@Table(name = "\"right\"")`

## PostgreSQL

### Boolean type

The SQGB stores `t` and `f` values.
JPA doesn't work with this type.  
So the column must be a `char`.
