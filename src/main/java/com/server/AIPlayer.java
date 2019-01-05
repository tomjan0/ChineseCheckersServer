package com.server;

public abstract class AIPlayer extends Player {
    public AIPlayer(String name) {
        super(name);
    }

    public static AIPlayer getAI(String difficultyLevel) {
        switch (difficultyLevel) {
            case "Easy": {
                return new EasyAIPlayer();
            }
            default: {
                return null;
            }
        }
    }
}
