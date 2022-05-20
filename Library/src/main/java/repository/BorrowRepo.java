package repository;

import javafx.util.Pair;
import model.Borrow;
import org.javatuples.Triplet;

import java.time.LocalDate;

public interface BorrowRepo extends Repository<Triplet<String,Integer, LocalDate>, Borrow>{
}
