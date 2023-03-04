package actions;

import actor.ActorsAwards;
import entitati.Show;
import fileio.ActorInputData;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Query {
    /**
     * Maps title with show.
     */
    private Map<String, Show> map;
    /**
     * Maps actorAwards string to constant actorAward.
     */
    private static Map<String, ActorsAwards> mapActorsAwards = new HashMap<>();

    public Query(final Map<String, Show> map) {

        this.map = map;
        if (mapActorsAwards.size() == 0) {
            mapActorsAwards.put("BEST_PERFORMANCE", ActorsAwards.BEST_PERFORMANCE);
            mapActorsAwards.put("BEST_DIRECTOR", ActorsAwards.BEST_DIRECTOR);
            mapActorsAwards.put("PEOPLE_CHOICE_AWARD", ActorsAwards.PEOPLE_CHOICE_AWARD);
            mapActorsAwards.put("BEST_SUPPORTING_ACTOR", ActorsAwards.BEST_SUPPORTING_ACTOR);
            mapActorsAwards.put("BEST_SCREENPLAY", ActorsAwards.BEST_SCREENPLAY);
        }
    }

    /**
     * Sorts actors by rating.
     * @param nrActori
     * @param listaActori
     * @param semn
     * @return
     */
    public String average(final int nrActori, final List<ActorInputData> listaActori,
                            final int semn) {

        List<String> listaNactori = new ArrayList<>();
        List<ActorInputData> presentActors = new ArrayList<>();

        for (ActorInputData actor : listaActori) {
            if (medieActorFilme(actor, map) > 0) {
                presentActors.add(actor);
            }
        }
        presentActors.sort(new Comparator<ActorInputData>() {
            @Override
            public int compare(final ActorInputData actor1, final ActorInputData actor2) {
                double avg1 = medieActorFilme(actor1, map);
                double avg2 = medieActorFilme(actor2, map);
                if (Double.compare(avg1, avg2) == 0) {
                    return semn * actor1.getName().compareTo(actor2.getName());
                } else {
                    return semn * Double.compare(avg1, avg2);
                }
            }
        });
        for (int i = 0; i < presentActors.size() && i < nrActori; i++) {
            listaNactori.add(presentActors.get(i).getName());
        }

        return "Query result: " + listaNactori;
    }

    private static double medieActorFilme(final ActorInputData actor, final Map<String, Show> map) {
        double suma = 0;
        int nrFilme = 0;
        for (String filmography : actor.getFilmography()) {
            if (map.containsKey(filmography)) {
                if (map.get(filmography).average() > 0) {
                    suma += map.get(filmography).average();
                    nrFilme++;
                }
            }
        }
        if (nrFilme == 0) {
            return -1;
        }
        return suma / nrFilme;
    }

    /**
     * Sorts actors by number of awards.
     * @param listaActori
     * @param awards
     * @param semn
     * @return
     */
    public String awards(final List<ActorInputData> listaActori, final List<String> awards,
                            final int semn) {

        List<String> listaNactori = new ArrayList<>();
        List<ActorInputData> presentActors = new ArrayList<>();
        for (ActorInputData actor : listaActori) {
            boolean ok = true;
            for (String award : awards) {
                if (!actor.getAwards().containsKey(mapActorsAwards.get(award))) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                presentActors.add(actor);
            }
        }
        presentActors.sort(new Comparator<ActorInputData>() {
            @Override
            public int compare(final ActorInputData actor1,
                               final ActorInputData actor2) {
                int avg1 = awardsActori(actor1);
                int avg2 = awardsActori(actor2);
                if (Double.compare(avg1, avg2) == 0) {
                    return semn * actor1.getName().compareTo(actor2.getName());
                } else {
                    return semn * Double.compare(avg1, avg2);
                }

            }
        });
        for (int i = 0; i < presentActors.size(); i++) {
            listaNactori.add(presentActors.get(i).getName());
        }

        return "Query result: " + listaNactori;

    }

    private static Integer awardsActori(final ActorInputData actor) {
        int nrPremii = 0;
        for (Map.Entry<ActorsAwards, Integer> premii : actor.getAwards().entrySet()) {
            nrPremii += premii.getValue();
        }
        return nrPremii;
    }

    /**
     * Returns actors who has all keywords in description.
     * @param listaActori
     * @param words
     * @param semn
     * @return
     */
    public String filterDescription(final List<ActorInputData> listaActori,
                                    final List<String> words, final int semn) {

        List<String> listaNactori = new ArrayList<>();
        List<ActorInputData> presentActors = new ArrayList<>();
        for (ActorInputData actor : listaActori) {
            boolean ok = true;

            for (String word : words) {

                Pattern pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(actor.getCareerDescription());
                if (!matcher.find()) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                presentActors.add(actor);
            }
        }
        presentActors.sort(new Comparator<ActorInputData>() {
            @Override
            public int compare(final ActorInputData actor1,
                               final ActorInputData actor2) {
                return semn * actor1.getName().compareTo(actor2.getName());
            }
        });

        for (int i = 0; i < presentActors.size(); i++) {
            listaNactori.add(presentActors.get(i).getName());
        }

        return "Query result: " + listaNactori;

    }
}
