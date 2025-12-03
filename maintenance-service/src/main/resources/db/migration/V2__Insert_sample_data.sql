-- Тестовые данные для maintenance-service

INSERT INTO maintenance_log (
    spacecraft_id, maintenance_type, performed_by_user_id, supervised_by_user_id,
    start_time, end_time, status, description, cost
) VALUES
    (1, 'ROUTINE', 4, 2, '2025-01-10 10:00:00', '2025-01-10 14:30:00', 'COMPLETED', 'Routine check-up and fluid replacement', 1500.00),
    (2, 'REPAIR', 4, NULL, '2025-01-12 08:00:00', NULL, 'IN_PROGRESS', 'Engine diagnostic and possible repair', 5000.00);

