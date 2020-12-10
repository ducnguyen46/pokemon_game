CREATE DATABASE pikachu;
use pikachu;
#DROP DATABASE pikachu;

CREATE TABLE user (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Score DOUBLE NOT NULL DEFAULT 0,
    State INT DEFAULT 0
);

CREATE TABLE game (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    TimeFinish TIME NOT NULL
);

CREATE TABLE game_detail (
    IDGame INT,
    IDPlayer INT,
    Point DOUBLE,
    
    FOREIGN KEY (IDGame) REFERENCES game (ID),
    FOREIGN KEY (IDPlayer) REFERENCES user (ID)
);
INSERT INTO user VALUES
('1', 'Đạt', 'deng126', '123456', 0, 1),
('2', 'Đức', 'ducnguyen46', '654321', 0, 1),
('3', 'Sự', 'susmurf', '123456', 0, 1),
('5', 'Minh', 'mnh', '111111', 0, 2);
SELECT username, score, state FROM user WHERE state != 0 and username != 'deng126';
SELECT username, score, state FROM user WHERE state != 0 AND username != ?;
-- INSERT INTO user VALUES
-- ('5', 'Minh', 'mnh', '111111', 0, 2)