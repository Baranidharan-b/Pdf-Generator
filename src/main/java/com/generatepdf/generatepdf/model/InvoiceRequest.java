package com.generatepdf.generatepdf.model;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a request for generating an invoice.
 * Contains details of the seller, buyer, and items to be included in the invoice.
 */
@Getter
@Setter
public class InvoiceRequest {

    /**
     * Name of the seller.
     * Cannot be empty.
     */
    @NotEmpty
    private String seller;

    /**
     * GSTIN of the seller.
     * Cannot be empty.
     */
    @NotEmpty
    private String sellerGstin;

    /**
     * Address of the seller.
     * Cannot be empty.
     */
    @NotEmpty
    private String sellerAddress;

    /**
     * Name of the buyer.
     * Cannot be empty.
     */
    @NotEmpty
    private String buyer;

    /**
     * GSTIN of the buyer.
     * Cannot be empty.
     */
    @NotEmpty
    private String buyerGstin;

    /**
     * Address of the buyer.
     * Cannot be empty.
     */
    @NotEmpty
    private String buyerAddress;

    /**
     * List of items to be included in the invoice.
     * Cannot be null.
     */
    @NotNull
    private List<Item> items;

}
