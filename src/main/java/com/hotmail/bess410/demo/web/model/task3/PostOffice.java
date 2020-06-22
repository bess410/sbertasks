package com.hotmail.bess410.demo.web.model.task3;
import lombok.Data;

@Data
public class PostOffice {
    private Postman inputPostman;
    private Postman outputPostman;
    private int number;
    private volatile int boxes;
    private volatile int boxesFromEnemy;
    private int inputBoxes;
    private int outBoxes;
    private String status = "Unknown";

    synchronized void addBoxes(int value) {
        this.boxes += value;
    }

    synchronized void addBoxesFromEnemy(int value) {
        this.boxesFromEnemy += value;
    }

    public PostOffice(int number, int boxes, int outBoxes) {
        this.number = number;
        this.boxes = boxes;
        this.outBoxes = outBoxes;
    }

    public void checkBoxes() {
        if (boxes <= 0) {
            status = "Winner";
        }
        if (boxes > 100) {
            status = "Loser";
        }
    }

    public String getAllStatus() {
        return String.format("Почтовое отделение №%d\n" +
                        "Почтальон %s в статусе: %s, статус обеда: %s.\n" +
                        "Почтальон %s в статусе %s, статус обеда: %s \n" +
                        "На почте %d осталось %d посылок, принято %d посылок, отправлено %d посылок, подброшено %d посылок\n" +
                        "Статус почты: %s\n",
                this.number,
                this.inputPostman.getPostmanName(), this.inputPostman.getStatus(), this.inputPostman.getDinnerStatus(),
                this.outputPostman.getPostmanName(), this.outputPostman.getStatus(), this.outputPostman.getDinnerStatus(),
                this.number, this.boxes, this.inputBoxes, this.outBoxes, this.boxesFromEnemy,
                this.status);
    }
}
