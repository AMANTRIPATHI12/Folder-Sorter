import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FolderSorterGUI {

    static Map<String, String> folderMap = new HashMap<>();

    static {
        folderMap.put("jpg", "Images");
        folderMap.put("jpeg", "Images");
        folderMap.put("png", "Images");
        folderMap.put("gif", "Images");
        folderMap.put("mp4", "Videos");
        folderMap.put("avi", "Videos");
        folderMap.put("mp3", "Audio");
        folderMap.put("wav", "Audio");
        folderMap.put("pdf", "Documents");
        folderMap.put("docx", "Documents");
        folderMap.put("xlsx", "Documents");
        folderMap.put("zip", "Archives");
        folderMap.put("exe", "Applications");
    }

    public static void main(String[] args) {
        // Show input dialog
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Folder Sorter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);

            String path = JOptionPane.showInputDialog(frame, "Enter directory path to sort:");
            if (path == null || path.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No directory entered. Exiting.");
                return;
            }

            File dir = new File(path.trim());
            if (!dir.exists() || !dir.isDirectory()) {
                JOptionPane.showMessageDialog(frame, "Invalid directory path!");
                return;
            }

            sortFiles(dir);

            JOptionPane.showMessageDialog(frame, "Sorting complete!");
            System.exit(0);
        });
    }

    private static void sortFiles(File dir) {
        File[] files = dir.listFiles();

        if (files == null) return;

        for (File file : files) {
            if (file.isFile()) {
                String ext = getExtension(file.getName()).toLowerCase();
                String folderName = folderMap.getOrDefault(ext, "Others");

                File targetFolder = new File(dir, folderName);
                if (!targetFolder.exists()) {
                    targetFolder.mkdir();
                }

                File targetFile = new File(targetFolder, file.getName());
                if (file.renameTo(targetFile)) {
                    System.out.println("Moved: " + file.getName() + " → " + folderName);
                }
            }
        }
    }

    private static String getExtension(String name) {
        int dot = name.lastIndexOf(".");
        return (dot > 0) ? name.substring(dot + 1) : "";
    }
}
