ALTER TABLE cfg_factor ADD COLUMN rcvr_multiple DOUBLE DEFAULT 1;
ALTER TABLE cfg_factor ADD COLUMN rcvr_type INT DEFAULT 0;
ALTER TABLE cfg_factor_list ADD COLUMN rcvr_multiple DOUBLE DEFAULT 1;
ALTER TABLE cfg_factor_list ADD COLUMN rcvr_type INT DEFAULT 0;
