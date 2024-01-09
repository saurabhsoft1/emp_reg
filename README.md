Employee Registartion service responsible to create user , do the authentication and authorize of user using Spring Security, and then user can access his profle do the 
GET,PUT,POST,DELETE operation of his profile. using JWT Token

1) POST http://localhost:8080/auth/addNewUser
     {
    "name": "vishal",
    "email": "vishal@gmail.com",
    "password": "vishal",
    "roles": "ROLE_USER"
     }
2) POST http://localhost:8080/auth/generateToken  // this API generate JWT Token
     {
    "username":"vishal",
    "password":"vishal"
     }

3) GET http://localhost:8080/auth/v1/getAllUsers
4) GET http://localhost:8080/auth/v1/getUserById/{userId}
5) PUT http://localhost:8080/auth/v1/updateUser/{userId}
    {
    "name": "vishal",
    "email": "vishal@gmail.com",
    "password": "vishal",
    "roles": "ROLE_USER"
    }
6) DELETE http://localhost:8080/auth/v1/deleteUser/{userId}

7) POST http://localhost:8080/auth/employees/register
   {
    "firstName": "vishal",
    "lastName": "rai",
    "address": "bangalore",
    "age": 31,
    "maritalStatus": "Single",
    "expectedSalary": 50000.0,
    "employmentDetails": [
       {
            "companyName": "tcs",
            "totalCTC": 2000000.0,
            "startDate": "2008-01-01",
            "endDate": "2025-02-01"
        },
       {
            "companyName": "oracle",
            "totalCTC": 1000000.0,
            "startDate": "2005-05-23",
            "endDate": "2007-03-23"
        },
        {
            "companyName": "accenture",
            "totalCTC": 5000000.0,
            "startDate": "2008-02-23",
            "endDate": "2016-01-23"
        }
   ]
 }   

 8) GET  http://localhost:8080/auth/employees/getallemployees
 9) GET http://localhost:8080/auth/employees/longest-employment/{employeeId}
 10) PUT http://localhost:8080/auth/employees/update/{employeeId}
        {
    "firstName": "vishal",
    "lastName": "rai",
    "address": "bangalore",
    "age": 31,
    "maritalStatus": "Single",
    "expectedSalary": 50000.0,
    "employmentDetails": [
       {
            "companyName": "tcs",
            "totalCTC": 2000000.0,
            "startDate": "2008-01-01",
            "endDate": "2025-02-01"
        },
       {
            "companyName": "oracle",
            "totalCTC": 1000000.0,
            "startDate": "2005-05-23",
            "endDate": "2007-03-23"
        },
        {
            "companyName": "accenture",
            "totalCTC": 5000000.0,
            "startDate": "2008-02-23",
            "endDate": "2016-01-23"
        }
   ]
 } 

 11) PUT http://localhost:8080/auth/employees/addemploymentdetails/{employeeId}
          {
           
            "companyName": "igate",
            "totalCTC": 1000000.0,
            "startDate": "2018-05-23",
            "endDate": "2019-03-23"
        }
  12) DELETE http://localhost:8080/auth/employees/employmentdetaildeletion/{employeeId}/{employmentId}
  13) http://localhost:8080/auth/employees/delete/{employeeId}
   
