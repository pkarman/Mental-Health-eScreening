/* We have to do this again because the code fix was ineffective at stopping the creation of duplicate keys.
 * Removing duplicate entries in the variable_template table (found by Ming) */
delete from variable_template
where variable_template_id not in (
select cid from (
    (select max(variable_template_id) as cid from variable_template b
        group by template_id, assessment_variable_id)
    ) as C
);