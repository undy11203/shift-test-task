package com.shift.test_task.service;

import com.shift.test_task.repository.IntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class IntervalService {
    @Autowired
    private IntervalRepository intervalRepository;

    public List<List<Integer>> MergeIntervalsInt(List<List<Integer>> intervals){
        Collections.sort(intervals, Comparator.comparing((List<Integer> o) -> o.get(0)));
        List<List<Integer>> mergedIntervals = new ArrayList<>();

        int start = intervals.get(0).get(0);
        int end = intervals.get(0).get(1);

        for (List<Integer> interval : intervals) {
            int intervalStart = interval.get(0);
            int intervalEnd = interval.get(1);

            if (intervalStart <= end) {
                end = Math.max(end, intervalEnd);
            }
            else {
                mergedIntervals.add(List.of(start, end));
                start = intervalStart;
                end = intervalEnd;
            }
        }
        mergedIntervals.add(List.of(start, end));
        return mergedIntervals;
    }

    public List<List<Character>> MergeIntervalsChar(List<List<Character>> intervals){
        Collections.sort(intervals, Comparator.comparing((List<Character> o) -> o.get(0)));
        List<List<Character>> mergedIntervals = new ArrayList<>();
        List<Character> currentInterval = intervals.get(0);

        char start = intervals.get(0).get(0);
        char end = intervals.get(0).get(1);

        for (List<Character> interval : intervals) {
            char intervalStart = interval.get(0);
            char intervalEnd = interval.get(1);

            if (intervalStart <= end) {
                end = end > intervalEnd ? end : intervalEnd;
            }
            else {
                mergedIntervals.add(List.of(start, end));
                start = intervalStart;
                end = intervalEnd;
            }
        }
        mergedIntervals.add(List.of(start, end));
        return mergedIntervals;
    }

    public void AddIntegerIntervals(List<List<Integer>> intervals){
        try {
            for (List<Integer> interval : intervals) {
                intervalRepository.AddIntegerIntervalInDb(interval);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void AddCharacterIntervals(List<List<Character>> intervals){
        try {
            for (List<Character> interval : intervals) {
                intervalRepository.AddCharacterIntervalInDb(interval);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> GetIntervalsOfDigits(){
        try {
            return intervalRepository.GetIntegerIntervalFromDb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Character> GetIntervalsOfLetters(){
        try {
            return intervalRepository.GetCharacterIntervalFromDb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
