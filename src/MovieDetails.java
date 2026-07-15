import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MovieDetails extends JFrame {
    private Movie currentMovie;

    public MovieDetails(Movie movie) {
        this.currentMovie = movie;
        setTitle("Movie Details: " + movie.getTitle());
        setSize(860, 750); // Expanded width to easily fit 5 columns of cards across
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // --- MAIN CONTAINER ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. TITLE & META INFO SECTION
        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel metaLabel = new JLabel("Genre: " + movie.getGenre() + "  |  Year: " + movie.getReleaseYear() + "  |  Rating: " + movie.getRating() + "/10");
        metaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        metaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. OVERVIEW SECTION
        JLabel overviewHeader = new JLabel("Overview:");
        overviewHeader.setFont(new Font("Arial", Font.BOLD, 14));
        overviewHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea overviewArea = new JTextArea(movie.getOverview());
        overviewArea.setWrapStyleWord(true);
        overviewArea.setLineWrap(true);
        overviewArea.setEditable(false);
        overviewArea.setBackground(getBackground());
        overviewArea.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JScrollPane overviewScroll = new JScrollPane(overviewArea);
        overviewScroll.setPreferredSize(new Dimension(800, 100));
        overviewScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        overviewScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 3. HORIZONTAL 5x2 MATRIX RECOMMENDATIONS SECTION
        JPanel recSection = new JPanel(new BorderLayout());
        recSection.setBorder(BorderFactory.createTitledBorder("People Also Liked"));
        recSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        recSection.setPreferredSize(new Dimension(800, 290)); 
        recSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        // Layout matrix: 2 rows fixed, dynamic columns growing horizontally
        JPanel matrixPanel = new JPanel(new GridLayout(2, 0, 12, 12));
        
        // Fetch up to 10 recommended items
        ArrayList<Movie> recommendations = RecommendationEngine.getTopRecommendations(movie, 10);
        
        // Add actual recommendations to the grid
        for (Movie rec : recommendations) {
            matrixPanel.add(createMiniMovieCard(rec));
        }
        
        // Fallback display if absolutely no matching recommendations exist
        if (recommendations.isEmpty()) {
            JLabel noRecsLabel = new JLabel("No matching recommendations available for this genre.", SwingConstants.CENTER);
            noRecsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            matrixPanel.setLayout(new BorderLayout());
            matrixPanel.add(noRecsLabel, BorderLayout.CENTER);
        } else {
            // Prevent tile resizing issues if we have less than 10 movies in the pool.
            int itemsInMatrix = recommendations.size();
            if (itemsInMatrix < 10) {
                for (int i = 0; i < (10 - itemsInMatrix); i++) {
                    JPanel placeholder = new JPanel();
                    placeholder.setOpaque(false); // Invisible placeholder
                    matrixPanel.add(placeholder);
                }
            }
        }
        
        // ScrollPane housing the matrix grid
        JScrollPane horizontalScroll = new JScrollPane(matrixPanel);
        horizontalScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        horizontalScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        horizontalScroll.getHorizontalScrollBar().setUnitIncrement(16);
        horizontalScroll.setBorder(null);
        
        JPanel alignmentWrapper = new JPanel(new BorderLayout());
        alignmentWrapper.add(horizontalScroll, BorderLayout.WEST);
        
        recSection.add(alignmentWrapper, BorderLayout.CENTER);

        // 4. ACTION BUTTONS SECTION
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton addWishlist = new JButton("Add to Wishlist");
        JButton addWatched = new JButton("Mark as Watched");
        buttonPanel.add(addWishlist);
        buttonPanel.add(addWatched);

        // --- ASSEMBLE VIEWS VERTICALLY ---
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(metaLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(overviewHeader);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(overviewScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(recSection);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    /**
     * Helper method to generate customized miniature clickable movie cards 
     * for recommendations.
     */
    private JPanel createMiniMovieCard(Movie movie) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        card.setBackground(Color.WHITE);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Strict fixed card dimensions tailored for a 5-column display
        card.setPreferredSize(new Dimension(145, 110));
        card.setMinimumSize(new Dimension(145, 110));
        card.setMaximumSize(new Dimension(145, 110));

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel genreLabel = new JLabel(movie.getGenre());
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        genreLabel.setForeground(Color.GRAY);
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel ratingLabel = new JLabel("★ " + movie.getRating());
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 11));
        ratingLabel.setForeground(new Color(218, 165, 32));
        ratingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 3)));
        card.add(genreLabel);
        card.add(Box.createRigidArea(new Dimension(0, 3)));
        card.add(ratingLabel);
        card.add(Box.createVerticalGlue());

        // Single click displays details of this recommendation card recursively
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new MovieDetails(movie).setVisible(true);
                dispose(); 
            }
        });

        return card;
    }
}