package entity;

public record Address(String city, String street, int number) {
    @Override
    public String toString() {
        return street + " " + number + ", " + city;
    }
}

