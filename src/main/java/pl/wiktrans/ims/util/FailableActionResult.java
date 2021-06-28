package pl.wiktrans.ims.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FailableActionResult {
    private boolean success;
    private String error;

    public static FailableActionResult success() {
        return new FailableActionResult(true, null);
    }

    public static FailableActionResult failure(String error) {
        return new FailableActionResult(false, error);
    }

}
