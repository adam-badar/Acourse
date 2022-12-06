import java.util.*;

public class Course {

	String code;
	String name;
	ArrayList<Course> prerequisites;
	String sessions;
	int weight;
	int yearToTake;
	int sessionToTake;
	
	public Course(String c, String n, ArrayList<Course> p, String s) {
		code = c; //
		name = n;
		prerequisites = p;
		sessions = s;
		yearToTake = 0; 
		sessionToTake = 0;
	}

	int getWeight() {
		return weight;
	}
	
	// function to add course to master list
	
	//if you add click on a button representing a course that has been displayed, that button's course id  is the String accessed
	
	// so under functionality for the 'adding to timetable' button, every time it's clicked, call this function
	void AddtoMasterList(ArrayList<Course> allCourses) {
		if (allCourses.contains(this)){
			System.out.println("already exists");
		}allCourses.add(this);	
	}
	
}