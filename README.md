EXAMPLE PROJECT TO SHOW CLEANING APPROACH

ALL ENDPOINTS ARE DESCRIBED IN [USE-CASES.md](USE-CASES.md) FILE

**RUN POSTGRES FOR APP:** <br />
sudo docker run --name clinic-postgresql -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test123 -p 5432:5432 -d postgres:
13.7

TODO:

1. FINISH E2E TEST FOR BASIC FUNCTIONALITY
2. ADD FLYWAY DEPENDENCY
3. ADD INITIAL MIGRATION SCRIPT
4. ADD SPRING SECURITY

<br />

REFACTORS:

0. INITIAL VERSION ([branch v0](https://github.com/Klukov/example-clinic/tree/v0-initialVersion))- application is split
   between DTO, Domain and DAO (DB)
1. PACKAGE SCOPE ([commit](https://github.com/Klukov/example-clinic/commit/4fe9c00106b5b71378dd3ab660caff38f870f7a9)) -
   thanks to [pawel-b](https://github.com/pawel-b) for split
2. USE CASES - todo
3. HEXAGON - todo
