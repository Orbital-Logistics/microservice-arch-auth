package org.orbitalLogistic.maintenance.repositories;

import org.orbitalLogistic.maintenance.entities.MaintenanceLog;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceLogRepository extends CrudRepository<MaintenanceLog, Long> {

    List<MaintenanceLog> findBySpacecraftId(Long spacecraftId);

    @Query("""
        SELECT m.* FROM maintenance_log m
        ORDER BY m.start_time DESC NULLS LAST, m.id DESC
        LIMIT :limit OFFSET :offset
    """)
    List<MaintenanceLog> findAllPaginated(
        @Param("offset") int offset,
        @Param("limit") int limit
    );

    @Query("SELECT COUNT(*) FROM maintenance_log")
    long countAll();

    @Query("""
        SELECT m.* FROM maintenance_log m
        WHERE m.spacecraft_id = :spacecraftId
        ORDER BY m.start_time DESC NULLS LAST, m.id DESC
        LIMIT :limit OFFSET :offset
    """)
    List<MaintenanceLog> findBySpacecraftIdPaginated(
        @Param("spacecraftId") Long spacecraftId,
        @Param("limit") int limit,
        @Param("offset") int offset
    );

    @Query("""
        SELECT COUNT(*) FROM maintenance_log
        WHERE spacecraft_id = :spacecraftId
    """)
    long countBySpacecraftId(@Param("spacecraftId") Long spacecraftId);
}

