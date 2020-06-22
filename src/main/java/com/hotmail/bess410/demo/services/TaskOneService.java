package com.hotmail.bess410.demo.services;

import com.hotmail.bess410.demo.web.model.TaskOneModel;
import org.springframework.stereotype.Service;

@Service
public class TaskOneService {

    public String doTask(TaskOneModel model) {
        return Integer.toString(model.getNumberToFormat(), model.getRadix());
    }
}
