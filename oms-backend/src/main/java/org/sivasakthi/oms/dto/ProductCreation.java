package org.sivasakthi.oms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductCreation {

    private long id;

    @NotNull
    @Size(min = 3, max = 100, message = "name length should be between 3 and 100")
    private String name;

    @Min(value = 1, message = "id should be min 1")
    private long categoryId;
}
