-- --------------------------------------------------
-- USERS
-- --------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE
    );

-- --------------------------------------------------
-- ROLES
-- --------------------------------------------------
CREATE TABLE IF NOT EXISTS roles (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL UNIQUE
    );

-- --------------------------------------------------
-- USER_ROLES (many-to-many)
-- --------------------------------------------------
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id INT REFERENCES users(id) ON DELETE CASCADE,
    role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY(user_id, role_id)
    );

-- --------------------------------------------------
-- PERMISSIONS
-- --------------------------------------------------
CREATE TABLE IF NOT EXISTS permissions (
                                           id SERIAL PRIMARY KEY,
                                           name VARCHAR(50) NOT NULL UNIQUE
    );

-- --------------------------------------------------
-- ROLE_PERMISSIONS
-- --------------------------------------------------
CREATE TABLE IF NOT EXISTS role_permissions (
                                  role_id INT REFERENCES roles(id) ON DELETE CASCADE,
                                  permission VARCHAR(50) NOT NULL,
                                  PRIMARY KEY(role_id, permission)
);

-- --------------------------------------------------
-- CATEGORIES
-- --------------------------------------------------
CREATE TABLE IF NOT EXISTS categories (
                                          id SERIAL PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL,
    description TEXT
    );

-- --------------------------------------------------
-- RECIPES
-- --------------------------------------------------
CREATE TABLE IF NOT EXISTS recipes (
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR(100) NOT NULL,
    description TEXT,
    preparation_time INT NOT NULL,
    category_id INT REFERENCES categories(id),
    user_id INT REFERENCES users(id) ON DELETE CASCADE
    );


-- --------------------------------------------------
-- Default permissions
-- --------------------------------------------------
INSERT INTO permissions (name) VALUES ('USER_READ') ON CONFLICT DO NOTHING;
INSERT INTO permissions (name) VALUES ('USER_UPDATE') ON CONFLICT DO NOTHING;
INSERT INTO permissions (name) VALUES ('USER_DELETE') ON CONFLICT DO NOTHING;
INSERT INTO permissions (name) VALUES ('USER_VIEW') ON CONFLICT DO NOTHING;
INSERT INTO permissions (name) VALUES ('CATEGORY_CREATE') ON CONFLICT DO NOTHING;
INSERT INTO permissions (name) VALUES ('CATEGORY_UPDATE') ON CONFLICT DO NOTHING;
INSERT INTO permissions (name) VALUES ('CATEGORY_DELETE') ON CONFLICT DO NOTHING;
INSERT INTO permissions (name) VALUES ('RECIPE_CREATE') ON CONFLICT DO NOTHING;
INSERT INTO permissions (name) VALUES ('RECIPE_UPDATE') ON CONFLICT DO NOTHING;
INSERT INTO permissions (name) VALUES ('RECIPE_DELETE') ON CONFLICT DO NOTHING;

