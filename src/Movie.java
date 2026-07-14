public class Movie {

    private int movieId;
    private String title;
    private String genre;
    private int releaseYear;
    private double rating;
    private String overview;
    // Default Constructor
    public Movie() {
    }
    // Parameterized Constructor
    public Movie(int movieId, String title, String genre,
                 int releaseYear, double rating, String overview) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.overview = overview;
    }
    // Getters
    public int getMovieId() {
        return movieId;
    }
    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public double getRating() {
        return rating;
    }
    public String getOverview() {
        return overview;
    }

    // Setters
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    @Override
    public String toString() {
        return title;
    }
}