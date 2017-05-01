# Liquibase

## Run

First, for new database install, the script `database-create.sql` must be executed.

Then, the *liquibase* process can be executed.

### Manually

```
liquibase
    --changeLogFile=changelog-master.yaml
    --url=
    --username=
    --password=
    update
```

### Maven

```
mvn liquibase:update
```

## Changelogs

## Main changelog

The `changelog-master.yaml` is the main changelog, who `include` all `changelogs/**` files.

This file is **auto-generated** by the Python script `changelogs-listing.py`.

### New update

The changelog file is placed into `./changelogs/` folder.

It must follow the current naming convension: `XX-NAME.yaml`, where:
* `XX` is an **incremental** number (used to *orderer* the changelogs);
* `NAME` is a short name of changelog;
* `yaml` is the extension.
