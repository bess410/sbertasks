package com.hotmail.bess410.demo.web.model.task3;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class OutputPostman extends Postman{
    @Override
    public void doWork() {
        int boxes = random.nextInt(limitBoxes) + 1;
        int postBoxes = postOffice.getBoxes();
        if (postBoxes < boxes) {
            boxes = postBoxes;
        }
        postOffice.addBoxes(-boxes);
        postOffice.setOutBoxes(postOffice.getOutBoxes() + boxes);
        System.out.println(String.format("Почтальон %s отправил %d посылок.",
                postmanName, boxes));
//        System.out.println(String.format("На почте %d = %d посылок, отправлено всего %d посылок",
//                postOffice.getNumber(), postOffice.getBoxes(), postOffice.getOutBoxes()));
        System.out.println(this.getPostOfficeMonitor().getPostOfficesStatus());
    }
}
