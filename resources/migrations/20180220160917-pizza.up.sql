
CREATE TABLE pizza (
  id BIGSERIAL PRIMARY KEY,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  data JSONB NOT NULL
);