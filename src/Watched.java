import java.awt.*;
import java.util.Stack;
import javax.swing.*;

public class Watched extends JFrame {
    private DefaultListModel<Movie> listModel;
    private JList<Movie> watchedJList;
    private Home parentHome;

    public Watched(Home home) {
        this.parentHome = home;
        setTitle("Watched Movies");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(home);
        setLayout(new BorderLayout(10, 10));

        listModel = new DefaultListModel<>();
        watchedJList = new JList<>(listModel);
        
        refreshList();

        add(new JScrollPane(watchedJList), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton popBtn = new JButton("Pop Last Watched");
        btnPanel.add(popBtn);
        add(btnPanel, BorderLayout.SOUTH);

        popBtn.addActionListener(e -> {
            Movie popped = DataStructures.removeFromWatched();
            if (popped != null) {
                JOptionPane.showMessageDialog(this, "Removed from Watched: " + popped.getTitle(), "Popped from Stack", JOptionPane.INFORMATION_MESSAGE);
                refreshList();
            } else {
                JOptionPane.showMessageDialog(this, "Watched history is empty!", "Empty Stack", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void refreshList() {
        listModel.clear();
        Stack<Movie> stack = DataStructures.getWatched();
        for (int i = stack.size() - 1; i >= 0; i--) {
            listModel.addElement(stack.get(i));
        }
    }
}