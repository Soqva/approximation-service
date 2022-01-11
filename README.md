# Approximation Service

(Web application) Approximation service that uses Lagrangian and Cubic Spline methods for approximation.

Tools used
- Jakarta Servlets
- Java 16
- JSP (JSTL)
- Lombok
- Maven
- MXParser
- Tomcat 10

## Key Features 

- Data for approximation is entered by the user
- The table of values of arguments and functions is created automatically in accordance with the entered borders and the number of parts 
- A message about invalid data appears when the user enters invalid data or doesn't enter anything
- A scroll bar for arguments appears when the user enters a large number of parts
- An absolute fault of calculations is written next to the result of the approximation method

## Data Input

There are named fields in which the user has to enter data for approximation.
- [double] left border - the first point for approximation
- [double] right border - the last point for approximation
- [int] number of parts - determines a number of points to approximate
- [double] interpolation point - the point at which we want to know a value of the function
- [string] approximated function - not used for approximation methods, but it is necessary to create points for approximation

![Alt Text](https://github.com/Soqva/approximation-service/blob/master/github/gifs/enter-data.gif)

## Data Validation

User data for approximation is validated by Validator that checks them for existence and correctness. If the user enters incorrect data
a message about it appears on the page.

![Alt Text](https://github.com/Soqva/approximation-service/blob/master/github/gifs/invalid-data-1.gif)
![Alt Text](https://github.com/Soqva/approximation-service/blob/master/github/gifs/invalid-data-2.gif)

## Result

To send data for approximation the user has to click the "Submit" button. When data is processed the user is taken to the result page. 
At this page they (user) can see the table of values, results and absolute fault of calculations.
To return to the first page just click the "Back" button.

![Alt Text](https://github.com/Soqva/approximation-service/blob/master/github/gifs/send-data.gif)

![Alt Text](https://github.com/Soqva/approximation-service/blob/master/github/gifs/return.gif)
