-- Baseline migration for the RoosterViewCall database.
-- Enables pgcrypto, which future migrations will rely on for UUID generation.
CREATE EXTENSION IF NOT EXISTS pgcrypto;
