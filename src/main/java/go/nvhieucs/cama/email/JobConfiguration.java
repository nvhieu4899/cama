package go.nvhieucs.cama.email;


import org.quartz.*;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JobConfiguration {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean()
    {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        return schedulerFactoryBean;
    }

    public JobDetail sendEmailJobDetail() {
        return JobBuilder.newJob(SendMailJob.class)
                .withIdentity(JobKey.jobKey("Send email"))
                .storeDurably()
                .build();
    }


    public Trigger sendMailJobTriggerBuilder(Calendar calendar) {
        String minutes = String.valueOf(calendar.get(Calendar.MINUTE));
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String cron = "0 " + minutes + " " + hour + " " + day + " " + month + " ? " + year;
        return TriggerBuilder.newTrigger()
                .forJob(sendEmailJobDetail())
                .withIdentity(TriggerKey.triggerKey("Send email"))
                .withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionIgnoreMisfires())
                .build();
    }
}
