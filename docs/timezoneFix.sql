# set database timezone (I don't know why this is even needed)
SET GLOBAL time_zone = '00:00';
SELECT @@global.time_zone, @@session.time_zone;