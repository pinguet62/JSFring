FROM webdevops/liquibase:postgres

COPY ./src/main/resources /opt/jsfring/database

ENV LIQUIBASE_CHANGELOG=/opt/jsfring/database/changelog-master.yaml
