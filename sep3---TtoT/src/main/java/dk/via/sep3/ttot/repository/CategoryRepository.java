package dk.via.sep3.ttot.repository;

import dk.via.sep3.ttot.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>
{
}
