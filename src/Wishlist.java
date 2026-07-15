import java.awt.*;
import java.util.Stack;
import javax.swing.*;

public class Wishlist extends JFrame {
    private DefaultListModel<Movie> listModel;
    private JList<Movie> wishlistJList;
    private Home parentHome;

    public Wishlist(Home home) {
        this.parentHome = home;
        setTitle("My Wishlist");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(home);
        setLayout(new BorderLayout(10, 10));

        listModel = new DefaultListModel<>();
        wishlistJList = new JList<>(listModel);
        
        refreshList();

        add(new JScrollPane(wishlistJList), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton popBtn = new JButton("Pop Top Movie");
        btnPanel.add(popBtn);
        add(btnPanel, BorderLayout.SOUTH);

        popBtn.addActionListener(e -> {
            Movie popped = DataStructures.removeFromWishlist();
            if (popped != null) {
                JOptionPane.showMessageDialog(this, "Removed: " + popped.getTitle(), "Popped from Stack", JOptionPane.INFORMATION_MESSAGE);
                refreshList();
            } else {
                JOptionPane.showMessageDialog(this, "Wishlist is already empty!", "Empty Stack", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void refreshList() {
        listModel.clear();
        Stack<Movie> stack = DataStructures.getWishlist();
        // Displays top elements first
        for (int i = stack.size() - 1; i >= 0; i--) {
            listModel.addElement(stack.get(i));
        }
    }
}