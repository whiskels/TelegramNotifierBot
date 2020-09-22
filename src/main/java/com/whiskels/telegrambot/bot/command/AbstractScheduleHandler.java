package com.whiskels.telegrambot.bot.command;

import com.whiskels.telegrambot.model.User;
import com.whiskels.telegrambot.service.ScheduleService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.Serializable;
import java.util.List;

public class AbstractScheduleHandler extends AbstractBaseHandler {
    protected final ScheduleService scheduleService;

    public AbstractScheduleHandler(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> operate(User user, String message) {
        return null;
    }

    @Override
    public Command supportedCommand() {
        return null;
    }
}