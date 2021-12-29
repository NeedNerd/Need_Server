package com.nerd.need_server.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoResponse {
    private int userIdx;
    private String name;
    private int count;
}
