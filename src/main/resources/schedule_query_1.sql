SELECT DISTINCT
	ValidTime.vt_start_time, 
	ValidTime.vt_end_time, 
	ValidTime.vt_days,
	ValidRoom.vr_building_code,
	ValidRoom.vr_room_number
FROM (
	/* Identify all available times for students and professors in a given section */
	SELECT
		Time.time_id AS vt_time_id,
		Time.start_time AS vt_start_time, 
		Time.end_time AS vt_end_time, 
		Time.days AS vt_days, 
		Time.term AS vt_term, 
		Time.term_length AS vt_term_length
	FROM Time
	/* Select times that do not conflict with Student/Professor schedules */
	WHERE Time.time_id NOT IN (
		/* Find all conflicting times */
		SELECT DISTINCT CandidateTime.time_id
		FROM Time AS CandidateTime
		INNER JOIN (
			/* Gather all Student class times */
		    SELECT
			    Time.time_id AS reserved_time_id, 
				Time.start_time AS reserved_start_time, 
				Time.end_time AS reserved_end_time, 
				Time.days AS reserved_days,
				Time.term AS reserved_term,
				Time.term_length AS reserved_term_length
		    FROM Student
		    INNER JOIN Enroll               		ON Enroll.banner_id = Student.banner_id
		    INNER JOIN Section              		ON Section.crn = Enroll.crn
		    INNER JOIN Time  						ON Time.time_id = Section.time_id
		    WHERE Student.banner_id IN (
		    	/* Identify all students in a class */
		    	SELECT ClassStudent.banner_id
				FROM Section
				INNER JOIN Enroll       				ON Section.crn = Enroll.crn 
				INNER JOIN Student AS ClassStudent		ON Enroll.banner_id = ClassStudent.banner_id 
				WHERE Section.crn = ?
		    )
		    
		    UNION
			
			/* Gather all Professor class times */
			SELECT 
				Time.time_id AS reserved_time_id, 
				Time.start_time AS reserved_start_time, 
				Time.end_time AS reserved_end_time, 
				Time.days AS reserved_days,
				Time.term AS reserved_term,
				Time.term_length AS reserved_term_length
			FROM Professor
			INNER JOIN Assign       				ON Assign.professor_id = Professor.professor_id 
			INNER JOIN Section						ON Section.crn = Assign.crn
		    INNER JOIN Time							ON Time.time_id = Section.time_id
			WHERE Professor.professor_id IN (
				/* Identify all professors in a class */
				SELECT ClassTeacher.professor_id
				FROM Section
				INNER JOIN Assign						ON Section.crn = Assign.crn
				INNER JOIN Professor AS ClassTeacher	ON Assign.professor_id = ClassTeacher.professor_id
		        WHERE Section.crn = ?
		    )
		) AS ReservedTime
		/* Criteria for adding a Time to the set of invalid Times */
		ON (
			   (ReservedTime.reserved_start_time <= CandidateTime.start_time	AND ReservedTime.reserved_end_time >= CandidateTime.start_time)
			OR (ReservedTime.reserved_start_time <= CandidateTime.end_time		AND ReservedTime.reserved_end_time >= CandidateTime.end_time)
			OR (ReservedTime.reserved_start_time <= CandidateTime.start_time	AND ReservedTime.reserved_end_time >= CandidateTime.end_time)
			OR (ReservedTime.reserved_start_time >= CandidateTime.start_time	AND ReservedTime.reserved_end_time <= CandidateTime.end_time)
		) AND (
			   (ReservedTime.reserved_days LIKE '%M%' AND CandidateTime.days LIKE '%M%')
			OR (ReservedTime.reserved_days LIKE '%T%' AND CandidateTime.days LIKE '%T%')
			OR (ReservedTime.reserved_days LIKE '%W%' AND CandidateTime.days LIKE '%W%')
			OR (ReservedTime.reserved_days LIKE '%R%' AND CandidateTime.days LIKE '%R%')
			OR (ReservedTime.reserved_days LIKE '%F%' AND CandidateTime.days LIKE '%F%')
			OR (ReservedTime.reserved_days LIKE '%S%' AND CandidateTime.days LIKE '%S%')
			OR (ReservedTime.reserved_days LIKE '%U%' AND CandidateTime.days LIKE '%U%')
		)
		
		UNION
		
		/* Insure that the same timeframes are being compared */
		SELECT DISTINCT Time.time_id
		FROM Time AS CandidateTime
		INNER JOIN Section						ON Section.time_id = CandidateTime.time_id
		WHERE Section.crn = ?
		AND (
			   (Time.term <> CandidateTime.term)					-- classes must be in the correct term
			OR (Time.term_length <> CandidateTime.term_length)		-- classes must have the correct term length
		)
	)
	/* Filter out odd or invalid times */
	AND Time.end_time - Time.start_time > 10						-- classes must meet for more than 10 minutes
) AS ValidTime
INNER JOIN (
	/* Join any rooms */
	SELECT
		Time.start_time AS vr_start_time, 
		Time.end_time AS vr_end_time, 
		Time.days AS vr_days,
		Time.term AS vr_term,
		Time.term_length AS vr_term_length,
		Room.room_id AS vr_room_id, 
		Room.building_code AS vr_building_code, 
		Room.room_number AS vr_room_number
	FROM Room
	INNER JOIN Section					ON Section.room_id = Room.room_id
	INNER JOIN Time 					ON Time.time_id = Section.time_id
	/* But only if they're in the same building */
	WHERE vr_building_code = (
		SELECT Room.building_code
		FROM Room
		INNER JOIN Section				ON Section.room_id = Room.room_id
		WHERE Section.crn = ?
	)
) AS ValidRoom
ON NOT (
	/* Join all rooms with no intersections */
	   (ValidRoom.vr_start_time <= ValidTime.vt_start_time	AND ValidRoom.vr_end_time >= ValidTime.vt_start_time)
	OR (ValidRoom.vr_start_time <= ValidTime.vt_end_time	AND ValidRoom.vr_end_time >= ValidTime.vt_end_time)
	OR (ValidRoom.vr_start_time <= ValidTime.vt_start_time	AND ValidRoom.vr_end_time >= ValidTime.vt_end_time)
	OR (ValidRoom.vr_start_time >= ValidTime.vt_start_time	AND ValidRoom.vr_end_time <= ValidTime.vt_end_time)
) AND (
	   (ValidRoom.vr_days LIKE '%M%' AND ValidTime.vt_days LIKE '%M%')
	OR (ValidRoom.vr_days LIKE '%T%' AND ValidTime.vt_days LIKE '%T%')
	OR (ValidRoom.vr_days LIKE '%W%' AND ValidTime.vt_days LIKE '%W%')
	OR (ValidRoom.vr_days LIKE '%R%' AND ValidTime.vt_days LIKE '%R%')
	OR (ValidRoom.vr_days LIKE '%F%' AND ValidTime.vt_days LIKE '%F%')
	OR (ValidRoom.vr_days LIKE '%S%' AND ValidTime.vt_days LIKE '%S%')
	OR (ValidRoom.vr_days LIKE '%U%' AND ValidTime.vt_days LIKE '%U%')
) AND (
	ValidRoom.vr_term = ValidTime.vt_term
) AND (
	ValidRoom.vr_term_length = ValidTime.vt_term_length
)
ORDER BY
	(ValidTime.vt_end_time - ValidTime.vt_start_time) * LENGTH(ValidTime.vt_days) DESC,
	ValidTime.vt_start_time,
	ValidTime.vt_end_time,
	ValidTime.vt_days,
	ValidRoom.vr_room_number;
