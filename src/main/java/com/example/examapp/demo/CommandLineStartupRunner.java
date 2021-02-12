package com.example.examapp.demo;

import com.example.examapp.demo.model.*;
import com.example.examapp.demo.dao.Dao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class CommandLineStartupRunner implements CommandLineRunner {

    @Autowired
    private Dao<Exam> examDao;

    @Autowired
    private Dao<Student> studentDao;

    @Override
    public void run(String... args) throws Exception{
//        testExamDAO();
//        testDeserializeData();
//        testSerializeData();
//        testAttendance();
//        testAttendance2();
    }

    //Passed
    private void testExamDAO() {

        List<Question> quests = Arrays.asList(
                Question.builder().questionTitle("Quest#1").build(),
                Question.builder().questionTitle("Quest#2").build(),
                Question.builder().questionTitle("Quest#3").build(),
                Question.builder().questionTitle("Quest#4").build()
        );

        List<Choice> choices = Arrays.asList(
                Choice.builder().description("Choice#1").correct(false).build(),
                Choice.builder().description("Choice#2").correct(false).build(),
                Choice.builder().description("Choice#3").correct(false).build(),
                Choice.builder().description("Choice#4").correct(true).build()
        );

        for (Question q: quests) {
            q.setChoices(choices);
        }


        Exam exam = Exam.builder()
                .endDate(new Date())
                .startDate(new Date())
                .title("Cloud")
                .questions(quests)
                .build();

        // SAVE TEST
        exam = examDao.save(exam);
        System.out.println(exam.getExamId());
        System.out.println(exam.getTitle());
//
//        // UPDATE TEST
//        exam.setTitle("Data Mining");
//        exam = examDao.save(exam);
//        System.out.println(exam.getTitle());
//
//        // UPDATE CHILD TEST
//        exam.getQuestions().get(0).getChoices().get(0).setCorrect(true);
//        examDao.save(exam);
//        System.out.println(exam.getQuestions().get(0).getChoices().get(0).isCorrect());
//
//        // Delete and Deletion Cascading TEST
//        examDao.delete(exam);
    }

    //Passed
    private void testDeserializeData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Exam exam = objectMapper.readValue(new File("test-data/create-exam-data.json"), Exam.class);

        exam = examDao.save(exam);

        System.out.println(exam.getExamId());
    }

    //Passed
    private void testSerializeData() throws IOException {

        Exam theExam = examDao.getEntityById(1);

        ObjectMapper om = new ObjectMapper();

        om.writeValue(new File("test-data/test-output-data.json"), theExam);

    }

    private void testAttendance() throws IOException {
        ObjectMapper om = new ObjectMapper();
//
//        Attendance attendance = om.readValue(new File("test-data/apply-exam-data.json"), Attendance.class);
//
//
        Student student = studentDao.getEntityById(1);
//        student.addAttendance(attendance);
//
        Exam exam = examDao.getEntityById(1);
//        exam.addAttendance(attendance);
//
//        studentDao.save(student);

        Attendance attendance = new Attendance();
        attendance.setAttended(false);
        exam.addAttendance(attendance);
        student.addAttendance(attendance);
        studentDao.save(student);

    }

    private void testAttendance2() throws IOException {
        ObjectMapper om = new ObjectMapper();

        Student stu = studentDao.getEntityById(1);
        om.writeValue(new File("test-data/output-student.json"), stu);
    }
}
