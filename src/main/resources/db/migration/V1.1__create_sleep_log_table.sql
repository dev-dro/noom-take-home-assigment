create table if not exists sleep_log (
    id int generated always as identity primary key,
    username varchar not null,
    date date not null,
    started_sleep timestamp not null,
    woke_up timestamp not null,
    minutes_slept numeric not null,
    felt_when_woke_up varchar(4) not null
)