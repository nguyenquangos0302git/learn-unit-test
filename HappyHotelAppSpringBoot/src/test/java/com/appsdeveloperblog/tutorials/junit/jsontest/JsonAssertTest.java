package com.appsdeveloperblog.tutorials.junit.jsontest;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonAssertTest {

    private static String actualJson = "{\n" +
            "    \"email\": \"nguyenquangos0232app@gmail.com\",\n" +
            "    \"password\": \"Testdaw@!.dk1w\",\n" +
            "    \"firstName\": \"Quang\",\n" +
            "    \"lastName\": \"Nguyen\"\n" +
            "}";

    @Test
    void test() throws JSONException {
        String expectJson = "{\n" +
                "    \"email\": \"nguyenquangos0232app@gmail.com\",\n" +
                "    \"password\": \"Testdaw@!.dk1w\",\n" +
                "    \"firstName\": \"Quang\",\n" +
                "    \"lastName\": \"Nguyen\"\n" +
                "}";

        JSONAssert.assertEquals(expectJson, actualJson, true);

    }

}
