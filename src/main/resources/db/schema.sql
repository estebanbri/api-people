CREATE TABLE IF NOT EXISTS CONTACT_DATA (
    ID  NUMBER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CREATED_DATE DATETIME NOT NULL,
    MODIFIED_DATE DATETIME,
    CREATED_BY varchar(255),
    MODIFIED_BY varchar(255),
    PHONE_NUMBER varchar(255),
    EMAIL varchar(255)
);

CREATE TABLE IF NOT EXISTS PERSON (
    ID NUMBER NOT NULL AUTO_INCREMENT,
    CREATED_DATE DATETIME NOT NULL,
    MODIFIED_DATE DATETIME,
    CREATED_BY varchar(255),
    MODIFIED_BY varchar(255),
    DNI_TYPE varchar(255),
    COUNTRY varchar(255),
    FIRST_NAME varchar(255) NOT NULL,
    LAST_NAME varchar(255) NOT NULL,
    GENDER varchar(10) NOT NULL,
    BIRTH_DATE DATE NOT NULL,
    DNI NUMBER NOT NULL,
    CONTACT_DATA_ID NUMBER NOT NULL,
    PARENT_ID NUMBER,
    CONSTRAINT PK PRIMARY KEY (DNI_TYPE, COUNTRY, DNI, GENDER),
    FOREIGN KEY (CONTACT_DATA_ID) REFERENCES CONTACT_DATA(ID)
);

CREATE VIEW IF NOT EXISTS v_statistics AS
select
ROW_NUMBER() OVER(ORDER BY country ASC) AS id,f.*, m.*, a.*
from
(((select count(*) as FEMALE_QUANTITY_TOTAL from person where gender = 'FEMALE'))) f
join
(((select count(*) as MALE_QUANTITY_TOTAL from person where gender = 'MALE'))) m
join
(select COUNTRY, CAST((count(*) * 100.0 / (select count(country) from person)) AS int) as argentinian_percent
from person
group by COUNTRY) a

