package com.server.player;

import com.server.gameMode.Rules;

public abstract class AIPlayer extends Player {
    Rules rules;

    public AIPlayer(String name, Rules rules) {
        super(name);
        this.rules = rules;
    }

    public static AIPlayer getAI(String difficultyLevel, Rules rules) {
        switch (difficultyLevel) {
            case "Easy": {
                return new EasyAIPlayer(rules);
            }
            default: {
                return null;
            }
        }
    }

    protected abstract void makeMove(int roomID);
}
