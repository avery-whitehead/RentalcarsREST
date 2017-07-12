# RentalcarsREST
Extension of Rentalcars code to allow for output through REST via Glassfish

Glassfish 4.1.2 web profile needed

https://javaee.github.io/glassfish/download

From `glassfish4\bin` folder:

Start domain with `asadmin start-domain domain1`

Deploy WAR file with `asadmin deploy <path\to\war\file\RentalcarsREST_war.war`

Output can be viewed at:

http://localhost:8080/RentalcarsREST_war/rentalcars/price
http://localhost:8080/RentalcarsREST_war/rentalcars/sipp
http://localhost:8080/RentalcarsREST_war/rentalcars/supplier
http://localhost:8080/RentalcarsREST_war/rentalcars/score

Stop domain with `asadmin stop-domain`
