-- Maintenance Service Schema

CREATE TABLE IF NOT EXISTS maintenance_log (
    id BIGSERIAL PRIMARY KEY,
    spacecraft_id BIGINT NOT NULL,
    maintenance_type VARCHAR(50) NOT NULL,
    performed_by_user_id BIGINT NOT NULL,
    supervised_by_user_id BIGINT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'SCHEDULED',
    description TEXT,
    cost DECIMAL(10, 2)
);

CREATE INDEX idx_maintenance_spacecraft ON maintenance_log(spacecraft_id);
CREATE INDEX idx_maintenance_performed_by ON maintenance_log(performed_by_user_id);
CREATE INDEX idx_maintenance_supervised_by ON maintenance_log(supervised_by_user_id);
CREATE INDEX idx_maintenance_status ON maintenance_log(status);
CREATE INDEX idx_maintenance_type ON maintenance_log(maintenance_type);
