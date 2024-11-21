package com.travel.seoul.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Long userSerial;
    private String userName;
    private String userID;
    private String userPassword;
    private String userAge;
    private String userSex;
    private String userArea1;
    private String userArea2;
    private String userArea3;
    private String fileName;
    
}
