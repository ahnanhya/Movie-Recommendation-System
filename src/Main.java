import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;

public class Main {
    private static Database database;
    private static ArrayList<Movie> movieList;
    public static void main(String[] args) {
        // Connect to Database
        database = new Database();
        // Load Movies
        movieList = database.getMovies();
        // Sort Movies by Rating
        if (!movieList.isEmpty()) {
            DataStructures.mergeSort(movieList, 0, movieList.size() - 1);
        }
        // Create Genre HashMap
        DataStructures.createGenreMap(movieList);
        System.out.println("Movie Recommendation System Started");
        System.out.println("Movies Loaded : " + movieList.size());
        // Open Frontend
        openHomePage();
    }
    // Opens index.html
    private static void openHomePage() {
        try {
            File file = new File("frontend/index.html");
            if (file.exists()) {
                Desktop.getDesktop().browse(file.toURI());
            } else {
                System.out.println("Cannot find frontend/index.html");
                System.out.println(file.getAbsolutePath());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Returns all movies
    public static ArrayList<Movie> getMovieList() {
        return movieList;
    }
    // Returns database object
    public static Database getDatabase() {
        return database;
    }
}