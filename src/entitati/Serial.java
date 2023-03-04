package entitati;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public final class Serial extends Show {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private ArrayList<Sezon> seasons = new ArrayList<>();

    public Serial(final SerialInputData serial) {
        super(serial.getTitle(), serial.getYear(), serial.getCast(), serial.getGenres());
        this.numberOfSeasons = serial.getNumberSeason();
        int nr = 0;
        for (Season sezon : serial.getSeasons()) {
           this.seasons.add(new Sezon(sezon, nr));
           duration += sezon.getDuration();
           nr++;
        }
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Sezon> getSeasons() {
        return seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }


    public void recalculareRating() {
        double suma = 0;
        int nr = 0;
        for (int i = 0; i < this.numberOfSeasons; i++) {
                suma = suma + this.seasons.get(i).medieRating();
                nr++;
        }
        if (nr == 0) {
            setRating(0);
            return;
        }
        setRating(suma / nr);
    }
}

