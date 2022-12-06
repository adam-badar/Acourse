import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class User {
	String name;
	ArrayList<Course> pastCourses;

	public User(String s, ArrayList<Course> p){
		name = s;
		pastCourses = p;
	}


	// function returns true if student has taken all prerequisites of a course

	boolean takenCourses(Course c) {        
		for(Course x:c.prerequisites) {
			if (!(this.pastCourses.contains(x)))
				return false;
		}
		return true;
	}

	//function fills initially empty list with all courses required


	void bigArray(ArrayList<Course> allCourses, int weight, ArrayList<Course> emptyInitially) { 
		if (allCourses == null) 
			return;
		
		for (Course x: allCourses) {

			if (!this.pastCourses.contains(x)) {
				if (emptyInitially.contains(x)) {
					int p = emptyInitially.indexOf(x);
					emptyInitially.get(p).weight = Math.max(emptyInitially.get(p).weight, weight);
				}

				if (x.prerequisites == null) { 
					x.weight = weight;
					if (!emptyInitially.contains(x)) 
						emptyInitially.add(x);
					

				}else {
					if (!emptyInitially.contains(x)) {
						x.weight = weight;
						emptyInitially.add(x);
					}
					if (this.takenCourses(x) == true)
						System.out.println("user took all prereqs\n");

					else {
						for (Course y: x.prerequisites) {
							if (this.pastCourses.contains(y)) {
							}

							else {
								if (emptyInitially.contains(y)) {
									int p = emptyInitially.indexOf(y);
									emptyInitially.get(p).weight = Math.max(emptyInitially.get(p).weight, weight+1);
								}

								y.weight = weight+1;
								if (!emptyInitially.contains(y)) 
									emptyInitially.add(y);

								bigArray(y.prerequisites, y.weight + 1, emptyInitially); 
							}
						}
					}
				}
			}

		}

		Collections.sort(emptyInitially, Comparator.comparing(Course::getWeight));
		Collections.reverse(emptyInitially);

	}




	int sznInt(String x) {
		if (x.contains("Winter"))
			return 1;
		else if (x.contains("Summer"))
			return 2;
		return 3;
	}

	String sznValue(int x) {
		if (x==1) 
			return "Winter";
		if (x==2) 
			return "Summer";
		return "Fall";
	}


	//creates an list of integers representing the sessions a course is available

	ArrayList<Integer> getszns(Course s){

		ArrayList<Integer> szns = new ArrayList<Integer>();

		if (s.sessions.contains("Winter"))
			szns.add(1);
		if (s.sessions.contains("Summer"))
			szns.add(2);
		if (s.sessions.contains("Fall"))
			szns.add(3);
		return szns;
	}


	// function returning a dictionary with sessions sorted
	LinkedHashMap<String, ArrayList<String>> sortDict(Course c, LinkedHashMap<String, ArrayList<String>> dict) {
		int insert = 0;
		int year = c.yearToTake;
		int session = c.sessionToTake;

		//iterating through existing sessions dates
		for (String key : dict.keySet()) {
			//looking at the last digit of our key to determine the year
			int keyVal = Integer.parseInt((key).substring(key.length()-4, key.length()));
			//            System.out.println("KEYVAL: " + keyVal);
	
			//checking if the current key is in the next year / same year but a later session
			if (year > keyVal || (year == keyVal && session > sznInt(key))) {

				insert++;
			}
		}

		String generate = sznValue(c.sessionToTake) + " " + String.valueOf(c.yearToTake);
		LinkedHashMap<String, ArrayList<String>> newDict = new LinkedHashMap<String, ArrayList<String>>();


		if (insert == dict.size()) {
			for (String key : dict.keySet()) {
				newDict.put(key, dict.get(key));
			}

			ArrayList<String> courses = new ArrayList<String>();
			courses.add(c.code);
			dict.put(generate, courses);
			return dict;
		}else {
			int tab = 0;
			for (String key : dict.keySet()) {
				if (insert == tab) {
					ArrayList<String> courses = new ArrayList<String>();
					courses.add(c.code);
					newDict.put(generate, courses);
				}
				newDict.put(key, dict.get(key));
				tab++;
			}
			return newDict;
		}
	}



	void inDict(Course c, LinkedHashMap<String, ArrayList<String>> dict){
		String session = sznValue(c.sessionToTake) + " " + String.valueOf(c.yearToTake);
		ArrayList<String> courses = dict.get(session);
		courses.add(c.code);
		dict.put(session, courses);
	}


	boolean isInDict(Course c, LinkedHashMap<String, ArrayList<String>> dict) {
		String session = sznValue(c.sessionToTake) + " " + String.valueOf(c.yearToTake);
		if (dict.containsKey(session)) 
			return true;

		return false;
	}


	ArrayList<String> displaySessions(LinkedHashMap<String, ArrayList<String>> dict){
		ArrayList<String> list = new ArrayList<String>();
		for (String key: dict.keySet()) {
			list.add(key);
		}return list;
	}

	ArrayList<ArrayList<String>> displayCoursesToTake(LinkedHashMap<String, ArrayList<String>> dict){
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (String key: dict.keySet()) {
			ArrayList<String> value = dict.get(key);
			list.add(value);
		}return list;
	}
	
	
	ArrayList<String> finalSessions(LinkedHashMap<String, ArrayList<String>> dict){
		
		ArrayList<String> list = new ArrayList<String>();
		for (String key: dict.keySet()) {
			ArrayList<String> temp = dict.get(key);
			String added = "";
			
			for (String y: temp) 
				added = added+y + "  ";
			
			if (key.contains("Fall"))
				list.add("  " + key + ":   " + added + "\n");
			else
				list.add(key + ":   " + added+ "\n");
		
		}
		return list;
	}
	
	
	ArrayList<String> insertInDict(ArrayList<Course> courses) {
		int year = 2023;    
		
		LinkedHashMap<String, ArrayList<String>> my_dict = new LinkedHashMap<String, ArrayList<String>>();
		ArrayList<Course> emptyInitially = new ArrayList<Course>();

		for (Course s: courses){
			boolean added = false;
			ArrayList<Integer> szns = getszns(s);
			int currentVal = 1;

			if (s.prerequisites == null) {
				added = false;

				while (!added) {

					if (szns.contains(currentVal)) {    
						s.yearToTake = year;
						s.sessionToTake = currentVal;

						if (isInDict(s, my_dict)) 
							inDict(s, my_dict);
						else 
							my_dict = sortDict(s, my_dict);

						added = true;

					}else {
						currentVal++;
						if (currentVal == 4) {
							currentVal = 1;
							year++;
						}
					}
				}

			}else {

				int newVal = 0;
				int newYear = 0;
				for (Course a: s.prerequisites) {
					if (emptyInitially.contains(a)) {
						newYear = Math.max(newYear, a.yearToTake);
						newVal = Math.max(newVal, a.sessionToTake);
					}
				}

				newVal++;
				if (newVal == 4) {
					newVal = 1;
					newYear++;
				}

				added = false;

				while (!added) {

					if (szns.contains(newVal)) {

						s.yearToTake = newYear;
						s.sessionToTake = newVal;


						if (isInDict(s, my_dict))
							inDict(s, my_dict);
						else
							my_dict = sortDict(s, my_dict);

						added = true;

					}else {
						newVal++;
						if (newVal == 4) {
							newVal = 1;
							newYear++;
						}
					}
				}               
			}
			emptyInitially.add(s);
		}
		
		ArrayList<String> output = new ArrayList<String>();
		output = finalSessions(my_dict);
		return output;
	}   


}



