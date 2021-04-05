package com.whiskels.notifier.telegram.handler.impl;

import com.whiskels.notifier.telegram.annotations.BotCommand;
import com.whiskels.notifier.telegram.annotations.Schedulable;
import com.whiskels.notifier.telegram.domain.User;
import com.whiskels.notifier.telegram.handler.AbstractBaseHandler;
import com.whiskels.notifier.telegram.security.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationEventPublisher;

import static com.whiskels.notifier.telegram.Command.SCHEDULE_HELP;
import static com.whiskels.notifier.telegram.builder.MessageBuilder.create;
import static com.whiskels.notifier.telegram.domain.Role.*;

/**
 * Shows help message for {@link ScheduleAddHandler} supported syntax
 * <p>
 * Available to: Manager, Head, Admin, HR
 */
@Slf4j
@BotCommand(command = SCHEDULE_HELP, requiredRoles = {MANAGER, HEAD, ADMIN})
@ConditionalOnBean(annotation = Schedulable.class)
public class ScheduleHelpHandler extends AbstractBaseHandler {
    public ScheduleHelpHandler(AuthorizationService authorizationService,
                               ApplicationEventPublisher publisher) {
        super(authorizationService, publisher);
    }

    @Override
    protected void handle(User user, String message) {
        log.debug("Preparing /SCHEDULE_HELP");
        publish(create(user)
                .line("*Help message for /schedule command*")
                .line()
                .line("[/schedule *time*](/schedule time) - set daily message at time. Examples: ")
                .line("   /schedule 1 - 01:00")
                .line("   /schedule 10 - 10:00")
                .line("   /schedule 1230 - 12:30")
                .line("Please note that daily messages are not sent on *sundays and saturdays*!")
                .build());
    }
}
