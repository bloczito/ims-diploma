package pl.wiktrans.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderPriority {

    LOW(0),
    MEDIUM(100),
    HIGH(200);


    private int id;

    public String getText() {
        switch (id) {
            case 0: return "Niski";
            case 100: return "Średni";
            case 200: return "Wysoki";
            default: return "Error";
        }
    }
}
