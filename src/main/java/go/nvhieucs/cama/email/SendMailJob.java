package go.nvhieucs.cama.email;

import org.apache.catalina.core.ApplicationContext;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SendMailJob extends QuartzJobBean {

    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        javaMailSender = (JavaMailSender) jobDataMap.get("mailSender");
        String[] addressList = (String[]) jobDataMap.get("email");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("HIEU DEP TRAI TEST MAIL JAVA");
        mailMessage.setText("HIEU DEP TRAI VKL");
        mailMessage.setBcc(addressList);
        this.javaMailSender.send(mailMessage);
    }
}
