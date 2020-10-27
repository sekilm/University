package org.example.p9.web.converter;

import org.example.p9.core.domain.Client;
import org.example.p9.web.dto.ClientDto;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        Client client = Client.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = ClientDto.builder()
                .name(client.getName())
                .email(client.getEmail())
                .age(client.getAge())
                .build();
        dto.setId(client.getId());
        return dto;
    }
}
