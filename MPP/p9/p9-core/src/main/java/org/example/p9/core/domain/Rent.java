package org.example.p9.core.domain;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Rent extends BaseEntity<Long> {
    private Long clientId;
    private Long movieId;
    private String returnDate;
}
