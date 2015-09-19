package gov.va.escreening.repository;

import gov.va.escreening.entity.ClinicProgram;
import gov.va.escreening.entity.UserProgram;
import org.springframework.stereotype.Repository;

@Repository("clinicProgramRepository")
public class ClinicProgramRepositoryImpl extends AbstractHibernateRepository<ClinicProgram> implements ClinicProgramRepository {
    public ClinicProgramRepositoryImpl() {
        setClazz(ClinicProgram.class);
    }
}
