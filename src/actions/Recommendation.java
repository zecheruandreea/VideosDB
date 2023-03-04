package actions;

import entitati.Show;
import entitati.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Recommendation {

    /**
     * Returns standard recommendation to user.
     * @param user
     * @param shows
     * @return
     */
    public String getStandard(final User user, final List<Show> shows) {

        if (user == null) {
            return "StandardRecommendation cannot be applied!";
        }

        for (Show show :shows) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                return "StandardRecommendation result: " + show.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * Returns best unseen video for user.
     * Sorts the shows in decreasing order by rating and returns the first unseen.
     * @param user
     * @param shows
     * @return
     */
    public String getBestUnseen(final User user, final List<Show> shows) {

        if (user == null) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }

        shows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show show1, final Show show2) {
                double avg1 = show1.average();
                double avg2 = show2.average();
                return -Double.compare(avg1, avg2);
            }
        });

        for (Show show :shows) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                return "BestRatedUnseenRecommendation result: " + show.getTitle();
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";

    }

    /**
     * Returns popular recommendation for user.
     * Sorts videos in decreasing order by popularity and returns the first unseen.
     * @param shows
     * @param users
     * @param user
     * @return
     */
    public String getPopular(final List<Show> shows, final List<User> users,
                             final User user) {

        if (user == null || user.getSubscriptionType().equals("BASIC")) {
            return "PopularRecommendation cannot be applied!";
        }

        shows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show show1, final Show show2) {
                double popularitate1 = show1.popular(shows, users);
                double popularitate2 = show2.popular(shows, users);
                return -Double.compare(popularitate1, popularitate2);
            }
        });

        for (Show show :shows) {
            if (!user.getHistory().containsKey(show.getTitle())) {
                return "PopularRecommendation result: " + show.getTitle();
            }
        }
        return "PopularRecommendation cannot be applied!";

    }

    /**
     * Returns favorite recommendation for user.
     * Sorts videos in decreasing order of favourite and returns the first unseen.
     * @param shows
     * @param user
     * @param users
     * @return
     */
    public String getFavorite(final List<Show> shows, final User user,
                              final List<User> users) {

        if (user == null || user.getSubscriptionType().equals("BASIC")) {
            return "FavoriteRecommendation cannot be applied!";
        }

        shows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show show1, final Show show2) {
                double avg1 = show1.favoriteViewsUsers(users);
                double avg2 = show2.favoriteViewsUsers(users);
                return -Double.compare(avg1, avg2);
            }
        });

        for (Show presentShow : shows) {
            if (presentShow.favoriteViewsUsers(users) > 0
                    && !user.getHistory().containsKey(presentShow.getTitle())) {
                return "FavoriteRecommendation result: " + presentShow.getTitle();
            }
        }

        return "FavoriteRecommendation cannot be applied!";
    }

    /**
     * Returns search recommendation for user.
     * Sorts shows by rating in decreasing order and return the first unseen of that genre.
     * @param shows
     * @param user
     * @param gen
     * @return
     */
    public String searchShows(final List<Show> shows, final User user,
                              final String gen) {

        if (user == null || user.getSubscriptionType().equals("BASIC")) {
            return "SearchRecommendation cannot be applied!";
        }

        List<Show> presentShows = new ArrayList<>();
        for (Show show : shows) {
            if (show.getGenres().contains(gen) && !user.getHistory().containsKey(show.getTitle())) {
                presentShows.add(show);
            }
        }

        presentShows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show show1, final Show show2) {
                double avg1 = show1.average();
                double avg2 = show2.average();
                if (Double.compare(avg1, avg2) == 0) {
                    return show1.getTitle().compareTo(show2.getTitle());
                } else {
                    return Double.compare(avg1, avg2);
                }
            }
        });

        List<String> listShows = new ArrayList<>();
        for (Show presentshow : presentShows) {
            listShows.add(presentshow.getTitle());
        }

        if (listShows.size() > 0) {
            return "SearchRecommendation result: " + listShows;
        } else {
            return "SearchRecommendation cannot be applied!";
        }

    }


}
