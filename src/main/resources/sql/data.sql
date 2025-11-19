USE `library`;

SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM book;
DELETE FROM author;
DELETE FROM publisher;

ALTER TABLE author AUTO_INCREMENT = 1;
ALTER TABLE publisher AUTO_INCREMENT = 1;
ALTER TABLE book AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `author` (`name`, `nationality`, `birth_date`, `email`, `website`, `create_date`, `update_date`)
VALUES (
    'Brandon Sanderson',
    'estadounidense',
    '1975-12-19',
    '',
    'https://www.brandonsanderson.com/',
    NOW(),
    NOW()
);

INSERT INTO `publisher` (`name`, `country`, `website`, `email`, `address`, `create_date`, `update_date`)
VALUES (
    'Tor Books',
    'Estados Unidos',
    'http://us.macmillan.com/torpublishinggroup/',
    'torpublicity@tor.com',
    'Tor Publishing Group\n120 Broadway\nNew York, NY 10271',
    NOW(),
    NOW()
);


INSERT INTO `book`
(`title`, `isbn`, `publication_date`, `pages`, `language`, `summary`, `genre`, `create_date`, `update_date`, `author_id`, `publisher_id`)
VALUES
(
    'The Way of Kings',
    '978-0-7653-2635-5',
    '2010-08-31',
    1007,
    'English',
    'The backstory of the novel revolves around recurring disasters known as Desolations,\nwhere monstrous Voidbringers ravage the world and human survival hangs in the balance.\nTo counter the threat, the Knights Radiant (so named for their glowing aura and eyes) battle\nagainst the Voidbringers using magical armor and swords known as Shardplate and Shardblades,\nas well as magical powers. The most recent Desolation, which occurred thousands of years before\nthe main events of the novel, was believed to be the final one, and has become a subject of\nmyth and legend. The Knights Radiant discarded armor and swords which remain as some of the\nmost priceless heirlooms.\n\nThe magic of the world is based on "stormlight" from recurring, hurricane force magically powered\nstorms known as highstorms. Commonplace gemstones infused with stormlight are used as mundane currency\nin merchant transactions, as well as interior lighting at night in wealthy houses and palaces.\nMany artifacts are powered by stormlight, such as one that convert matter into another form, like\nstone into grain. Nobility is also based on eye color, blue eyes being seen as the purest royalty\ndue to the association with the legendary Knights Radiant, who had glowing eyes.\n\nThe world itself has flora and fauna which have adapted to the common and extremely powerful highstorms.\nMost animal life is based on crustaceans, many of which can burrow into the ground to survive a highstorm.\nPlant life is also mobile, in that it retracts into the ground to survive highstorms.\nBecause all highstorms come from the eastern ocean and travel west, the western side of rocks and mountains\nharbor plant and animal life. Also, spirits called spren exist and react to the emotions of people and the environment.\nHigh wind will have windspren in the form of ribbons of light that flow with the wind and can change their shapes.\nSuffering from pain will cause red painspren to appear around the wound, and giving a noble, heartfelt speech or\ncompleting a hard task will have gloryspren of golden, twinkling lights form a halo around the head of the speaker.\nSpren are so common that many people pay no attention to them.',
    'fantasy',
    NOW(),
    NOW(),
    1,
    1
),
(
    'Words of Radiance',
    '978-0-7653-2636-2',
    '2014-03-04',
    1248,
    'English',
    'No summary',
    'fantasy',
    NOW(),
    NOW(),
    1,
    1
),
(
    'Oathbringer',
    '978-0-7653-2637-9',
    '2017-11-14',
    1258,
    'English',
    'No summary',
    'fantasy',
    NOW(),
    NOW(),
    1,
    1
);