package com.example.freetalk.dto;

import com.example.freetalk.entity.NoticeAlram;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeAlramDTO {
    private Long id;
    private String content;
    private String senderName;
    private boolean isRead;

    public static NoticeAlramDTO fromEntity(NoticeAlram n) {
        NoticeAlramDTO dto = new NoticeAlramDTO();
        dto.id = n.getIdx();
        dto.content = n.getContent();
        dto.senderName = n.getSender().getUsername();
        dto.isRead = n.isRead();
        return dto;
    }
}
