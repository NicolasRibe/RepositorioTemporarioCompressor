INSERT INTO compressor_db.registro_compressor
(data_hora, ligado, estado, hora_carga, hora_total, pressao_ar_comprimido, pressao_carga,
 pressao_alivio, temperatura_ambiente, temperatura_ar_comprimido, temperatura_oleo, temperatura_orvalho, compressor_id)
VALUES
('2025-10-03 09:30:00', TRUE, 'STANDBY', 120, 540, 7.5, 8.0, 6.8, 25.3, 45.2, 60.8, 5.2, 1);

INSERT INTO compressor_db.registro_compressor
(data_hora, ligado, estado, hora_carga, hora_total, pressao_ar_comprimido, pressao_carga,
 pressao_alivio, temperatura_ambiente, temperatura_ar_comprimido, temperatura_oleo, temperatura_orvalho, compressor_id)
VALUES
('2025-10-03 09:35:00', FALSE, 'RUNNING', 120, 540, 7.5, 8.0, 6.8, 25.3, 45.2, 60.8, 10.1, 1);

INSERT INTO compressor_db.registro_compressor
(data_hora, ligado, estado, hora_carga, hora_total, pressao_ar_comprimido, pressao_carga,
 pressao_alivio, temperatura_ambiente, temperatura_ar_comprimido, temperatura_oleo, temperatura_orvalho, compressor_id)
VALUES
('2025-10-03 09:40:00', TRUE, 'STOPPED', 120, 540, 7.5, 8.0, 6.8, 25.3, 45.2, 60.8, 23.1, 2);
