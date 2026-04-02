package commandsabstraction;

import models.MusicBand;

/**
 * This class contains the necessary data for all commands to work
 */
public class CommandRequest {
    private String argument;
    private MusicBand band;

    public CommandRequest(String argument, MusicBand band) {
        this.argument = argument;
        this.band = band;
    }

    public CommandRequest(String arguemt) {
        this.argument = arguemt;
        this.band = null;
    }

    public String getArgument() {
        return this.argument;
    }

    public MusicBand getBand() {
        return this.band;
    }
}
