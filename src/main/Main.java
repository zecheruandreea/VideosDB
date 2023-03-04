package main;

import actions.Command;
import actions.Query;
import actions.QueryVideo;
import actions.Recommendation;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import entitati.*;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();
        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();
        String string;
        JSONObject jsonObject;
        Command command = new Command();
        Lists listsinput = new Lists(input);
        Map<String, Show> map = new HashMap<>();
        Query query = new Query(map);
        QueryVideo queryVideo = new QueryVideo();
        Recommendation recommendation = new Recommendation();

        for (Movie movie : listsinput.getMovies()) {
            map.put(movie.getTitle(), movie);
        }

        for (Serial serial : listsinput.getSerials()) {
            map.put(serial.getTitle(), serial);
        }

        for (ActionInputData action : listsinput.getCommands()) {
            User searchUser = null;
            Movie movie = null;
            Serial serial = null;

            for (User user1 : listsinput.getUsers()) {
                if (user1.getUsername().equals(action.getUsername())) {
                    searchUser = user1;
                    break;
                }
            }

            for (Movie movie1 : listsinput.getMovies()) {
                if (movie1.getTitle().equals(action.getTitle())) {
                    movie = movie1;
                    break;
                }
            }
            for (Serial serial1 : listsinput.getSerials()) {
                if (serial1.getTitle().equals(action.getTitle())) {
                    serial = serial1;
                    break;
                }
            }

            switch (action.getActionType()) {
                case "command" -> {
                    if (searchUser == null) {
                        continue;
                    }
                    switch (action.getType()) {
                        case "favorite":
                            string = command.getFavorite(searchUser, action.getTitle());
                            jsonObject = fileWriter.writeFile(action.getActionId(), "", string);
                            arrayResult.add(jsonObject);
                            break;
                        case "view":
                            string = command.getView(searchUser, action.getTitle());
                            jsonObject = fileWriter.writeFile(action.getActionId(), "", string);
                            arrayResult.add(jsonObject);
                            break;
                        case "rating":
                            if (serial == null) {
                                string = command.getRatingMovie(searchUser, movie,
                                        action.getTitle(), action.getGrade());
                                jsonObject = fileWriter.writeFile(action.getActionId(), "", string);
                                arrayResult.add(jsonObject);
                            } else {
                                string = command.getRatingSerial(searchUser, serial, action.getTitle(),
                                        action.getGrade(), action.getSeasonNumber());
                                jsonObject = fileWriter.writeFile(action.getActionId(), "", string);
                                arrayResult.add(jsonObject);
                            }
                            break;
                    }
                }
                case "query" -> {
                    int semn;
                    if (action.getSortType().equals("asc")) {
                        semn = 1;
                    } else {
                        semn = -1;
                    }
                    switch (action.getObjectType()) {
                        case "actors":

                            switch (action.getCriteria()) {
                                case "average" -> {
                                    string = query.average(action.getNumber(),
                                            listsinput.getActors(), semn);
                                    jsonObject = fileWriter.writeFile(action.getActionId(),
                                            "", string);
                                    arrayResult.add(jsonObject);
                                }
                                case "awards" -> {
                                    string = query.awards(listsinput.getActors(),
                                            action.getFilters().get(3), semn);
                                    jsonObject = fileWriter.writeFile(action.getActionId(),
                                            "", string);
                                    arrayResult.add(jsonObject);
                                }
                                case "filter_description" -> {
                                    string = query.filterDescription(listsinput.getActors(),
                                            action.getFilters().get(2), semn);
                                    jsonObject = fileWriter.writeFile(action.getActionId(),
                                            "", string);
                                    arrayResult.add(jsonObject);
                                }
                            }
                            break;
                        case "shows":
                        case "movies":

                            List<Show> shows = new ArrayList<>();

                            if (action.getObjectType().equals("shows")) {
                                for (Serial serialc : listsinput.getSerials()) {
                                    shows.add(serialc);
                                }

                            } else {
                                for (Movie moviec : listsinput.getMovies()) {
                                    shows.add(moviec);
                                }
                            }

                            if (action.getCriteria().equals("ratings")) {
                                string = queryVideo.getRating(shows,
                                        action.getFilters().get(1).get(0),
                                        action.getFilters().get(0).get(0),
                                        action.getNumber(), semn);
                                jsonObject = fileWriter.writeFile(action.getActionId(),
                                        "", string);
                                arrayResult.add(jsonObject);
                            }
                            if (action.getCriteria().equals("favorite")) {
                                string = queryVideo.getFavorite(listsinput.getUsers(), shows,
                                        action.getFilters().get(0).get(0),
                                        action.getFilters().get(1).get(0),
                                        action.getNumber(), semn);
                                jsonObject = fileWriter.writeFile(action.getActionId(),
                                        "", string);
                                arrayResult.add(jsonObject);
                            }
                            if (action.getCriteria().equals("longest")) {
                                string = queryVideo.getLongest(shows,
                                        action.getFilters().get(1).get(0),
                                        action.getFilters().get(0).get(0),
                                        action.getNumber(), semn);
                                jsonObject = fileWriter.writeFile(action.getActionId(),
                                        "", string);
                                arrayResult.add(jsonObject);
                            }
                            if (action.getCriteria().equals("most_viewed")) {
                                string = queryVideo.mostViewed(listsinput.getUsers(), shows,
                                        action.getFilters().get(0).get(0),
                                        action.getFilters().get(1).get(0),
                                        action.getNumber(), semn);
                                jsonObject = fileWriter.writeFile(action.getActionId(),
                                        "", string);
                                arrayResult.add(jsonObject);
                            }
                            break;
                        case "users":
                            if (action.getCriteria().equals("num_ratings")) {
                                string = queryVideo.numberOfRatings(listsinput.getUsers(),
                                        action.getNumber(), semn);
                                jsonObject = fileWriter.writeFile(action.getActionId(),
                                        "", string);
                                arrayResult.add(jsonObject);
                            }
                            break;
                    }
                }
                case "recommendation" -> {
                    List<Show> shows = new ArrayList<>();
                    for (Movie moviec : listsinput.getMovies()) {
                        shows.add(moviec);
                    }
                    for (Serial serialc : listsinput.getSerials()) {
                        shows.add(serialc);
                    }
                    if (action.getType().equals("standard")) {
                        string = recommendation.getStandard(searchUser, shows);
                        jsonObject = fileWriter.writeFile(action.getActionId(),
                                "", string);
                        arrayResult.add(jsonObject);
                    }
                    if (action.getType().equals("best_unseen")) {
                        string = recommendation.getBestUnseen(searchUser, shows);
                        jsonObject = fileWriter.writeFile(action.getActionId(),
                                "", string);
                        arrayResult.add(jsonObject);
                    }
                    if (action.getType().equals("popular")) {
                        string = recommendation.getPopular(shows, listsinput.getUsers(),
                                searchUser);
                        jsonObject = fileWriter.writeFile(action.getActionId(),
                                "", string);
                        arrayResult.add(jsonObject);
                    }
                    if (action.getType().equals("favorite")) {
                        string = recommendation.getFavorite(shows,
                                searchUser, listsinput.getUsers());
                        jsonObject = fileWriter.writeFile(action.getActionId(),
                                "", string);
                        arrayResult.add(jsonObject);
                    }
                    if (action.getType().equals("search")) {
                        string = recommendation.searchShows(shows, searchUser, action.getGenre());
                        jsonObject = fileWriter.writeFile(action.getActionId(),
                                "", string);
                        arrayResult.add(jsonObject);

                    }
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }

}
