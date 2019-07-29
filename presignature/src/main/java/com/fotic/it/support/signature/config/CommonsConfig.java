package com.fotic.it.support.signature.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: mfh
 */
@Component
@ConfigurationProperties(prefix = "commons-config")
public class CommonsConfig {
    private String scheduledTaskCycle;
    private String signatureCertificatePath;

    public String getSignatureCertificatePath() {
        return signatureCertificatePath;
    }

    public void setSignatureCertificatePath(String signatureCertificatePath) {
        this.signatureCertificatePath = signatureCertificatePath;
    }

    public String getScheduledTaskCycle() {
        return scheduledTaskCycle;
    }

    public void setScheduledTaskCycle(String scheduledTaskCycle) {
        this.scheduledTaskCycle = scheduledTaskCycle;
    }
}
