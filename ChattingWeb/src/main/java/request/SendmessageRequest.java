package request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendmessageRequest {
    private Integer userId;
    private Integer chatId;
    private String message;
}
