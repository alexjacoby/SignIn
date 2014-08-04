package ajacoby.signin;

import au.com.bytecode.opencsv.CSVParser;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Launches app by loading data and displaying main window.
 * <p/>
 * TODO: Support multiple sections
 *
 * @author Alex Jacoby
 * @version 7/31/14
 */
public class SignIn {
   private static final String SECTIONS_FILE = "sections.csv";

   private static Section _section;
   private static List<Student> _students;
   public static final Logger log = Logger.getLogger(SignIn.class.getSimpleName());

   public static void main(String[] args) {
      initFromFile();
      SignInForm sif = new SignInForm(_section, _students);
   } // main()

   /**
    * Loads data from SECTIONS_FILE, initializing _section and _students.
    */
   private static void initFromFile() {
      try (
            InputStream is = SignIn.class.getResourceAsStream(SECTIONS_FILE);
            Scanner configFile = new Scanner(is);
      ) {
         String sectionLine = configFile.nextLine();
         CSVParser parser = new CSVParser();
         String[] sectionInfo = parser.parseLine(sectionLine);
         if (sectionInfo.length != 3) {
            throw new IllegalStateException("Expected 3 CSV fields but found " + sectionLine);
         }
         int sectionId = Integer.parseInt(sectionInfo[0]);
         String name = sectionInfo[1];
         // DateFormat timeFormat = new SimpleDateFormat("HH:mm");
         String[] times = sectionInfo[2].split("-");
         if (times.length != 2) {
            throw new IllegalStateException("Expected time range (HH:MM-HH:MM) but found " + sectionInfo[2]);
         }
         String[] startHrMin = times[0].split(":");
         String[] stopHrMin  = times[1].split(":");
         //timeFormat.setCalendar(Calendar.getInstance());
         //Date startTimeDate = timeFormat.parse(times[0]);
         //Date stopTimeDate = timeFormat.parse(times[1]);
         Calendar startTime = Calendar.getInstance();
         Calendar stopTime = Calendar.getInstance();
         startTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHrMin[0]));
         startTime.set(Calendar.MINUTE,      Integer.parseInt(startHrMin[1]));
         startTime.set(Calendar.SECOND,      0);
         stopTime.set( Calendar.HOUR_OF_DAY, Integer.parseInt(stopHrMin[0]));
         stopTime.set( Calendar.MINUTE,      Integer.parseInt(stopHrMin[1]));
         stopTime.set( Calendar.SECOND,      0);
         logFine("start: " + startTime.getTime() + ", stop: " + stopTime.getTime());
         _section = new Section(sectionId, name, startTime, stopTime);
         // Read students
         String studentHeader = configFile.nextLine();
         if (!studentHeader.equals("[students]")) {
            throw new IllegalStateException("Expected \"[students]\" but found " + studentHeader);
         }
         _students = new ArrayList<>();
         String studentLine;
         while (configFile.hasNextLine()) {
            studentLine = configFile.nextLine();
            if (studentLine.equals("[/students]")) { break; }
            logFine("StudentLine: " + studentLine);
            String[] studentInfo = parser.parseLine(studentLine);
            int studentId = Integer.parseInt(studentInfo[0]);
            String studentName = studentInfo[1];
            String studentContacts = studentInfo[2];
            Student s = new Student(studentId, studentName, studentContacts);
            _students.add(s);
         }
      } catch (Exception e) {
         logException("Reading " + SECTIONS_FILE, e);
      }
   }

   private static void logFine(String msg) {
      System.out.println(msg);
   }

   private static void logError(String msg) {
      System.err.println(msg);
   }

   private static void logException(String msg, Exception e) {
      System.err.println(msg);
      e.printStackTrace();
   }
}
