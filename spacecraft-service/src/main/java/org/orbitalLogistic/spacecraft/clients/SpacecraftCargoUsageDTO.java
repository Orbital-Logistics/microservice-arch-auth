package org.orbitalLogistic.spacecraft.clients;

import java.math.BigDecimal;

public record SpacecraftCargoUsageDTO(
    Long spacecraftId,
    BigDecimal currentMassUsage,
    BigDecimal currentVolumeUsage
) {
}

