package go.nvhieucs.cama.models.openingJob;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import go.nvhieucs.cama.models.candidate.Candidate;
import go.nvhieucs.cama.models.job.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "opening_job")
public class OpeningJob implements Serializable {


    public OpeningJob(Job job, Date openDate, Date closeDate) {
        this.job = job;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Job job;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date openDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date closeDate;

    @ManyToMany
    private Set<Candidate> appliedCandidate;

}
