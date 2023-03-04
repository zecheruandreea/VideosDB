package entitati;

import java.util.ArrayList;
import java.util.List;

public class Show {

    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    /**
     * Show's rating
     */
    public double rating;
    /**
     * Show duration
     */
    public int duration;

    public Show(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        this.rating = 0;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public int getDuration() {
        return duration;
    }

    public final double average() {
        return rating;
    }

    /**
     * Returns the popularity of the show.
     * @param shows
     * @param users
     * @return
     */
    public final double popular(final List<Show> shows, final List<User> users) {

        int rezultat = 0;
        for (String gen : getGenres()) {
            int rezultatGen = 0;
            for (Show show : shows) {
                if (show.getGenres().contains(gen)) {
                    rezultatGen += show.nrMostViews(users);
                }
            }
            if (rezultatGen > rezultat) {
                rezultat = rezultatGen;
            }
        }
        return rezultat;
    }

    /**
     * Returns the number of views of the show.
     * @param users
     * @return
     */
    public final Integer nrMostViews(final List<User> users) {

        int nrViews = 0;
        for (User user : users) {
            if (user.getHistory().containsKey(this.getTitle())) {
                nrViews += user.getHistory().get(this.getTitle());
            }
        }
        return nrViews;
    }

    /**
     * Returns the number of times a user selected this show as favourite.
     * @param users
     * @return
     */
    public final Integer favoriteViewsUsers(final List<User> users) {

        int nrViews = 0;
        for (User user : users) {
            for (String movie : user.getFavoriteMovies()) {
                if (movie.equals(this.getTitle())) {
                    nrViews++;
                }
            }
        }
        return nrViews;
    }

}

