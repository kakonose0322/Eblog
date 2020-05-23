package com.example.eblog.service;

import com.example.eblog.im.vo.ImMess;
import com.example.eblog.im.vo.ImUser;
import java.util.List;

public interface ChatService {
    ImUser getCurrentUser();

    void setGroupHistoryMsg(ImMess responseMess);

    List<Object> getGroupHistoryMsg(int count);
}
