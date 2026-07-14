import java.util.ArrayList;

public class RecommendationEngine {
    // Recommend movies based on selected movie
    public static ArrayList<Movie> recommendMovies(Movie selectedMovie) {
        ArrayList<Movie> recommendations =
                new ArrayList<>(DataStructures.getMoviesByGenre(selectedMovie.getGenre()));
        // Remove the selected movie
        recommendations.removeIf(movie ->
                movie.getMovieId() == selectedMovie.getMovieId());
        // Sort by rating
        if (recommendations.size() > 1) {
            DataStructures.mergeSort(
                    recommendations,
                    0,
                    recommendations.size() - 1
            );
        }
        return recommendations;
    }
    // Return only top N recommendations
    public static ArrayList<Movie> getTopRecommendations(Movie selectedMovie, int limit) {
        ArrayList<Movie> recommendations =
                recommendMovies(selectedMovie);
        ArrayList<Movie> topMovies = new ArrayList<>();
        for (int i = 0;
             i < recommendations.size() && i < limit;
             i++) {
            topMovies.add(recommendations.get(i));
        }
        return topMovies;
    }
    // Search Movie
    public static Movie searchMovie(ArrayList<Movie> movies, String title) {
        return DataStructures.binarySearch(movies, title);
    }
    // Add to Wishlist
    public static void addToWishlist(Movie movie) {
        DataStructures.addToWishlist(movie);
    }
    // Add to Watched
    public static void addToWatched(Movie movie) {
        DataStructures.addToWatched(movie);
    }
    // Get Wishlist
    public static ArrayList<Movie> getWishlist() {
        return new ArrayList<>(DataStructures.getWishlist());
    }
    // Get Watched Movies
    public static ArrayList<Movie> getWatchedMovies() {
        return new ArrayList<>(DataStructures.getWatched());
    }
}