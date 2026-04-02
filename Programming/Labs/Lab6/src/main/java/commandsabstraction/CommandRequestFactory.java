package commandsabstraction;

import models.MusicBand;
import utilities.MusicBandValidator;

/**
 * Factory that builds a CommandRequest based on what the command needs.
 */
public class CommandRequestFactory {
    private final MusicBandValidator validator;

    public CommandRequestFactory(MusicBandValidator validator) {
        this.validator = validator;
    }

    /**
     * Builds a CommandRequest for the given command.
     * If the command requires a MusicBand, the validator is used to collect input.
     *
     * @param command  the command to build a request for.
     * @param argument the string argument from user input.
     * @return a fully populated CommandRequest.
     */
    public CommandRequest buildRequest(Command command, String argument) {
        MusicBand band = null;
        if (command.requiresBand()) {
            band = validator.askMusicBand();
        }
        return new CommandRequest(argument, band);
    }

    /**
     * Builds a CommandRequest with a pre-built band (used by script execution).
     */
    public static CommandRequest buildScriptRequest(String argument, MusicBand band) {
        return new CommandRequest(argument, band);
    }

    /**
     * Builds a simple request with no band.
     */
    public static CommandRequest buildSimpleRequest(String argument) {
        return new CommandRequest(argument);
    }
}
