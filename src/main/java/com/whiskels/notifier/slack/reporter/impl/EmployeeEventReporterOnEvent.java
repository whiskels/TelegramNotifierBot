package com.whiskels.notifier.slack.reporter.impl;

import com.whiskels.notifier.external.DataProvider;
import com.whiskels.notifier.external.employee.domain.Employee;
import com.whiskels.notifier.slack.reporter.AbstractEmployeeEventReporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.whiskels.notifier.common.datetime.DateTimeUtil.reportDate;
import static com.whiskels.notifier.external.employee.util.EmployeeUtil.isSameDay;
import static com.whiskels.notifier.external.employee.util.EmployeeUtil.notNull;

@Component
@Profile("slack-common")
@ConditionalOnProperty("slack.employee.webhook")
@ConditionalOnBean(value = Employee.class, parameterizedContainer = DataProvider.class)
public class EmployeeEventReporterOnEvent extends AbstractEmployeeEventReporter {
    @Value("${slack.employee.header.daily:Employee events on}")
    private String header;

    public EmployeeEventReporterOnEvent(@Value("${slack.employee.webhook}") String webHook,
                                        DataProvider<Employee> provider,
                                        ApplicationEventPublisher publisher) {
        super(webHook, provider, publisher);
    }

    @Scheduled(cron = "${slack.employee.cron.daily:0 0 9 * * *}", zone = "${common.timezone}")
    public void report() {
        createPayload(header + reportDate(provider.lastUpdate()), true);
    }

    protected List<Predicate<Employee>> birthdayPredicates() {
        return generalPredicates(Employee::getBirthday);
    }

    protected List<Predicate<Employee>> anniversaryPredicates() {
        return generalPredicates(Employee::getAppointmentDate);
    }

    private List<Predicate<Employee>> generalPredicates(Function<Employee, LocalDate> func) {
        return List.of(notNull(func),
                isSameDay(func, provider.lastUpdate()));
    }
}
