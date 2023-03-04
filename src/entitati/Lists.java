package entitati;

import fileio.*;

import java.util.ArrayList;
import java.util.List;

public final class Lists {

    /**
     * List of actors
     */
    private final List<ActorInputData> actorsData;
    /**
     * List of users
     */
    private final List<User> usersData = new ArrayList<>();
    /**
     * List of commands
     */
    private final List<ActionInputData> commandsData;
    /**
     * List of movies
     */
    private final List<Movie> moviesData = new ArrayList<>();
    /**
     * List of serials aka tv shows
     */
    private final List<Serial> serialsData = new ArrayList<>();

    public Lists(final Input input) {
        this.actorsData = input.getActors();
        for (UserInputData user : input.getUsers()) {
            this.usersData.add(new User(user));
        }
        this.commandsData = input.getCommands();
        for (MovieInputData movie : input.getMovies()) {
            this.moviesData.add(new Movie(movie));
        }
        for (SerialInputData serial : input.getSerials()) {
            this.serialsData.add(new Serial(serial));
        }

    }

    public List<ActorInputData> getActors() {
        return actorsData;
    }

    public List<User> getUsers() {
        return usersData;
    }

    public List<ActionInputData> getCommands() {
        return commandsData;
    }

    public List<Movie> getMovies() {
        return moviesData;
    }

    public List<Serial> getSerials() {
        return serialsData;
    }
}

