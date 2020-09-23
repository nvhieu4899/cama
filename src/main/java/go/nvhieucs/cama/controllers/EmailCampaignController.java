package go.nvhieucs.cama.controllers;

import go.nvhieucs.cama.email.SendMailJob;
import go.nvhieucs.cama.models.candidate.CandidateRepository;
import go.nvhieucs.cama.responsesModel.MessageResponse;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/campaign")
public class EmailCampaignController {


    @Autowired
    private Scheduler scheduler;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("schedule")
    public ResponseEntity<MessageResponse> addCampaigns(@RequestBody Map<String, String> body) {
        try {
            Date now = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
            Date fireDate = simpleDateFormat.parse(body.get("startDate"));

            if (fireDate.getTime() - now.getTime() < 500) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("startDate", "Campaign will not be fired"));
            }
            JobDetail jobDetail = buildJobDetail();
            Trigger trigger = buildJobTrigger(jobDetail,fireDate);
            scheduler.scheduleJob(jobDetail,trigger);
            return ResponseEntity.ok(new MessageResponse("Success", "Campaign is now scheduled"));

        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Server error", "Mail server error"));
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("startDate", "Wrong date format"));
        }
    }

    private JobDetail buildJobDetail()
    {
        JobDataMap jobDataMap = new JobDataMap();
        String[] emails = candidateRepository.getAllCandidateEmail().toArray(new String[0]);
        jobDataMap.put("email",emails);
        jobDataMap.put("mailSender",javaMailSender);
        return JobBuilder.newJob(SendMailJob.class)
                .setJobData(jobDataMap)
                .withIdentity(UUID.randomUUID().toString()," email jobs")
                .withDescription("Send email job")
                .storeDurably()
                .build();
    }
    private Trigger buildJobTrigger(JobDetail jobDetail, Date date)
    {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(),"email-triggers")
                .withDescription("Send email trigger")
                .startAt(date)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }


}
