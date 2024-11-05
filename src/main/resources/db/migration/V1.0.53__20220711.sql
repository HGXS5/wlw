ALTER TABLE cfg_factor ADD COLUMN non_negative tinyint(1) NOT NULL DEFAULT '0';
ALTER TABLE cfg_factor_list ADD COLUMN non_negative tinyint(1) NOT NULL DEFAULT '0';