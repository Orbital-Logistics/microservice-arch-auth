package org.orbitalLogistic.maintenance.repositories;

import org.orbitalLogistic.maintenance.entities.MaintenanceLog;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MaintenanceLogRepository extends ReactiveCrudRepository<MaintenanceLog, Long> {

    Flux<MaintenanceLog> findBySpacecraftId(Long spacecraftId);

    @Query("""
        SELECT m.* FROM maintenance_log m
        ORDER BY m.start_time DESC NULLS LAST, m.id DESC
        LIMIT :limit OFFSET :offset
    """)
    Flux<MaintenanceLog> findAllPaginated(
        @Param("offset") int offset,
        @Param("limit") int limit
    );

    @Query("SELECT COUNT(*) FROM maintenance_log")
    Mono<Long> countAll();

    @Query("""
        SELECT m.* FROM maintenance_log m
        WHERE m.spacecraft_id = :spacecraftId
        ORDER BY m.start_time DESC NULLS LAST, m.id DESC
        LIMIT :limit OFFSET :offset
    """)
    Flux<MaintenanceLog> findBySpacecraftIdPaginated(
        @Param("spacecraftId") Long spacecraftId,
        @Param("limit") int limit,
        @Param("offset") int offset
    );

    @Query("""
        SELECT COUNT(*) FROM maintenance_log
        WHERE spacecraft_id = :spacecraftId
    """)
    Mono<Long> countBySpacecraftId(@Param("spacecraftId") Long spacecraftId);
}
