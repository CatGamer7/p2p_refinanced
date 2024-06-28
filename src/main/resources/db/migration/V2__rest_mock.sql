create sequence loan_matches_seq start with 1 increment by 50;
create sequence loan_offers_seq start with 1 increment by 50;
create sequence loan_proposal_seq start with 1 increment by 50;
create sequence loan_requests_seq start with 1 increment by 50;

create table loan_matches (
    amount numeric(38,2),
    status smallint check (status between 0 and 1),
    match_id bigint not null,
    offer_offer_id bigint,
    request_request_id bigint,
    primary key (match_id)
);

create table loan_offers (
    amount numeric(38,2),
    interest_rate numeric(38,2),
    status smallint check (status between 0 and 2),
    duration_days bigint,
    lender_id bigint,
    offer_id bigint not null,
    primary key (offer_id)
);

create table loan_proposal (
    proposal_id bigint not null,
    primary key (proposal_id)
);

create table loan_proposal_matches (
    matches_match_id bigint not null unique,
    proposal_proposal_id bigint not null
);

create table loan_requests (
    requested_amount numeric(38,2),
    status smallint check (status between 0 and 2),
    borrower_id bigint, request_id bigint not null,
    reason varchar(255),
    primary key (request_id)
);

alter table if exists loan_matches add constraint FK6nhca9jf3tojylf1v1522bphw foreign key (offer_offer_id) references loan_offers;
alter table if exists loan_matches add constraint FKo5ydd114a6r6y09sm7rj9j8hs foreign key (request_request_id) references loan_requests;
alter table if exists loan_proposal_matches add constraint FKnt8r40rmm7bvbhy5dmkuq1so7 foreign key (matches_match_id) references loan_matches;
alter table if exists loan_proposal_matches add constraint FKa8eq69iwfqib72y1l09bj9gjr foreign key (proposal_proposal_id) references loan_proposal;
