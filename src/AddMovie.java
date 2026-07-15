import java.awt.*;
import javax.swing.*;

public class AddMovie extends JFrame {
    private JTextField titleField, genreField, yearField, ratingField;
    private JTextArea overviewField;
    private Home parentHome;

    public AddMovie(Home home) {
        this.parentHome = home;
        setTitle("Add New Movie");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(home);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Genre:"));
        genreField = new JTextField();
        formPanel.add(genreField);

        formPanel.add(new JLabel("Release Year:"));
        yearField = new JTextField();
        formPanel.add(yearField);

        formPanel.add(new JLabel("Rating:"));
        ratingField = new JTextField();
        formPanel.add(ratingField);

        formPanel.add(new JLabel("Overview:"));
        overviewField = new JTextArea();
        overviewField.setLineWrap(true);
        formPanel.add(new JScrollPane(overviewField));

        add(formPanel, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save Movie");
        add(saveBtn, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                String genre = genreField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                double rating = Double.parseDouble(ratingField.getText().trim());
                String overview = overviewField.getText().trim();

                if (title.isEmpty() || genre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Title and Genre cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Movie movie = new Movie();
                movie.setTitle(title);
                movie.setGenre(genre);
                movie.setReleaseYear(year);
                movie.setRating(rating);
                movie.setOverview(overview);

                // Add to database
                Main.getDatabase().addMovie(movie);

                // Sync data local copy configurations
                Main.movies = Main.getDatabase().getMovies();
                if (Main.movies.size() > 1) {
                    DataStructures.mergeSort(Main.movies, 0, Main.movies.size() - 1);
                }
                DataStructures.createGenreMap(Main.movies);

                JOptionPane.showMessageDialog(this, "Movie successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh main screen display
                parentHome.refreshMovieGrid();
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Year and Rating.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}