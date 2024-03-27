package com.healthycoderapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ActivityCalculatorTest {

    @Test
    void should_ReturnBad_When_AvgBelow20() {
        // give
        int weeklyCardioMin = 40;
        int weeklyWorkoutSessions = 1;

        // when
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkoutSessions);

        // then
        assertEquals("bad", actual);
    }

    @Test
    void should_ReturnAverage_When_AvgBetween20And40() {
        // give
        int weeklyCardioMin = 40;
        int weeklyWorkoutSessions = 3;

        // when
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkoutSessions);

        // then
        assertEquals("average", actual);
    }

    @Test
    void should_ReturnGood_When_AvgAbove40() {
        // give
        int weeklyCardioMin = 40;
        int weeklyWorkoutSessions = 7;

        // when
        String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkoutSessions);

        // then
        assertEquals("good", actual);
    }

    @Test
    void should_ThrowException_When_InputBelowZero() {
        // give
        int weeklyCardioMin = -40;
        int weeklyWorkoutSessions = 1;

        // when
        Executable executable = () -> ActivityCalculator.rateActivityLevel(weeklyCardioMin, weeklyWorkoutSessions);

        // then
        assertThrows(RuntimeException.class, executable);
    }

}