import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class AddMovie extends JFrame {
    private JTextField titleField, genreField, yearField, ratingField;
    private JTextArea overviewField;
    private Home parentHome;

    public AddMovie(Home home) {
        this.parentHome = home;
        setTitle("Add New Movie");
        setSize(420, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(home);
        
        Color wineRed = new Color(45, 6, 11);
        Color textWhite = new Color(245, 245, 245);
        Color inputBg = new Color(20, 4, 6);

        JPanel mainContainer = new JPanel(new BorderLayout(15, 15));
        mainContainer.setBackground(wineRed);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 12, 12)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 130)); // Translucent backdrop wrapper
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(255, 255, 255, 30), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));

        formPanel.add(createWhiteLabel("Title:"));
        titleField = new JTextField(); styleField(titleField, inputBg, textWhite);
        formPanel.add(titleField);

        formPanel.add(createWhiteLabel("Genre:"));
        genreField = new JTextField(); styleField(genreField, inputBg, textWhite);
        formPanel.add(genreField);

        formPanel.add(createWhiteLabel("Release Year:"));
        yearField = new JTextField(); styleField(yearField, inputBg, textWhite);
        formPanel.add(yearField);

        formPanel.add(createWhiteLabel("Rating:"));
        ratingField = new JTextField(); styleField(ratingField, inputBg, textWhite);
        formPanel.add(ratingField);

        formPanel.add(createWhiteLabel("Overview:"));
        overviewField = new JTextArea();
        overviewField.setLineWrap(true);
        overviewField.setWrapStyleWord(true);
        overviewField.setBackground(inputBg);
        overviewField.setForeground(textWhite);
        overviewField.setCaretColor(textWhite);
        
        JScrollPane areaScroll = new JScrollPane(overviewField);
        areaScroll.setBorder(new LineBorder(new Color(255, 255, 255, 30)));
        formPanel.add(areaScroll);

        mainContainer.add(formPanel, BorderLayout.CENTER);

        JButton saveBtn = new JButton("Save Movie");
        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(textWhite);
        saveBtn.setFocusPainted(false);
        saveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        saveBtn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainContainer.add(saveBtn, BorderLayout.SOUTH);

        add(mainContainer);

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

                Main.getDatabase().addMovie(movie);
                Main.movies = Main.getDatabase().getMovies();
                if (Main.movies.size() > 1) {
                    DataStructures.mergeSort(Main.movies, 0, Main.movies.size() - 1);
                }
                DataStructures.createGenreMap(Main.movies);

                JOptionPane.showMessageDialog(this, "Movie successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                parentHome.refreshMovieGrid();
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Year and Rating.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel createWhiteLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        return lbl;
    }

    private void styleField(JTextField field, Color bg, Color fg) {
        field.setBackground(bg);
        field.setForeground(fg);
        field.setCaretColor(fg);
        field.setBorder(new LineBorder(new Color(255, 255, 255, 30), 1));
    }
}