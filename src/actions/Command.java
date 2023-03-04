package actions;

import entitati.Movie;
import entitati.Serial;
import entitati.User;

public final class Command {

    public Command() {

    }

    /**
     * Add video to favorites if it was seen before.
     * @param user
     * @param title
     * @return
     */
    public String getFavorite(final User user, final String title) {

        if (user.getHistory().containsKey(title)) {
            if (user.getFavoriteMovies().contains(title)) {
                return "error -> " + title + " is already in favourite list";
            }
            user.getFavoriteMovies().add(title);
            return "success -> " + title + " was added as favourite";
        } else {
            return "error -> " + title + " is not seen";
        }
    }

    /** User watches the video.
     * @param user
     * @param title
     * @return
     */
    public String getView(final User user, final String title) {

        if (user.getHistory().containsKey(title)) {
            user.getHistory().put(title, user.getHistory().get(title) + 1);
        } else {
            user.getHistory().put(title, 1);
        }
        return "success -> " + title + " was viewed with total views of "
                + user.getHistory().get(title);
    }

    /**
     * User gives rating to the season.
     * @param user
     * @param video
     * @param title
     * @param nota
     * @param seasonNumber
     * @return
     */
    public String getRatingSerial(final User user, final Serial video,
                                  final String title, final double nota, final int seasonNumber) {

        if (user.getHistory().containsKey(title)) {
            return video.getSeasons().get(seasonNumber - 1).addRating(user, nota, video);
        }
            return "error -> " + title + " is not seen";
    }

    /**
     * User gives rating to the movie.
     * @param user
     * @param video
     * @param title
     * @param nota
     * @return
     */
    public String getRatingMovie(final User user, final Movie video,
                                 final String title, final double nota) {

        if (user.getHistory().containsKey(title)) {
            return video.addRating(user, nota);
        }
        return "error -> " + title + " is not seen";
    }


}
