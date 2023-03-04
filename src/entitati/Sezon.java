package entitati;

import entertainment.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Sezon {

    /**
     * Number of current season
     */
    private int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;
    /**
     * The ratings of the users
     */
    private Map<String, Double> usersRatings = new HashMap<>();

    public Sezon(final Season sezon, final int currentSeason) {
        this.currentSeason = currentSeason;
        this.duration = sezon.getDuration();
        this.ratings = new ArrayList<>();
    }

    public int getDuration() {
            return duration;
        }

    public void setDuration(final int duration) {
            this.duration = duration;
        }

    public List<Double> getRatings() {
            return ratings;
        }

    public void setRatings(final List<Double> ratings) {
            this.ratings = ratings;
        }

        @Override
        public String toString() {
            return "Episode{"
                    + "currentSeason="
                    + currentSeason
                    + ", duration="
                    + duration
                    + '}';
        }

    public String addRating(final User user, final Double rating,
                            final Serial serial) {
        if (this.usersRatings.containsKey(user.getUsername())) {
            return "error -> " + serial.getTitle() + " has been already rated";
        }
        user.incrementNrRating();
        this.usersRatings.put(user.getUsername(), rating);
        medieRating();
        serial.recalculareRating();
        return "success -> " + serial.getTitle() + " was rated with "
                + rating + " by " + user.getUsername();
    }

    public double medieRating() {
        double sum = 0;
        int nr = 0;
        for (Map.Entry<String, Double> pair : usersRatings.entrySet()) {
            sum = sum + pair.getValue();
            nr++;
        }
        if (nr == 0) {
            return 0;
        }
        return sum / nr;
    }

    }
