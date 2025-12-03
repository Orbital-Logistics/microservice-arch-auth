package org.orbitalLogistic.cargo.dto.response;

import java.math.BigDecimal;

public record SpacecraftCargoUsageDTO(
    Long spacecraftId,
    BigDecimal currentMassUsage,
    BigDecimal currentVolumeUsage
) {
}

