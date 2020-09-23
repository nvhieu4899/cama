package go.nvhieucs.cama.models.candidate;


import go.nvhieucs.cama.models.openingJob.OpeningJob;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity(name = "candidate")
public class Candidate implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long candidateId;
    private String name;
    private Date dateOfBirth;

    private String email;

    @ManyToMany
    @Lazy
    private Set<OpeningJob> appliedJob;

    public Candidate(String name, Date dateOfBirth, Set<OpeningJob> appliedJob) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.appliedJob = appliedJob;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public Candidate() {
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<OpeningJob> getAppliedJob() {
        return appliedJob;
    }

    public void setAppliedJob(Set<OpeningJob> appliedJob) {
        this.appliedJob = appliedJob;
    }

}
