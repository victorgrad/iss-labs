package repository;

import javafx.util.Pair;
import model.Borrow;
import org.javatuples.Triplet;
import utils.BorrowId;
import utils.Trip;

import java.time.LocalDate;

public interface BorrowRepo extends Repository<BorrowId, Borrow>{
}
