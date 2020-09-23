package go.nvhieucs.cama.controllers;

import go.nvhieucs.cama.models.candidate.Candidate;
import go.nvhieucs.cama.models.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Optional;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private final CandidateRepository candidateRepository;

    public CandidateController(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @PostMapping("")
    public ResponseEntity<Object> addCandidate(@RequestBody Candidate candidate)
    {
        return ResponseEntity.ok(candidateRepository.save(candidate));
    }


    @Cacheable(value = "candidates", key = "#candidateId")
    @GetMapping("/{candidateId}")
    public Candidate getCandidateInfo(@PathVariable("candidateId") BigInteger candidateId)
    {
        Optional<Candidate> candidate = candidateRepository.findById(candidateId);
        return null;
    }
}
