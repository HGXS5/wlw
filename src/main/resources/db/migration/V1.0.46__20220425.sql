ALTER TABLE cfg_dev ADD COLUMN related_id2 int DEFAULT 0;
ALTER TABLE cfg_factor ADD COLUMN rcvr_limit double DEFAULT 0;
ALTER TABLE cfg_factor_list ADD COLUMN rcvr_limit double DEFAULT 0;
