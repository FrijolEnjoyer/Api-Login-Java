package com.PruebaLogin.Login.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EditRequest {
    int id;
    String username;
    String password;
    String firtsname;
    String lastname;
    String country;

}
