#扩展单位转换系数
ALTER TABLE cfg_factor_list ADD COLUMN check_type INT DEFAULT 0;
ALTER TABLE cfg_factor ADD COLUMN unit_param DOUBLE DEFAULT 1;
ALTER TABLE cfg_factor_list ADD COLUMN unit_param DOUBLE DEFAULT 1;