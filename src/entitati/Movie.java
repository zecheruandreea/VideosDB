package entitati;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Movie extends Show {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    /**
     * List of ratings for a movie
     */
    private final List<Double> ratings;
    /**
     * Rating for a user
     */
    private Map<String, Double> usersRatings = new HashMap<>();

    public Movie(final MovieInputData movie) {
        super(movie.getTitle(), movie.getYear(), movie.getCast(), movie.getGenres());
        this.duration = movie.getDuration();
        this.ratings = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public String addRating(final User user, final Double rating) {
        if (this.usersRatings.containsKey(user.getUsername())) {
            return "error -> " + this.getTitle() + " has been already rated";
        }
        user.incrementNrRating();
        this.usersRatings.put(user.getUsername(), rating);
        recalculareMedie();
        return "success -> " + this.getTitle() + " was rated with "
                + rating + " by " + user.getUsername();
    }

    public void recalculareMedie() {

        double suma = 0;
        int nrRating = 0;
        for (Map.Entry<String, Double> pair : usersRatings.entrySet()) {
            suma = suma + pair.getValue();
            nrRating++;
        }
        if (nrRating == 0) {
            this.rating = 0;
        }
        this.rating = suma / nrRating;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }


}
