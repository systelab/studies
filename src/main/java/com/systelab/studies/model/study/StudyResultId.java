package com.systelab.studies.model.study;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class StudyResultId implements Serializable {

    @Column(name = "fk_study")
    protected UUID studyId;

    @Column(name = "fk_result")
    protected Long resultId;

    public StudyResultId() {
    }

    public StudyResultId(UUID studyId, Long resultId) {
        this.studyId = studyId;
        this.resultId = resultId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((studyId == null) ? 0 : studyId.hashCode());
        result = prime * result
                + ((resultId == null) ? 0 : resultId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        StudyResultId other = (StudyResultId) obj;

        if (studyId == null) {
            if (other.studyId != null)
                return false;
        } else if (!studyId.equals(other.studyId))
            return false;

        if (resultId == null) {
            if (other.resultId != null)
                return false;
        } else if (!resultId.equals(other.resultId))
            return false;

        return true;
    }
}
