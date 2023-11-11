package com.totterman.polarbookshop.quotefunction;

import com.totterman.polarbookshop.quotefunction.domain.Genre;
import com.totterman.polarbookshop.quotefunction.domain.Quote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteFunctionApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	void whenAllQuotesThenReturn() {
		webTestClient
				.get()
				.uri("/allQuotes")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(Quote.class);
	}

	@Test
	void whenRandomQuoteThenReturn() {
		webTestClient
				.get()
				.uri("/randomQuote")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Quote.class);
	}

	@Test
	void whenGenreQuoteThenReturn() {
		webTestClient
				.post()
				.uri("/genreQuote")
				.bodyValue(Genre.FANTASY)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(Quote.class)
				.value(q -> assertThat(q.genre()).isEqualTo(Genre.FANTASY));
	}
	@Test
	void whenGenreQuoteLogQuoteThenReturn() {
//		Quote testQuote = new Quote("Content", "Author", Genre.ADVENTURE);
		webTestClient
				.post()
				.uri("/genreQuote,logQuote")
				.bodyValue(Genre.ADVENTURE)
				.exchange()
				.expectStatus().is2xxSuccessful();
	}

	@Test
	void whenLogQuoteThenReturn() {
		Quote testQuote = new Quote("Content", "Author", Genre.ADVENTURE);
		webTestClient
				.post()
				.uri("/logQuote")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(testQuote)
				.exchange()
				.expectStatus().is2xxSuccessful();
	}
}
