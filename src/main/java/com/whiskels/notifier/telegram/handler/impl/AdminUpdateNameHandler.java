package com.whiskels.notifier.telegram.handler.impl;

import com.whiskels.notifier.telegram.annotation.BotCommand;
import com.whiskels.notifier.telegram.domain.User;
import com.whiskels.notifier.telegram.handler.AbstractUserHandler;
import com.whiskels.notifier.telegram.security.AuthorizationService;
import com.whiskels.notifier.telegram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import static com.whiskels.notifier.telegram.Command.ADMIN_NAME;
import static com.whiskels.notifier.telegram.builder.MessageBuilder.builder;
import static com.whiskels.notifier.telegram.domain.Role.ADMIN;
import static com.whiskels.notifier.telegram.util.ParsingUtil.extractArguments;

/**
 * Allows bot admin to change user name by sending bot a chat command
 * Syntax: /ADMIN_NAME userId name
 * <p>
 * Available to: Admin
 */
@Slf4j
@BotCommand(command = ADMIN_NAME, requiredRoles = {ADMIN})
public class AdminUpdateNameHandler extends AbstractUserHandler {
    public AdminUpdateNameHandler(AuthorizationService authorizationService,
                                  ApplicationEventPublisher publisher,
                                  UserService userService) {
        super(authorizationService, publisher, userService);
    }

    @Override
    protected void handle(User admin, String message) {
        final String arguments = extractArguments(message);
        final int userId = Integer.parseInt(arguments.substring(0, arguments.indexOf(" ")));

        final User toUpdate = userService.get(userId).orElse(null);

        if (toUpdate != null) {
            toUpdate.setName(extractArguments(arguments));
            userService.update(toUpdate);

            publish(builder(admin)
                    .line("Updated user: %s", toUpdate.toString())
                    .build());
        } else {
            publish(builder(admin)
                    .line("Couldn't find user: %d", userId)
                    .build());
        }
    }
}
