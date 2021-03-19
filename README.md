# Webserver - Payroll System

Payroll System is a combination of few APIs which deals with CRUD operations of staff members as well few operations on their payment like Work Log , Amount calculations , Data in PDF format etc.

## Architectural Thoughts

Before I define the Prerequisites , I want to brief the architecture which I kept in my mind before preparing it. As per my experience I build it as Microservice architecture using Spring Boot.

I have follwed SOLID principle before designing it so that each layer does have their own responsibilities like as follows 

```
REST LAYER [End point for external systems] <---> SERVICE LAYER [RESPONSIBLE FOR BUSINESS LOGIC , AND PASS THROUGH DATA TO DAO LAYER] <--> DAO LAYER [RESPONSIBLE FOR DATA BASE OPERATIONS]

```

AS UI is not made for this I request to deploy this as per below instructions and test it via POST MAN or Advanced REST Client or Swagger UI.

## Database Design

Payroll System is an API based approch where we have to keep track for users data along with payment information.

As per the design please find below table approach which I kept in Database.

Table 1 : staff_members responsible for storing Staff Related data like name.

```
+---------------+--------------+------+-----+---------+----------------------------------------------------------------------+
| Field         | Type         | Null | Key | Default | Extra 												 				 |
+---------------+--------------+------+-----+---------+----------------------------------------------------------------------+
| staff_id      | int(11)      | NO   | PRI | NULL    |  Auto Incremented Unique Id for each member     	 				 |
| created_time  | datetime(6)  | YES  |     | NULL    |  Creation time for a Staff Memeber in DB for Audit purpose        	 |
| modified_time | datetime(6)  | YES  |     | NULL    |  Modified time for a Staff Memeber in DB for Audit purpose        	 |
| name          | varchar(255) | NO   |     | NULL    |  Name of the member							    	 				 |
| payroll_type  | varchar(8)   | NO   |     | NULL    |  payroll can be of type fixed salary or hourly salary				 |	
+---------------+--------------+------+-----+---------+----------------------------------------------------------------------+

```
Table 2 : staff_payroll_info responsible for storing Payroll Related data like work log , amount etc.

```
+------------------+--------------+------+-----+-------------------+------------------------------------------------------------+
| Field            | Type         | Null | Key | Default           | Extra                       								|
+------------------+--------------+------+-----+-------------------+------------------------------------------------------------+
| id               | int(11)      | NO   | PRI | NULL              |  Auto Incremented Unique Id for each entry                 |
| create_date      | timestamp    | NO   |     | CURRENT_TIMESTAMP |  Creation time for a Staff Memeber in DB for Audit purpose	|
| entry_date       | date         | YES  |     | NULL              |  Modified time for a Staff Memeber in DB for Audit purpose |
| net_pay          | double       | YES  |     | NULL              |  Total amount need to pay to member according to their work|
| per_hour_wage    | double       | YES  |     | NULL              |  Hourly wage for member                           			|
| total_hours      | double       | YES  |     | NULL              |  Total working hour for member                           	|
| work_log         | varchar(255) | YES  |     | NULL              |  A Work Log description done by member                     |
| staff_members_id | int(11)      | YES  | MUL | NULL              |  Relationship with Staff Member                           	|
+------------------+--------------+------+-----+-------------------+------------------------------------------------------------+

```

### Assumptions

While designing APIs I tried to fulfill the scenarios given in Assignment PDF but still I kept some assumptions as briefed below.

```
Period considered as duration between From-To-End date i.e. User will pass Start date to End date for fulfilling data criteria for few APIs
From-TO-End dates are allowed in form of YYYY-MM-DD format only wherever applicable
Net pay for Hourly empolyees are calculated as hourly_wage*working_hours
```
#There should be one API to retrieve a json object with information about the money that should be paid out to an employee in a certain period.

```
For Above mentioned API I considered to provide DATA for all the employees for certain period

```
#The system should allow getting payrolls for an employee for a certain period

```
For Above mentioned API I considered to provide DATA for only an employee for certain period

```

### Prerequisites

For using APIs , seek below softwares to be available in system

```
JAVA [>=1.8]
MAVEN 3
REST CLIENT [IF CURL DOESN'T WORK]
INTERNET BROWSER [For accessing APIs / Swagger UI]
MYSQL
Eclipse [for code walkthrough]

```

Either you may allow to create schema by Application itself or Import Payroll System schema [available in ../resources/payroll_system.sql] in mysql using below command :

```
mysql > source ../resources/payroll_system.sql;

```

### Installing

Please see through below steps for system to be up and running

For Preparing WAR file

```
[code directory]>mvn clean install

```
As I am using Spring-boot it does have embedded Tomcat which we may run using below command :

```
[code directory]>mvn spring-boot:run

```
Please access at given URL for more operations : 

```
http://localhost:8080/api/staffMembers/{endpoint}

```

By Default it would be accessible on 8080 port which may be changed in application.properties by following value change :

```
[code directory]>src>main>resources>application.properties

server.port=[desired port no]

```

## Running the tests

To See the code Coverage I used the JACOCO library which needs below command to be run :

```
[code directory]>mvn clean verify

```
Above command would build the WAR file as well JACOCO index.html file which would give insight for Test coverage :

```
[code directory]/test/>mvn clean verify

```
Open following file in browser to see the code Coverage percentage :

```
file://[code directory]/target/site/jacoco/index.html

```

### API Docs

API docs are also embedded & available in form of SWAGGER OPEN API docs which may be accessible at below URL once Server is up and running :

```
http://localhost:{port-no}/swagger-ui/index.html

```

## Built With

* [Spring-boot](https://start.spring.io/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [MySQL](https://www.mysql.com/) - Database used
* [iText] (https://itextpdf.com/en) - PDF Generation

## Author

* **Pushpank Tugnawat**  - [Github](https://github.com/pushpanktugnawat)
