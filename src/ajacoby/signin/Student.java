package ajacoby.signin;

/**
 * A student: id, name, and contact info.
 *
 * @author Alex Jacoby
 * @version 7/31/14
 */
public class Student {
   private int _id;
   private String _name;
   private String _contacts;

   public Student(int id, String name, String contacts) {
      _id = id;
      _name = name;
      _contacts = contacts;
   }

   /**
    * Returns student ID number (matches official school ID).
    */
   public int getId() { return _id; }

   /**
    * Returns student display name (first and last).
    */
   public String getName() { return _name; }

   /**
    * Returns all contact info (e-mails and phone #s) in comma-separated
    * String.  Returns null if no contact info.
    */
   public String getContacts() { return _contacts; }

   @Override
   public String toString() {
      return "Student{" +
            "id=" + _id +
            ", name='" + _name + '\'' +
            ", contacts='" + _contacts + '\'' +
            '}';
   }
}
