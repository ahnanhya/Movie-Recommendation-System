import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MovieDetails extends JFrame {
    private Movie currentMovie;

    public MovieDetails(Movie movie) {
        this.currentMovie = movie;
        setTitle("Movie Details: " + movie.getTitle());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // --- DETAILS PANEL ---
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        
        JLabel metaLabel = new JLabel("Genre: " + movie.getGenre() + "  |  Year: " + movie.getReleaseYear() + "  |  Rating: " + movie.getRating() + "/10");
        metaLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextArea overviewArea = new JTextArea(movie.getOverview());
        overviewArea.setWrapStyleWord(true);
        overviewArea.setLineWrap(true);
        overviewArea.setEditable(false);
        overviewArea.setBackground(getBackground());
        overviewArea.setFont(new Font("Arial", Font.PLAIN, 13));

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(metaLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanel.add(new JLabel("Overview:"));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(new JScrollPane(overviewArea));

        add(infoPanel, BorderLayout.CENTER);

        // --- BUTTONS PANEL ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton addWishlist = new JButton("Add to Wishlist");
        JButton addWatched = new JButton("Mark as Watched");

        buttonPanel.add(addWishlist);
        buttonPanel.add(addWatched);
        add(buttonPanel, BorderLayout.SOUTH);

        // --- RECOMMENDATIONS PANEL ---
        JPanel recPanel = new JPanel(new BorderLayout());
        recPanel.setBorder(BorderFactory.createTitledBorder("People Also Liked"));
        DefaultListModel<Movie> recListModel = new DefaultListModel<>();
        
        // Fetch recommendations based on the same genre
        ArrayList<Movie> recommendations = RecommendationEngine.getTopRecommendations(movie, 4);
        for (Movie rec : recommendations) {
            recListModel.addElement(rec);
        }
        
        JList<Movie> recList = new JList<>(recListModel);
        recList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recPanel.add(new JScrollPane(recList), BorderLayout.CENTER);
        add(recPanel, BorderLayout.EAST);

        // --- ACTION LISTENERS ---
        addWishlist.addActionListener(e -> {
            RecommendationEngine.addToWishlist(currentMovie);
            JOptionPane.showMessageDialog(this, "Added to Wishlist Stack!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        addWatched.addActionListener(e -> {
            RecommendationEngine.addToWatched(currentMovie);
            JOptionPane.showMessageDialog(this, "Added to Watched Stack!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Double-clicking recommendation opens details for that movie
        recList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Movie selected = recList.getSelectedValue();
                    if (selected != null) {
                        new MovieDetails(selected).setVisible(true);
                        dispose();
                    }
                }
            }
        });
    }
}