insert into albums(naam,artiestid) values
('test', (select id from artiesten where naam='test'));