package com.ms.personApi.dto.request;


import com.ms.personApi.entity.SecurityGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;


    private String login;


    private String password;

    private List<SecurityGroup> securityGroups;
}
