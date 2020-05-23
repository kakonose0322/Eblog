package com.example.eblog.im.message;

import com.example.eblog.im.vo.ImMess;
import lombok.Data;

@Data
public class ChatOutMess {
    private String emit;
    private ImMess data;
}
