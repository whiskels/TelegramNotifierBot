package com.whiskels.notifier.slack.impl;

import com.whiskels.notifier.external.employee.service.EmployeeService;
import com.whiskels.notifier.slack.SlackWebHookHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("slack-common")
@Slf4j
@ConditionalOnBean(EmployeeService.class)
public class BirthdayWebHookHandler implements SlackWebHookHandler {
    @Value("${slack.employee.birthday.webhook}")
    private String webHook;

    private final EmployeeService service;

    @Scheduled(cron = "${slack.employee.birthday.dailyCron}", zone = "${common.timezone}")
    public void dailyPayload() {
        final String result = sendDailyReport(webHook, service);
        log.info(result);
    }

    @Scheduled(cron = "${slack.employee.birthday.monthlyCron}", zone = "${common.timezone}")
    public void monthlyPayload() {
        final String result = sendMonthlyReport(webHook, service);
        log.info(result);
    }
}
