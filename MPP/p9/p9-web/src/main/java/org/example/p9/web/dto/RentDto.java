package org.example.p9.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class RentDto extends BaseDto {
    private Long clientId;
    private Long movieId;
    private String returnDate;
}
