DROP DATABASE IF EXISTS pikachu;
CREATE DATABASE pikachu;
use pikachu;


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
('1', 'Đạt', 'deng126', '123456', 0, 0),
('2', 'Đức', 'ducnguyen46', '654321', 0, 0),
('3', 'Nam', 'abcdef', 'zxc123', 0, 0),
('4', 'Sự', 'smurf', '111222', 0, 0),
('5', 'Minh', 'mnh', '111111', 0, 0);
INSERT INTO game VALUES
('1','00:01:30'),
('2','00:02:30'),
('3','00:02:00'),
('4','00:03:00'),
('5','00:05:00'),
('6','00:05:00'),
('7','00:04:00');
INSERT INTO game_detail VALUES
('1','1','1'),
('1','3','0'),
('2','1','1'),
('2','4','0'),
('3','1','0'),
('3','2','1'),
('4','2','1'),
('4','5','0'),
('5','4','0.5'),
('5','5','0.5'),
('6','1','0.5'),
('6','2','0.5'),
('7','4','0'),
('7','5','1');

SELECT username, score, state FROM user WHERE state != 0 and username != 'deng126';
-- SELECT username, score, state FROM user WHERE state != 0 AND username != ?;
update user set score=2.5 where id=1;
update user set score=2.5 where id=2;
update user set score=0 where id=3;
update user set score=0.5 where id=4;
update user set score=1.5 where id=5;
update user set state=1 where id=1;
update user set state=1 where id=2;
update user set state=0 where id=3;
update user set state=2 where id=4;
update user set state=2 where id=5;
SELECT * FROM pikachu.user;
SELECT * FROM pikachu.game;
SELECT * FROM pikachu.game_detail;
SELECT username, score FROM user 
ORDER BY score DESC
;

SELECT username FROM user, game_detail 
WHERE game_detail.IDPlayer = user.ID
ORDER BY score DESC
;
SELECT a.username, AVG(b.SCORE) AS DTB FROM user a, user b WHERE a.ID = 3 AND b.ID IN (
	SELECT IDPlayer FROM game_detail
	WHERE IDGame IN (SELECT IDGame FROM game_detail WHERE IDPlayer = 3)
						AND IDPlayer != 3
	GROUP BY IDPlayer);
SELECT COUNT(*) FROM user;
SELECT * FROM pikachu.game;
SELECT * FROM pikachu.game_detail;    
SELECT time_to_sec(TimeFinish) FROM game;
SELECT username, SEC_TO_TIME(AVG(TIME_TO_SEC(TimeFinish))) AS TGTB
FROM user, game_detail, game
WHERE user.ID = game_detail.IDPlayer AND game_detail.IDGame = game.ID AND game_detail.Point = '1'
GROUP BY username
ORDER BY TGTB DESC;
