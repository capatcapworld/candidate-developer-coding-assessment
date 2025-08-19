create table tenancy (
    id binary(16) not null,
    name varchar(100) not null,
    primary key (id)
);

create table rent_collection (
    id binary(16) not null,
    tenancy_id binary(16) not null,
    date date not null,
    primary key (id),
    constraint fk__rent_collection__tenancy_id
        foreign key (tenancy_id)
            references tenancy (id)
            on update cascade
            on delete cascade
);

create table order_line (
    id binary(16) not null,
    rent_collection_id binary(16) not null,
    name varchar(100) not null,
    amount decimal(16, 2) not null,
    booking_date date not null,
    booked boolean not null,
    primary key (id),
    constraint fk__order_line__rent_collection_id
        foreign key (rent_collection_id)
            references rent_collection (id)
            on update cascade
            on delete cascade
);
