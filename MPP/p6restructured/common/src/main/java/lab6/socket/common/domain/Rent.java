package lab6.socket.common.domain;

import javafx.util.Pair;

import java.time.LocalDate;

public class Rent extends BaseEntity<Pair<Long, Long>> {
    private LocalDate returnDate;

    public Rent(Long _clientId, Long _movieId, LocalDate _returnDate) {
        super(new Pair<>(_clientId, _movieId));
        returnDate = _returnDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Movie " + super.getId().getValue() + " rented to client " + super.getId().getKey();
    }
}
