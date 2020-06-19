drop database jeopardy;
create database jeopardy;
USE `jeopardy`;

create table `template` (
	`templateName` varchar(50) NOT NULL,
    primary key(`templateName`)
);

create table `category` (
	`categoryId` int NOT NULL auto_increment,
    `categoryName` varchar(50) NOT NULL,
    `templateName` varchar(50) NOT NULL,
    primary key(`categoryId`),
    FOREIGN KEY (`templateName`) REFERENCES Template(`templateName`)
);

create table `field` (
	`fieldId` int NOT NULL auto_increment,
    `categoryId` int NOT NULL,
    `rowNumber` int not null,
    primary key(`fieldId`),
    FOREIGN KEY (`categoryId`) REFERENCES category(`categoryId`)
);

create table `question` (
	`questionId` int not null auto_increment,
	`fieldId` int NOT NULL,
    `statement` varchar(200) not null,
    `answer` varchar(50) not null,
    primary key(`questionId`),
    FOREIGN KEY (`fieldId`) REFERENCES `field`(`fieldId`)
    );
    
    create table `game` (
	`gameId` int not null auto_increment,
    `templateName` varchar(50) NOT NULL,
     primary key(`gameId`),
    FOREIGN KEY (`templateName`) REFERENCES template(`templateName`)
    );
    
create table `player` (
	`playerId` int not null auto_increment,
    `playerName` varchar(50) not null,
    `gameId` int not null,
	`points` int not null,
    primary key(`playerId`),
    FOREIGN KEY (`gameId`) REFERENCES game(`gameId`)
    );

    
create table `answeredQuestions` (
	`anweredQuestionsId` int not null auto_increment,
	`gameId` int not null,
    `questionId` int not null,
	primary key(`anweredQuestionsId`),
    FOREIGN KEY (`gameId`) REFERENCES game(`gameId`),
    FOREIGN KEY (`questionId`) REFERENCES question(`questionId`)
    );
    
    