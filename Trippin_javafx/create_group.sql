DROP DATABASE IF EXISTS GroupData;
CREATE DATABASE GroupData;

USE GroupData;

CREATE TABLE User(
	userID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
	fname VARCHAR(20) NOT NULL,
    lname VARCHAR(20) NOT NULL,
    profileImg VARCHAR(100) NULL
);

INSERT INTO User(username, password, fname, lname)
	VALUES ('ttrojan', 'tommy', 'tommy', 'trojan'),
		   ('jmiller', 'jmiller', 'Jeffrey', 'Miller');

CREATE TABLE Trip(
	tripID INT PRIMARY KEY AUTO_INCREMENT,
    tripName VARCHAR(80) NOT NULL,
    meetingTime TIME NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    ifPublic TINYINT(1) NOT NULL,
    commentTrip LONGTEXT
);

INSERT INTO Trip(tripName, ifPublic, startDate, endDate,meetingTime, commentTrip)
	VALUES ('World-class university tour', 1, '2017-06-15', ' 2017-06-30', '00:01', 'Trojan rules'),
		   ('Santa Barbara tour', 1, '2017-06-30', '2017-07-10', '23:59', ''),
           ('Salt Lake City', 1, '2018-03-10', '2017-03-20', '16:30', 'Spring Break!'),
           ('The Big Apple Tour', 1, '2017-12-20', '2017-12-29', '00:00', 'Christmas time!'),
           ('Bay Area Tour', 1, '2018-11-24', ' 2018-11-30', '12:00', 'Thanksgiving'),
           ('Canada Road Trip', 1, '2017-10-1', '2017-10-31', '8:00', 'trip to Canada');
           

CREATE TABLE Destination(
	destinationID INT PRIMARY KEY AUTO_INCREMENT,
    groupID INT NOT NULL,
    destinations VARCHAR(100) NOT NULL,
    des_order INT NOT NULL,
    lon DOUBLE NOT NULL,
    lat DOUBLE NOT NULL,
    FOREIGN KEY (groupID) REFERENCES Trip (tripID) ON DELETE CASCADE
);

INSERT INTO Destination(groupID, destinations, lon, lat, des_order)
		VALUES (1, 'USC', 34.054935, -118.2444759, 1),
			   (2, 'Santa Barbara', 34.4221319, -119.7026672, 1),
               (3, 'Salt Lake City', 40.7670126, -111.8904307, 1),
               (4, 'New York', 40.7305991, -73.9865811, 1),
               (5, 'San Francisco', 37.7792808, -122.4192362, 1),
               (6, 'Vancouver', 49.2608944, -123.1139382, 1);
               
               

CREATE TABLE Song(
	songQueueID INT PRIMARY KEY AUTO_INCREMENT,
    groupID INT NOT NULL,
    song VARCHAR(30) NOT NULL,
    vote TINYINT NOT NULL,
    FOREIGN KEY (groupID) REFERENCES Trip (tripID) ON DELETE CASCADE
);

INSERT INTO Song(groupID, song, vote)
	VALUES (1, 'Bad & Boujee', 4);

CREATE TABLE Budget(
	budgetID INT PRIMARY KEY AUTO_INCREMENT,
    groupID INT NOT NULL,
    item VARCHAR(100) NOT NULL,
    amount INT NOT NULL,
    FOREIGN KEY (groupID) REFERENCES Trip (tripID) ON DELETE CASCADE
);

INSERT INTO Budget(groupID, item, amount)
	VALUES (1, 'food', 35);

CREATE TABLE UserGroup(
    tripID INT NOT NULL,
    userID INT NOT NULL,
    CONSTRAINT PK_UserGroup PRIMARY KEY
    (
        tripID,
        userID
    ),
    FOREIGN KEY fk1(tripID) REFERENCES Trip(tripID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY fk2(userID) REFERENCES User(UserID) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO UserGroup(tripID, userID)	
	VALUES (1, 2),
			(2, 2),
            (3, 1),
            (4, 1);
