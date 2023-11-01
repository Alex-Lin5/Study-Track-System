insert into Commit (commit_id, date_posted, start_hour, end_hour) values (0, 1669947792, 1669947798, 1669947799);
insert into Commit (commit_id, date_posted, start_hour, end_hour) values (1, 1669947793, 1669947798, 1669947799);
insert into Commit (commit_id, date_posted, start_hour, end_hour) values (2, 1669947794, 1669947798, 1669947799);

insert into Material (material_id, name, description) values (10, 'spring', 'framework');
insert into Material (material_id, name, description) values (20, 'leetcode', 'coding chalanges');
insert into Material (material_id, name, description) values (30, 'git', 'version control');

insert into Track (track_id, material_from, progress) values (10, 30, 5);
insert into Track (track_id, material_from, progress) values (11, 10, 5);

update Commit set track_from = 10 where commit_id = 0;
update Commit set track_from = 10 where commit_id = 1;
update Commit set track_from = 11 where commit_id = 2;

-- insert into Track_Commits values (0, 10);
-- insert into Track_Commits values (1, 10);
-- insert into Track_Commits values (2, 11);