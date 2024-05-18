package org.xenakil.productservice.dto.response;

import java.math.BigDecimal;

public record ProductResponse (String id,String name, String description, Integer count, BigDecimal value) {}
