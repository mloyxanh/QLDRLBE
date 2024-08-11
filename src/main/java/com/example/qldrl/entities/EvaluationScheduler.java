package com.example.qldrl.entities;

import com.example.qldrl.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EvaluationScheduler {
    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationScheduler(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Chạy hàng ngày vào lúc nửa đêm
    public void checkAndSaveDefaultEvaluations() {
        evaluationService.saveDefaultEvaluations();
    }
}
