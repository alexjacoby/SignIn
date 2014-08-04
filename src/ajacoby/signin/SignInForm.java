package ajacoby.signin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

/**
 * Sign-in form for students.
 * <p/>
 * Ideally has a grid of students (in alpha or seating chart layout).
 * Teacher can use it to take attendance (clicking), then students can "sign in"
 * by clicking their names after teacher has finished taking attendance.
 *
 * @author Alex Jacoby
 * @version 7/31/14
 */
public class SignInForm extends JFrame {

   private static final int MAX_ROWS = 10;
   private static final int MAX_COLS = 4;

   public SignInForm(final Section section, final List<Student> students) {
      if (students.size() > MAX_ROWS * MAX_COLS) {
         System.err.println("Too many students to display! Max:" + (MAX_ROWS * MAX_COLS) + ", found:" + students.size());
      }
      Container content = getContentPane();
      content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
      JLabel titleLabel = new JLabel("<html><h1>Click your name to sign in:</h1></html>");
      // Make title align left!
      content.add(titleLabel);
      JPanel studentPanel = initStudentPanel(section, students);
      content.add(studentPanel);
      // Make full-screen and display it
      setExtendedState(JFrame.MAXIMIZED_BOTH);
      validate();
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setVisible(true);
   }

   private JPanel initStudentPanel(final Section section, final List<Student> students) {
      // Fill a grid in row-major order, starting in top-left.
      int rows = (students.size() < MAX_ROWS)? students.size() : MAX_ROWS;
      int cols = 0;
      final JPanel panel = new JPanel(new GridLayout(rows, cols));
      for (final Student student : students) {
         JButton button = new JButton(student.getName());
         button.addActionListener(new ActionListener() {
            static final long MS_IN_MIN = 1000 * 60;
            static final long MS_IN_HALF_SEC = 500;

            @Override
            public void actionPerformed(ActionEvent e) {
               Calendar now = Calendar.getInstance();
               long diff = now.getTimeInMillis() - section.getStartTime().getTimeInMillis();
               int minLate = (int) ((diff + MS_IN_HALF_SEC) / MS_IN_MIN); // Add half second for normal rounding
               long classLengthMs = section.getEndTime().getTimeInMillis() - section.getStartTime().getTimeInMillis();
               int classLengthMin = (int) (classLengthMs / MS_IN_MIN);
               int percentClassMissed = (100 * minLate) / classLengthMin;
               String msg = String.format("<html><body>Welcome, <b>%s</b>!<p/>" +
                     "Late: %d min (%d%% of class)<br/>" +
                     "Cumulative: ???<br/>" +
                     "Total tardies: ??? (??%% of classes)<p/>" +
                     "Excuse? " +
                     "</html>",
                     student.getName(),
                     minLate,
                     percentClassMissed);
               String response =
                     JOptionPane.showInputDialog(panel, msg, "Confirm", JOptionPane.QUESTION_MESSAGE);
               System.out.println("Got response: " + response);
               if (response == null || response.isEmpty()) { return; }
               recordTardy(student, section, minLate);
            }
         });
         panel.add(button);
      }
      return panel;
   }

   /**
    * Called when student signs in late: adds record to database and emails/texts contact list.
    */
   private void recordTardy(Student student, Section section, int minLate) {
      
   }
}

