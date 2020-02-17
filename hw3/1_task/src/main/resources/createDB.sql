drop table if exists Users;
create table Users (
  ip VARCHAR(50),
  browser VARCHAR(50),
  sex Bool,
  age INT
);

drop table if exists IPRegions;
create table IPRegions (
  ip VARCHAR(50),
  region VARCHAR(100)
);

drop table if exists Logs;
create table Logs (
  ip VARCHAR(50),
  time VARCHAR(8),
  request VARCHAR(200),
  size INT,
  status INT,
  browser VARCHAR(50)
);


--
--SELECT region, num FROM
--(SELECT region, COUNT(IPRegions.ip) as num from IPRegions
--JOIN Logs on Logs.ip = IPRegions.ip
--GROUP BY region)
--WHERE num >
--    (
--select avg(num) as mid from (
--  SELECT region, COUNT(IPRegions.ip) as num from IPRegions
--    JOIN Logs on Logs.ip = IPRegions.ip
--  GROUP BY region
--) as tmp
--    );





