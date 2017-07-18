Name: Trippin'
Class: CSCI 201
Team: 21
Date: 04/16/2017

================================================

Contributors(in alphabetical order of last name):

- Bei Deng(beideng) - beideng@usc.edu
- Stephanie Lee(lee970) - lee970@usc.edu
- Aarav Malpani(malpani) - malpani@usc.edu
- Taixiang Zeng(taixianz) - taixianz@usc.edu

-------------------------------------------------

-- PROGRAM FEATURES --





-- DEPLOYMENT INFORMATION --

Download and import Trippin.zip file into Eclipse, this
should generate a program called "Trippin" consisting of
ReadMe.txt, MYSQL script (create_group.sql) and other necessary
java codes of the project. The application package inside src 
contians implementation of the front-end while the server 
package contains those of back-end server logic.

In our code, we use one outside library, mysql-connector-java-5.1.40-bin.jar
(JDBC driver) to connect to MySQL database. Other than JDBC and Eclipse, 
We MapBox, Nominatim and Leaflet API to achieve map functionalities.
To create front-end of a higher quality and in a more straightfoward
fashion, we employ GLUON Scenebuilder JavaFX to generate the front-end GUI.

To start the program, a step by step instruction is given below: 

	-	Create a new connection in MySQL workbench and set both the 
		username and password to "root"
		
	-	Import the "create_group.sql" script into MySQL Workbench and
		run the script
		
	-	To run server for Trippin', run Server.java in src/server package
	
	-	Run the "Main.java" in src/application
	
To use the program, 
	
	-	In the start page, user who already has an account can click login and
		enter the credentials
		
	-	If the user doesn't have an account he or she can click "Create Account" and enter 
		necessary information to create an account
		
	-	Or if the user doesn't want to start a new account, click "continue as guest" to 
		view all the existing groups
		
		(an existing account is username: ttrojan and password: tommy)
		
	-	After logged in or created s new account, a page with all the existing
		trips in the world will show up. To view information of individual group
		select one in the left column "trips" and click the "go to Trip" button
		
		(connection failure to map api server is possible, if the map does show
		up right click on the blank space and click "Reload page"
	


