ALTER USER 'root'@'localhost' IDENTIFIED BY 'rootpassword';
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY 'rootpassword';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;

-- Allow aliyara to connect from anywhere with full permissions
CREATE USER IF NOT EXISTS 'aliyara'@'%' IDENTIFIED BY 'aliyarapassword';
GRANT ALL PRIVILEGES ON *.* TO 'aliyara'@'%' WITH GRANT OPTION;

-- Refresh privileges
FLUSH PRIVILEGES;