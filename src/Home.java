import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Home extends JFrame {
    private JTextField searchField;
    private JComboBox<String> genreFilter;
    private JPanel moviesPanel;
    private ArrayList<Movie> displayedMovies;

    // --- COLOR PALETTE ---
    private static final Color WINE_RED = new Color(45, 6, 11);
    private static final Color BAR_MAROON = new Color(65, 10, 16);
    private static final Color TEXT_WHITE = new Color(245, 245, 245);
    private static final Color TEXT_MUTED = new Color(190, 180, 180);
    private static final Color STAR_GOLD = new Color(255, 204, 51);
    private static final Color INPUT_BG = new Color(20, 4, 6);

    public Home() {
        setTitle("Movie Recommendation System");
        
        // --- FIXED MEDIUM SCREEN SIZE (NOT FULL SCREEN) ---
        setSize(920, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers window beautifully on screen
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(WINE_RED);

        displayedMovies = new ArrayList<>(Main.getMovies());

        // --- TOP NAVIGATION PANEL ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        topPanel.setBackground(BAR_MAROON);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 20)));
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(TEXT_WHITE);
        searchField = new JTextField(25); // Slightly wider input line
        styleInput(searchField);
        
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setForeground(TEXT_WHITE);
        
        ArrayList<String> genres = new ArrayList<>();
        genres.add("All Genres");
        for (Movie m : Main.getMovies()) {
            if (!genres.contains(m.getGenre())) {
                genres.add(m.getGenre());
            }
        }
        genreFilter = new JComboBox<>(genres.toArray(new String[0]));
        genreFilter.setBackground(INPUT_BG);
        genreFilter.setForeground(TEXT_WHITE);
        
        JButton wishlistBtn = createStyledButton("Wishlist");
        JButton watchedBtn = createStyledButton("Watched");
        JButton addMovieBtn = createStyledButton("Add Movie");
        JButton deleteMovieBtn = createStyledButton("Delete Movie");

        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(genreLabel);
        topPanel.add(genreFilter);
        topPanel.add(wishlistBtn);
        topPanel.add(watchedBtn);
        topPanel.add(addMovieBtn);
        topPanel.add(deleteMovieBtn);

        add(topPanel, BorderLayout.NORTH);

        // --- CENTER SCROLLABLE MOVIES PANEL ---
        moviesPanel = new JPanel(new GridLayout(0, 3, 18, 18));
        moviesPanel.setOpaque(false);
        moviesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel scrollContentWrapper = new JPanel(new BorderLayout());
        scrollContentWrapper.setOpaque(false);
        scrollContentWrapper.add(moviesPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(scrollContentWrapper);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        refreshMovieGrid();

        // --- REAL-TIME DOCUMENT LISTENER (LIVE TYPE-AHEAD SEARCH) ---
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { runLiveSearch(); }
            @Override
            public void removeUpdate(DocumentEvent e) { runLiveSearch(); }
            @Override
            public void changedUpdate(DocumentEvent e) { runLiveSearch(); }
        });

        genreFilter.addActionListener(e -> applyFilters());

        wishlistBtn.addActionListener(e -> new Wishlist(this).setVisible(true));
        watchedBtn.addActionListener(e -> new Watched(this).setVisible(true));
        addMovieBtn.addActionListener(e -> new AddMovie(this).setVisible(true));
        deleteMovieBtn.addActionListener(e -> new DeleteMovie(this).setVisible(true));
    }

    /**
     * Executes the live fuzzy matching evaluation whenever text modifies
     */
    private void runLiveSearch() {
        String query = searchField.getText().trim().toLowerCase();
        String selectedGenre = (String) genreFilter.getSelectedItem();

        if (query.isEmpty()) {
            applyFilters(); // Reset directly to drop-down selection parameters if bar is empty
            return;
        }

        ArrayList<FuzzyResult> searchResults = new ArrayList<>();
        
        // Scan target base catalog matching active filters
        for (Movie m : Main.getMovies()) {
            if (selectedGenre != null && !selectedGenre.equals("All Genres") && !m.getGenre().equals(selectedGenre)) {
                continue;
            }

            String title = m.getTitle().toLowerCase();
            
            // Check direct substring inclusion or fallback to Levenshtein proximity calculation
            if (title.contains(query)) {
                searchResults.add(new FuzzyResult(m, 0));
            } else {
                int distance = computeLevenshteinDistance(query, title);
                // Allow error margin threshold up to 3 typographical changes
                if (distance <= 3) {
                    searchResults.add(new FuzzyResult(m, distance));
                }
            }
        }

        // Sort dynamic elements using closest Levenshtein distance score weight
        Collections.sort(searchResults, (a, b) -> Integer.compare(a.distance, b.distance));

        displayedMovies.clear();
        for (FuzzyResult res : searchResults) {
            displayedMovies.add(res.movie);
        }
        refreshMovieGrid();
    }

    /**
     * Compute Levenshtein Edit Distance metrics
     */
    private int computeLevenshteinDistance(String s1, String s2) {
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    private static class FuzzyResult {
        Movie movie;
        int distance;

        FuzzyResult(Movie movie, int distance) {
            this.movie = movie;
            this.distance = distance;
        }
    }

    private void applyFilters() {
        String selectedGenre = (String) genreFilter.getSelectedItem();
        if (selectedGenre == null || selectedGenre.equals("All Genres")) {
            displayedMovies = new ArrayList<>(Main.getMovies());
        } else {
            displayedMovies = DataStructures.getMoviesByGenre(selectedGenre);
        }
        
        // Re-apply character tracking query if text exists inside input target box
        String query = searchField.getText().trim();
        if (!query.isEmpty()) {
            runLiveSearch();
        } else {
            refreshMovieGrid();
        }
    }

    public void refreshMovieGrid() {
        moviesPanel.removeAll();
        for (Movie m : displayedMovies) {
            moviesPanel.add(createMovieCard(m));
        }
        moviesPanel.revalidate();
        moviesPanel.repaint();
    }

    /**
     * Creates a card panel with advanced Anti-Aliased curved edges and native border glow paths
     */
    private JPanel createMovieCard(Movie movie) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                // Enable high rendering anti-alias vectors for flawless corner curves
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                int cornerRadius = 16; // Curves corners to clean streaming standards

                // 1. Paint Translucent Black Inner Panel Background
                g2d.setColor(new Color(0, 0, 0, 130)); 
                g2d.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
                
                // 2. Paint Custom Smooth Rounded Edge Boundary (Replaces legacy sharp LineBorder artifacts)
                g2d.setColor(new Color(255, 255, 255, 35)); 
                g2d.setStroke(new BasicStroke(1.2f)); 
                g2d.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
                
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        // Internal structural offset buffer spaces
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(250, 150));
        card.setMaximumSize(new Dimension(250, 150));

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        titleLabel.setForeground(TEXT_WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel genreLabel = new JLabel("Genre: " + movie.getGenre());
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        genreLabel.setForeground(TEXT_MUTED);
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Clean universal text representation completely avoids cross-platform font box render anomalies
        JLabel ratingLabel = new JLabel("Rating: " + movie.getRating() + "/10");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 12));
        ratingLabel.setForeground(STAR_GOLD);
        ratingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(genreLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(ratingLabel);
        card.add(Box.createVerticalGlue());

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new MovieDetails(movie).setVisible(true);
            }
        });

        return card;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.BLACK);
        btn.setForeground(TEXT_WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 60, 60), 1),
            new EmptyBorder(6, 12, 6, 12)
        ));
        return btn;
    }

    private void styleInput(JTextField field) {
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_WHITE);
        field.setCaretColor(TEXT_WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(255, 255, 255, 30), 1),
            new EmptyBorder(4, 6, 4, 6)
        ));
    }
}