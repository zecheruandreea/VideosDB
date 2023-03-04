package entitati;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Map;

public final class User {

    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    private int nrRating;

    public User(final UserInputData user) {
            this.username = user.getUsername();
            this.subscriptionType = user.getSubscriptionType();
            this.history = user.getHistory();
            this.favoriteMovies = user.getFavoriteMovies();
            this.nrRating = 0;
    }

    public String getUsername() {
            return username;
        }

        public Map<String, Integer> getHistory() {
            return history;
        }

        public String getSubscriptionType() {
            return subscriptionType;
        }

        public ArrayList<String> getFavoriteMovies() {
            return favoriteMovies;
        }

        public int getNrRating() {
            return nrRating;
        }

    public int incrementNrRating() {
        nrRating++;
        return nrRating;
    }

        @Override
        public String toString() {
            return "UserInputData{" + "username='"
                    + username + '\'' + ", subscriptionType='"
                    + subscriptionType + '\'' + ", history="
                    + history + ", favoriteMovies="
                    + favoriteMovies + '}';
        }
}


