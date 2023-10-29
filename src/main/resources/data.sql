drop table if exists Commit;
drop table if exists Material;
create table Commit(
    commit_id int primary key auto_increment,
    date_posted bigint,
    start_hour bigint,
    end_hour bigint
);
create table Material(
    material_id int primary key auto_increment,
    name varchar(255),
    description varchar(255),
    url varchar(255) default 'Unknown',
    note varchar(255) default 'None'
);

insert into Commit values (0, 1669947792, 1669947798, 1669947799);
insert into Commit values (1, 1669947793, 1669947798, 1669947799);
insert into Commit values (2, 1669947794, 1669947798, 1669947799);

insert into Material (material_id, name, description) values (10, 'spring', 'framework');
insert into Material (material_id, name, description) values (20, 'leetcode', 'coding chalanges');
insert into Material (material_id, name, description) values (30, 'git', 'version control');