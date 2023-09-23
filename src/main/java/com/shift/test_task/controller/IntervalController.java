package com.shift.test_task.controller;

import com.shift.test_task.service.IntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/intervals")
public class IntervalController {
    @Autowired
    private IntervalService intervalService;

    @GetMapping(path = "/min", params = "kind=digits")
    public List<Integer> GetMinIntervalOfDigits() {
        return intervalService.GetIntervalsOfDigits();
    }

    @GetMapping(path = "/min", params = "kind=letters")
    public List<Character> GetMinIntervalOfLetters(){
        return intervalService.GetIntervalsOfLetters();
    }

    @PostMapping(path = "/merge", params = "kind=digits")
    public ResponseEntity MergeIntervalsOfDigits(@RequestBody List<List<Integer>> intervals) {
        List<List<Integer>> interval = intervalService.MergeIntervalsInt(intervals);
        intervalService.AddIntegerIntervals(interval);
        return ResponseEntity.ok("POST request was successful");
    }

    @PostMapping(path = "/merge", params = "kind=letters")
    public ResponseEntity MergeIntervalsOfLetters (@RequestBody List<List<Character>> intervals) {
        List<List<Character>> interval = intervalService.MergeIntervalsChar(intervals);
        intervalService.AddCharacterIntervals(interval);
        return ResponseEntity.ok("POST request was successful");
    }
}
