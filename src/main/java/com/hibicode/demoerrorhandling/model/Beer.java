package com.hibicode.demoerrorhandling.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class Beer {

    @Id
    private Long id;

    @NotBlank(message = "name.mandatory")
    private String name;

    @NotNull(message = "type.mandatory")
    private BeerType type;

    @DecimalMin(message = "volume.minValue", value = "0")
    @NotNull(message = "volume.mandatory")
    private BigDecimal volume;

}
