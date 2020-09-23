package go.nvhieucs.cama.controllers;


import go.nvhieucs.cama.models.job.Job;
import go.nvhieucs.cama.models.openingJob.OpeningJob;
import go.nvhieucs.cama.models.job.JobRepository;
import go.nvhieucs.cama.models.openingJob.OpeningJobRepository;
import go.nvhieucs.cama.responsesModel.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/opening-job")
public class OpeningJobController {
    @Autowired
    private final OpeningJobRepository openingJobRepository;

    @Autowired
    private final JobRepository jobRepository;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


    public OpeningJobController(OpeningJobRepository openingJobRepository, JobRepository jobRepository) {

        this.openingJobRepository = openingJobRepository;
        this.jobRepository = jobRepository;
    }

    @GetMapping("")
    public List<OpeningJob> getOpeningJob(@RequestParam(value = "pageIndex", required = false,defaultValue = "1") Integer pageIndex, @RequestParam(value = "pageSize", required = false,defaultValue = "10") Integer pageSize) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? 10 : pageSize;
        return openingJobRepository.findAll(PageRequest.of(pageIndex - 1, pageSize)).getContent();
    }

    @PostMapping("")
    public ResponseEntity<OpeningJob> addOpeningJob(@RequestBody Map<String, Object> body) {
        Long jobId = ((Integer) body.get("jobId")).longValue();
        if (!jobRepository.existsById(jobId))
        {
            return ResponseEntity.notFound().build();
        }
        try {
            Date startDate = simpleDateFormat.parse((String) body.get("startDate"));
            Date endDate = simpleDateFormat.parse((String) body.get("endDate"));
            Job job = jobRepository.getOne(jobId);
            return ResponseEntity.ok(openingJobRepository.save(new OpeningJob(job, startDate, endDate)));
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOpeningJob(@PathVariable("id") Integer id)
    {
        Long lid = id.longValue();
        if (!openingJobRepository.existsById(lid))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("id","Opening job not found"));
        }
        else
        {
            openingJobRepository.deleteById(lid);
            Map<String,String> map = new HashMap<>(Collections.emptyMap());
            map.put("message","Opening job deleted");

            return ResponseEntity.ok(map);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> editOpeningJob(@PathVariable("id") Integer id,@RequestBody Map<String,String> body)
    {
        Long lid = id.longValue();
        if (!openingJobRepository.existsById(lid))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("id","Opening job not found"));
        }
        else
        {
            OpeningJob openingJob = openingJobRepository.getOne(lid);
            return ResponseEntity.ok(openingJobRepository.save(openingJob));
        }
    }



}
