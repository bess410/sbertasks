package com.hotmail.bess410.demo.web.model.task3;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class InputPostman extends Postman {
    @Override
    public void doWork() {
        int boxes = random.nextInt(limitBoxes) + 1;
        postOffice.addBoxes(boxes);
        postOffice.setInputBoxes(postOffice.getInputBoxes() + boxes);
        System.out.println(String.format("Почтальон %s добавил на почту %d %d посылок.",
                postmanName, postOffice.getNumber(), boxes));
        System.out.println(this.getPostOfficeMonitor().getPostOfficesStatus());
//        System.out.println(String.format("На почте %d = %d посылок", postOffice.getNumber(), postOffice.getBoxes()));
    }
}