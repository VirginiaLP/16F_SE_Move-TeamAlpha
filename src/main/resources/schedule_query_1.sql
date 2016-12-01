/* Identify all available times for students and professors in a given section */
SELECT Time.start_time, Time.end_time, Time.days, Time.term, Time.term_length
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
	    INNER JOIN Enroll               			ON Enroll.banner_id = Student.banner_id
	    INNER JOIN Section              			ON Section.crn = Enroll.crn
	    INNER JOIN Time  							ON Time.time_id = Section.time_id
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
		INNER JOIN Assign       					ON Assign.professor_id = Professor.professor_id 
		INNER JOIN Section							ON Section.crn = Assign.crn
	    INNER JOIN Time								ON Time.time_id = Section.time_id
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
	INNER JOIN Section ON Section.time_id = CandidateTime.time_id
	WHERE Section.crn = ?
	AND (
		   (Time.term <> CandidateTime.term)				-- classes must be in the correct term
		OR (Time.term_length <> CandidateTime.term_length)	-- classes must have the correct term length
	)
)
/* Filter out odd or invalid times */
AND Time.end_time - Time.start_time > 10						-- classes must meet for more than 10 minutes
ORDER BY (Time.end_time - Time.start_time) * LENGTH(Time.days);
