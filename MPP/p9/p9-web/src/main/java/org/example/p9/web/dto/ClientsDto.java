package org.example.p9.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientsDto {
    private Set<ClientDto> clients;
}
