drop table if exists Commit;
create table Commit(
    commit_id int primary key auto_increment,
    date_posted bigint,
    start_hour bigint,
    end_hour bigint
);

insert into Commit values (0, 1669947792, 1669947798, 1669947799);
insert into Commit values (1, 1669947793, 1669947798, 1669947799);
insert into Commit values (2, 1669947794, 1669947798, 1669947799);
