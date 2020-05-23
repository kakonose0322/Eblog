package com.example.eblog.im.message;

import com.example.eblog.im.vo.ImTo;
import com.example.eblog.im.vo.ImUser;
import lombok.Data;

@Data
public class ChatImMess {
    private ImUser mine;
    private ImTo to;
}
