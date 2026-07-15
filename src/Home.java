import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Home extends JFrame {
    private JTextField searchField;
    private JComboBox<String> genreFilter;
    private JPanel moviesPanel;
    private ArrayList<Movie> displayedMovies;

    public Home() {
        setTitle("Movie Recommendation System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Create initial movie list
        displayedMovies = new ArrayList<>(Main.getMovies());
        // --- TOP NAVIGATION PANEL ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        
        // Dynamic genre extraction from existing movies
        ArrayList<String> genres = new ArrayList<>();
        genres.add("All Genres");
        for (Movie m : Main.getMovies()) {
            if (!genres.contains(m.getGenre())) {
                genres.add(m.getGenre());
            }
        }
        genreFilter = new JComboBox<>(genres.toArray(new String[0]));
        
        JButton wishlistBtn = new JButton("Wishlist");
        JButton watchedBtn = new JButton("Watched");
        JButton addMovieBtn = new JButton("Add Movie");
        JButton deleteMovieBtn = new JButton("Delete Movie");

        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(new JLabel("Genre:"));
        topPanel.add(genreFilter);
        topPanel.add(wishlistBtn);
        topPanel.add(watchedBtn);
        topPanel.add(addMovieBtn);
        topPanel.add(deleteMovieBtn);

        add(topPanel, BorderLayout.NORTH);

        // --- CENTER SCROLLABLE MOVIES PANEL ---
        // 3 columns, auto-adjusting rows
        moviesPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        moviesPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // FIX: Wrap the grid panel in a BorderLayout panel's NORTH region.
        // This stops the grid from stretching vertically when there are few items.
        JPanel scrollContentWrapper = new JPanel(new BorderLayout());
        scrollContentWrapper.add(moviesPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(scrollContentWrapper);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Build grid of movies
        refreshMovieGrid();

        // --- ACTIONS ---
        searchBtn.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                Movie found = RecommendationEngine.searchMovie(Main.getMovies(), query);
                if (found != null) {
                    displayedMovies.clear();
                    displayedMovies.add(found);
                    refreshMovieGrid();
                } else {
                    JOptionPane.showMessageDialog(this, "Movie not found!", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                applyFilters();
            }
        });

        genreFilter.addActionListener(e -> applyFilters());

        wishlistBtn.addActionListener(e -> new Wishlist(this).setVisible(true));
        watchedBtn.addActionListener(e -> new Watched(this).setVisible(true));
        addMovieBtn.addActionListener(e -> new AddMovie(this).setVisible(true));
        deleteMovieBtn.addActionListener(e -> new DeleteMovie(this).setVisible(true));
    }

    private void applyFilters() {
        String selectedGenre = (String) genreFilter.getSelectedItem();
        if (selectedGenre == null || selectedGenre.equals("All Genres")) {
            displayedMovies = new ArrayList<>(Main.getMovies());
        } else {
            displayedMovies = DataStructures.getMoviesByGenre(selectedGenre);
        }
        refreshMovieGrid();
    }

    public void refreshMovieGrid() {
        moviesPanel.removeAll();
        for (Movie m : displayedMovies) {
            JPanel card = createMovieCard(m);
            moviesPanel.add(card);
        }
        moviesPanel.revalidate();
        moviesPanel.repaint();
    }

    private JPanel createMovieCard(Movie movie) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Give each card a fixed height so they look uniform
        card.setPreferredSize(new Dimension(250, 150));
        card.setMaximumSize(new Dimension(250, 150));

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel genreLabel = new JLabel("Genre: " + movie.getGenre());
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        genreLabel.setForeground(Color.DARK_GRAY);
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel ratingLabel = new JLabel("Rating: " + movie.getRating() + "/10");
        ratingLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        ratingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(genreLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(ratingLabel);
        card.add(Box.createVerticalGlue());

        // Click on Movie Card to open details
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new MovieDetails(movie).setVisible(true);
            }
        });

        return card;
    }
}