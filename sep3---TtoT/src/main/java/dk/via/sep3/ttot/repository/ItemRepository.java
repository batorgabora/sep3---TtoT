package dk.via.sep3.ttot.repository;

import dk.via.sep3.ttot.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>
{
  List<Item> findAllByOrderByIdAsc();
}
