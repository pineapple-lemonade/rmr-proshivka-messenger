insert into system_user (user_id, name, phone, password, created_date, updated_date, state)
values ('b43a2e70-b12c-44b7-882f-4c7f57174026', 'gosha', '79961073184',
        '$2a$10$rG.QPil/GZd9p4clOzOb3e3icmG0fXkTRlxZFSxgYzNjmsZiyJz06', '2022-09-05 09:37:17.108863 +00:00',
        '2022-09-05 09:37:17.108863 +00:00', 'CONFIRMED');
insert into system_user (user_id, name, phone, password, created_date, updated_date, state)
values ('b43a2e70-b12c-44b7-882f-4c7f57174027', 'gosha1', '79961073185', 'asdas', '2022-09-05 09:37:17.108863 +00:00',
        '2022-09-05 09:37:17.108863 +00:00', 'CONFIRMED');

insert into system_user (user_id, name, phone, password, created_date, updated_date, state)
values ('b43a2e70-b12c-44b7-882f-4c7f57174028', 'gosha2', '79961073186', 'asdas', '2022-09-05 09:37:17.108863 +00:00',
        '2022-09-05 09:37:17.108863 +00:00', 'CONFIRMED');

insert into user_friend (user_id, friend_id)
values ('b43a2e70-b12c-44b7-882f-4c7f57174026', 'b43a2e70-b12c-44b7-882f-4c7f57174027');

insert into user_friend (friend_id, user_id)
values ('b43a2e70-b12c-44b7-882f-4c7f57174026', 'b43a2e70-b12c-44b7-882f-4c7f57174027');