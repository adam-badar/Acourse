package com.example.test;
//
//import static com.example.test.TimelineOps.getAllCourses;
//import static com.example.test.TimelineOps.getCoursesTaken;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import kotlin.contracts.Returns;

public class ViewTimetable extends AppCompatActivity {
    TextView tv;
    ListView lv, lv2;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_timetable);

        tv = findViewById(R.id.timeTableBar);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArrays();
            }
        });
    }

    public void getArrays() {

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String tempSet2 = sp.getString("courses_taken", null); //String of all courses taken
        String [] coursesTaken = tempSet2.split(",");


        String[] allCourses2,allCourses;

        Set<String> tempSet = sp.getStringSet("courses", null);

        allCourses2 = tempSet.toArray(new String[tempSet.size()]);
        allCourses = new String[allCourses2.length];
        for (int i = 0; i <allCourses.length; i++){
            allCourses[i] = allCourses2[i].substring(0,6);
        }
        boolean[] selectedCourse;
        ArrayList<Integer> courseList = new ArrayList<>();
        ArrayList<String> tempArray = new ArrayList<>(); //creates tempArray


//      Returns the list of courses to be displayed on the dialog
        for (String i : allCourses) {//If course exists and is not taken
            if (!Arrays.asList(coursesTaken).contains(i)) tempArray.add(i); //add to temp array;
        }
        String[] courseArray = tempArray.toArray(new String[tempArray.size()]); //make string list

        selectedCourse = new boolean[courseArray.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewTimetable.this);

        builder.setTitle("Select Courses");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(courseArray, selectedCourse, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    courseList.add(i);
                    Collections.sort(courseList);
                } else {
                    courseList.remove(i);
                }
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder courses = new StringBuilder();

                for (int j = 0; j < courseList.size(); j++) {
                    courses.append(courseArray[courseList.get(j)]);

                    if (j != courseList.size() - 1) {
                        courses.append(",");
                    }
                }
                tv.setText(courses.toString());
                lv = findViewById(R.id.courseslist);
                String [] targets = courses.toString().split(",");
                ArrayList<ArrayList<String>> adj_list = generateCourseList(allCourses, sp);
                ArrayList<String>preReqs = getPrereqs(adj_list, targets);
                removeTakenCourses(preReqs, sp);
                orderPrereqs(preReqs,adj_list);
                ArrayList<AdminCourse> not_quite_last = courses_to_do(adj_list, preReqs, sp);
                ArrayList<String> last = finalSessions(not_quite_last);

                ArrayAdapter adapt = new ArrayAdapter(ViewTimetable.this, android.R.layout.simple_expandable_list_item_1, last);
                lv.setAdapter(adapt);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int j = 0; j < selectedCourse.length; j++) {
                    selectedCourse[j] = false;
                    courseList.clear();
                    tv.setText("");
                }
            }
        });
        builder.show();

    }

    public ArrayList<ArrayList<String>> generateCourseList(String [] coursesString,
                                                                  SharedPreferences sp) {
        //Creates Adjacency list structure
        ArrayList<ArrayList<String>> to_return = new ArrayList<ArrayList<String>>();
        Set<String> tempSet = sp.getStringSet("courses", null);

        for (int i = 0; i < coursesString.length; i++) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(coursesString[i]);
            for (String course:tempSet){
                if (course.substring(0,6).equals(coursesString[i])){
                    String [] temp2 = course.substring(7, course.lastIndexOf(";")).split(",");
                    if (course.substring(7,course.lastIndexOf(";")).length() != 0){
                            for(String name: temp2) temp.add(name.trim());
                    }
                }
            }
            to_return.add(temp);
        }
        return to_return;
    }

    public ArrayList<String> getPrereqs(ArrayList<ArrayList<String>> am, String[] targets){
        //Creates Queue
        Queue<String> last = new LinkedList<>();
        ArrayList <String> to_return = new ArrayList<>();

        for(int a = 0; a < targets.length; a++){
            String target = targets[a].trim();
//            Toast.makeText(ViewTimetable.this, targets[a], Toast.LENGTH_SHORT).show();
            for (int i = 0; i < am.size(); i++){ //Enters loop
                //Finds target course
                if (am.get(i).get(0).equals(target)){
                    //adds pre-reqs
                    for (int j = 1; j < am.get(i).size();j++){
                        if(!last.contains(am.get(i).get(j).trim())) last.offer(am.get(i).get(j).trim());
                        if(!to_return.contains(am.get(i).get(j).trim()))to_return.add(am.get(i).get(j).trim());
                    }
                }
            } //Ends the original thing
        }
        // enters loop
        while(last.size() != 0){
            for(int i = 0; i < am.size();i++){
                if(am.get(i).get(0).trim().equals(last.peek().trim())){
                    for(int j = 0; j < am.get(i).size();j++){
                        if(!last.contains(am.get(i).get(j).trim())) last.add(am.get(i).get(j).trim());
                        if(!to_return.contains(am.get(i).get(j).trim())) to_return.add(am.get(i).get(j).trim());
                    }
                }
            }
            String t = last.poll();
        }
        for (String i: targets){
            if(!to_return.contains(i.trim())) to_return.add(i.trim());
        }
        return to_return;
    }



    static void removeTakenCourses(ArrayList<String> prereqs, SharedPreferences sp){ //Self-explanatory
        ArrayList<String> coursesTaken = new ArrayList<>();
        String coursesTaken2 = sp.getString("courses_taken", null);
        String [] mid = coursesTaken2.split(",");
//4
        for (String i : mid){
            coursesTaken.add(i.trim());
        }
        prereqs.remove(coursesTaken2);
    }

     int weight(String a, ArrayList<ArrayList<String>> master){ //finds the number of prereqs for a course
        String [] temp = {a.trim()};
        return getPrereqs(master, temp).size();
    }

    void orderPrereqs(ArrayList<String>prereqs, ArrayList<ArrayList<String>> master){
        //Bubble sorts prerequisites in terms of course weigth
        LinkedList<String> to_return = new LinkedList<>();
        to_return.add(prereqs.get(0).trim());

        int n = prereqs.size();
        String temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (weight(prereqs.get(j - 1), master) > weight(prereqs.get(j), master)) {
                    //swap elements
                    temp = prereqs.get(j - 1);
                    prereqs.set(j-1, prereqs.get(j));
                    prereqs.set(j, temp);
                }
            }
        }

    }
    Map<Integer, ArrayList<String>> makeDict(ArrayList<String> prereqs,
                                             ArrayList<ArrayList<String>> master){
        Map to_return = new HashMap<Integer, ArrayList<String>>();

        int last = weight(prereqs.get(prereqs.size()-1), master);
        for(int i = 0; i <=last; i++){
                ArrayList<String> ind = new ArrayList<>();
            for (String prereq: prereqs){
                if(weight(prereq,master) == i) ind.add(prereq);
            }
            to_return.put(i,ind);
        }
        return to_return;
    }


    ArrayList<AdminCourse> courses_to_do (ArrayList<ArrayList<String>> master,
                                          ArrayList<String> prereqs,SharedPreferences sp){

        ArrayList<AdminCourse> to_return = new ArrayList<>();
        Set<String> tempSet = sp.getStringSet("courses", null);

        for(String prereq: prereqs){
            for(String name: tempSet){
                if(prereq.equals(name.substring(0,6))) {
//                    ArrayList<AdminCourse> test = new ArrayList<>();
//                    for(String i:name.substring(name.lastIndexOf(";")+1).split(",")){
//                        test.add(new AdminCourse(i.trim()));
//                    }
                    AdminCourse doing = new AdminCourse(prereq,
                            name.substring(7,name.lastIndexOf(";")),name.substring(name.lastIndexOf(";")+1),
                            weight(prereq, master));

                    to_return.add(doing);
                }
            }
        }
        return to_return;
    }

    ArrayList<Integer> getszns(AdminCourse s){
        ArrayList<Integer> szns = new ArrayList<Integer>();

        if (s.sessionOfferings.contains("Winter"))
            szns.add(1);
        if (s.sessionOfferings.contains("Summer"))
            szns.add(2);
        if (s.sessionOfferings.contains("Fall"))
            szns.add(3);
        return szns;
    }

    String sznValue(int x) {
        if (x==1)
            return "Winter";
        if (x==2)
            return "Summer";
        return "Fall";
    }

    boolean isInDict(AdminCourse c, LinkedHashMap<String, ArrayList<String>> dict) {
        String session = sznValue(c.sessionToTake) + " " + String.valueOf(c.yearToTake);
        if (dict.containsKey(session))
            return true;

        return false;
    }

    void inDict(AdminCourse c, LinkedHashMap<String, ArrayList<String>> dict){
        String session = sznValue(c.sessionToTake) + " " + String.valueOf(c.yearToTake);
        ArrayList<String> courses = dict.get(session);
        courses.add(c.courseCode);
        dict.put(session, courses);
    }
    int sznInt(String x) {
        if (x.contains("Winter"))
            return 1;
        else if (x.contains("Summer"))
            return 2;
        return 3;
    }

    LinkedHashMap<String, ArrayList<String>> sortDict(AdminCourse c, LinkedHashMap<String, ArrayList<String>> dict) {
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
            courses.add(c.courseCode);
            dict.put(generate, courses);
            return dict;
        }else {
            int tab = 0;
            for (String key : dict.keySet()) {
                if (insert == tab) {
                    ArrayList<String> courses = new ArrayList<String>();
                    courses.add(c.courseCode);
                    newDict.put(generate, courses);
                }
                newDict.put(key, dict.get(key));
                tab++;
            }
            return newDict;
        }
    }


    ArrayList<String> finalSessions(LinkedHashMap<String, ArrayList<String>> dict) {
        ArrayList<String> list = new ArrayList<String>();
        for (String key : dict.keySet()) {
            ArrayList<String> temp = dict.get(key);
            String added = "";
            for (String y : temp)
                added = added + y + "  ";
            if (key.contains("Fall"))
                list.add("  " + key + ":   " + added + "\n");
            else
                list.add(key + ":   " + added + "\n");
        }
        return list;
    }

    ArrayList<String> finalSessions(ArrayList<AdminCourse> dict) {
        int year = 2022;
        String[] sessions = {"Fall", "Winter", "Summer"};
        ArrayList<AdminCourse> checked = new ArrayList<>();

        int p = 0;
        for (AdminCourse i : dict) { //For each course required

            ArrayList<AdminCourse> names = new ArrayList<>();
            if (i.prerequisites.length() < 1) { //If no prerequisites
                i.yearToTake = year;
                i.sessionTake = i.getSessionOfferings()[0];

                checked.add(i); //put the usual.
            } else {
                AdminCourse f;
                int total = 0;
                int pos = 0;
                int ac = 0;
                int n_year = 0;
// //head
                for (String t : i.getPrerequisitesList()) { //goes over prereqs
                    f = find_actual(checked, t.trim()); //finds the course
                    if (f.yearToTake + add_sesh(to_int(f.sessionTake)) > total){
                       total = f.yearToTake+ add_sesh(to_int(f.sessionTake));
                       ac = pos; //finds the biggest one
                    }
                    pos++;
                }
                AdminCourse leak = find_actual(checked, i.getPrerequisitesList().get(ac));
                int g = iterate(leak.sessionToTake, i.getPrerequisitesList());
                if (to_int(leak.sessionTake) <= g){
                    year = year +1;
                }
                i.yearToTake = year;
                i.sessionTake = i.getSessionOfferings()[g];
                year = i.yearToTake;
                checked.add(i);
        }
    }
        ArrayList<String> last = new ArrayList<>();
        for (AdminCourse cc:checked){
            last.add(cc.courseCode + ": "+ cc.sessionTake+ " "+cc.yearToTake);
        }
        return last;
    }

////                    if(to_int(i.getSessionOfferings()[0]) >= to_int(f.sessionTake){
////
////                    }
//                    if (f.yearToTake+ add_sesh(to_int(f.sessionTake)) > total){
//                       total = f.yearToTake+ add_sesh(to_int(f.sessionTake));
//                       ac = pos; //finds the biggest one
//                    }
//                    pos++;
//                }
//                //end
//                AdminCourse leak = find_actual(checked, i.getPrerequisitesList().get(ac));
//                if (to_int(leak.sessionTake) > to_int(i.getSessionOfferings()[0])){
//                    year = year +1;
//                }
//                i.yearToTake = year;
//                i.sessionTake = i.getSessionOfferings()[0];
//                year = i.yearToTake;
//                checked.add(i);
//            }
//
////            if(to_int(i.getSessionOfferings()[0]) >= to_int(f.sessionTake){
////
////                i.yearToTake = year;
////            }else{
////                year = year + 1;
////                i.sessionTake = i.getSessionOfferings()[0];
////            }
////            if (f.yearToTake+ to_int(f.sessionTake) > total){
////                total = f.yearToTake+ to_int(f.sessionTake);
////                ac = pos; //finds the biggest one
////            }
////            pos++;
//
//        }
//


    boolean is_in(List <String> search, String target){
        for (String i : search){
            if (i.equals(target)) return true;
        }
        return false;
    }
    AdminCourse find_actual(ArrayList<AdminCourse> temp, String target){
        for(AdminCourse course: temp){
            if (course.courseCode.trim().equals(target.trim())) return course;
        }
        return null;
    }
    int to_int(String t){
        t = t.toLowerCase();
        if (t.equals("fall")) return 1;
        if (t.equals("winter")) return 2;
        if (t.equals("summer")) return 3;
        return 0;
    }
    int add_sesh(int i){
        if(i == 1) i =1;
        if(i == 2) i =10;
        if(i == 3) i =100;
        return i;
    }
    int iterate(int target, List<String> list){
        int c = 0;
        for(String i: list){
            if (to_int(i) == target){
                c = target+1;
            }
        }
        if (c >= list.size()) c  = 0;
        return c;
    }
}
