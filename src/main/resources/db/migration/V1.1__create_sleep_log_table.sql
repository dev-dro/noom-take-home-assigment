create type feeling as enum ('good', 'bad', 'ok');

create table if not exists sleep_log (
    id int generated always as identity primary key,
    username varchar not null,
    log_date date not null,
    started_sleep_at timestamp not null,
    woke_up_at timestamp not null,
    morning_feeling feeling not null
)