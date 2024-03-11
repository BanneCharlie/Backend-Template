package com.banne.template.model.dto;

import lombok.Data;

@Data
public class PageQueryRequest {

    private  String userAccount;

    private  String userName;

    private String userRole;

    private  int page;

    private  int pageSize;

}
