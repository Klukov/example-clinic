# EXAMPLE PROJECT TO SHOW THE CLEANING APPROACH

ALL ENDPOINTS ARE DESCRIBED IN [USE-CASES.md](USE-CASES.md) FILE

## REFACTORS:

1. ~~INITIAL VERSION~~ ([branch v0](https://github.com/Klukov/example-clinic/tree/v0-initialVersion))- application is
   split
   between DTO, Domain and DAO (DB)
2. ~~PACKAGE SCOPE~~ 
   ([commit](https://github.com/Klukov/example-clinic/commit/4fe9c00106b5b71378dd3ab660caff38f870f7a9)) -
   thanks to [pawel-b](https://github.com/pawel-b) for split
3. ~~USE CASES~~ 
   ([pull-request](https://github.com/Klukov/example-clinic/pull/1) = 
   [commit-1](https://github.com/Klukov/example-clinic/commit/7d963b64d59ad90744f5feb718cef41d712d0547) + 
   [commit-2](https://github.com/Klukov/example-clinic/commit/02fc648478d6b58d64541361140ee2cd8a487336))
4. SEPARATED READ MODEL - todo
5. FULL HEXAGON - todo

## RUN APP LOCALLY:

**POSTGRES:** <br />
* create docker:  
  `sudo docker run --name clinic-postgresql -e POSTGRES_DB=example-clinic -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test123 -p 5432:5432 -d postgres:13.7`
* run psql in docker:  
  `docker exec -ti clinic-postgresql psql -U test -d example-clinic`

## Next Steps:
1. Configure Spring Exception Handlers
2. Add and configure checkstyle or spotless
3. Add validation to patient registration - finish USE CASES 1.3
4. Finish visit confirmation - USE CASE 1.7
5. Add example usage to USE CASE 1.4
6. Implement USE CASE 1.5
7. Implement USE CASE 1.6
8. Implement USE CASE 1.8
9. Decompose SMS and EMAIL visit notification

## Infrastructure TODO:
1. ADD LIQUIBASE DEPENDENCY
2. ADD INITIAL MIGRATION SCRIPT
3. ADD SPRING SECURITY
