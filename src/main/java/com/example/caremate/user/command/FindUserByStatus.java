package com.example.caremate.user.command;

import com.example.caremate.user.entity.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FindUserByStatus {
    private List<UserStatus> status;

    public List<UserStatus> getStatus() {
        return null;
    }
}
