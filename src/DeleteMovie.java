import java.awt.*;
import javax.swing.*;

public class DeleteMovie extends JFrame {
    private JList<Movie> movieJList;
    private DefaultListModel<Movie> listModel;
    private Home parentHome;

    public DeleteMovie(Home home) {
        this.parentHome = home;
        setTitle("Delete a Movie");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(home);
        setLayout(new BorderLayout(10, 10));

        listModel = new DefaultListModel<>();
        movieJList = new JList<>(listModel);
        
        refreshList();

        add(new JScrollPane(movieJList), BorderLayout.CENTER);

        JButton deleteBtn = new JButton("Delete Selected Movie");
        add(deleteBtn, BorderLayout.SOUTH);

        deleteBtn.addActionListener(e -> {
            Movie selected = movieJList.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        this, 
                        "Are you sure you want to delete '" + selected.getTitle() + "'?", 
                        "Confirm Deletion", 
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // Delete in DB
                    Main.getDatabase().deleteMovie(selected.getMovieId());

                    // Sync database and local caches
                    Main.movies = Main.getDatabase().getMovies();
                    if (Main.movies.size() > 1) {
                        DataStructures.mergeSort(Main.movies, 0, Main.movies.size() - 1);
                    }
                    DataStructures.createGenreMap(Main.movies);

                    JOptionPane.showMessageDialog(this, "Movie deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshList();
                    parentHome.refreshMovieGrid();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please choose a movie to delete.", "Selection Missing", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void refreshList() {
        listModel.clear();
        for (Movie m : Main.getMovies()) {
            listModel.addElement(m);
        }
    }
}