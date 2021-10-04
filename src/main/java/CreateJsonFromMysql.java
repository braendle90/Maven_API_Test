
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Student;
import Mysql.DBconnection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Retrive information from mysql DB!
 *
 */
public class CreateJsonFromMysql
{

    private static Statement stmt;
    private static ResultSet results;


    public String getMysqlContent(int id){


        String sql_select = "Select * From student_info where id ='"+id+"'";

        //select * from student.student_info where name='dominik';

        System.out.println(sql_select);


        String JSONOutput = null;
        try (Connection conn = DBconnection.createNewDBconnection()) {

            stmt = conn.createStatement();
            results = stmt.executeQuery(sql_select);

            List<Student> studentsList = new ArrayList<Student>();

            while (results.next()) {

                Student stdObject = new Student();

                stdObject.setId(Integer.valueOf(results.getString("id")));
                stdObject.setName(results.getString("name"));
                stdObject.setAddress(results.getString("Address"));
                stdObject.setCourse_code(results.getString("course_code"));

                studentsList.add(stdObject);
            }

            ObjectMapper mapper = new ObjectMapper();
            JSONOutput = mapper.writeValueAsString(studentsList);
            System.out.println(JSONOutput);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return JSONOutput;

    }

    public String getMysqlContent(){


        String sql_select = "Select * From student_info;";

        //select * from student.student_info where name='dominik';

        System.out.println(sql_select);


        String JSONOutput = null;
        try (Connection conn = DBconnection.createNewDBconnection()) {

            stmt = conn.createStatement();
            results = stmt.executeQuery(sql_select);

            List<Student> studentsList = new ArrayList<Student>();

            while (results.next()) {

                Student stdObject = new Student();

                stdObject.setId(Integer.valueOf(results.getString("id")));
                stdObject.setName(results.getString("name"));
                stdObject.setAddress(results.getString("Address"));
                stdObject.setCourse_code(results.getString("course_code"));

                studentsList.add(stdObject);
            }

            ObjectMapper mapper = new ObjectMapper();
            JSONOutput = mapper.writeValueAsString(studentsList);
            System.out.println(JSONOutput);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return JSONOutput;

    }

}