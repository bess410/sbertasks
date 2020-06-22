package com.hotmail.bess410.demo.web.model.task3;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Random;

import static java.lang.Thread.sleep;

@SuperBuilder
@Data
public abstract class Postman implements Runnable {
    static final Random random = new Random();
    protected PostOffice postOffice;
    protected PostOffice otherPostOffice;
    private PostOfficeMonitor postOfficeMonitor;
    protected String postmanName;
    protected int limitBoxes;
    private String status;
    private String dinnerStatus;

    private int minMyPostBoxes;
    private int maxMyPostBoxes;
    private double maxCountDinnerTime;
    private double minCountDinnerTime;

    private int sizeToThrow;

    private int workingTime;
    protected int dinnerTime;


    private int limitOperation;
    private int count;

    public void run() {
        try {
            while (postOfficeMonitor.shouldWorking()) {
                //Обрабатываем почту
//                System.out.println(String.format("Почтальон %s начинает работать\n", postmanName));
                status = "Работает";
                sleep(workingTime);
                //Добавляем на почту посылки
                doWork();
                count++;
//                System.out.println(String.format("Обед почтальона %s через %d операций\n",
//                        postmanName, (limitOperation - count)));
                if (count == limitOperation) {
                    // Обед
                    status = "Обед";
                    throwOwnBoxesToEnemy();
                    goDinner();
                    count = 0;
                }
            }
            Thread.currentThread().interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void goDinner() throws InterruptedException {
        long currentDinnerTime;
        int boxes = postOffice.getBoxes();
        if (boxes > maxMyPostBoxes) {
            currentDinnerTime = (long) maxCountDinnerTime * 1000;
        } else if (boxes < minMyPostBoxes) {
            currentDinnerTime = (long) (minCountDinnerTime * 1000);
        } else {
            currentDinnerTime = this.dinnerTime * 1000;
        }
        if (currentDinnerTime > this.dinnerTime * 1000) {
            dinnerStatus = String.format("Увеличил свой обед до %d миллисек", currentDinnerTime);
        } else if (currentDinnerTime < this.dinnerTime * 1000) {
            dinnerStatus = String.format("Сократил свой обед до %d миллисек", currentDinnerTime);
        } else {
            dinnerStatus = String.format("Обед без изменений %d миллисек", currentDinnerTime);
        }

//        System.out.println(String.format("Почтальон %s ушел на обед на %d миллисекунд\n", postmanName, currentDinnerTime));
        sleep(currentDinnerTime);
    }

    abstract void doWork();

    private void throwOwnBoxesToEnemy() {
        int i = random.nextInt(sizeToThrow) + 1;
        int currentBoxes = postOffice.getBoxes();
        if (i >= currentBoxes) {
            i = currentBoxes;
        }
        postOffice.addBoxes(-i);
        otherPostOffice.addBoxes(i);
        otherPostOffice.addBoxesFromEnemy(i);
        System.out.println(String.format("Почтальон %s подкинул врагам %d посылок\n ", postmanName, i));
        System.out.println(this.getPostOfficeMonitor().getPostOfficesStatus());
    }
}
