package com.generatepdf.generatepdf.model;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an item in an invoice.
 */
@Getter
@Setter
public class Item {

    /**
     * The name of the item. Cannot be empty.
     */
    @NotEmpty
    private String name;

    /**
     * The quantity of the item. Cannot be empty.
     */
    @NotEmpty
    private String quantity;

    /**
     * The rate at which the item is sold. Cannot be null.
     */
    @NotNull
    private double rate;

    /**
     * The total amount for the item. Cannot be null.
     */
    @NotNull
    private double amount;
}
