
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

INSERT INTO movies (title, genre, release_year, rating, overview) VALUES

('Vada Chennai', 'Action', 2018, 8.6, 'A young carrom player becomes embroiled in a turf war between rival gangsters.'),
('Mankatha', 'Action', 2011, 8.1, 'A suspended police officer plans a high-stakes heist involving cricket betting money.'),
('Petta', 'Action', 2019, 7.9, 'A hostel warden with a mysterious violent past takes on a dangerous politician.'),
('Thuppakki', 'Action', 2012, 8.2, 'An army captain tracks down and destroys terrorist sleeper cells in Mumbai.'),
('Kaththi', 'Action', 2014, 8.1, 'An escaped convict switches places with his lookalike to fight a corporate giant stealing village water.'),
('Ayan', 'Action', 2009, 8.1, 'A smart young man works for a smuggling ring while evading a ruthless customs official.'),
('Sarpatta Parambarai', 'Action', 2021, 8.6, 'A young man in 1970s Madras gets a shot at reviving his boxing clan''s lost glory.'),
('Soorarai Pottru', 'Action', 2020, 9.1, 'A visionary young man dreams of launching a low-cost airline accessible to the common man.'),
('Vikram Vedha', 'Action', 2017, 8.4, 'A pragmatic police officer tracks down a notorious gangster who surrenders and flips his worldview with stories.'),
('Ghillie', 'Action', 2004, 8.1, 'A Kabaddi player rescues a young woman from a powerful and ruthless faction leader in Madurai.'),

('Soodhu Kavvum', 'Comedy', 2013, 8.3, 'A quirky kidnapper and his three eccentric associates find themselves trapped in a chaotic plot gone wrong.'),
('Tamizh Padam', 'Comedy', 2010, 7.7, 'A hilarious parody that pokes fun at mainstream commercial Tamil cinema tropes and stereotypes.'),
('Kalakalappu', 'Comedy', 2012, 7.5, 'Two brothers resort to hilarious schemes to save their ancestral family restaurant from debt.'),
('Mark Antony', 'Comedy', 2023, 7.6, 'Two gangsters discover a time-traveling telephone that allows them to alter the timeline of their chaotic past.'),
('Jigarthanda', 'Comedy', 2014, 8.2, 'An aspiring filmmaker tries to secretly shadow a cold-blooded gangster to write a film script.'),
('Panchatanthiram', 'Comedy', 2002, 8.3, 'A married man and his four loyal friends get trapped in a web of lies surrounding a missing diamond.'),
('Comali', 'Comedy', 2019, 7.4, 'A man wakes up from a 16-year coma and struggles to adjust to the bizarre realities of modern technology.'),
('Naanum Rowdy Dhaan', 'Comedy', 2015, 7.8, 'The son of a police officer wants to become a gangster just to impress a girl seeking revenge.'),
('Goa', 'Comedy', 2010, 7.3, 'Three carefree village friends run away to Goa in hopes of marrying wealthy foreign tourists.'),
('Kalakku', 'Comedy', 2024, 7.1, 'A dynamic wedding planner creates complete structural havoc trying to fix his own broken marriage proposal.'),

('Raja Rani', 'Romance', 2013, 7.6, 'A newlywed couple who strongly dislike each other learn to live together after discovering their tragic past relationships.'),
('Alaipayuthey', 'Romance', 2000, 8.3, 'A young eloped couple finds out that marriage is far more complicated than the fantasy of romance.'),
('Sita Ramam', 'Romance', 2022, 8.6, 'An orphan soldier receives anonymous love letters from a girl named Sita, sparking an epic romance across borders.'),
('Vaaranam Aayiram', 'Romance', 2008, 8.2, 'A young man navigates the painful loss of his first love and finds himself through his father''s guidance.'),
('Thiruchitrambalam', 'Romance', 2022, 7.9, 'A delivery driver navigates his turbulent family life and his dynamic feelings for his supportive childhood best friend.'),
('Neethaane En Ponvasantham', 'Romance', 2012, 6.9, 'Two childhood sweethearts drift apart and cross paths again at different stages of their adult lives.'),
('Kushi', 'Romance', 2001, 7.8, 'Two egotistical college students fall in love but refuse to confess it due to their massive pride.'),
('Kadhalil Sodhappuvadhu Yeppadi', 'Romance', 2012, 7.4, 'A witty exploration of how trivial misunderstandings can completely tear apart a young couple''s love life.'),
('Paiyaa', 'Romance', 2010, 7.2, 'A carefree young man agrees to drive a beautiful woman on a spontaneous and dangerous road trip to Mumbai.'),
('Meyaadha Maan', 'Romance', 2017, 7.7, 'A local stage singer struggles for years to confess his deep love for his college crush.'),

('Imaikkaa Nodigal', 'Thriller', 2018, 7.6, 'A CBI officer tracks a dynamic serial killer who claims to be the ghost of a dead murderer.'),
('Irumbu Thirai', 'Thriller', 2018, 7.6, 'An army officer fights a shadow hacker who stole money from his bank account using data theft.'),
('Kuttram 23', 'Thriller', 2017, 7.1, 'An upright medical crime investigator looks into a suspicious case involving illegal fertility clinics.'),
('Pizza', 'Thriller', 2012, 8.0, 'A pizza delivery boy gets trapped inside a mysterious, haunted house while out on a routine delivery.'),
('Visaranai', 'Thriller', 2015, 8.5, 'Four migrant workers are brutally tortured by corrupt police officers to confess to a crime they did not commit.'),
('Game Over', 'Thriller', 2019, 7.2, 'A video game designer battling PTSD must defend her home from a terrifying home invasion.'),
('Maara', 'Thriller', 2021, 7.6, 'A young restoration artist searches for a wandering painter after spotting his art drawn on a coastal village wall.'),
('Gargi', 'Thriller', 2022, 8.1, 'A courageous young schoolteacher fights a complex legal battle to prove her father''s innocence.'),
('Thegidi', 'Thriller', 2014, 7.7, 'A rookie private detective realizes that the subjects he was hired to shadow are dying mysteriously.'),
('Spyder', 'Thriller', 2017, 6.3, 'An intelligence officer uses specialized data tapping tools to track down a sadistic serial killer.'),

('Deiva Thirumagal', 'Drama', 2011, 8.1, 'A mentally challenged father fights a fierce legal custody battle against his wealthy in-laws for his daughter.'),
('Kadaisi Vivasayi', 'Drama', 2021, 8.6, 'An 85-year-old traditional farmer stands as the absolute last agricultural holdout in his converting village.'),
('Kaaka Muttai', 'Drama', 2014, 8.3, 'Two resourceful slum children embark on a quest just to taste a slice of expensive pizza.'),
('Cuckoo', 'Drama', 2014, 7.6, 'A tender, moving romance that blooms between two fiercely independent, visually impaired individuals.'),
('Super Deluxe', 'Drama', 2019, 8.3, 'An unpredictable day weaves together the lives of an estranged wife, a trans woman, and curious teenagers.'),

('Captain', 'Sci-Fi', 2022, 5.4, 'An army team sent into a restricted forest zone encounters a highly hostile alien lifeform.'),
('Miruthan', 'Sci-Fi', 2016, 6.0, 'A traffic cop fights to rescue his sister and find a cure during a sudden, violent zombie outbreak.'),
('7aum Arivu', 'Sci-Fi', 2011, 6.6, 'A genetic engineering student tries to revive the ancient combat skills of Bodhidharma to halt a bio-weapon attack.'),
('Aalavandhan', 'Sci-Fi', 2001, 7.2, 'A commando protects his fiancee from his twin brother, who suffers from severe drug-induced hallucinations.'),
('Hero', 'Sci-Fi', 2019, 6.3, 'A young man takes on the mantle of a tech-savvy vigilante to expose an exploitative educational system.');