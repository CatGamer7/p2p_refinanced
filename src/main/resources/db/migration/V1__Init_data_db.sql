CREATE TABLE Users (
  user_id SERIAL PRIMARY KEY,
  user_type VARCHAR CHECK (user_type IN ('borrower', 'lender')),
  username character varying(50),
  user_email character varying(50),
  password character varying(50),
  created_at TIMESTAMP
);

CREATE TABLE BorrowerDetails (
  borrower_id INT PRIMARY KEY REFERENCES Users(user_id),
  credit_score INT,
  status VARCHAR CHECK (status IN ('pending', 'approved', 'rejected'))
);

CREATE TABLE LoanRequests (
  request_id SERIAL PRIMARY KEY,
  borrower_id INT REFERENCES Users(user_id),
  requested_amount DECIMAL,
  reason character varying(50),
  status VARCHAR CHECK (status IN ('pending', 'matched', 'approved', 'rejected'))
);

CREATE TABLE LoanOffers (
  offer_id SERIAL PRIMARY KEY,
  lender_id INT REFERENCES Users(user_id),
  amount DECIMAL NOT NULL,
  status VARCHAR CHECK (status IN ('available', 'matched', 'completed'))
);

CREATE TABLE LoanMatches (
  match_id SERIAL PRIMARY KEY,
  request_id INT REFERENCES LoanRequests(request_id),
  offer_id INT REFERENCES LoanOffers(offer_id),
  amount DECIMAL NOT NULL,
  status VARCHAR CHECK (status IN ('active', 'completed', 'defaulted'))
);

CREATE TABLE Payments (
  payment_id SERIAL PRIMARY KEY,
  match_id INT REFERENCES LoanMatches(match_id),
  amount DECIMAL NOT NULL,
  payment_date DATE,
  status VARCHAR CHECK (status IN ('pending', 'completed'))
);