import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Connect to Database
        Database database = new Database();
        // Load Movies
        ArrayList<Movie> movies = database.getMovies();
        System.out.println("--------------------------------");
        System.out.println("Movie Recommendation System");
        System.out.println("--------------------------------");
        System.out.println("Movies Loaded : " + movies.size());
        // Sort Movies by Rating
        if (movies.size() > 1) {
            DataStructures.mergeSort(movies, 0, movies.size() - 1);
        }
        System.out.println("\nTop Rated Movies");
        for (Movie movie : movies) {
            System.out.println(
                    movie.getTitle() +
                    " | " +
                    movie.getGenre() +
                    " | Rating : " +
                    movie.getRating()
            );
        }
        // Create Genre HashMap
        DataStructures.createGenreMap(movies);
        // ----------------------------
        // Binary Search Demo
        // ----------------------------
        System.out.println("\nSearching for 'Leo'...");
        Movie searchedMovie =
                RecommendationEngine.searchMovie(movies, "Leo");
        if (searchedMovie != null) {
            System.out.println("Movie Found : "
                    + searchedMovie.getTitle());
            // Recommendations
            System.out.println("\nRecommended Movies");
            ArrayList<Movie> recommendations =
                    RecommendationEngine.getTopRecommendations(
                            searchedMovie,
                            5
                    );
            for (Movie movie : recommendations) {
                System.out.println(movie.getTitle()
                        + " (" + movie.getRating() + ")");
            }
            // Wishlist Demo
            RecommendationEngine.addToWishlist(searchedMovie);
            // Watched Demo
            RecommendationEngine.addToWatched(searchedMovie);
        }
        else {
            System.out.println("Movie Not Found!");
        }
        // ----------------------------
        // Display Wishlist
        // ----------------------------
        System.out.println("\nWishlist");
        for (Movie movie :
                RecommendationEngine.getWishlist()) {
            System.out.println(movie.getTitle());
        }
        // ----------------------------
        // Display Watched Movies
        // ----------------------------
        System.out.println("\nRecently Watched");
        for (Movie movie :
                RecommendationEngine.getWatchedMovies()) {
            System.out.println(movie.getTitle());
        }
        // Close Database
        database.closeConnection();
    }
}