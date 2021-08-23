package bgu.spl.net.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

	private Map<Integer, Course> courseMap;
	private Map<String, Member> memberMap;

	private static class singletonHolder{
		private static Database instance = new Database();
	}

	//to prevent user from creating new Database
	private Database() {
		courseMap = new ConcurrentHashMap<Integer, Course>();
		memberMap  = new ConcurrentHashMap<String, Member>();
		initialize("coursesFilePath");
		}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return singletonHolder.instance;
	}
	
	/**
	 * loads the courses from the file path specified 
	 * into the Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) {
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader("./Courses.txt"));
			String line = reader.readLine();
			int courseIndex = 0;
			while (line != null){
				lineToDataMembers(line, courseIndex);
				line = reader.readLine();
				courseIndex++;
			}
			for (Course course : courseMap.values()) {
				orderKdam(course);
			}
			reader.close();
		}

		catch (IOException e) { e.printStackTrace(); }
		return true;
	}

	private List<Integer> arrayStrToArray(String arr){
		arr = arr.substring(1, arr.length()-1);
		String[] list = arr.split(",");
		List<Integer> array = new LinkedList<Integer>();
		for (String str : list) {
			if (!str.equals("")) array.add(Integer.parseInt(str));
		}
		return array;
	}

	private void lineToDataMembers(String line, int index){
		String[] Course = line.split("\\|");
		Course currentCourse = new Course(index,
									Integer.parseInt(Course[0]),
									Course[1],
									arrayStrToArray(Course[2]),
									Integer.parseInt(Course[3]));
		courseMap.put(Integer.parseInt(Course[0]), currentCourse);
	}

	// thread safe admin adding. if user already registered (by key) return false
	public boolean adminReg(String username, String password){
		if (memberMap.containsKey(username)) return false;
		return (null == memberMap.putIfAbsent(username, new Member(username, password, true)));
	}

	public boolean login(String username, String password){
		if (!memberMap.containsKey(username)) return false;
		Member user = memberMap.get(username);
		if (!user.comperPassword(password)) return false;
		if (user.isActive()) return false;
		user.setLoggedIn();
		return true;
	}

	public String isRegistered(Integer courseNum, String student){
		Course course = courseMap.get(courseNum);
		if (null == course) return "NOT REGISTERED";
		synchronized (course) {
			if (course.isReg(student)) return "REGISTERED";
			else return "NOT REGISTERED";
		}
	}

	public String myCourses(String student){
		List<Integer> courses = new LinkedList<Integer>(); 
		for (Course course : courseMap.values()) {
			if (course.isReg(student)) courses.add(course.getCourseNum());
		}
		SortedSet<Course> courseList = new TreeSet<>(Comparator.comparing(Course::getCourseIndex));
		List<Integer> orderdKadm = new LinkedList<Integer>();
		for (Integer courseNum : courses) {
			courseList.add(courseMap.get(courseNum));
		}
		for (Course courseObj : courseList) {
			orderdKadm.add(courseObj.getCourseNum());
		}
		return  orderdKadm.toString().replace(" ", "");
	}

	public String courseStatus(Integer courseNum){
		Course course = courseMap.get(courseNum);
		if (course == null) return null;
		synchronized (course) {
			return String.format("Course: (%d) %s\nSeats Available: %s/%s\nStudents Registered: %s", courseNum,
												course.getCourseName(),
												course.getAvailableSeats(),
												course.getMaxSeats(),
												course.getRegisteredStudents());
		}
	}

	
	public boolean registerStudent(String username, String password) {
		if (memberMap.containsKey(username))
			return false;
		else {
			return (null == memberMap.putIfAbsent(username, new Member(username, password, false)));
		}
	}

	public boolean CourseReg(String student,Integer courseNum){
		Course course = courseMap.get(courseNum);
		if (course == null) return false;
		synchronized (course) {
			Member studentObj = getMember(student);
			if (!hasKdam(studentObj, courseMap.get(courseNum))) return false;
			studentObj.registerCourse(courseNum);
			return course.assignMember(studentObj.getName());
		}
	}

	public void orderKdam(Course course){
		SortedSet<Course> courseList = new TreeSet<>(Comparator.comparing(Course::getCourseIndex));
		List<Integer> orderdKadm = new LinkedList<Integer>();
		for (Integer courseNum : course.getPreliminaryCoursesList()) {
			courseList.add(courseMap.get(courseNum));
		}
		for (Course courseObj : courseList) {
			orderdKadm.add(courseObj.getCourseNum());
		}
		course.setKadm(orderdKadm);
	}

	public List<Integer> kadmCheck(Integer courseNum){
		if(courseMap.containsKey(courseNum))
			return courseMap.get(courseNum).getPreliminaryCoursesList();
		return null;
	}

	public String studentStatus(String username){
		if (memberMap.containsKey(username)){
			return "Student: " + username + "\nCourses: "+ myCourses(username);
		}
		return null;
	}

	public boolean unregister(String student,Integer courseNum){
		Course course= courseMap.get(courseNum);
		if (course ==null) return false;
		synchronized (course) { return course.removeStudent(student); }
	}

	private Member getMember(String username){
		return memberMap.get(username);
	}

	public Boolean isAdmin(String username){
		return memberMap.get(username).isAdmin();
	}

	public void setLogin(String username){
		 getMember(username).setLoggedIn();
	}

	public void setLogout(String username){
		getMember(username).setLoggedOut();
   }

   private Boolean hasKdam(Member student, Course course){
	   for (Integer kdam : course.getPreliminaryCoursesList()) {
		   if (!student.hasKdam(kdam)) return false;
	   }
	   return true;
   }
}
