package com.alysrazor.library;

import com.alysrazor.library.entity.Author;
import com.alysrazor.library.entity.Book;
import com.alysrazor.library.entity.Publisher;
import com.alysrazor.library.repository.AuthorRepository;
import com.alysrazor.library.repository.BookRepository;
import com.alysrazor.library.repository.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

    @Bean
    @Profile("dev")
    public CommandLineRunner demoData(
            AuthorRepository authorRepo,
            BookRepository bookRepo,
            PublisherRepository publisherRepo
    ) {
        return args -> {
            bookRepo.deleteAll();
            authorRepo.deleteAll();
            publisherRepo.deleteAll();
            Author brandon = authorRepo.save(
                    new Author(
                            "Brandon Sanderson",
                            "estadounidense",
                            LocalDate.of(1975, 12, 19),
                            "",
                            "https://www.brandonsanderson.com/"
                    )
            );

            Publisher tor = publisherRepo.save(
                    new Publisher(
                            "Tor Books",
                            "Estados Unidos",
                            "http://us.macmillan.com/torpublishinggroup/",
                            "torpublicity@tor.com",
                            """
                                    Tor Publishing Group
                                    120 Broadway
                                    New York, NY 10271"""
                    )
            );


            bookRepo.save(
                    new Book(
                            "The Way of Kings",
                            "978-0-7653-2635-5",
                            LocalDate.of(2010, 8, 31),
                            1007,
                            "English",
                            """
                                    The backstory of the novel revolves around recurring disasters known as Desolations, 
                                    where monstrous Voidbringers ravage the world and human survival hangs in the balance. 
                                    To counter the threat, the Knights Radiant (so named for their glowing aura and eyes) battle
                                    against the Voidbringers using magical armor and swords known as Shardplate and Shardblades,
                                    as well as magical powers. The most recent Desolation, which occurred thousands of years before
                                    the main events of the novel, was believed to be the final one, and has become a subject of 
                                    myth and legend. The Knights Radiant discarded armor and swords which remain as some of the 
                                    most priceless heirlooms.
                                    
                                    The magic of the world is based on "stormlight" from recurring, hurricane force magically powered
                                    storms known as highstorms. Commonplace gemstones infused with stormlight are used as mundane currency
                                    in merchant transactions, as well as interior lighting at night in wealthy houses and palaces. 
                                    Many artifacts are powered by stormlight, such as one that convert matter into another form, like
                                    stone into grain. Nobility is also based on eye color, blue eyes being seen as the purest royalty
                                    due to the association with the legendary Knights Radiant, who had glowing eyes.
                                    
                                    The world itself has flora and fauna which have adapted to the common and extremely powerful highstorms.
                                    Most animal life is based on crustaceans, many of which can burrow into the ground to survive a highstorm.
                                    Plant life is also mobile, in that it retracts into the ground to survive highstorms. 
                                    Because all highstorms come from the eastern ocean and travel west, the western side of rocks and mountains
                                    harbor plant and animal life. Also, spirits called spren exist and react to the emotions of people and the environment.
                                    High wind will have windspren in the form of ribbons of light that flow with the wind and can change their shapes.
                                    Suffering from pain will cause red painspren to appear around the wound, and giving a noble, heartfelt speech or 
                                    completing a hard task will have gloryspren of golden, twinkling lights form a halo around the head of the speaker.
                                    Spren are so common that many people pay no attention to them.
                            """,
                            "fantasy",
                            brandon,
                            tor
                    )
            );

            bookRepo.save(
                    new Book(
                            "Words of Radiance",
                            "978-0-7653-2636-2",
                            LocalDate.of(2014, 3, 4),
                            1248,
                            "English",
                            """
                            No summary
                            """,
                            "fantasy",
                            brandon,
                            tor
                    )
            );

            bookRepo.save(
                    new Book(
                            "Oathbringer",
                            "978-0-7653-2637-9",
                            LocalDate.of(2017, 11, 14),
                            1258,
                            "English",
                            """
                            No summary
                            """,
                            "fantasy",
                            brandon,
                            tor
                    )
            );
        };
    }
}
