
CREATE SCHEMA business_invite;

DROP TABLE IF EXISTS business_invite.eatco2_business_invite;

CREATE TABLE business_invite.eatco2_business_invite (
    INVITE_ID BIGINT NOT NULL AUTO_INCREMENT,
    USER_ID BIGINT NOT NULL,  
    INVITER_BUSINESS_NAME varchar(150) NOT NULL,
    INVITER_BUSINESS_CATEGORY_NAME varchar(50) NOT NULL,
    INVITER_EMAIL_ADDRESS varchar(75) NOT NULL,
    INVITED_BUSINESS_NAME varchar(150),
    INVITEE_EMAIL_ADDRESS varchar(75) NOT NULL,   
    INVITEE_BUSINESS_CATEGORY_NAME varchar(50), 
    CREATED_DATE DATE NOT NULL,
    MODIFIED_DATE DATE,
    STATUS varchar(30) NOT NULL,
    PRIMARY KEY (INVITE_ID)
);

create table hibernate_sequence(
    next_val INTEGER NOT null
);

 INSERT INTO my_sql_rds.hibernate_sequence (next_val) VALUES (10000);
 
 
 