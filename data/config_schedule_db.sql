DROP TABLE IF EXISTS Student;
CREATE TABLE Student(
    banner_id   INTEGER,
    first_name  VARCHAR(30),
    last_name   VARCHAR(30),
    class       VARCHAR(5),
    PRIMARY KEY(banner_id));

CREATE TABLE Professor(
    professor_id    INTEGER,
    first_name      VARCHAR(30),
    last_name       VARCHAR(30),
    PRIMARY KEY(professor_id));

CREATE TABLE Section(
    crn             INTEGER,
    term            VARCHAR(6),
    term_length     VARCHAR(30),
    class_subject   VARCHAR(10),
    class_number    VARCHAR(3),
    class_title     VARCHAR(70),
    class_suffix    VARCHAR(5),
    year            INTEGER,
    time_start      INTEGER,
    time_end        INTEGER,
	room_id			INTEGER,
    PRIMARY KEY(crn));

CREATE TABLE Enroll(
    banner_id   INTEGER,
    crn         INTEGER,
    PRIMARY KEY(banner_id, crn),
    CONSTRAINT fk_enroll_has_banner FOREIGN KEY(banner_id)
        REFERENCES Student(banner_id),
    CONSTRAINT fk_enroll_has_crn FOREIGN KEY(crn)
        REFERENCES Section(crn));

CREATE TABLE Assign(
    professor_id    INTEGER,
    crn             INTEGER,
    PRIMARY KEY(professor_id, crn),
    CONSTRAINT fk_assign_has_prof_id FOREIGN KEY(professor_id)
        REFERENCES Professor(professor_id),
    CONSTRAINT fk_assign_has_crn FOREIGN KEY(crn)
        REFERENCES Section(crn));

CREATE TABLE Room (
	room_id			INTEGER,
	building_code	VARCHAR(30),
	room_number		VARCHAR(30),
	max_capacity	INTEGER,
	PRIMARY KEY(room_id));