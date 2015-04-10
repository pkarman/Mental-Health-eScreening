package gov.va.escreening.repository;

import gov.va.escreening.entity.ReportType;
import gov.va.escreening.entity.TemplateType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kliu on 2/22/15.
 */
@Repository
public class ReportTypeRepositoryImpl extends AbstractHibernateRepository<ReportType> implements ReportTypeRepository{

    public ReportTypeRepositoryImpl() {
        super();
        setClazz(ReportType.class);
    }
}
