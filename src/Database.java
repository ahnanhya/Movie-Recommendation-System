import java.sql.*;
import java.util.ArrayList;
public class Database {
    private Connection con;
    // Constructor
    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/movierecommendation",
                    "root",
                    "Psgr@123"
            );
            System.out.println("Database Connected!");
        }
        catch (Exception e) {
            System.out.println("Connection Error");
            e.printStackTrace();
        }
    }
    // Get All Movies
    public ArrayList<Movie> getMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            String sql = "SELECT * FROM movies";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setGenre(rs.getString("genre"));
                movie.setReleaseYear(rs.getInt("release_year"));
                movie.setRating(rs.getDouble("rating"));
                movie.setOverview(rs.getString("overview"));

                movies.add(movie);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
    // Add Movie
    public void addMovie(Movie movie) {
        try {
            String sql = "INSERT INTO movies(title, genre, release_year, rating, overview) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getGenre());
            ps.setInt(3, movie.getReleaseYear());
            ps.setDouble(4, movie.getRating());
            ps.setString(5, movie.getOverview());

            ps.executeUpdate();
            System.out.println("Movie Added!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Delete Movie
    public void deleteMovie(int movieId) {
        try {
            String sql = "DELETE FROM movies WHERE movie_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, movieId);
            ps.executeUpdate();
            System.out.println("Movie Deleted!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Update Movie
    public void updateMovie(Movie movie) {
        try {
            String sql = "UPDATE movies SET title=?, genre=?, release_year=?, rating=?, overview=? WHERE movie_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getGenre());
            ps.setInt(3, movie.getReleaseYear());
            ps.setDouble(4, movie.getRating());
            ps.setString(5, movie.getOverview());
            ps.setInt(6, movie.getMovieId());

            ps.executeUpdate();
            System.out.println("Movie Updated!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Close Connection
    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Connection Closed!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}