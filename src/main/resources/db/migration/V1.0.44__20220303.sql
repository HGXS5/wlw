#增加串口FlowControl参数
ALTER TABLE cfg_serial ADD COLUMN flowcontrol int DEFAULT 0;
