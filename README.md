# SafetyNet Alerts

SafetyNet Alerts is a webapp built to exchange informations with emergency services.

## Installation

Clone the project from github.

Execute maven goals clean, verify and install

If surefire plugin raises an error, modify the pom like so

```xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-surefire-plugin</artifactId>
	<version>3.0.0-M5</version>
	<configuration>
		<testFailureIgnore>true</testFailureIgnore>
	</configuration>
</plugin>
```

## Usage

Here are the endpoints

To obtain informations: 

```Http

http://localhost:8080/firestation?stationNumber=<station_number>

http://localhost:8080/childAlert?address=<address>

http://localhost:8080/phoneAlert?firestation=<firestation_number>

http://localhost:8080/fire?address=<address>

http://localhost:8080/flood/stations?stations=<a list of station_numbers>

http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>

http://localhost:8080/communityEmail?city=<city>

```

To edit/manipulate data used by the app: 

```Http
http://localhost:8080/person

http://localhost:8080/firestation

http://localhost:8080/medicalRecord

```

 
