import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DataStructures {

    // =========================
    // MERGE SORT (BY RATING)
    // =========================

    public static void mergeSort(ArrayList<Movie> movies, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(movies, left, mid);
            mergeSort(movies, mid + 1, right);
            merge(movies, left, mid, right);
        }
    }
    private static void merge(ArrayList<Movie> movies, int left, int mid, int right) {
        ArrayList<Movie> temp = new ArrayList<>();
        int i = left;
        int j = mid + 1;
        while (i <= mid && j <= right) {
            if (movies.get(i).getRating() >= movies.get(j).getRating()) {
                temp.add(movies.get(i));
                i++;
            } else {
                temp.add(movies.get(j));
                j++;
            }
        }
        while (i <= mid) {
            temp.add(movies.get(i));
            i++;
        }
        while (j <= right) {
            temp.add(movies.get(j));
            j++;
        }
        for (int k = 0; k < temp.size(); k++) {
            movies.set(left + k, temp.get(k));
        }
    }
        // =========================
    // BINARY SEARCH (BY TITLE)
    // =========================

    public static Movie binarySearch(ArrayList<Movie> movies, String title) {
        // Create a copy so the original rating order is not affected
        ArrayList<Movie> sortedMovies = new ArrayList<>(movies);
        // Sort alphabetically by title
        sortByTitle(sortedMovies);
        int left = 0;
        int right = sortedMovies.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            Movie movie = sortedMovies.get(mid);
            int compare = movie.getTitle().compareToIgnoreCase(title);
            if (compare == 0) {
                return movie;
            }
            else if (compare < 0) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return null;
    }
    // =========================
    // SORT MOVIES BY TITLE
    // =========================

    private static void sortByTitle(ArrayList<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            for (int j = i + 1; j < movies.size(); j++) {
                if (movies.get(i).getTitle().compareToIgnoreCase(movies.get(j).getTitle()) > 0) {
                    Movie temp = movies.get(i);
                    movies.set(i, movies.get(j));
                    movies.set(j, temp);
                }
            }
        }
    }
    // =========================
    // HASHMAP (GENRE)
    // =========================
    private static HashMap<String, ArrayList<Movie>> genreMap = new HashMap<>();
    // Create Genre Map
    public static void createGenreMap(ArrayList<Movie> movies) {
        genreMap.clear();
        for (Movie movie : movies) {
            String genre = movie.getGenre();
            if (!genreMap.containsKey(genre)) {
                genreMap.put(genre, new ArrayList<>());
            }
            genreMap.get(genre).add(movie);
        }
    }
    // Get Movies by Genre
    public static ArrayList<Movie> getMoviesByGenre(String genre) {
        if (genreMap.containsKey(genre)) {
            return genreMap.get(genre);
        }
        return new ArrayList<>();
    }
    // Display Genre Map (Optional)
    public static void displayGenreMap() {
        for (String genre : genreMap.keySet()) {
            System.out.println("\nGenre : " + genre);
            ArrayList<Movie> movies = genreMap.get(genre);
            for (Movie movie : movies) {
                System.out.println(movie.getTitle());
            }
        }
    }
    // =========================
    // WISHLIST STACK
    // =========================
    private static Stack<Movie> wishlist = new Stack<>();
    public static void addToWishlist(Movie movie) {
        wishlist.push(movie);
    }
    public static Movie removeFromWishlist() {
        if (!wishlist.isEmpty()) {
            return wishlist.pop();
        }
        return null;
    }
    public static Stack<Movie> getWishlist() {
        return wishlist;
    }
    // =========================
    // WATCHED STACK
    // =========================
    private static Stack<Movie> watched = new Stack<>();
    public static void addToWatched(Movie movie) {
        watched.push(movie);
    }
    public static Movie removeFromWatched() {
        if (!watched.isEmpty()) {
            return watched.pop();
        }
        return null;
    }
    public static Stack<Movie> getWatched() {
        return watched;
    }
    // =========================
    // DISPLAY STACKS (OPTIONAL)
    // =========================
    public static void displayWishlist() {
        System.out.println("\nWishlist");
        for (Movie movie : wishlist) {
            System.out.println(movie.getTitle());
        }
    }
    public static void displayWatched() {
        System.out.println("\nRecently Watched");
        for (Movie movie : watched) {
            System.out.println(movie.getTitle());
        }
    }
}