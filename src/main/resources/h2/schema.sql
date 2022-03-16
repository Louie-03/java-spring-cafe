DROP TABLE ARTICLE IF EXISTS;
DROP TABLE USER IF EXISTS;

create table USER (
    USER_ID VARCHAR(100) not null primary key,
    PASSWORD VARCHAR(100) not null,
    NAME VARCHAR(100) not null,
    EMAIL VARCHAR(100)
);

create table ARTICLE (
    ID BIGINT not null AUTO_INCREMENT primary key,
    USER_ID VARCHAR(100),
    CONTENTS VARCHAR(1000),
    TITLE VARCHAR(100),
    CREATE_DATE DATETIME default CURRENT_TIMESTAMP,
    foreign key (USER_ID) references USER(USER_ID)
);
