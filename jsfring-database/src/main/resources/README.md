# Liquibase

## Changelogs

### New update

The changelog file is placed into `./changelogs/` folder.

### Naming

New file must follow the current naming convension: `XX-NAME.yaml`, where:
* `XX` is an **incremental** number (used to *orderer* the changelogs);
* `NAME` is a short name of changelog;
* `yaml` is the extension.

## Main changelog

The `changelog-master.yaml` is the main changelog, who `include` all `changelogs/**` files.

The Python script `changelogs-listing.py` generate this file.

## Run

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
