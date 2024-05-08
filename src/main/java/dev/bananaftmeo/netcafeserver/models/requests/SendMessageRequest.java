package dev.bananaftmeo.netcafeserver.models.requests;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    private Long senderId;
    private Long receiverId;
    private String message;
    private LocalTime sentTime;
}
