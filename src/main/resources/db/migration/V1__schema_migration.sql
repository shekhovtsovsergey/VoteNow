drop table if exists PERSONS;

CREATE TABLE PERSONS
(
    ID             BIGSERIAL        PRIMARY KEY,
    NAME           VARCHAR(255)     NOT NULL,
    DOCUMENT       VARCHAR(255)     NOT NULL,
    VOTE_TYPE      VARCHAR(255)     NOT NULL
);