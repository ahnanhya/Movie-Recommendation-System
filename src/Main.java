import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main {
    public static Database database;
    public static ArrayList<Movie> movies;

    public static void main(String[] args) {
        // Track when the splash screen was started
        long startTime = System.currentTimeMillis();

        // --- 1. SHOW SPLASH SCREEN (IMAGE ONLY) ---
        JWindow splash = createSplashScreen();
        splash.setVisible(true);

        // --- 2. BACKEND LOAD SEQUENCE ---
        try {
            // Connect to Database
            database = new Database(); 

            // Load Movies
            movies = database.getMovies(); 

            // Sort Movies by Rating
            if (movies.size() > 1) { 
                DataStructures.mergeSort(movies, 0, movies.size() - 1); 
            }

            // Create Genre HashMap
            DataStructures.createGenreMap(movies); 

            // Calculate remaining sleep time to hit exactly 5 seconds (5000ms)
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = 3000 - elapsedTime;
            if (remainingTime > 0) {
                Thread.sleep(remainingTime);
            }

        } catch (Exception e) {
            System.out.println("Error during initialization sequence:");
            e.printStackTrace();
        }

        // --- 3. DESTROY SPLASH & LAUNCH HOME ---
        splash.dispose();

        System.out.println("Movie Recommendation System Started"); 
        System.out.println("Movies Loaded : " + movies.size()); 

        javax.swing.SwingUtilities.invokeLater(() -> {
            new Home().setVisible(true); 
        });
    }

    /**
     * Creates a minimal, frameless window holding only the scaled target image.
     */
    private static JWindow createSplashScreen() {
        JWindow window = new JWindow();
        window.setSize(300, 300); // Fixed square dimension matching the image target
        window.setLocationRelativeTo(null); // Center perfectly on user's monitor

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 6, 11)); // Wine red fallback back-plate

        JLabel imageLabel = new JLabel();
        try {
            String imagePath = "C:\\Users\\ahnan\\Downloads\\Netfix.jpg";
            ImageIcon originalIcon = new ImageIcon(imagePath);
            
            // Scaled smoothly to cover the entire 300x300 surface area
            Image scaledImage = originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.out.println("Splash image failed to load from local file path.");
        }
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(imageLabel, BorderLayout.CENTER);
        window.add(panel);
        return window;
    }

    public static ArrayList<Movie> getMovies() {
        return movies; 
    }

    public static Database getDatabase() {
        return database; 
    }
}