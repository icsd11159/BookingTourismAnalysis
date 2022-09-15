package com.company;

public class Accommodation {

    private String HotelName;
    private Double rating;
    private Double price;

public Accommodation() {}

    public String getHotelNameName() {
        return HotelName;
    }

    public Double getRating() {
        return rating;
    }

    public Double getPrice() {
        return price;
    }

// Fields omitted for brevity
    // The same fields should be in `Accommodation` and `AccommodationBuilder`

    // Private constructor means we can't instantiate it
    // by simply calling `new Accommodation()`

    public static class AccommodationBuilder {
        private String HotelName;
        private Double rating;
        private Double price;


        public AccommodationBuilder() {
        }

        public AccommodationBuilder withHotelName(String HotelName) {
            this.HotelName = HotelName;
            return this;
        }

        public AccommodationBuilder withRating(Double rating) {
            this.rating = rating;
            return this;
        }

        public AccommodationBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public Accommodation build() {
            Accommodation accommodation = new Accommodation();
            accommodation.HotelName = this.HotelName;
            accommodation.rating = this.rating;
            accommodation.price = this.price;
            return accommodation;
        }
    }
    @Override
    public String toString() {
        return "Hotel_Name{" +
                "HotelName='" + HotelName + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                '}';
    }


}
