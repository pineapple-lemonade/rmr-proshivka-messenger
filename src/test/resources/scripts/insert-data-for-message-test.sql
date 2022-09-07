insert into system_user (user_id, name, phone, password, created_date, updated_date, state)
values ('b43a2e70-b12c-44b7-882f-4c7f57174026', 'gosha', '79961073184',
        '$2a$10$rG.QPil/GZd9p4clOzOb3e3icmG0fXkTRlxZFSxgYzNjmsZiyJz06', '2022-09-05 09:37:17.108863 +00:00',
        '2022-09-05 09:37:17.108863 +00:00', 'CONFIRMED');
insert into system_user (user_id, name, phone, password, created_date, updated_date, state)
values ('b43a2e70-b12c-44b7-882f-4c7f57174027', 'gosha1', '79961073185', 'asdas', '2022-09-05 09:37:17.108863 +00:00',
        '2022-09-05 09:37:17.108863 +00:00', 'CONFIRMED');

insert into user_friend (user_id, friend_id)
values ('b43a2e70-b12c-44b7-882f-4c7f57174026', 'b43a2e70-b12c-44b7-882f-4c7f57174027');
insert into chat(chat_id, type, created_date, updated_date)
values ('eb75ef46-6890-47fd-bc10-6caf046b6c5d', 'PRIVATE', '2022-09-05 09:37:17.108863 +00:00',
        '2022-09-05 09:37:17.108863 +00:00');
insert into chat_user(chat_id, user_id)
values ('eb75ef46-6890-47fd-bc10-6caf046b6c5d', 'b43a2e70-b12c-44b7-882f-4c7f57174026');
insert into chat_user(chat_id, user_id)
values ('eb75ef46-6890-47fd-bc10-6caf046b6c5d', 'b43a2e70-b12c-44b7-882f-4c7f57174027');

insert into message(message_id, text, issuer_id, chat_id, created_date, updated_date)
values ('4e4b6941-584a-4929-99c1-00b09cc3dd8c', 'hello', 'b43a2e70-b12c-44b7-882f-4c7f57174026',
        'eb75ef46-6890-47fd-bc10-6caf046b6c5d', '2022-09-05 09:37:17.108863 +00:00',
        '2022-09-05 09:37:17.108863 +00:00')