package com.example.examapp.demo.util;

import com.example.examapp.demo.model.Student;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;


public class StudentSerializer extends StdSerializer<Student> {


    protected StudentSerializer(Class<Student> t) {
        super(t);
    }

    protected StudentSerializer() {
        this(null);
    }

    @Override
    public void serialize(Student student,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeString(String.valueOf(student.getUserId()));
    }


}
