package com.masssimeliano.tbot;

import com.annimon.tgbotsmodule.BotHandler;
import com.annimon.tgbotsmodule.BotModule;
import com.annimon.tgbotsmodule.Runner;
import com.annimon.tgbotsmodule.beans.Config;

import java.util.List;

public class TBotModule implements BotModule {

    public static void main(String[] args) {
        String profile = (args.length >= 1 && !args[0].isEmpty()) ? args[0] : "";

        Runner.run(profile, List.of(new TBotModule()));
    }

    @Override
    public BotHandler botHandler(Config config) {
        return new TBotHandler();
    }
}
