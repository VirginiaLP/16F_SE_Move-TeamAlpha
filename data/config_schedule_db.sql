DROP TABLE IF EXISTS Student;
CREATE TABLE Student(
    banner_id   INTEGER,
    first_name  VARCHAR(45),
    last_name   VARCHAR(45),
    class       CHAR(5),
        PRIMARY KEY(banner_id));

DROP TABLE IF EXISTS Professor;
CREATE TABLE Professor(
    professor_id    INTEGER,
    first_name      VARCHAR(45),
    last_name       VARCHAR(45),
        PRIMARY KEY(professor_id));

DROP TABLE IF EXISTS Section;
CREATE TABLE Section(
    crn             INTEGER,
    class_subject   VARCHAR(10),
    class_number    CHAR(5),
    class_title     VARCHAR(70),
    class_suffix    CHAR(5),
	time_id         INTEGER,
    room_id			INTEGER,
        PRIMARY KEY(crn),
        CONSTRAINT fk_section_has_room FOREIGN KEY(room_id)
            REFERENCES Room(room_id));

DROP TABLE IF EXISTS Time;
CREATE TABLE Time(
    time_id         INTEGER,
    start_time      INTEGER,
    end_time        INTEGER,
    days            CHAR(7),
    term            VARCHAR(10),
    term_length     VARCHAR(30),
        PRIMARY KEY(time_id));

DROP TABLE IF EXISTS Enroll;
CREATE TABLE Enroll(
    banner_id   INTEGER,
    crn         INTEGER,
        PRIMARY KEY(banner_id, crn),
        CONSTRAINT fk_enroll_has_banner FOREIGN KEY(banner_id)
            REFERENCES Student(banner_id),
        CONSTRAINT fk_enroll_has_crn FOREIGN KEY(crn)
            REFERENCES Section(crn));

DROP TABLE IF EXISTS Assign;
CREATE TABLE Assign(
    professor_id    INTEGER,
    crn             INTEGER,
        PRIMARY KEY(professor_id, crn),
        CONSTRAINT fk_assign_has_prof_id FOREIGN KEY(professor_id)
            REFERENCES Professor(professor_id),
        CONSTRAINT fk_assign_has_crn FOREIGN KEY(crn)
            REFERENCES Section(crn));

DROP TABLE IF EXISTS Room;
CREATE TABLE Room (
	room_id			INTEGER,
	building_code	CHAR(5),
	room_number		CHAR(5),
	max_capacity	INTEGER,
	    PRIMARY KEY(room_id));

