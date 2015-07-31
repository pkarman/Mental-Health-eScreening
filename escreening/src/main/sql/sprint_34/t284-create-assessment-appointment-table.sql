
create table if not exists assessment_appoint (
	vet_assessment_id INT NOT NULL references veteran_assessment (veteran_assessment_id),
	appointment_date TIMESTAMP NOT NULL,
	date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (vet_assessment_id)
);