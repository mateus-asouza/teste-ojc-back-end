package com.ms.personApi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDto {

    private Long id;

    @NotEmpty
    @Size( max = 14)
    private String phoneNumber;

}
