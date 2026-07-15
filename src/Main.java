import java.util.ArrayList;

public class Main {
    public static Database database;
    public static ArrayList<Movie> movies;
    public static void main(String[] args) {
        // Connect to Database
        database = new Database();
        // Load Movies
        movies = database.getMovies();
        // Sort Movies by Rating
        if (movies.size() > 1) {
            DataStructures.mergeSort(movies, 0, movies.size() - 1);
        }
        // Create Genre HashMap
        DataStructures.createGenreMap(movies);
        System.out.println("Movie Recommendation System Started");
        System.out.println("Movies Loaded : " + movies.size());
        // Open Home Screen
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }
    public static ArrayList<Movie> getMovies() {
        return movies;
    }
    public static Database getDatabase() {
        return database;
    }
}