package com.hotmail.bess410.demo.services;

import com.hotmail.bess410.demo.web.model.task3.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TaskThreeService implements Runnable {

    private PostOfficeMonitor monitor;
    private Postman inputPostmanOne;
    private Postman outputPostmanOne;
    private Postman inputPostmanTwo;
    private Postman outputPostmanTwo;

    {
        init();
    }

    private void init(){
        PostOffice postOffice1 = new PostOffice(1, 50, 0);
        PostOffice postOffice2 = new PostOffice(2, 55, 0);
        List<PostOffice> postOfficeList = new ArrayList<>();
        postOfficeList.add(postOffice1);
        postOfficeList.add(postOffice2);

        this.monitor = new PostOfficeMonitor(postOfficeList);
        this.inputPostmanOne = InputPostman.builder()
                .postmanName("InputPostman1")
                .postOfficeMonitor(monitor)
                .postOffice(postOffice1)
                .otherPostOffice(postOffice2)
                .limitBoxes(3)
                .workingTime(500)
                .limitOperation(20)
                .sizeToThrow(5)
                .maxMyPostBoxes(80)
                .minMyPostBoxes(20)
                .maxCountDinnerTime(6)
                .minCountDinnerTime(4)
                .dinnerTime(5)
                .status("Не работает")
                .dinnerStatus("Без изменений")
                .build();

        this.outputPostmanOne = OutputPostman.builder()
                .postmanName("OutputPostman1")
                .postOfficeMonitor(monitor)
                .postOffice(postOffice1)
                .otherPostOffice(postOffice2)
                .limitBoxes(5)
                .workingTime(600)
                .limitOperation(25)
                .sizeToThrow(5)
                .maxMyPostBoxes(75)
                .minMyPostBoxes(25)
                .maxCountDinnerTime(5)
                .minCountDinnerTime(7)
                .dinnerTime(6)
                .status("Не работает")
                .dinnerStatus("Без изменений")
                .build();

        this.inputPostmanTwo = InputPostman.builder()
                .postmanName("InputPostman2")
                .postOfficeMonitor(monitor)
                .postOffice(postOffice2)
                .otherPostOffice(postOffice1)
                .limitBoxes(2)
                .workingTime(550)
                .limitOperation(23)
                .sizeToThrow(7)
                .maxMyPostBoxes(79)
                .minMyPostBoxes(19)
                .maxCountDinnerTime(6.5)
                .minCountDinnerTime(4)
                .dinnerTime(6)
                .status("Не работает")
                .dinnerStatus("Без изменений")
                .build();

        this.outputPostmanTwo = OutputPostman.builder()
                .postmanName("OutputPostman2")
                .postOfficeMonitor(monitor)
                .postOffice(postOffice2)
                .otherPostOffice(postOffice1)
                .limitBoxes(4)
                .workingTime(450)
                .limitOperation(27)
                .sizeToThrow(7)
                .maxMyPostBoxes(72)
                .minMyPostBoxes(23)
                .maxCountDinnerTime(4.5)
                .minCountDinnerTime(6.5)
                .dinnerTime(5)
                .status("Не работает")
                .dinnerStatus("Без изменений")
                .build();

        postOffice1.setInputPostman(inputPostmanOne);
        postOffice1.setOutputPostman(outputPostmanOne);
        postOffice2.setInputPostman(inputPostmanTwo);
        postOffice2.setOutputPostman(outputPostmanTwo);
    }

    public void runProcess() {
        this.init();
        new Thread(this).start();
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.submit(inputPostmanOne);
        executor.submit(inputPostmanTwo);
        executor.submit(outputPostmanOne);
        executor.submit(outputPostmanTwo);
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MILLISECONDS);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }
        while (!executor.isTerminated()) {
        }
        System.out.println(monitor.getPostOfficesStatus());
    }

    public String getStatus() {
        return monitor.getPostOfficesStatus();
    }
}
