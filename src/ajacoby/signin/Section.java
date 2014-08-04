package ajacoby.signin;

import java.util.Calendar;

/**
 * Represents a section of a course, aka, a class.
 *
 * @author Alex Jacoby
 * @version 7/31/14
 */
public class Section {
   private int _id;
   private String _name;
   private Calendar _startTime;
   private Calendar _endTime;

   public Section(int id, String name, Calendar startTime, Calendar endTime) {
      _id = id;
      _name = name;
      _startTime = startTime;
      _endTime = endTime;
   }

   public int getId() { return _id; }

   public String getName() { return _name; }

   public Calendar getStartTime() { return _startTime; }

   public Calendar getEndTime() { return _endTime; }

   @Override
   public String toString() {
      return "Section{" +
            "id=" + _id +
            ", name='" + _name + '\'' +
            ", startTime=" + _startTime +
            ", endTime=" + _endTime +
            '}';
   }
}
