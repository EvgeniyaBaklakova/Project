ALTER TABLE group_chat
    ADD COLUMN is_global BOOLEAN NOT NULL DEFAULT TRUE;