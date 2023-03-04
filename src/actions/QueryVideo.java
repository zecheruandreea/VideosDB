package actions;

import entitati.Show;
import entitati.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public final class QueryVideo {

    public QueryVideo() {

    }

    public static boolean verifGen(final List<String> genuri, final String gen) {
        for (String genCurent : genuri) {
            if (genCurent.equals(gen)) {
                return true;
            }
        }
        return false;
    }

    public String getRating(final List<Show> shows, final String gen,
                            final String year, final int nrShow, final int semn) {

        List<String> listaNshows = new ArrayList<>();
        List<Show> presentShows = new ArrayList<>();
        for (Show show : shows) {
            if ((gen == null || verifGen(show.getGenres(), gen))
                    && ((year == null) || show.getYear() == Integer.valueOf(year))
                    && (show.average() > 0)) {
                presentShows.add(show);
            }
        }

        presentShows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show show1, final Show show2) {
                double avg1 = show1.average();
                double avg2 = show2.average();
                if (Double.compare(avg1, avg2) == 0) {
                    return semn * show1.getTitle().compareTo(show2.getTitle());
                } else {
                    return semn * Double.compare(avg1, avg2);
                }
            }
        });

        for (int i = 0; i < presentShows.size() && i < nrShow; i++) {
            listaNshows.add(presentShows.get(i).getTitle());
        }

        return "Query result: " + listaNshows;
    }

    public String getFavorite(final List<User> users, final List<Show> shows,
                              final String year, final String gen, final int nrShow,
                              final int semn) {

        List<String> listaNshows = new ArrayList<>();
        List<Show> presentShows = new ArrayList<>();
        for (Show show : shows) {
            if ((gen == null || verifGen(show.getGenres(), gen))
                    && ((year == null) || show.getYear() == Integer.valueOf(year))
                    && (show.favoriteViewsUsers(users) > 0)) {
                presentShows.add(show);
            }
        }

        presentShows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show show1, final Show show2) {
                double avg1 = show1.favoriteViewsUsers(users);
                double avg2 = show2.favoriteViewsUsers(users);
                if (Double.compare(avg1, avg2) == 0) {
                    return semn * show1.getTitle().compareTo(show2.getTitle());
                } else {
                    return semn * Double.compare(avg1, avg2);
                }
            }
        });

        for (int i = 0; i < presentShows.size() && i < nrShow; i++) {
            listaNshows.add(presentShows.get(i).getTitle());
        }

        return "Query result: " + listaNshows;

    }


    public String getLongest(final List<Show> shows, final String gen,
                             final String year, final int nrShow,
                             final int semn) {

        List<String> listaNshows = new ArrayList<>();
        List<Show> presentShows = new ArrayList<>();
        for (Show show : shows) {
            if ((gen == null || verifGen(show.getGenres(), gen))
                    && ((year == null) || show.getYear() == Integer.valueOf(year))) {
                presentShows.add(show);
            }
        }

        presentShows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show show1, final Show show2) {
                double avg1 = show1.getDuration();
                double avg2 = show2.getDuration();
                if (Double.compare(avg1, avg2) == 0) {
                    return semn * show1.getTitle().compareTo(show2.getTitle());
                } else {
                    return semn * Double.compare(avg1, avg2);
                }
            }
        });

        for (int i = 0; i < presentShows.size() && i < nrShow; i++) {
            listaNshows.add(presentShows.get(i).getTitle());
        }

        return "Query result: " + listaNshows;
    }

    public String mostViewed(final List<User> users, final List<Show> shows,
                            final String year, final String gen, final int nrShow,
                             final int semn) {

        List<String> listaNshows = new ArrayList<>();
        List<Show> presentShows = new ArrayList<>();
        for (Show show : shows) {
            if ((gen == null || verifGen(show.getGenres(), gen))
                    && ((year == null) || show.getYear() == Integer.valueOf(year))
                    && (show.nrMostViews(users) > 0)) {
                presentShows.add(show);
            }
        }

        presentShows.sort(new Comparator<Show>() {
            @Override
            public int compare(final Show show1, final Show show2) {
                double avg1 = show1.nrMostViews(users);
                double avg2 = show2.nrMostViews(users);
                if (Double.compare(avg1, avg2) == 0) {
                    return semn * show1.getTitle().compareTo(show2.getTitle());
                } else {
                    return semn * Double.compare(avg1, avg2);
                }
            }
        });

        for (int i = 0; i < presentShows.size() && i < nrShow; i++) {
            listaNshows.add(presentShows.get(i).getTitle());
        }

        return "Query result: " + listaNshows;

    }


    public String numberOfRatings(final List<User> users, final int nrUser,
                                  final int semn) {

        List<String> listaNusers = new ArrayList<>();
        List<User> presentUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getNrRating() > 0) {
                presentUsers.add(user);
            }
        }

        presentUsers.sort(new Comparator<User>() {
            @Override
            public int compare(final User user1, final User user2) {
                double avg1 = user1.getNrRating();
                double avg2 = user2.getNrRating();
                if (Double.compare(avg1, avg2) == 0) {
                    return semn * user1.getUsername().compareTo(user2.getUsername());
                } else {
                    return semn * Double.compare(avg1, avg2);
                }
            }
        });

        for (int i = 0; i < presentUsers.size() && i < nrUser; i++) {
            listaNusers.add(presentUsers.get(i).getUsername());
        }

        return "Query result: " + listaNusers;

    }

}
