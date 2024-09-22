create table if not exists sleep_log (
    id int generated always as identity primary key,
    username varchar not null,
    date date not null,
    start_time timestamp not null,
    end_time timestamp not null,
    total_time interval not null,
    feeling varchar not null
)