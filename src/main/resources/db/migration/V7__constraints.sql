create sequence base_users_seq start with 1 increment by 50;
create sequence loan_matches_seq start with 1 increment by 50;
create sequence loan_offers_seq start with 1 increment by 50;
create sequence loan_proposal_seq start with 1 increment by 50;
create sequence loan_requests_seq start with 1 increment by 50;

create table base_users (
    is_active boolean, 
    is_staff boolean, 
    created_timestamp timestamp(6), 
    pk_id bigint not null, 
    email varchar(255) not null unique, 
    name varchar(255) not null, 
    password_digest varchar(255) not null, 
    primary key (pk_id)
);

create table loan_matches (
    amount numeric(38,2), 
    status smallint not null check (status between 0 and 1), 
    created_timestamp timestamp(6), 
    fk_offer bigint not null, 
    fk_proposal bigint, 
    pk_id bigint not null, 
    primary key (pk_id)
);

create table loan_offers (
    amount numeric(38,2) not null, 
    interest_rate numeric(38,2) not null, 
    status smallint not null check (status between 0 and 3), 
    created_timestamp timestamp(6), 
    duration_days bigint check (duration_days>=1), 
    fk_lender_id bigint not null, 
    pk_id bigint not null, 
    primary key (pk_id)
);

create table loan_proposal (
    status smallint not null check (status between 0 and 1), 
    created_timestamp timestamp(6), 
    fk_request bigint not null, 
    pk_id bigint not null, 
    primary key (pk_id)
);

create table loan_requests (
    requested_amount numeric(38,2) not null, 
    status smallint not null check (status between 0 and 3), 
    created_timestamp timestamp(6), 
    fk_borrower_id bigint not null, 
    pk_id bigint not null, 
    reason varchar(255) not null, 
    primary key (pk_id)
);

alter table if exists loan_matches add constraint FKg1lx9wehqg75feotypxgd02w3 foreign key (fk_offer) references loan_offers;
alter table if exists loan_matches add constraint FKofba9eaeds2prwcputxa3bq03 foreign key (fk_proposal) references loan_proposal;
alter table if exists loan_offers add constraint FKer1ydkm5og16vw5m6rj9qy0fh foreign key (fk_lender_id) references base_users;
alter table if exists loan_proposal add constraint FKln3gpjldxf102m66yvyuacmil foreign key (fk_request) references loan_requests;
alter table if exists loan_requests add constraint FK5s1gx9s5k3wdh13h4pww9u5h8 foreign key (fk_borrower_id) references base_users;
