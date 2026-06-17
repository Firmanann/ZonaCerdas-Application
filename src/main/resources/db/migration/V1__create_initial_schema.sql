-- 1. Table Users (Domain: Auth)
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    country VARCHAR(2) DEFAULT 'ID',

    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. Table Generated Games (Domain: Generator)
CREATE TABLE generated_games (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    title VARCHAR(100) NOT NULL,
    html_content TEXT,
    download_url TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_game FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. Table Contents (Domain: Catalog / Contributor)
CREATE TABLE contents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contributor_id UUID NOT NULL,
    content_type VARCHAR(50) NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    pricing VARCHAR(20) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'APPROVED',
    subject VARCHAR(50),      -- (Misal: ipa, matematika)
    grade_level VARCHAR(20),  -- (Misal: SD, SMP, SMA)
    file_url TEXT,            -- (URL file .html / .pdf)
    thumbnail_url TEXT,       -- (URL gambar cover)
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_contributor_content FOREIGN KEY (contributor_id) REFERENCES users(id) ON DELETE CASCADE
);