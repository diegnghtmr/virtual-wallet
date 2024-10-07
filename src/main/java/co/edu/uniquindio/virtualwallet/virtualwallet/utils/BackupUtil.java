package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class BackupUtil {
    private static final String BACKUP_DIR = "src/main/resources/persistence/backup"; // Update with actual backup directory path
    private static final int MAX_BACKUPS = 3;

    public static void backupXMLFile(String originalFilePath) {
        try {
            // Ensure backup directory exists
            File backupDir = new File(BACKUP_DIR);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            // List all backup files
            File[] backupFiles = backupDir.listFiles((dir, name) -> name.endsWith(".xml"));
            if (backupFiles != null && backupFiles.length >= MAX_BACKUPS) {
                // Sort files by last modified date
                Arrays.sort(backupFiles, Comparator.comparingLong(File::lastModified));
                // Delete the oldest file
                backupFiles[0].delete();
            }

            // Extract original file name without extension
            String originalFileName = new File(originalFilePath).getName();
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));

            // Generate backup file name
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy_HH_mm_ss"));
            String backupFileName = baseName + "_" + timestamp + ".xml";
            Path backupFilePath = Paths.get(BACKUP_DIR, backupFileName);

            // Copy original file to backup location
            Files.copy(Paths.get(originalFilePath), backupFilePath);
            System.out.println("Backup created at: " + backupFilePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}