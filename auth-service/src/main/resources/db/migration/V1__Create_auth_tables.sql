-- Users table
CREATE TABLE user (
    id BIGSERIAL PRIMARY KEY,
    enabled BOOLEAN NOT NULL DEFAULT true,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

-- Roles table
CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE
);

-- User-Roles table
CREATE TABLE user_role (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES roles(id) ON DELETE CASCADE,

    PRIMARY KEY (user_id, role_id)
);

-- Create indexes for better performance
CREATE INDEX idx_role_name ON role(name);
CREATE INDEX idx_user_username ON user(username);
