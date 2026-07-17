import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

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
        
        // --- COLOR PALETTE ---
        Color wineRed = new Color(45, 6, 11);
        Color textWhite = new Color(245, 245, 245);

        // --- MAIN CONTAINER ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(wineRed);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        listModel = new DefaultListModel<>();
        movieJList = new JList<>(listModel);
        
        // Style list component with translucent black look
        movieJList.setBackground(new Color(0, 0, 0, 130));
        movieJList.setForeground(textWhite);
        movieJList.setFont(new Font("Arial", Font.PLAIN, 14));
        movieJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(isSelected);
                label.setBackground(isSelected ? new Color(255, 255, 255, 40) : new Color(0, 0, 0, 0));
                label.setForeground(Color.WHITE);
                label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
                return label;
            }
        });
        
        refreshList();

        JScrollPane scrollPane = new JScrollPane(movieJList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new LineBorder(new Color(255, 255, 255, 30)));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- BUTTON INTERFACE (Solid Black / White Text) ---
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);
        
        JButton deleteBtn = new JButton("Delete Selected Movie");
        deleteBtn.setBackground(Color.BLACK);
        deleteBtn.setForeground(textWhite);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 13));
        deleteBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 60, 60), 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        
        btnPanel.add(deleteBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // --- CONTROLLER LOGIC ---
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