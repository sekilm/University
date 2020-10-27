package org.example.p9.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentsDto {
    private Set<RentDto> rents;
}
