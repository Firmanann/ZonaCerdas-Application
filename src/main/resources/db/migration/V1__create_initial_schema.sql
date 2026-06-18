-- 1. Table Roles (Domain: Auth)
CREATE TABLE roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status VARCHAR(50) UNIQUE NOT NULL
);

-- Insert Default Roles
INSERT INTO roles (status) VALUES ('USER'), ('CONTRIBUTOR');

-- 2. Table Users (Domain: Auth / User)
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance INTEGER NOT NULL DEFAULT 0,
    bank_name VARCHAR(50),
    bank_account VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT
);

-- 3. Table Contents (Domain: Catalog / Contributor)
CREATE TABLE contents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contributor_id UUID NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    price INTEGER NOT NULL DEFAULT 0,
    file_url TEXT NOT NULL,
    thumbnail_url TEXT,
    category VARCHAR(50),
    subject VARCHAR(50),
    grade_level VARCHAR(20),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_content_contributor FOREIGN KEY (contributor_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. Table Purchases (Domain: Library)
CREATE TABLE purchases (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    content_id UUID NOT NULL,
    price_paid INTEGER NOT NULL DEFAULT 0,
    purchased_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_purchase_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_purchase_content FOREIGN KEY (content_id) REFERENCES contents(id) ON DELETE CASCADE,
    CONSTRAINT uq_user_content UNIQUE (user_id, content_id)
);