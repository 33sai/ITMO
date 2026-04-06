package commandsabstraction;

import models.MusicBand;

import java.io.Serializable;

/**
 * This class contains the necessary data for all commands to work
 */
public class CommandRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String argument;
    private final MusicBand band;

    public CommandRequest(String argument, MusicBand band) {
        this.argument = argument;
        this.band = band;
    }

    public CommandRequest(String argument) {
        this.argument = argument;
        this.band = null;
    }

    public String getArgument() {
        return this.argument;
    }

    public MusicBand getBand() {
        return this.band;
    }
}
