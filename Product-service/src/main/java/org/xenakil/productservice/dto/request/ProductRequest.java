package org.xenakil.productservice.dto.request;

import java.math.BigDecimal;

public record ProductRequest (String name, String description, Integer count, BigDecimal value){}
