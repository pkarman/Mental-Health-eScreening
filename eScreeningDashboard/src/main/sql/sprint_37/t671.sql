-- correct matrix questions so they do not have their is_mha set to true
UPDATE measure 
SET is_mha=0
where measure_type_id=6;