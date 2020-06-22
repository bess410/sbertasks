package com.hotmail.bess410.demo.web.controller;

import com.hotmail.bess410.demo.services.TaskOneService;
import com.hotmail.bess410.demo.services.TaskThreeService;
import com.hotmail.bess410.demo.services.TaskTwoService;
import com.hotmail.bess410.demo.web.model.TaskOneModel;
import com.hotmail.bess410.demo.web.model.TaskTwoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskOneService taskOneService;

    @Autowired
    TaskThreeService taskThreeService;

    @Autowired
    TaskTwoService taskTwoService;

    @PostMapping(path = "/1", consumes = "application/json", produces = "application/json")
    public String doTask(@RequestBody TaskOneModel model) {
        return taskOneService.doTask(model);
    }

    @PostMapping(path = "/2", consumes = "application/json", produces = "application/json")
    public String doTask(@RequestBody TaskTwoModel model) {
        return taskTwoService.getPathToResult(model);
    }

    @PostMapping(path = "/3", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> doTask() {
        this.taskThreeService.runProcess();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/3")
    public String getStatus() {
        return this.taskThreeService.getStatus();
    }
}
