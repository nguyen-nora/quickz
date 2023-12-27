CREATE TABLE USERS (
  	USERNAME VARCHAR(50) UNIQUE NOT NULL,
  	PWD VARCHAR(50) NOT NULL,
	PRIMARY KEY (USERNAME)
);

/*
CREATE TABLE ADMINS (
	USERNAME VARCHAR(50) UNIQUE NOT NULL,
	PWD VARCHAR(50) NOT NULL,
	PRIMARY KEY (USERNAME)
);
*/

CREATE TABLE QUIZ (
	ID_QUIZ serial PRIMARY KEY,
	NAME text
);

CREATE TABLE QUIZITEM (
	ID_QUIZITEM serial PRIMARY KEY,
	ID_QUIZ serial,
	FOREIGN KEY (ID_QUIZ) REFERENCES QUIZ(ID_QUIZ),
	QUIZITEM jsonb
);

CREATE TABLE SCORE (
	USERNAME VARCHAR(50),
	FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME),
	ID_QUIZ serial, 
	FOREIGN KEY (ID_QUIZ) REFERENCES QUIZ(ID_QUIZ),
	SCORE int,
	PRIMARY KEY (USERNAME, ID_QUIZ)
);

INSERT INTO USERS(USERNAME, PWD)
VALUES ('admin', 'admin');

INSERT INTO QUIZ(NAME)
VALUES ('Test Quiz');

INSERT INTO QUIZITEM(ID_QUIZ, QUIZITEM)
VALUES (1, '{"question": "What is the capital of France?", "incorrect_answers": ["London", "Berlin"], "correct_answers": ["Paris"]}'
),(	1, '{"question": "What is the tallest mountain in the world?", "incorrect_answers": ["K2", "Kilimanjaro"], "correct_answers": ["Mount Everest"]}'
),(	1, '{"question": "What is the largest country in the world by area?", "incorrect_answers": ["Canada", "China"], "correct_answers": ["Russia"]}'
),(	1, '{"question": "What is the most populous country in the world?", "incorrect_answers": ["India", "United States"], "correct_answers": ["China"]}'
);

INSERT INTO SCORE(USERNAME, ID_QUIZ, SCORE)
VALUES ('admin', 1, 100);
