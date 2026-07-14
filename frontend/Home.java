package frontend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Home extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addMovieButton;
    private JButton wishlistButton;
    private JButton watchedButton;
    private JComboBox<String> genreComboBox;
    private JPanel moviePanel;
    private JScrollPane scrollPane;
    private ArrayList<Movie> movies;

    public Home() {
        movies = Main.getMovies();
        setTitle("Movie Recommendation System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel("🎬 Movie Recommendation System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        genreComboBox = new JComboBox<>();
        genreComboBox.addItem("All Genres");
        genreComboBox.addItem("Action");
        genreComboBox.addItem("Adventure");
        genreComboBox.addItem("Comedy");
        genreComboBox.addItem("Drama");
        genreComboBox.addItem("Fantasy");
        genreComboBox.addItem("Horror");
        genreComboBox.addItem("Romance");
        genreComboBox.addItem("Sci-Fi");
        genreComboBox.addItem("Thriller");
        addMovieButton = new JButton("Add Movie");
        wishlistButton = new JButton("Wishlist");
        watchedButton = new JButton("Watched");
        topPanel.add(new JLabel("Search"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(genreComboBox);
        topPanel.add(addMovieButton);
        topPanel.add(wishlistButton);
        topPanel.add(watchedButton);
        add(topPanel, BorderLayout.SOUTH);
        
        // Movie Panel
        moviePanel = new JPanel();
        moviePanel.setLayout(new GridLayout(0, 3, 20, 20));
        moviePanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20,20)
        );
        scrollPane = new JScrollPane(moviePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
}
// Load all movies
private void loadMovies(ArrayList<Movie> movieList) {

    moviePanel.removeAll();

    for (Movie movie : movieList) {

        JPanel card = createMovieCard(movie);

        moviePanel.add(card);

    }

    moviePanel.revalidate();

    moviePanel.repaint();

}
// Create a movie card
private JPanel createMovieCard(Movie movie) {

    JPanel card = new JPanel();

    card.setLayout(new BorderLayout());

    card.setPreferredSize(new Dimension(220, 170));

    card.setBackground(Color.WHITE);

    card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

    JLabel title = new JLabel(movie.getTitle(), JLabel.CENTER);

    title.setFont(new Font("Arial", Font.BOLD, 18));

    JLabel genre = new JLabel(
            "Genre : " + movie.getGenre(),
            JLabel.CENTER
    );

    JLabel rating = new JLabel(
            "⭐ Rating : " + movie.getRating(),
            JLabel.CENTER
    );

    JPanel infoPanel = new JPanel(new GridLayout(3,1));

    infoPanel.add(title);

    infoPanel.add(genre);

    infoPanel.add(rating);

    card.add(infoPanel, BorderLayout.CENTER);

    card.setCursor(new Cursor(Cursor.HAND_CURSOR));

    card.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {

            new MovieDetails(movie);

            dispose();

        }
    });

    return card;

}