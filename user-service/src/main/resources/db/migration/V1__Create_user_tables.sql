-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    enabled BOOLEAN NOT NULL DEFAULT true,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE
);

-- Roles table
CREATE TABLE roles (
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
CREATE INDEX idx_roles_name ON roles(name);
CREATE INDEX idx_users_username ON users(username);
