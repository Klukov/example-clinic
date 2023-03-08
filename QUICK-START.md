# QUICK START

## Intellij
To be compatible with spotless you have to add google-java-format plugin.  
Please follow these steps: https://github.com/google/google-java-format
  
  
## Run app locally:

**POSTGRES:** <br />
* create docker:  
  `sudo docker run --name clinic-postgresql -e POSTGRES_DB=example-clinic -e POSTGRES_USER=test -e POSTGRES_PASSWORD=test123 -p 5432:5432 -d postgres:13.7`
* run psql in docker:  
  `docker exec -ti clinic-postgresql psql -U test -d example-clinic`