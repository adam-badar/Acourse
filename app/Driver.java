import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

public class Driver {
	public static void main(String[] args) {
		
		
		ArrayList<Course> nully = new ArrayList<Course>();

		
		ArrayList<Course> list1 = new ArrayList<Course>();
		ArrayList<Course> list2 = new ArrayList<Course>();

	
		Course B41 = new Course("MATB41", "Calculus 3", null, "Winter");
		Course B24 = new Course("MATB24", "Algebra 2", null, "Fall");
		
		list1.add(B41);
		list1.add(B24);

		Course B07 = new Course("CSCB07", "Software Design", list1, "Summer, Winter");

		list2.add(B07);

		Course B09 = new Course("CSCB09", "Software Tools", list2, "Fall, Summer, Winter");

		ArrayList<Course> Master = new ArrayList<Course>();

		
		
		Course A08 = new Course("CSCA08", "Intro to CS", null, "Fall, Winter");

		Course A67 = new Course("CSCA67", "Discrete Math", null, "Fall, Winter");

		ArrayList<Course> lista = new ArrayList<Course>();
		lista.add(A08);

		Course A48 = new Course("CSCA48", "Intro to CS 2", lista, "Fall, Summer, Winter");


		ArrayList<Course> listb = new ArrayList<Course>();
		listb.add(A48);
		listb.add(A67);

	
		Course B36 = new Course("CSCB36", "Computation", listb, "Winter");
		Course B52 = new Course("STAB52", "Statistics", null, "Fall, Summer");
		
		Course A31 = new Course("MATA31", "Calculus 1", null, "Fall, Summer");
		
		
		ArrayList<Course> lista3 = new ArrayList<Course>();
		lista3.add(A31);

		Course A37 = new Course("MATA37", "Calculus 2", lista3, "Fall, Summer");
		Course A22 = new Course("MATA22", "Algebra", null, "Winter, Summer");


		ArrayList<Course> listaa3 = new ArrayList<Course>();
		listaa3.add(A37);
		listaa3.add(A48);
		
		Course C63 = new Course("CSCC63", "Computability", listaa3, "Fall, Summer, Winter");
		
		
		ArrayList<Course> lista5 = new ArrayList<Course>();
		lista5.add(B36);
		lista5.add(C63);
		lista5.add(A22);

		Course C69 = new Course("CSCC69", "TopTier", lista5, "Winter");

		
		ArrayList<Course> lister3 = new ArrayList<Course>();
		lister3.add(A22);
		lister3.add(A08);
		lister3.add(A48);

		User A = new User("Aaliyah", lister3);
		
		B09.AddtoMasterList(Master);
		B36.AddtoMasterList(Master);
		B52.AddtoMasterList(Master);
		C69.AddtoMasterList(Master);
		
		
		ArrayList<Course> emptyInitially = new ArrayList<Course>();
		A.bigArray(Master, 0, emptyInitially);
		
		for (Course s: emptyInitially)
	    	  System.out.println("course: " + s.code + " weight: " + s.weight + "\n");
		
		ArrayList<Course> emp = new ArrayList<Course>();
		
		//LinkedHashMap<String, ArrayList<String>> my_dict = new LinkedHashMap<String, ArrayList<String>>();
		
		ArrayList<String> oop = new ArrayList<String>();

		ArrayList<String> finalList = A.insertInDict(emptyInitially);
		
		
//		ArrayList<String> seeList = A.displaySessions(finalList);
//		ArrayList<ArrayList<String>> list = A.displayCoursesToTake(finalList);
		
		//ArrayList<String> veryFinal = A.finalSessions(finalList);
//		
		for (String key: finalList)
			System.out.println(key);
//      for (String key: finalList.keySet())
//    	  System.out.println(key + ": " + finalList.get(key) + "\n");
	}
}
