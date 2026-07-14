
CREATE DATABASE movierecommendation;
USE movierecommendation;
CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    genre VARCHAR(30) NOT NULL,
    release_year INT,
    rating DECIMAL(2,1),
    overview TEXT
);
INSERT INTO movies (title, genre, release_year, rating, overview) VALUES
('Leo','Action',2023,8.4,'A cafe owner''s peaceful life changes after his violent past resurfaces.'),
('Vikram','Action',2022,8.8,'A special investigator uncovers a drug syndicate.'),
('Kaithi','Action',2019,8.5,'An ex-convict helps police fight criminals in one night.'),
('Master','Action',2021,8.2,'A professor confronts a ruthless gangster using juveniles.'),
('Jailer','Action',2023,8.3,'A retired jailer hunts down criminals to rescue his son.'),

('Doctor','Comedy',2021,8.0,'A military doctor rescues kidnapped children.'),
('Don','Comedy',2022,7.8,'A college student discovers his true passion.'),
('Varuthapadatha Valibar Sangam','Comedy',2013,7.6,'A carefree youngster falls in love.'),
('Boss Engira Bhaskaran','Comedy',2010,7.9,'A jobless man tries to prove himself.'),
('OK OK','Comedy',2012,7.5,'Two people fall in love despite their differences.'),

('96','Romance',2018,8.6,'Two school sweethearts reunite after many years.'),
('Love Today','Romance',2022,8.1,'A couple exchanges phones before marriage.'),
('Sillunu Oru Kadhal','Romance',2006,8.0,'A husband learns about his wife''s past love.'),
('Oh My Kadavule','Romance',2020,8.2,'A man gets a second chance at love.'),
('Vinnaithaandi Varuvaayaa','Romance',2010,8.4,'A filmmaker falls deeply in love.'),

('Ratsasan','Thriller',2018,8.7,'A police officer hunts a serial killer.'),
('Thadam','Thriller',2019,8.2,'Twin brothers become murder suspects.'),
('Dhuruvangal Pathinaaru','Thriller',2016,8.3,'A retired officer recalls an unsolved case.'),
('Por Thozhil','Thriller',2023,8.4,'Two officers investigate serial murders.'),
('Yutham Sei','Thriller',2011,8.1,'A police officer searches for his missing brother.'),

('Aruvi','Drama',2017,8.5,'A young woman questions society.'),
('Karnan','Drama',2021,8.3,'A fearless youth fights oppression.'),
('Asuran','Drama',2019,8.5,'A farmer protects his family from powerful enemies.'),
('Jai Bhim','Drama',2021,9.1,'A lawyer fights for justice for tribal people.'),
('Pariyerum Perumal','Drama',2018,8.8,'A law student battles caste discrimination.'),

('Enthiran','Sci-Fi',2010,8.0,'A robot develops human emotions.'),
('24','Sci-Fi',2016,8.2,'A scientist invents a time-travel watch.'),
('Maanaadu','Sci-Fi',2021,8.4,'A man is trapped in a time loop.'),
('Indru Netru Naalai','Sci-Fi',2015,8.1,'Friends discover a time machine.'),
('Tik Tik Tik','Sci-Fi',2018,6.8,'A team travels to space to stop an asteroid.');