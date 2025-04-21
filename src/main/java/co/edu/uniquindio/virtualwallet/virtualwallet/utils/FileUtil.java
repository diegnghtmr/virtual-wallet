package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import java.beans.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class has static methods that allow them to be used without creating instances of the class.
 * What was done was to create this library for file handling.
 */
public class FileUtil {

    static String systemDate = "";

    /**
     * This method receives a string with the content to be saved in the file.
     * @param path is the path where the file is located.
     * @throws IOException
     */
    public static void saveFile(String path, String content, Boolean appendContent) throws IOException {
        FileWriter fw = new FileWriter(path, appendContent);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(content);
        bfw.close();
        fw.close();
    }

    /**
     * This method returns the content of the file located at a path, with the list of strings.
     * @param path
     * @return
     * @throws IOException
     */
    public static ArrayList<String> readFile(String path) throws IOException {
        ArrayList<String> content = new ArrayList<String>();
        FileReader fr = new FileReader(path);
        BufferedReader bfr = new BufferedReader(fr);
        String line = "";
        while ((line = bfr.readLine()) != null) {
            content.add(line);
        }
        bfr.close();
        fr.close();
        return content;
    }

    public static void saveLogRecord(String logMessage, int level, String action, String logFilePath) {
        String log = "";
        Logger LOGGER = Logger.getLogger(action);
        FileHandler fileHandler = null;
        loadSystemDate();
        try {
            fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            switch (level) {
                case 1:
                    LOGGER.log(Level.INFO, action + "," + logMessage + "," + systemDate);
                    break;
                case 2:
                    LOGGER.log(Level.WARNING, action + "," + logMessage + "," + systemDate);
                    break;
                case 3:
                    LOGGER.log(Level.SEVERE, action + "," + logMessage + "," + systemDate);
                    break;
                default:
                    break;
            }
        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {
            fileHandler.close();
        }
    }

    private static void loadSystemDate() {
        String dayStr = "";
        String monthStr = "";
        String yearStr = "";

        Calendar cal1 = Calendar.getInstance();

        int day = cal1.get(Calendar.DATE);
        int month = cal1.get(Calendar.MONTH) + 1;
        int year = cal1.get(Calendar.YEAR);
        int hour = cal1.get(Calendar.HOUR);
        int minute = cal1.get(Calendar.MINUTE);

        if (day < 10) {
            dayStr += "0" + day;
        } else {
            dayStr += "" + day;
        }
        if (month < 10) {
            monthStr += "0" + month;
        } else {
            monthStr += "" + month;
        }

        systemDate = year + "-" + monthStr + "-" + dayStr;
    }

    //------------------------------------SERIALIZATION and XML

    /**
     * Writes the object sent to the file passed to it.
     * @param filePath path of the file to be written.
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static Object loadSerializedResource(String filePath) throws Exception {
        Object aux = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
            aux = ois.readObject();
        } catch (Exception e2) {
            throw e2;
        } finally {
            if (ois != null)
                ois.close();
        }
        return aux;
    }

    public static void saveSerializedResource(String filePath, Object object) throws Exception {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(object);
        } catch (Exception e) {
            throw e;
        } finally {
            if (oos != null)
                oos.close();
        }
    }

    public static Object loadSerializedXMLResource(String filePath) throws IOException {
        XMLDecoder xmlDecoder;
        Object xmlObject;

        xmlDecoder = new XMLDecoder(new FileInputStream(filePath));
        xmlObject = xmlDecoder.readObject();
        xmlDecoder.close();
        return xmlObject;
    }

    public static void saveSerializedXMLResource(String filePath, Object object) throws IOException {
        XMLEncoder xmlEncoder = null;
        try {
            xmlEncoder = new XMLEncoder(new FileOutputStream(filePath));
            // Registrar un PersistenceDelegate para LocalDate
            xmlEncoder.setPersistenceDelegate(LocalDate.class, new DefaultPersistenceDelegate() {
                @Override
                protected Expression instantiate(Object obj, Encoder enc) {
                    LocalDate date = (LocalDate) obj;
                    return new Expression(obj, LocalDate.class, "of", new Object[]{
                            date.getYear(),
                            date.getMonthValue(),
                            date.getDayOfMonth()
                    });
                }
            });
            xmlEncoder.writeObject(object);
        } catch (IOException e) {
            throw e;
        } finally {
            if (xmlEncoder != null) {
                xmlEncoder.close();
            }
        }
    }
}