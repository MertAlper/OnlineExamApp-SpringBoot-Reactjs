package com.example.examapp.demo.util;

import com.example.examapp.demo.model.Exam;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ExamSerializer extends StdSerializer<Exam> {

    protected ExamSerializer(Class<Exam> t) {
        super(t);
    }

    @Override
    public void serialize(Exam exam,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(String.valueOf(exam.getExamId()));
    }
}
