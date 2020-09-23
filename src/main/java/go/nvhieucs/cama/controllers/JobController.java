package go.nvhieucs.cama.controllers;

import go.nvhieucs.cama.models.job.Job;
import go.nvhieucs.cama.models.job.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Job>> getAllJobs(Authentication authentication) {

        List<Job> jobs = jobRepository.findAll();
        return ResponseEntity.ok(jobs);
    }


    @PostMapping("")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        return ResponseEntity.ok(jobRepository.save(job));
    }
}
