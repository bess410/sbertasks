package com.hotmail.bess410.demo.web.model.task3;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PostOfficeMonitor {
    private List<PostOffice> postOffices;
    private static long time = 2;
    private static long end = System.currentTimeMillis() + time * 60 * 1000;

    public boolean shouldWorking() {
        postOffices.forEach(PostOffice::checkBoxes);
        long count = postOffices.stream()
                .filter(postOffice -> !postOffice.getStatus().equals("Unknown"))
                .count();
        if (count != 0 || System.currentTimeMillis() > end) {
            return false;
        }
        return true;
    }

    public String getPostOfficesStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---------------------\n");
        postOffices.forEach(postOffice -> {
            stringBuilder.append(postOffice.getAllStatus());
        });
        stringBuilder.append("---------------------\n");
        return stringBuilder.toString();
    }
}
