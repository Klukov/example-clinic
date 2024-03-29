# EXAMPLE PROJECT TO SHOW THE CLEANING APPROACH

ALL ENDPOINTS ARE DESCRIBED IN [USE-CASES.md](USE-CASES.md) FILE

HOW TO RUN THE PROJECT IN [QUICK-START.md](QUICK-START.md) FILE

## Refactors:

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


## Functional next steps:

1. Configure Spring Exception Handlers
2. Add validation to patient registration - finish USE CASES 1.3
3. Finish visit confirmation - USE CASE 1.7
4. Add example usage to USE CASE 1.4
5. Implement USE CASE 1.5
6. Implement USE CASE 1.6
7. Implement USE CASE 1.8
8. Decompose SMS and EMAIL visit notification


## Infrastructure / technical next steps:

1. ~~ADD FLYWAY DEPENDENCY~~
2. ~~ADD INITIAL MIGRATION SCRIPT~~
3. ~~ADD AND CONFIGURE SPOTLESS~~
4. ADD SPRING SECURITY
