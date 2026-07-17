import java.awt.*;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Wishlist extends JFrame {
    private DefaultListModel<Movie> listModel;
    private JList<Movie> wishlistJList;
    private Home parentHome;

    public Wishlist(Home home) {
        this.parentHome = home;
        setTitle("My Wishlist Collection");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(home);
        
        Color wineRed = new Color(45, 6, 11);
        Color textWhite = new Color(245, 245, 245);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(wineRed);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        listModel = new DefaultListModel<>();
        wishlistJList = new JList<>(listModel);
        
        wishlistJList.setBackground(new Color(0, 0, 0, 130));
        wishlistJList.setForeground(textWhite);
        wishlistJList.setFont(new Font("Arial", Font.PLAIN, 14));
        wishlistJList.setCellRenderer(new DefaultListCellRenderer() {
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

        JScrollPane scrollPane = new JScrollPane(wishlistJList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new LineBorder(new Color(255, 255, 255, 30)));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);
        
        JButton popBtn = new JButton("Pop Last Added");
        popBtn.setBackground(Color.BLACK);
        popBtn.setForeground(textWhite);
        popBtn.setFocusPainted(false);
        popBtn.setFont(new Font("Arial", Font.BOLD, 13));
        popBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 60, 60), 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        
        btnPanel.add(popBtn);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);
        add(mainPanel);

        popBtn.addActionListener(e -> {
            Movie popped = DataStructures.removeFromWishlist();
            if (popped != null) {
                JOptionPane.showMessageDialog(this, "Removed from Wishlist: " + popped.getTitle(), "Popped from Stack", JOptionPane.INFORMATION_MESSAGE);
                refreshList();
            } else {
                JOptionPane.showMessageDialog(this, "Your Wishlist is already empty!", "Empty Stack", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void refreshList() {
        listModel.clear();
        Stack<Movie> stack = DataStructures.getWishlist();
        for (int i = stack.size() - 1; i >= 0; i--) {
            listModel.addElement(stack.get(i));
        }
    }
}