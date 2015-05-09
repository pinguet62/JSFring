# SQL scripts

## Heroku

Heroku use PostgreSQL database.

### Execute script

Command line:
`psql -h server -p port -d database -U username -f pathToScript`

For current database:
`psql -h ec2-184-73-165-195.compute-1.amazonaws.com -p 5432 -d d54utom7qlqe15 -U lveshvmnnklrze -f create_database_postgre.sql`

### Schema

To set default **schema** used to the current script, add this line at begining of script:
`set schema 'dictionary';`
