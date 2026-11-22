package dk.via.sep3.ttot.controller;

import dk.via.sep3.ttot.dto.ItemDto;
import dk.via.sep3.ttot.model.Item;
import dk.via.sep3.ttot.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/items")
@Tag(name = "Item Management", description = "APIs for managing items")
public class ItemController
{
  private final ItemService itemService;

  public ItemController(ItemService itemService)
  {
    this.itemService = itemService;
  }

  @GetMapping
  @Operation(summary = "Get all items", description = "Returns every item, ordered by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Items returned")
  })
  public List<ItemDto> getAll()
  {
    return itemService.getAll().stream().map(ItemController::toDto).toList();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get an item", description = "Returns the item with the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item found"),
      @ApiResponse(responseCode = "404", description = "No item with that id")
  })
  public ResponseEntity<ItemDto> getById(@PathVariable int id)
  {
    return itemService.getById(id)
        .map(item -> ResponseEntity.ok(toDto(item)))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @Operation(summary = "Create an item", description = "Creates a new item. The id is ignored and upload date defaults to today")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Item created")
  })
  public ResponseEntity<ItemDto> create(@RequestBody ItemDto dto)
  {
    Item item = new Item(dto.name(), dto.description(), dto.categoryId(), dto.uploadDate(),
        dto.location(), dto.latitude(), dto.longitude(), dto.uploaderId());
    Item saved = itemService.save(item);
    return ResponseEntity.created(URI.create("/api/items/" + saved.getId())).body(toDto(saved));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an item", description = "Replaces the fields of the item with the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item updated"),
      @ApiResponse(responseCode = "404", description = "No item with that id")
  })
  public ResponseEntity<ItemDto> update(@PathVariable int id, @RequestBody ItemDto dto)
  {
    Optional<Item> existing = itemService.getById(id);
    if (existing.isEmpty())
    {
      return ResponseEntity.notFound().build();
    }

    Item item = existing.get();
    item.setName(dto.name());
    item.setDescription(dto.description());
    item.setCategoryId(dto.categoryId());
    item.setUploadDate(dto.uploadDate());
    item.setLocation(dto.location());
    item.setLatitude(dto.latitude());
    item.setLongitude(dto.longitude());
    item.setUploaderId(dto.uploaderId());
    return ResponseEntity.ok(toDto(itemService.save(item)));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an item", description = "Deletes the item with the given id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Item deleted"),
      @ApiResponse(responseCode = "404", description = "No item with that id")
  })
  public ResponseEntity<Void> delete(@PathVariable int id)
  {
    if (itemService.getById(id).isEmpty())
    {
      return ResponseEntity.notFound().build();
    }
    itemService.delete(id);
    return ResponseEntity.noContent().build();
  }

  private static ItemDto toDto(Item item)
  {
    return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getCategoryId(),
        item.getUploadDate(), item.getLocation(), item.getLatitude(), item.getLongitude(),
        item.getUploaderId());
  }
}
