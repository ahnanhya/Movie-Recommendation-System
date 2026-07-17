import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class MovieDetails extends JFrame {
    private Movie currentMovie;

    public MovieDetails(Movie movie) {
        this.currentMovie = movie;
        setTitle("Movie Details: " + movie.getTitle());
        setSize(860, 750); // Expanded width to easily fit 5 columns of cards across
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // --- COLOR PALETTE ---
        Color wineRed = new Color(45, 6, 11);
        Color translucentBlack = new Color(0, 0, 0, 130);
        Color textWhite = new Color(245, 245, 245);
        Color textMuted = new Color(190, 180, 180);
        Color buttonBlack = new Color(20, 20, 20);

        // --- MAIN CONTAINER ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(wineRed); // Apply Wine Red background

        // 1. TITLE & META INFO SECTION
        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(textWhite);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel metaLabel = new JLabel("Genre: " + movie.getGenre() + "  |  Year: " + movie.getReleaseYear() + "  |  Rating: " + movie.getRating() + "/10");
        metaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        metaLabel.setForeground(textMuted);
        metaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 2. OVERVIEW SECTION
        JLabel overviewHeader = new JLabel("Overview:");
        overviewHeader.setFont(new Font("Arial", Font.BOLD, 15));
        overviewHeader.setForeground(textWhite);
        overviewHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea overviewArea = new JTextArea(movie.getOverview());
        overviewArea.setWrapStyleWord(true);
        overviewArea.setLineWrap(true);
        overviewArea.setEditable(false);
        overviewArea.setBackground(translucentBlack); // Card view for the text log
        overviewArea.setForeground(textWhite);
        overviewArea.setFont(new Font("Arial", Font.PLAIN, 14));
        overviewArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane overviewScroll = new JScrollPane(overviewArea);
        overviewScroll.setPreferredSize(new Dimension(800, 100));
        overviewScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        overviewScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        overviewScroll.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 30)));
        overviewScroll.setOpaque(false);
        overviewScroll.getViewport().setOpaque(false);

        // 3. HORIZONTAL 5x2 MATRIX RECOMMENDATIONS SECTION
        JPanel recSection = new JPanel(new BorderLayout());
        TitledBorder titledBorder = BorderFactory.createTitledBorder("People Also Liked");
        titledBorder.setTitleColor(textWhite);
        recSection.setBorder(titledBorder);
        recSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        recSection.setPreferredSize(new Dimension(800, 290)); 
        recSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        recSection.setOpaque(false);

        // Layout matrix: 2 rows fixed, dynamic columns growing horizontally
        JPanel matrixPanel = new JPanel(new GridLayout(2, 0, 12, 12));
        matrixPanel.setOpaque(false);
        
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
            noRecsLabel.setForeground(textMuted);
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
        horizontalScroll.setOpaque(false);
        horizontalScroll.getViewport().setOpaque(false);
        
        JPanel alignmentWrapper = new JPanel(new BorderLayout());
        alignmentWrapper.setOpaque(false);
        alignmentWrapper.add(horizontalScroll, BorderLayout.WEST);
        
        recSection.add(alignmentWrapper, BorderLayout.CENTER);

        // 4. ACTION BUTTONS SECTION (Solid Black with White Text)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton addWishlist = styleButton(new JButton("Add to Wishlist"), buttonBlack, textWhite);
        JButton addWatched = styleButton(new JButton("Mark as Watched"), buttonBlack, textWhite);
        
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

        // --- WORKING BUTTON ACTION LISTENERS ---
        addWishlist.addActionListener(e -> {
            RecommendationEngine.addToWishlist(currentMovie);
            JOptionPane.showMessageDialog(this, "Added to Wishlist!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        addWatched.addActionListener(e -> {
            RecommendationEngine.addToWatched(currentMovie);
            JOptionPane.showMessageDialog(this, "Marked as Watched!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private JButton styleButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        return btn;
    }

    /**
     * Helper method to generate customized miniature clickable movie cards 
     * for recommendations with a beautiful translucent layer override.
     */
    private JPanel createMiniMovieCard(Movie movie) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Renders the translucent glass alpha texture smoothly
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 0, 0, 130)); // Translucent black
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 30), 1), // Soft glow rim
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Strict fixed card dimensions tailored for a 5-column display
        card.setPreferredSize(new Dimension(145, 110));
        card.setMinimumSize(new Dimension(145, 110));
        card.setMaximumSize(new Dimension(145, 110));

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setForeground(new Color(245, 245, 245));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel genreLabel = new JLabel(movie.getGenre());
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        genreLabel.setForeground(new Color(180, 170, 170));
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel ratingLabel = new JLabel("★ " + movie.getRating());
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 11));
        ratingLabel.setForeground(new Color(255, 204, 51)); // Gold star
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