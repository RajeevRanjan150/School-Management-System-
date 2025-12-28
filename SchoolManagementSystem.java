import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

class Student {
  private String name;
  private String admissionNumber;
  private String standard;
  private int rollNumber;
  private int Totalfee;
  private int remainingFee;
  private HashMap<String, Integer> markSheet;
  private double percentage;

  Student(String admissionNumber, String name, String standard, int rollNumber, int Totalfee) {
    this.admissionNumber = admissionNumber;
    this.name = name;
    this.standard = standard;
    this.rollNumber = rollNumber;
    this.Totalfee = Totalfee;
    this.remainingFee = Totalfee;
    this.markSheet = new HashMap<String, Integer>();
    this.percentage = 0;
  }

  public void promote(String standard, int Totalfee, int rollNumber) {
    if (remainingFee > 0) {
      System.out.println("Cannot promote: Pending fee - ₹" + remainingFee);
      return;
    }
    this.standard = standard;
    this.Totalfee = Totalfee;
    this.remainingFee = 0;
    this.rollNumber = rollNumber;
  }

  public void setRoll(int rollNumber) {
    this.rollNumber = rollNumber;
  }

  public double getPercentage() {
    if (markSheet.isEmpty()) {
      System.out.println("No subject exists in System!");
      return 0;
    }
    int subjects = markSheet.size();
    double totalMark = 0;
    for (int mark : markSheet.values()) {
      totalMark += mark;
    }
    this.percentage = (totalMark / subjects) * 100;
    return Math.round(percentage);
  }

  public String getName() {
    return name;
  }

  public String getAdmissionNumber() {
    return admissionNumber;
  }

  public String getStandard() {
    return standard;
  }

  public int getRollNumber() {
    return rollNumber;
  }

  public int getTotalfee() {
    return Totalfee;
  }

  public int getRemainingfee() {
    return remainingFee;
  }

  public void submitFee(int fee) {
    if (remainingFee == 0) {
      System.out.println("Fee Already Cleared!");
      return;
    }
    if (fee > Totalfee || fee > remainingFee) {
      System.out.println("Invalid fee!");
      return;
    }
    this.remainingFee -= fee;
  }

  public void addMarkIn(String subject, int mark) {
    if(mark < 0 || mark > 100) {
      System.out.println("Invalid Mark!");
      return;
    }
    if (!markSheet.containsKey(subject)) {
      System.out.println("Such subject doesn't exists in the system!");
      return;
    }
    int newMark = this.markSheet.get(subject) + mark;
    this.markSheet.put(subject, newMark);
  }

  public HashMap<String, Integer> getMarkSheet() {
    return markSheet;
  }

  public void addSubject(String subject) {
    this.markSheet.put(subject, 0);
  }
}

class Teacher {
  private int id;
  private String name;
  private String subject;
  private int salary;
  private String specialization;
  private String contactNumber;

  // no salary for intern teachers.
  Teacher(int id, String name, String subject, String specialization, String contactNumber) {
    this.id = id;
    this.name = name;
    this.subject = subject;
    this.specialization = specialization;
    this.contactNumber = contactNumber;
    this.salary = 0;
  }

  Teacher(int id, String name, String subject, int salary, String specialization, String contactNumber) {
    this.id = id;
    this.name = name;
    this.subject = subject;
    this.salary = salary;
    this.specialization = specialization;
    this.contactNumber = contactNumber;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSubject() {
    return subject;
  }

  public int getSalary() {
    return salary;
  }

  public String getSpecialization() {
    return specialization;
  }

  public String getContact() {
    return contactNumber;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public void changeSubject(String subject) {
    this.subject = subject;
  }

  public void changeContact(String contactNumber) {
    this.contactNumber = contactNumber;
  }

}

class Administrative {
  private int id;
  private String name;
  private int salary;
  private String contactNumber;

  Administrative(int id, String name, int salary, String contactNumber) {
    this.id = id;
    this.name = name;
    this.salary = salary;
    this.contactNumber = contactNumber;
  }

  Administrative(int id, String name, String contactNumber) {
    this.id = id;
    this.name = name;
    this.contactNumber = contactNumber;
    this.salary = 0;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getSalary() {
    return salary;
  }

  public String getContact() {
    return contactNumber;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  public void changeContact(String contactNumber) {
    this.contactNumber = contactNumber;
  }
}

class Password {
  private String Admin_Login_Password = "open";
  private String Teacher_Login_Password = "Teacher";
  private String Administrative_Login_Password = "Administration";

  // Only Administrator will have access to change password.
  void changeAdminPass(String password) {
    this.Admin_Login_Password = password;
  }

  void changeTeacherPass(String password) {
    this.Teacher_Login_Password = password;
  }

  void changeAdministrativePass(String password) {
    this.Administrative_Login_Password = password;
  }

  boolean isAdminPass(String password) {
    return password.equals(Admin_Login_Password);
  }

  boolean isTeacherPass(String password) {
    return password.equals(Teacher_Login_Password);
  }

  boolean isAdministrativePass(String password) {
    return password.equals(Administrative_Login_Password);
  }
}

class SchoolManagementSystem {
  public static void main(String[] args) {
    HashMap<Integer, Administrative> administrationDepartment = new HashMap<Integer, Administrative>();
    HashMap<Integer, Teacher> teachers = new HashMap<Integer, Teacher>();
    HashMap<String, HashMap<Integer, Student>> students = new HashMap<String, HashMap<Integer, Student>>();

    Helper hp = new Helper();

    Scanner sc = new Scanner(System.in);
    Password TempPassword = new Password();
    int Attempts = 1;
    boolean Auth = false;
    while (Attempts <= 5) {
      System.out.print("Enter Password to Enter: ");
      String userPass = sc.nextLine();

      if (TempPassword.isAdminPass(userPass)) {
        Auth = true;
        System.out.println("Access Granted as Admin!");
        System.out.println("============ School Management System ===============");
        adminPanel(sc, administrationDepartment, teachers, students, hp);
        break;
      } else if (TempPassword.isTeacherPass(userPass)) {
        Auth = true;
        System.out.println("Access Granted as Teacher!");
        System.out.println("============ School Management System ===============");
        teacherPanel(sc, students, hp);
        break;
      } else if (TempPassword.isAdministrativePass(userPass)) {
        Auth = true;
        System.out.println("Access Granted as Administrative Staff!");
        System.out.println("============ School Management System ===============");
        administrativePanel(sc, teachers, students, hp);
        break;
      }
      System.out.println("Wrong Password!");
      System.out.println("Attempts left: " + (5 - Attempts));
      Attempts++;
    }
    if (!Auth) {
      System.out.println("Access Denied!");
      sc.close();
      System.exit(0);
    }
    sc.close();
    System.exit(0);
  }

  public static void adminPanel(Scanner sc, HashMap<Integer, Administrative> administrationDepartment,
      HashMap<Integer, Teacher> teachers, HashMap<String, HashMap<Integer, Student>> students, Helper hp) {

    while (true) {
      System.out.println("---------- Admin Panel ----------");
      System.out.println("1. Add Teacher");
      System.out.println("2. Remove Teacher");
      System.out.println("3. Add Administrative Staff");
      System.out.println("4. Remove Administrative Staff");
      System.out.println("5. View Teachers");
      System.out.println("6. View Administrative Staffs");
      System.out.println("7. View Students");
      System.out.println("8. Change Teacher Salary");
      System.out.println("9. Change Administrative Staff Salary");
      System.out.println("10. Change Administrative Staff's Contact Number");
      System.out.println("11. Add Subject");
      System.out.println("12. Change Password");
      System.out.println("0. Exit");
      System.out.print("Choose an option: ");
      int choice = sc.nextInt();
      sc.nextLine();
      switch (choice) {
        case 1:
          hp.addTeacher(sc, teachers);
          break;
        case 2:
          hp.removeTeacher(sc, teachers);
          break;
        case 3:
          hp.addAdministrativeStaff(sc, administrationDepartment);
          break;
        case 4:
          hp.removeAdministrativeStaff(sc, administrationDepartment);
          break;
        case 5:
          hp.viewTeachers(teachers);
          break;
        case 6:
          hp.viewAdministrativeStaffs(administrationDepartment);
          break;
        case 7:
          hp.viewStudents(sc, students);
          break;
        case 8:
          hp.changeTeacherSalary(sc, teachers);
          break;
        case 9:
          hp.changeAdministrativeSalary(sc, administrationDepartment);
          break;
        case 10:
          hp.changeAdministrativeContact(sc, administrationDepartment);
          break;
        case 11:
          hp.addSubject(sc, students);
          break;
        case 12:
          hp.changePass(sc);
          break;
        case 0:
          hp.exit();
          return;
        default:
          System.out.println("Please choose a valid option!");
      }
    }
  }

  public static void teacherPanel(Scanner sc, HashMap<String, HashMap<Integer, Student>> students, Helper hp) {

    while (true) {
      System.out.println("---------- Teacher Panel ----------");
      System.out.println("1. View Students");
      System.out.println("2. Change Student's Roll number");
      System.out.println("3. View Student's Mark Sheet");
      System.out.println("4. Add Mark");
      System.out.println("5. View Student's Percentage");
      System.out.println("0. Exit");
      System.out.print("Choose an option: ");
      int choice = sc.nextInt();
      sc.nextLine();
      switch (choice) {
        case 1:
          hp.viewStudents(sc, students);
          break;
        case 2:
          hp.changeStudentRoll(sc, students);
          break;
        case 3:
          hp.viewStudentMarkSheet(sc, students);
          break;
        case 4:
          hp.addMark(sc, students);
          break;
        case 5:
          hp.viewStudentPercentage(sc, students);
          break;
        case 6:
          hp.exit();
          return;
        default:
          System.out.println("Please choose a valid option!");
      }

    }
  }

  public static void administrativePanel(Scanner sc, HashMap<Integer, Teacher> teachers,
HashMap<String, HashMap<Integer, Student>> students, Helper hp) {

    while(true) {
      System.out.println("---------- Administrative Panel ----------");
      System.out.println("1. Add Student");
      System.out.println("2. Remove Student");
      System.out.println("3. View Teachers");
      System.out.println("4. View Students");
      System.out.println("5. Update Fee of Student");
      System.out.println("6. View Mark Sheet of Student");
      System.out.println("7. Promote Student");
      System.out.println("8. Change Student's Roll number");
      System.out.println("9. Get Teacher's contact number");
      System.out.println("10. Change Teacher's contact number");
      System.out.println("0. Exit");
      System.out.print("Choose an option: ");
      int choice = sc.nextInt();
      sc.nextLine();

      switch(choice) {
        case 1:
          hp.addStudent(sc, students);
          break;
        case 2:
          hp.removeStudent(sc, students);
          break;
        case 3:
          hp.viewTeachers(teachers);
          break;
        case 4:
          hp.viewStudents(sc, students);
          break;
        case 5:
          hp.updateFee(sc, students);
          break;
        case 6:
          hp.viewStudentMarkSheet(sc, students);
          break;
        case 7:
          hp.promoteStudent(sc, students);
          break;
        case 8:
          hp.changeStudentRoll(sc, students);
          break;
        case 9:
          hp.getTeacherContact(sc, teachers);
          break;
        case 10:
          hp.changeTeacherContact(sc, teachers);
          break;
        case 0:
          hp.exit();
          return;
        default:
          System.out.println("Please choose a valid option!");
          
      }
      
    }
  }
}

class Helper {
  public void exit() {
    System.out.print("Exiting");
    for (int i = 1; i <= 3; i++) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.print(".");
    }
    System.out.println("\nThank You for using The School Management System!");
  }

  public boolean doesExist(HashMap<String, HashMap<Integer, Student>> students, String AN) {
    for(HashMap<Integer, Student> student : students.values()) {
      for(Student s : student.values()) {
        if(s.getAdmissionNumber().equals(AN)) {
          return true;
        }
      }
    }
    return false;
  }
  public boolean existsInStandard(HashMap<String, HashMap<Integer, Student>> students, int roll, String standard) {
    if(!students.containsKey(standard)) return false;
    for(Student s : students.get(standard).values()) {
      if(s.getRollNumber() == roll) {
        return true;
      }
    }
    return false;
  }
  public void addStudent(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    System.out.println("Enter Student's Credentials: ");
    System.out.print("• Student's Name: ");
    String name = sc.nextLine();
    System.out.print("• Student's Admission Number: ");
    String admissionNumber = sc.nextLine();
    System.out.print("• Student's Standard: ");
    String standard = sc.nextLine();
    System.out.print("• Student's Roll Number: ");
    int roll = sc.nextInt();
    sc.nextLine();
    System.out.print("• Student's Total Fee: ₹");
    int totalFee = sc.nextInt();
    sc.nextLine();

    if(!students.containsKey(standard)) {
      System.out.println("Standard " + standard + "does NOT exists!");
      return;
    }
    if(doesExist(students, admissionNumber)) {
      System.out.println("Student with same Adminssion Number exists!");
      return;
    }
    if(existsInStandard(students, roll, standard)) {
      System.out.println("Student with same Roll Number in Standard " + standard + " exists!");
      return;
    }
    Student student = new Student(admissionNumber, name, standard, roll, totalFee);
    students.get(standard).put(roll, student);
    
  }

  public void removeStudent(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    if(students.isEmpty()) {
      System.out.println("No Student Found!");
      return;
    }
    System.out.print("Enter Student's Admission Number: ");
    String AN = sc.next();
    System.out.print("Enter Student's Standard: ");
    String standard = sc.nextLine();
    System.out.print("Enter Student's Roll number: ");
    int roll = sc.nextInt();
    sc.nextLine();
    if(doesExist(students, AN)) {
      students.get(standard).remove(roll);
    } else {
      System.out.println("Student with Adminssion Number " + AN +" does NOT exists!");
      return;
    }
  }

  public void updateFee(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    if(students.isEmpty()) {
      System.out.println("No Student Found!");
      return;
    } 
    System.out.print("Enter Student's Adminssion Number: ");
    String AN = sc.nextLine();
    if(!doesExist(students, AN)) {
      System.out.println("No Student Found with Admission Number " + AN);
    }
    System.out.print("Enter Student's Standard: ");
    String standard = sc.nextLine();
    System.out.print("Enter Student's Roll number: ");
    int roll = sc.nextInt();
    sc.nextLine();
    if(!existsInStandard(students, roll, standard)) {
      System.out.println("Student with roll " + roll + " does not exists in Standard " + standard);
      return;
    }
    System.out.print("Enter Deposited Fee: ₹");
    int fee = sc.nextInt();
    sc.nextLine();
    students.get(standard).get(roll).submitFee(fee);
    System.out.println("Fee added!");
  }

  public void promoteStudent(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    if(students.isEmpty()) {
      System.out.println("No Student Found");
      return;
    }
    System.out.print("Enter Student's Admission Number: ");
    String AN = sc.nextLine();
    if(!doesExist(students, AN)) {
      System.out.println("No Student found with Admission Number "+ AN);
      return;
    }
    System.out.print("Enter Student's Current Standard: ");
    String standard = sc.nextLine();
    System.out.print("Enter Student's Roll number: ");
    int roll = sc.nextInt();
    sc.nextLine();
    if(!existsInStandard(students, roll, standard)) {
      System.out.println("Student with Roll number " + roll +" does not exists in Standard " + standard);
      return;
    }
    System.out.print("Enter New Standard: ");
    String newStandard = sc.nextLine();
    System.out.print("Enter New Roll number: ");
    int newRoll = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter New Total Fee: ");
    int totalFee = sc.nextInt();
    sc.nextLine();
    Student student = students.get(standard).get(roll);
    student.promote(newStandard, totalFee, newRoll);
    students.get(newStandard).put(newRoll, student);
    students.get(standard).remove(roll);
    System.out.println("Student Promoted Successfully!");
  }

  public void getTeacherContact(Scanner sc, HashMap<Integer, Teacher> teachers) {
    if(teachers.isEmpty()) {
      System.out.println("No Teachers Found!");
      return;
    }
    System.out.print("Enter Teacher's Id: ");
    int id = sc.nextInt();
    sc.nextLine();
    System.out.println("Contact of Mr/Mrs " + teachers.get(id).getName() +": + "+teachers.get(id).getContact());
  }
  
  public void addTeacher(Scanner sc, HashMap<Integer, Teacher> teachers) {
    System.out.println("Enter Teacher's Credentials: ");
    System.out.print("Enter Teacher's Id: ");
    int id = sc.nextInt();
    sc.nextLine();
    if(teachers.containsKey(id)) {
      System.out.println("Teacher with same Id already exists!");
      return;
    }
    System.out.print("Enter Teacher's name: ");
    String name = sc.nextLine();
    System.out.print("Enter Teacher's Subject: ");
    String subject = sc.nextLine();
    System.out.print("Enter Teacher's Salary: ");
    int salary = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter Teacher's Specialization: ");
    String specialization = sc.nextLine();
    System.out.print("Enter Teacher's Contact Number: +");
    String contact = sc.nextLine();
    if(salary <= 0) {
      teachers.put(id, new Teacher(id, name, subject, specialization, contact));
      System.out.println("Teacher Added!");
    } else {
      teachers.put(id, new Teacher(id, name, subject, salary, specialization, contact));
      System.out.println("Teacher Added!");
    }
  }

  public void removeTeacher(Scanner sc, HashMap<Integer, Teacher> teachers) {
    if(teachers.isEmpty()) {
      System.out.println("No Teachers found!");
      return;
    }
    System.out.print("Enter Teacher's Id: ");
    int id = sc.nextInt();
    sc.nextLine();
    if(!teachers.containsKey(id)) {
      System.out.println("No Teacher found with Id " + id);
      return;
    }
    teachers.remove(id);
    System.out.println("Teacher Removed!");
  }

  public void addAdministrativeStaff(Scanner sc, HashMap<Integer, Administrative> AD) {
    System.out.println("Enter Administrative Staff's Credentials: ");
    System.out.print("Enter Administrative Staff's Id: ");
    int id = sc.nextInt();
    sc.nextLine();
    if(AD.containsKey(id)) {
      System.out.println("Staff with same Id already exists!");
      return;
    }
    System.out.print("Enter Staff's name: ");
    String name = sc.nextLine();
    System.out.print("Enter Staff's Salary: ");
    int salary = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter Staff's Contact Number: ");
    String contact = sc.nextLine();
    if(salary <= 0) {
      AD.put(id, new Administrative(id, name, contact));
      System.out.println("Administrative Staff Added!");
    }
    AD.put(id, new Administrative(id, name, salary, contact));
    System.out.println("Administrative Staff Added!");
  }
  
  public void removeAdministrativeStaff(Scanner sc, HashMap<Integer, Administrative> AD) {
    if(AD.isEmpty()) {
      System.out.println("No Administrative Staff found!");
      return;
    }
    System.out.print("Enter Staff's Id: ");
    int id = sc.nextInt();
    sc.nextLine();
    if(!AD.containsKey(id)) {
      System.out.println("No Administrative Staff found with Id " + id);
      return;
    }
    AD.remove(id);
    System.out.println("Administrative Staff removed!");
  }
  public void viewTeachers(HashMap<Integer, Teacher> teachers) {
    if(teachers.isEmpty()) {
      System.out.println("No Teacher found!");
      return;
    }
    System.out.println("Teachers: ");
    for(Map.Entry<Integer, Teacher> teacher : teachers.entrySet()) {
      System.out.println(teacher.getKey() + ". " + teacher.getValue().getName() + " " + teacher.getValue().getSubject() + " " + teacher.getValue().getSalary());
    }
  }
  
  public void viewAdministrativeStaffs(HashMap<Integer, Administrative> AD) {
    if(AD.isEmpty()) {
      System.out.println("No Administrative Staff found!");
      return;
    }
    System.out.println("Administrative: ");
    for(Map.Entry<Integer, Administrative> ad : AD.entrySet()) {
      System.out.println(ad.getKey() + ". " + ad.getValue().getName() + " " + ad.getValue().getSalary());
    }
  }

  public void viewStudents(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    if(students.isEmpty()) {
      System.out.println("No Student Found!");
      return;
    }
    System.out.print("Enter Student's Standard: ");
    String standard = sc.nextLine();
    if(!students.containsKey(standard)) {
      System.out.println("No such Standard found!");
      return;
    }
    System.out.print("Enter Student's Roll number: ");
    int roll = sc.nextInt();
    sc.nextLine();
    if(!students.get(standard).containsKey(roll)) {
      System.out.println("Student not found with Roll number " + roll);
      return;
    }
    for(Map.Entry<Integer, Student> student : students.get(standard).entrySet()) {
      System.out.println(student.getKey() + ". " + student.getValue().getAdmissionNumber() + " " + student.getValue().getName() + " " + student.getValue().getRemainingfee());
    }
  }

  public void changeTeacherSalary(Scanner sc, HashMap<Integer, Teacher> teachers) {
    if(teachers.isEmpty()) {
      System.out.println("No Teacher found!");
      return;
    }
    System.out.print("Enter Teacher's Id: ");
    int id = sc.nextInt();
    sc.nextLine();
    if(!teachers.containsKey(id)) {
      System.out.println("No Teacher found with Id " + id);
      return;
    }
    System.out.println("Current Salary: ₹" + teachers.get(id).getSalary());
    System.out.print("New Salary: ₹");
    int newSalary = sc.nextInt();
    sc.nextLine();
    teachers.get(id).setSalary(newSalary);
  }

  public void changeAdministrativeSalary(Scanner sc, HashMap<Integer, Administrative> AD) {
    if(AD.isEmpty()) {
      System.out.println("No Administrative Staff found!");
      return;
    }
    System.out.print("Enter Administrative Staff's Id: ");
    int id = sc.nextInt();
    sc.nextLine();
    if(!AD.containsKey(id)) {
      System.out.println("No Staff found with Id " + id);
      return;
    }
    System.out.println("Current Salary: ₹" + AD.get(id).getSalary());
    System.out.print("New Salary: ₹");
    int newSalary = sc.nextInt();
    sc.nextLine();
    AD.get(id).setSalary(newSalary);
  }

  public void changeAdministrativeContact(Scanner sc, HashMap<Integer, Administrative> AD) {
    if(AD.isEmpty()) {
      System.out.println("No Administrative Staff Found!");
      return;
    }
    System.out.print("Enter Administrative Staff's Id: ");
    int id = sc.nextInt();
    sc.nextLine();
    if(!AD.containsKey(id)) {
      System.out.println("No Staff found with Id " + id);
      return;
    }
    System.out.print("Enter new Contact Number: ");
    String contact = sc.nextLine();
    AD.get(id).changeContact(contact);
  }

  public void changeTeacherContact(Scanner sc, HashMap<Integer, Teacher> teachers) {
    if(teachers.isEmpty()) {
      System.out.println("No Teacher Found!");
      return;
    }
    System.out.print("Enter Teacher's Id: ");
    int id = sc.nextInt();
    sc.nextLine();
    if(!teachers.containsKey(id)) {
      System.out.println("No Teacher found with Id " + id);
      return;
    }
    System.out.print("Enter new Contact Number: ");
    String contact = sc.nextLine();
    teachers.get(id).changeContact(contact);
  }

  public void addSubject(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    System.out.print("Enter Standard: ");
    String standard = sc.nextLine();
    System.out.print("Enter Subject name: ");
    String subject = sc.nextLine();
    for(Map.Entry<Integer, Student> student : students.get(standard).entrySet()) {
      student.getValue().addSubject(subject);
    }
  }

  public void changePass(Scanner sc) {
    Password password = new Password();
    while (true) {
      System.out.println("Enter Field (Teacher, Administrative, Admin): ");
      String field = sc.nextLine();
      if (field.equalsIgnoreCase("Teacher")) {
        System.out.println("Enter new Password: ");
        String userPass = sc.nextLine();
        password.changeTeacherPass(userPass);
        return;
      } else if (field.equalsIgnoreCase("Administrative")) {
        System.out.println("Enter new Password: ");
        String userPass = sc.nextLine();
        password.changeAdministrativePass(userPass);
        return;
      } else if (field.equalsIgnoreCase("Admin")) {
        System.out.println("Enter new Password: ");
        String userPass = sc.nextLine();
        password.changeAdminPass(userPass);
        return;
      } else {
        System.out.println("Please enter a valid feild!");
      }
    }

  }
  public void changeStudentRoll(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    if(students.isEmpty()) {
      System.out.println("No Student found!");
      return;
    }
    System.out.print("Enter Student's Standard: ");
    String standard = sc.nextLine();
    if(!students.containsKey(standard)) {
      System.out.println("No such Standard found!");
      return;
    }
    System.out.print("Enter Student's Roll number: ");
    int roll = sc.nextInt();
    sc.nextLine();
    if(!existsInStandard(students, roll, standard)) {
      System.out.println("Student with Roll " + roll + " does not exists!");
      return;
    }
    System.out.print("Enter new Roll number: ");
    int newRoll = sc.nextInt();
    sc.nextLine();
    if(!students.get(standard).containsKey(newRoll)) {
      System.out.println("Student already exists with Roll " + newRoll);
      return;
    }
    Student student = students.get(standard).get(roll);
    student.setRoll(newRoll);
    students.get(standard).remove(roll);
    students.get(standard).put(newRoll, student);
    System.out.println("Roll Number changed successfully!");
    
  }
  
  public void viewStudentMarkSheet(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    if(students.isEmpty()) {
      System.out.println("No Student found!");
      return;
    }
    System.out.print("Enter Standard: ");
    String standard = sc.nextLine();
    if(!students.containsKey(standard)) {
      System.out.println("No such Standard found!");
      return;
    }
    System.out.print("Enter Student's Roll number: ");
    int roll = sc.nextInt();
    sc.nextLine();
    if(!existsInStandard(students, roll, standard)) {
      System.out.println("Student does not exists with Roll " + roll);
      return;
    }
    System.out.println("Mark Sheet for " + students.get(standard).get(roll).getName() +": ");
    for(Map.Entry<String, Integer> markSheet : students.get(standard).get(roll).getMarkSheet().entrySet()) {
      System.out.println(markSheet.getKey() + " -> " + markSheet.getValue());
    }
  }
  public void addMark(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    if(students.isEmpty()) {
      System.out.println("No Student found!");
      return;
    }
    System.out.print("Enter Standard: ");
    String standard = sc.nextLine();
    if(!students.containsKey(standard)) {
      System.out.println("No such Standard found!");
      return;
    }
    System.out.print("Enter Student's Roll number: ");
    int roll = sc.nextInt();
    sc.nextLine();
    if(!existsInStandard(students, roll, standard)) {
      System.out.println("Student doens't exists with Roll " + roll);
      return;
    }
    System.out.print("Enter Subject: ");
    String subject = sc.nextLine();
    if(!students.get(standard).get(roll).getMarkSheet().containsKey(subject)) {
      System.out.println("No such Subject Available for Standard " + standard);
      return;
    }
    System.out.print("Enter Mark: ");
    int mark = sc.nextInt();
    students.get(standard).get(roll).getMarkSheet().put(subject, mark);
    
  }
  public void viewStudentPercentage(Scanner sc, HashMap<String, HashMap<Integer, Student>> students) {
    if(students.isEmpty()) {
      System.out.println("No Student found!");
      return;
    }
    System.out.print("Enter Standard: ");
    String standard = sc.nextLine();
    if(!students.containsKey(standard)) {
      System.out.println("No such Standard exists!");
      return;
    }
    System.out.print("Enter Student's Roll number: ");
    int roll = sc.nextInt();
    if(!existsInStandard(students, roll, standard)) {
      System.out.println("Student doens't exists with Roll " + roll);
      return;
    }
    System.out.print("Percent: " + students.get(standard).get(roll).getPercentage());
  }
}