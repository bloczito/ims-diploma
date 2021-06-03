package pl.wiktrans.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    NEW(0),
    IN_PROGRESS(500),
    FINISHED(1000);

    private Integer id;

    public String getText() {
        switch (id) {
            case 0: return "Nowe";
            case 500: return "W realizacji";
            case 1000: return "zakończone";
            default: return "Error";
        }
    }
}
