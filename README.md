# EXAMPLE PROJECT TO SHOW THE CLEANING APPROACH

ALL ENDPOINTS ARE DESCRIBED IN [USE-CASES.md](USE-CASES.md) FILE

## REFACTORS:

0. INITIAL VERSION ([branch v0](https://github.com/Klukov/example-clinic/tree/v0-initialVersion))- application is split
   between DTO, Domain and DAO (DB)
1. PACKAGE SCOPE ([commit](https://github.com/Klukov/example-clinic/commit/4fe9c00106b5b71378dd3ab660caff38f870f7a9)) -
   thanks to [pawel-b](https://github.com/pawel-b) for split
2. USE CASES - todo
3. HEXAGON - todo

## RUN APP LOCALLY:

**POSTGRES:** <br />
sudo docker run --name clinic-postgresql -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test123 -p 5432:5432 -d postgres:
13.7

## Next Steps:

1. Configure Spring Exception Handlers
2. Add and configure checkstyle
3. Add validation to patient registration - finish USE CASES 1.3
4. Finish visit confirmation - USE CASE 1.7
5. Add example usage to USE CASE 1.4
6. Implement USE CASE 1.5
7. Implement USE CASE 1.6
8. Implement USE CASE 1.8
9. Decompose SMS and EMAIL visit notification

## Infrastructure TODO:

1. ADD FLYWAY DEPENDENCY
2. ADD INITIAL MIGRATION SCRIPT
3. ADD SPRING SECURITY
