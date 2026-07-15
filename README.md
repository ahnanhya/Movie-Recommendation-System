# Movie Recommendation System 🎬

A desktop-based Movie Recommendation System built using **Java Swing** for the graphical user interface and **MySQL** for data persistence. The application features a collection of over 80 Tamil movies across multiple genres (Action, Comedy, Romance, Thriller, Drama, and Sci-Fi). Users can view item profiles, add films to custom lists, and receive dynamic, scrollable recommendations.

## Features ✨

*   **Dynamic Dashboard:** Filter movies instantly by genre, search by title keyword, or view targeted list metrics.
*   **Intuitive Details Screen:** Displays comprehensive metrics including title, year, rating, and written log summaries.
*   **$5 \times 2$ Recommendation Matrix:** Shows up to 10 contextually relevant movies ("People Also Liked") in a sleek horizontal grid pattern layout.
*   **Recursive Discovery:** Click on any recommendation item card to instantly open its profile seamlessly.
*   **Persistent Tracking:** Save items straight into local tracking sets with "Add to Wishlist" and "Mark as Watched" functionalities.

## Prerequisites 🛠️

Before setting up the project locally, make sure you have installed:
*   **Java Development Kit (JDK 8 or higher)**
*   **MySQL Server**
*   **MySQL Connector/J (JDBC Driver)**

## Installation & Setup 🚀

### 1. Database Setup
1. Open your MySQL Command Line Client or preferred GUI tool (like MySQL Workbench).
2. Execute the setup script provided in the `data/movies.sql` file to create the schema and populate the seeding rows:
   ```sql
   SOURCE path/to/data/movies.sql;