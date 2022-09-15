package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


public class Parsing {

    private final String destination;
    private final LocalDate checkin;
    private final LocalDate checkout;
    private static final Logger logger = Logger.getLogger(Parsing.class.getName());
    private static final DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Parsing(String destination, Date date) {
        this.destination = destination;
        this.checkin = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.checkout = this.checkin.plusDays(1);
    }


    // booking.com page retrieval and parsing.
    public static List< Accommodation > parseBookingPage(String destination, String date, Integer pageNumber) throws IOException {
        LocalDate checkin = LocalDate.parse(date);
        LocalDate checkout = checkin.plusDays(1);
        // Search url is created
        String url = "https://www.booking.com/searchresults.en.html?ss=" + destination + "&checkin_year=" + checkin.getYear() +"&checkin_month="+ checkin.getMonthValue() +"&checkin_monthday=" + checkin.getDayOfMonth() +
                "&checkout_year=" + checkout.getYear() + "&checkout_month=" + checkout.getMonthValue()+ "&checkout_monthday=" + checkout.getDayOfMonth() + "&offset=" + pageNumber * 25;
        Document doc = Jsoup.connect(url.toString()).get();

        // extraction
        List<Accommodation> accommodations = new ArrayList<>();
        for (Element div : doc.select(".sr_property_block")) {
            Double rating = null;
            try {
                rating = Double.parseDouble(div.select(".bui-review-score__badge").text());
            } catch (NullPointerException | NumberFormatException e) {
                /* Score value remains null. */
            }
            Double price = null;
            try {
                price = Double.parseDouble(div.select(".bui-price-display__value").text().replace("€ ", ""));
            } catch (NullPointerException | NumberFormatException e) {
                /* Price value remains null. Property is considered as unavailable. */
            }
            Accommodation accommodation = new Accommodation.AccommodationBuilder().withHotelName(div.select(".sr-hotel__name").text()).withRating(rating).withPrice(price).build();
            accommodations.add(accommodation);
        }
        return accommodations;
    }

    // hotels.com page retrieval and parsing.
    public static List<Accommodation> parseHotelsPage(String destination, String date, Integer pageNumber) throws IOException {
        LocalDate checkin = LocalDate.parse(date);
        LocalDate checkout = checkin.plusDays(1);
        String url = "https://el.hotels.com/search.do?q-destination=" + destination + "&q-check-in=" + checkin.format(formatter) + "&q-check-out=" + checkout.format(formatter) + "&pn=" + pageNumber;
        Document doc = Jsoup.connect(url).get();

        // Properties extraction.
        List<Accommodation> accommodations = new ArrayList<>();
        for (Element div : doc.select(".hotel-wrap")) {
            Double rating = null;
            try {
                rating = Double.parseDouble(div.select(".guest-reviews-badge").text().replaceAll("\\D+", "")) / 10;
            } catch (NullPointerException | NumberFormatException e) {
                /* Score value remains null. */
            }
            Double price = null;
            try {
                price = Double.parseDouble(div.select(".price").text().replace("€", ""));
            } catch (NullPointerException | NumberFormatException e) {
                /* Price value remains null. Property is considered as unavailable. */
            }
            Accommodation accommodation = new Accommodation.AccommodationBuilder()
                    .withHotelName(div.select(".p-name").text())
                    .withRating(rating)
                    .withPrice(price)
                    .build();
            accommodations.add(accommodation);
        }
        return accommodations;
    }

    // ekdromi.gr page retrieval and parsing.
    public static List<Accommodation> parseEkdromiPage(String destination) throws IOException {
        String url = "https://www.agoda.com/search?asq=" + destination;
        Document doc = Jsoup.connect(url).get();

        // Properties extraction.
        List<Accommodation> accommodations = new ArrayList<>();
        for (Element div : doc.select(".hotel-list-container")) {
            Double price = null;
            try {
                price = Double.parseDouble(div.select(".PropertyCardPrice").text().replace("€", ""));
            } catch (NullPointerException | NumberFormatException e) {
                /* Price value remains null. Property is considered as unavailable. */
            }
            Accommodation accommodation = new Accommodation.AccommodationBuilder()
                    .withHotelName(div.select(".hotel-name").text())
                    .withPrice(price)
                    .build();
            accommodations.add(accommodation);

        }
        return accommodations;


    }

    // ekdromi.gr page retrieval and parsing.
    public static List<Accommodation> parseAgodaPage(String destination) throws IOException {
        String url = "https://www.ekdromi.gr/frontend/deals/search?term=" + destination;
        Document doc = Jsoup.connect(url).get();

        // Properties extraction.
        List<Accommodation> accommodations = new ArrayList<>();
        for (Element div : doc.select(".list_deal_container")) {
            Double price = null;
            try {
                price = Double.parseDouble(div.select(".price").text().replace("€", ""));
            } catch (NullPointerException | NumberFormatException e) {
                /* Price value remains null. Property is considered as unavailable. */
            }
            Accommodation accommodation = new Accommodation.AccommodationBuilder()
                    .withHotelName(div.select(".hotel").text())
                    .withPrice(price)
                    .build();
            accommodations.add(accommodation);

        }
        return accommodations;


    }


}











