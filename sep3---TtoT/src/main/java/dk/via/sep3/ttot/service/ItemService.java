package dk.via.sep3.ttot.service;

import dk.via.sep3.ttot.model.Item;
import dk.via.sep3.ttot.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService
{
  private final ItemRepository itemRepository;

  public ItemService(ItemRepository itemRepository)
  {
    this.itemRepository = itemRepository;
  }

  public List<Item> getAll()
  {
    return itemRepository.findAllByOrderByIdAsc();
  }

  public Optional<Item> getById(int id)
  {
    return itemRepository.findById(id);
  }

  public Item save(Item item)
  {
    if (item.getUploadDate() == null)
    {
      item.setUploadDate(LocalDate.now());
    }
    return itemRepository.save(item);
  }

  public void delete(int id)
  {
    itemRepository.deleteById(id);
  }
}
