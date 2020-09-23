package go.nvhieucs.cama.models.job;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;


@Entity(name = "job")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "jobId")
    private Long jobId;

    private String jobName;

    @Column(columnDefinition = "VARCHAR(15) CHECK (level IN ('internship','junior','intermediate','senior','manager'))")
    private String level;

    public Job(String jobName, String level) {
        this.jobName = jobName;
        this.level = level;
    }

}
