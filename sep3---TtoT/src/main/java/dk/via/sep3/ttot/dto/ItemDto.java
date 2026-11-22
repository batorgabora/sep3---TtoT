package dk.via.sep3.ttot.dto;

import java.time.LocalDate;

public record ItemDto(Integer id, String name, String description, Integer categoryId,
                      LocalDate uploadDate, String location, Double latitude, Double longitude,
                      Integer uploaderId)
{
}
