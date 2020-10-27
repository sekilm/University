package lab6.socket.common.domain;

public class Movie extends BaseEntity<Long> {
    private String title;
    private double rating;
    private int nrOfAvailableDVDs;

    public Movie(long _id, String _title, double _rating, int _nrOfAvailableDVDs) {
        super(_id);
        title = _title;
        rating = _rating;
        nrOfAvailableDVDs = _nrOfAvailableDVDs;
    }

    public String getTitle() { return title; }
    public double getRating() { return rating; }
    public int getNrOfAvailableDVDs() { return nrOfAvailableDVDs; }

    public void setTitle(String _title) { title = _title; }
    public void setRating(double _rating) { rating = _rating; }
    public void setNrOfAvailableDVDs(int _nrOfAvailableDVDs) { nrOfAvailableDVDs = _nrOfAvailableDVDs; }

    @Override
    public String toString() {
        return "Movie{ id = " + id + ", title = " + title + ", rating = " + rating + ", available DVDs = " + nrOfAvailableDVDs + " }";
    }
}
