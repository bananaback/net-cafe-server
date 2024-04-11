IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'netcafeserverdb')
BEGIN
  CREATE DATABASE netcafeserverdb;
END;